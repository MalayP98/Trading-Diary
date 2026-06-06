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

@Service
@RequiredArgsConstructor
@Transactional
public class PlannedTradeService {

    private final PlannedTradeRepository plannedTradeRepository;

    private final TradeService tradeService;

    private final CompanyService companyService;

    private final PersonService personService;

    public PlannedTrade savePlannedTrade(PlannedTrade plannedTrade) {
        plannedTrade.setSuggestedBy(personService.getOrCreatePerson(plannedTrade.getSuggestedBy().getName()));
        plannedTrade.setCompany(companyService.getOrCreateCompany(plannedTrade.getCompany().toString()));
        return plannedTradeRepository.save(plannedTrade);
    }

    public void deletePlannedTrade(long plannedTradeId) {
        plannedTradeRepository.deleteById(plannedTradeId);
    }

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
}
