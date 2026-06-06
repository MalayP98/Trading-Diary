package com.trading.diary.terminalui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.trading.diary.explainers.Explainer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.LongSupplier;

/**
 * Generic paginated list window for viewing and selecting trade items.
 * In selection mode, clicking an item returns its ID.
 * In view mode, only navigation and close buttons are available.
 */
@Component
@RequiredArgsConstructor
public class TradeListWindow {

    private final UiNavigator navigator;

    @Value("${trade.page.size:5}")
    private int pageSize;

    /**
     * Opens a paginated list window.
     *
     * @param title         Window title
     * @param countSupplier Supplies total item count
     * @param pageSupplier  Supplies items for a given Pageable
     * @param explainer     Converts an item to a display string
     * @param idExtractor   Extracts the ID from an item (used in selection mode)
     * @param selectionMode If true, clicking an item closes the window and returns its ID
     * @return Selected item ID (selection mode) or null (view mode / no selection)
     */
    public <T> Long open(String title,
                         LongSupplier countSupplier,
                         Function<Pageable, List<T>> pageSupplier,
                         Explainer<T> explainer,
                         Function<T, Long> idExtractor,
                         boolean selectionMode) {

        AtomicLong selectedId = new AtomicLong(-1);
        int[] currentPage = {0};

        // Outer panel uses BorderLayout: content in CENTER, nav always pinned to BOTTOM.
        // FULL_SCREEN hint ensures the window fills the terminal so BOTTOM is always visible.
        Panel outerPanel = new Panel(new BorderLayout());
        Panel contentPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        Panel navPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

        outerPanel.addComponent(contentPanel, BorderLayout.Location.CENTER);
        outerPanel.addComponent(navPanel, BorderLayout.Location.BOTTOM);

        BasicWindow window = new BasicWindow(title);
        window.setHints(Collections.singleton(com.googlecode.lanterna.gui2.Window.Hint.FULL_SCREEN));
        window.setComponent(outerPanel);

        populatePage(contentPanel, navPanel, window, title, countSupplier, pageSupplier,
                explainer, idExtractor, selectionMode, selectedId, currentPage);

        navigator.show(window);

        return selectedId.get() == -1 ? null : selectedId.get();
    }

    private <T> void populatePage(Panel contentPanel,
                                   Panel navPanel,
                                   BasicWindow window,
                                   String title,
                                   LongSupplier countSupplier,
                                   Function<Pageable, List<T>> pageSupplier,
                                   Explainer<T> explainer,
                                   Function<T, Long> idExtractor,
                                   boolean selectionMode,
                                   AtomicLong selectedId,
                                   int[] currentPage) {
        contentPanel.removeAllComponents();
        navPanel.removeAllComponents();

        contentPanel.addComponent(new Label("=== " + title + " ==="));
        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        // Show configured page size with full details. FULL_SCREEN + BorderLayout keeps
        // nav buttons always visible at bottom even if content overflows center area.
        long total = countSupplier.getAsLong();
        int totalPages = total == 0 ? 1 : (int) Math.ceil((double) total / pageSize);
        currentPage[0] = Math.max(0, Math.min(currentPage[0], totalPages - 1));

        Pageable pageable = PageRequest.of(currentPage[0], pageSize);
        List<T> items = pageSupplier.apply(pageable);

        if (items.isEmpty()) {
            contentPanel.addComponent(new Label("No items found."));
        } else {
            for (T item : items) {
                String summary;
                try {
                    summary = explainer.summarize(item);
                } catch (Exception e) {
                    summary = "(Error: " + e.getMessage() + ")";
                }
                if (selectionMode) {
                    long itemId = idExtractor.apply(item);
                    contentPanel.addComponent(new Button(summary, () -> {
                        selectedId.set(itemId);
                        window.close();
                    }));
                } else {
                    // View mode: clicking opens a full-detail popup
                    String finalSummary = summary;
                    contentPanel.addComponent(new Button(summary, () -> {
                        try {
                            showDetailPopup(finalSummary, explainer.explain(item));
                        } catch (Exception e) {
                            showDetailPopup(finalSummary, "(Error loading details: " + e.getMessage() + ")");
                        }
                    }));
                }
            }
        }

        contentPanel.addComponent(new Label("Page " + (currentPage[0] + 1) + " of " + totalPages));

        // Nav buttons always visible at the bottom via BorderLayout.BOTTOM
        if (currentPage[0] > 0) {
            navPanel.addComponent(new Button("< Previous", () -> {
                currentPage[0]--;
                populatePage(contentPanel, navPanel, window, title, countSupplier, pageSupplier,
                        explainer, idExtractor, selectionMode, selectedId, currentPage);
            }));
        }

        if (currentPage[0] < totalPages - 1) {
            navPanel.addComponent(new Button("Next >", () -> {
                currentPage[0]++;
                populatePage(contentPanel, navPanel, window, title, countSupplier, pageSupplier,
                        explainer, idExtractor, selectionMode, selectedId, currentPage);
            }));
        }

        navPanel.addComponent(new Button("Close", window::close));
    }

    private void showDetailPopup(String title, String detail) {
        List<String> lines = detail.lines().collect(java.util.stream.Collectors.toList());
        int[] scrollOffset = {0};

        int terminalRows;
        try {
            terminalRows = navigator.getGui().getScreen().getTerminalSize().getRows();
        } catch (Exception e) {
            terminalRows = 24;
        }
        // Reserve rows for the nav bar + scroll indicator at bottom
        int visibleLines = Math.max(5, terminalRows - 4);

        BasicWindow popup = new BasicWindow(title);
        popup.setHints(Collections.singleton(com.googlecode.lanterna.gui2.Window.Hint.FULL_SCREEN));

        Panel outer = new Panel(new BorderLayout());
        Panel content = new Panel(new LinearLayout(Direction.VERTICAL));
        Panel nav = new Panel(new LinearLayout(Direction.HORIZONTAL));

        outer.addComponent(content, BorderLayout.Location.CENTER);
        outer.addComponent(nav, BorderLayout.Location.BOTTOM);
        popup.setComponent(outer);

        renderScrollView(content, nav, popup, lines, scrollOffset, visibleLines);

        navigator.showOnTop(popup);
    }

    private void renderScrollView(Panel content, Panel nav, BasicWindow popup,
                                  List<String> lines, int[] scrollOffset, int visibleLines) {
        content.removeAllComponents();
        nav.removeAllComponents();

        int total = lines.size();
        int from = scrollOffset[0];
        int to = Math.min(from + visibleLines, total);

        for (int i = from; i < to; i++) {
            content.addComponent(new Label(lines.get(i)));
        }
        content.addComponent(new Label("─── " + (from + 1) + "-" + to + " / " + total + " lines ───"));

        if (scrollOffset[0] > 0) {
            nav.addComponent(new Button("↑ Up", () -> {
                scrollOffset[0] = Math.max(0, scrollOffset[0] - visibleLines);
                renderScrollView(content, nav, popup, lines, scrollOffset, visibleLines);
            }));
        }
        if (to < total) {
            nav.addComponent(new Button("↓ Down", () -> {
                scrollOffset[0] = Math.min(total - 1, scrollOffset[0] + visibleLines);
                renderScrollView(content, nav, popup, lines, scrollOffset, visibleLines);
            }));
        }
        nav.addComponent(new Button("Close", popup::close));
    }
}

