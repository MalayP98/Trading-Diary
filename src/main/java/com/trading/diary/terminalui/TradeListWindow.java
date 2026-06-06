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

        // Always show 1 item per page so full details fit within the terminal height.
        long total = countSupplier.getAsLong();
        int totalPages = total == 0 ? 1 : (int) (total);
        currentPage[0] = Math.max(0, Math.min(currentPage[0], totalPages - 1));

        Pageable pageable = PageRequest.of(currentPage[0], 1);
        List<T> items = pageSupplier.apply(pageable);

        if (items.isEmpty()) {
            contentPanel.addComponent(new Label("No items found."));
        } else {
            for (T item : items) {
                String displayText;
                try {
                    displayText = explainer.explain(item);
                } catch (Exception e) {
                    displayText = "(Error displaying item: " + e.getMessage() + ")";
                }
                // Always show full detail as a Label
                contentPanel.addComponent(new Label(displayText));
                // In selection mode, add a Select button below the details
                if (selectionMode) {
                    long itemId = idExtractor.apply(item);
                    contentPanel.addComponent(new Button("[ Select this trade ]", () -> {
                        selectedId.set(itemId);
                        window.close();
                    }));
                }
                contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
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
}


