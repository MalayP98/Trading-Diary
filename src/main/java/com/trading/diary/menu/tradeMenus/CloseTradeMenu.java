package com.trading.diary.menu.tradeMenus;

import com.trading.diary.helpers.Explainer;
import com.trading.diary.helpers.Target;
import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.formation_menu.support_reversal.paginationMenus.SimplePaginationMenu;
import com.trading.diary.pojo.dao.CloseTradeDTO;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.TargetStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class CloseTradeMenu extends AbstractMenu<CloseTradeDTO> {

    private final TradeService tradeService;

    private final SimplePaginationMenu<Void, Trade> tradePaginationMenu;
    
    private final Explainer explainer;

    public CloseTradeMenu(TradeService tradeService, Explainer explainer) {
        this.tradeService = tradeService;
        this.explainer = explainer;
        this.tradePaginationMenu = new SimplePaginationMenu<>(
                tradeService::countAllActiveTrade,
                (attr, page) -> tradeService.getAllOpenTrades(page),
                () -> null,
                explainer
        );
    }

    @Override
    public CloseTradeDTO showMenu() {
        Trade trade = selectTrade();
        skipLines(2);

        print("Enter average closing price.");
        float closingPrice = InputType.FLOAT.nextInput();
        skipLines(2);

        print("Enter closing date.");
        LocalDateTime closingDate = InputType.DATE.nextInput();

        print("Reason to close.\n1. Target hit.\n2. Stoploss hit.\n3. Other");
        int choice = InputType.INT.nextInput();

        switch (choice) {
            case 1:
                targetsHitOrMiss(trade.getTargets());
                break;
            case 2:
                targetsHitOrMiss(trade.getStoploss());
                break;
            default:
                break;
        }

        return new CloseTradeDTO(trade.getId(), closingPrice, closingDate, trade.getTargets(), trade.getStoploss());
    }

    private void targetsHitOrMiss(List<Target> targets) {
        for (Target target : targets) {
            targetHitOrMiss(target);
        }
    }

    private void targetHitOrMiss(Target target) {
        print("Was this target [" + target + "] hit or miss?\n1. HIT\n2. MISS");
        int choice = InputType.INT.nextInput();
        switch (choice) {
            case 1:
                target.setTargetStatus(TargetStatus.HIT);
                break;
            case 2:
                target.setTargetStatus(TargetStatus.MISS);
                break;
            default:
                print("Wrong input. Try again!");
                targetHitOrMiss(target);
                break;
        }
    }

    public Trade selectTrade() {
        print("=== Select a Trade to close ===");
        Trade trade = tradePaginationMenu.showMenu();
        if (trade == null) {
            print("No open trades found!");
        }
        return trade;
    }
}
