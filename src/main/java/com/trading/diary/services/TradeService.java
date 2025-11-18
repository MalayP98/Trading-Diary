package com.trading.diary.services;

import com.trading.diary.pojo.dao.CloseTradeDAO;
import com.trading.diary.repositories.trade.TradeRepository;
import com.trading.diary.trade.impls.LongTrade;
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

    public LongTrade addTrade(LongTrade longTrade){
        return tradeRepository.save(longTrade);
    }

    public List<LongTrade> getAllActiveTrades(){
        return tradeRepository.findAllByIsDeletedFalseAndState(TradeState.OPEN);
    }

    public LongTrade closeTrade(CloseTradeDAO closeTradeDAO){
        LongTrade longTrade = tradeRepository.findById(closeTradeDAO.getTradeId())
                .orElseThrow(() -> new IllegalArgumentException("Trade not found!"));
        longTrade.close(closeTradeDAO.getClosingPrice(), closeTradeDAO.getClosingDate());
        return tradeRepository.save(longTrade);
    }
}
