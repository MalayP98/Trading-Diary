package com.trading.diary.services;

import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.dto.PlannedTradeConfirmationDTO;
import com.trading.diary.repositories.trade.PlannedTradeRepository;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Coordinates persistence and lifecycle transitions for planned trades. It also converts a planned trade into a live trade when the setup is actually executed.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PlannedTradeService {

    private final PlannedTradeRepository plannedTradeRepository;

    private final TradeService tradeService;

    private final CompanyService companyService;

    private final PersonService personService;

    /**
     * Normalizes related company and person references before storing a planned trade.
     */
    public PlannedTrade savePlannedTrade(PlannedTrade plannedTrade) {
        plannedTrade.setSuggestedBy(personService.getOrCreatePerson(plannedTrade.getSuggestedBy().getName()));
        plannedTrade.setCompany(companyService.getOrCreateCompany(plannedTrade.getCompany().toString()));
        return plannedTradeRepository.save(plannedTrade);
    }

    public void deletePlannedTrade(long plannedTradeId) {
        plannedTradeRepository.deleteById(plannedTradeId);
    }

    /**
     * Converts a planned trade into a live trade using the execution details supplied by the UI, then removes the original plan.
     */
    public Trade confirmPlannedTrade(PlannedTradeConfirmationDTO plannedTradeConfirmationDTO) {
        PlannedTrade plannedTrade = plannedTradeRepository
                .findById(plannedTradeConfirmationDTO.getPlannedTradeId())
                .orElseThrow(() -> new RuntimeException("No planned trade found by id " + plannedTradeConfirmationDTO.getPlannedTradeId()));
        Trade trade = Trade.builder()
                .averageBuyingPrice(plannedTradeConfirmationDTO.getBuyingPrice())
                .shares(plannedTradeConfirmationDTO.getQuantity())
                .openingDate(plannedTradeConfirmationDTO.getOpeningDate())
                .buildWithPlannedTrade(plannedTrade);
        deletePlannedTrade(plannedTrade.getId());
        return tradeService.addTrade(trade);
    }

    public List<PlannedTrade> getPlannedTradeByCompany(Company company, Pageable pageable){
        return plannedTradeRepository.findAllByCompanyAndDeletedFalse(company, pageable);
    }

    public List<PlannedTrade> getAllPlannedTrade(Pageable pageable){
        return plannedTradeRepository.findAllByDeletedFalse(pageable).getContent();
    }

    public long getCount(){
        return plannedTradeRepository.countByDeletedFalse();
    }

    public long getCountByCompany(Company company){
        return plannedTradeRepository.countByCompanyAndDeletedFalse(company);
    }

    /**
     * Appends additional notes to an existing plan without disturbing the rest of the planned setup.
     */
    public PlannedTrade updatePlannedTrade(long plannedTradeId, String notes) {
        PlannedTrade plannedTrade = plannedTradeRepository.findById(plannedTradeId)
                .orElseThrow(() -> new RuntimeException("No planned trade found by id " + plannedTradeId));
        if (notes != null && !notes.isBlank()) {
            plannedTrade.addNotes(notes);
        }
        return plannedTradeRepository.save(plannedTrade);
    }
}
