package com.trading.diary.services;

import com.trading.diary.pojo.dao.CloseTradeDTO;
import com.trading.diary.repositories.trade.TradeRepository;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.TradeState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;

    public Trade addTrade(Trade trade){
        return tradeRepository.save(trade);
    }

    public List<Trade> getAllActiveTrades(){
        return tradeRepository.findAllByDeletedFalseAndState(TradeState.OPEN);
    }

    public Trade closeTrade(CloseTradeDTO closeTradeDTO){
        Trade trade = tradeRepository.findById(closeTradeDTO.getTradeId())
                .orElseThrow(() -> new IllegalArgumentException("Trade not found!"));
        trade.close(closeTradeDTO.getClosingPrice(), closeTradeDTO.getClosingDate());
        return tradeRepository.save(trade);
    }
}
