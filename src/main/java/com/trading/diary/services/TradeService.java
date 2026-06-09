package com.trading.diary.services;

import com.trading.diary.formations.Formation;
import com.trading.diary.pojo.dto.CloseTradeDTO;
import com.trading.diary.repositories.trade.TradeRepository;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.emums.TradeState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service for creating, updating, listing, and closing live trades while keeping referenced companies and people normalized.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;

    private final FormationServiceFactory<? extends Formation> formationServiceFactory;

    private final CompanyService companyService;

    private final PersonService personService;

    /**
     * Normalizes related company and person references before persisting a live trade.
     */
    public Trade addTrade(Trade trade){
        trade.setSuggestedBy(personService.getOrCreatePerson(trade.getSuggestedBy().getName()));
        trade.setCompany(companyService.getOrCreateCompany(trade.getCompany().toString()));
        return tradeRepository.save(trade);
    }

    public List<Trade> getAllOpenTrades(Pageable pageable){
        return Optional.ofNullable(tradeRepository.findAllByDeletedFalseAndState(TradeState.OPEN, pageable))
                .orElseGet(Page::empty).getContent();
    }

    public List<Trade> getAllTrades(Pageable pageable){
        return Optional.of(tradeRepository.findAllByDeletedFalse(pageable))
                .orElseGet(Page::empty).getContent();
    }

    /**
     * Closes an open trade, lets the domain model mark targets and stoplosses, and then persists the updated state.
     */
    public Trade closeTrade(CloseTradeDTO closeTradeDTO) {
        Trade trade = tradeRepository.findById(closeTradeDTO.getTradeId())
                .orElseThrow(() -> new IllegalArgumentException("Trade not found!"));
        trade.close(closeTradeDTO);
        return tradeRepository.save(trade);
    }

    public long countAllTrade(){
        return tradeRepository.countByDeletedFalse();
    }

    public long countAllActiveTrade(){
        return tradeRepository.countByDeletedFalseAndState(TradeState.OPEN);
    }

    /**
     * Updates an open trade in place, optionally replacing share and average-price data and appending new notes.
     */
    public Trade updateTrade(long tradeId, int shares, float averageBuyingPrice, String notes) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found!"));
        if (!TradeState.OPEN.equals(trade.getState())) {
            throw new IllegalStateException("Only open trades can be updated");
        }
        if (shares > 0) {
            trade.addShare(shares, averageBuyingPrice);
        }
        if (notes != null && !notes.isBlank()) {
            trade.addNotes(notes);
        }
        return tradeRepository.save(trade);
    }
}
