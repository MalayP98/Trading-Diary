package com.trading.diary.services;

import com.trading.diary.formations.Formation;
import com.trading.diary.pojo.dto.CloseTradeDTO;
import com.trading.diary.repositories.trade.TradeRepository;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.TradeState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;

    private final FormationServiceFactory<? extends Formation> formationServiceFactory;

    public Trade addTrade(Trade trade){
        return tradeRepository.save(trade);
    }

    public List<Trade> getAllOpenTrades(Pageable pageable){
        return Optional.ofNullable(tradeRepository.findAllByDeletedFalseAndState(TradeState.OPEN, pageable))
                .orElseGet(Page::empty).getContent();
    }

    public Trade closeTrade(CloseTradeDTO closeTradeDTO) {
        if(closeTradeDTO.getTradeId() == 0){
            throw new RuntimeException("Invalid trade supplied for closing.");
        }
        Trade trade = tradeRepository.findById(closeTradeDTO.getTradeId())
                .orElseThrow(() -> new IllegalArgumentException("Trade not found!"));
        trade.close(closeTradeDTO.getClosingPrice(), closeTradeDTO.getClosingDate(),
                closeTradeDTO.getTargets(), closeTradeDTO.getStoplosses());
        return tradeRepository.save(trade);
    }

    public long countAllActiveTrade(){
        return tradeRepository.countByDeletedFalseAndState(TradeState.OPEN);
    }
}
