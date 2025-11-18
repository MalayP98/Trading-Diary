package com.trading.diary.services;

import com.trading.diary.pojo.dao.TradeDTO;
import com.trading.diary.repositories.trade.PlannedTradeRepository;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.LongTrade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PlannedTradeService {

    private final PlannedTradeRepository plannedTradeRepository;

    private final TradeService tradeService;

    public PlannedTrade savePlannedTrade(PlannedTrade plannedTrade) {
        return plannedTradeRepository.save(plannedTrade);
    }

    public PlannedTrade deletePlannedTrade(long plannedTradeId) {
        return plannedTradeRepository.deleteById(plannedTradeId);
    }

    public LongTrade confirmPlannedTrade(TradeDTO tradeDTO, long plannedTradeId) {
        PlannedTrade plannedTrade = plannedTradeRepository.findById(plannedTradeId)
                .orElseThrow(() -> new IllegalArgumentException("Planned trade not found!"));
        return confirmPlannedTrade(tradeDTO, plannedTrade);
    }

    public LongTrade confirmPlannedTrade(TradeDTO tradeDTO, PlannedTrade plannedTrade){
        LongTrade longTrade = LongTrade.tradeWithPlannedTradeBuilder()
                .plannedTrade(plannedTrade)
                .averageBuyingPrice(tradeDTO.getAverageBuyingPrice())
                .shares(tradeDTO.getShares())
                .buyingDate(tradeDTO.getBuyingDate())
                .build();
        deletePlannedTrade(plannedTrade.getId());
        return tradeService.addTrade(longTrade);
    }
}
