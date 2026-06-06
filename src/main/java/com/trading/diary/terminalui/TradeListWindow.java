package com.trading.diary.terminalui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.trading.diary.explainers.Explainer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
     * @param countSupplier Supplies total item count (used for page boundary checks)
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
        BasicWindow window = new BasicWindow(title);

        buildPage(window, title, countSupplier, pageSupplier, explainer,
                idExtractor, selectionMode, selectedId, currentPage);

        navigator.show(window);

        return selectedId.get() == -1 ? null : selectedId.get();
    }

    private <T> void buildPage(BasicWindow window,
                                String title,
                                LongSupplier countSupplier,
                                Function<Pageable, List<T>> pageSupplier,
                                Explainer<T> explainer,
                                Function<T, Long> idExtractor,
                                boolean selectionMode,
                                AtomicLong selectedId,
                                int[] currentPage) {
        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("=== " + title + " ==="));
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        long total = countSupplier.getAsLong();
        int totalPages = total == 0 ? 1 : (int) Math.ceil((double) total / pageSize);
        currentPage[0] = Math.max(0, Math.min(currentPage[0], totalPages - 1));

        Pageable pageable = PageRequest.of(currentPage[0], pageSize);
        List<T> items = pageSupplier.apply(pageable);

        if (items.isEmpty()) {
            panel.addComponent(new Label("No items found."));
        } else {
            for (T item : items) {
                String displayText = explainer.explain(item);
                if (selectionMode) {
                    long itemId = idExtractor.apply(item);
                    panel.addComponent(new Button(displayText, () -> {
                        selectedId.set(itemId);
                        window.close();
                    }));
                } else {
                    panel.addComponent(new Label(displayText));
                }
                panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            }
        }

        panel.addComponent(new Label("Page " + (currentPage[0] + 1) + " of " + totalPages));
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        Panel navPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

        if (currentPage[0] > 0) {
            navPanel.addComponent(new Button("< Previous", () -> {
                currentPage[0]--;
                buildPage(window, title, countSupplier, pageSupplier, explainer,
                        idExtractor, selectionMode, selectedId, currentPage);
            }));
        }

        if (currentPage[0] < totalPages - 1) {
            navPanel.addComponent(new Button("Next >", () -> {
                currentPage[0]++;
                buildPage(window, title, countSupplier, pageSupplier, explainer,
                        idExtractor, selectionMode, selectedId, currentPage);
            }));
        }

        navPanel.addComponent(new Button("Close", window::close));
        panel.addComponent(navPanel);

        window.setComponent(panel);
    }
}

