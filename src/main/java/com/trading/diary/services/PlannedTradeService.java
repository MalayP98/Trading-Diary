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

    public PlannedTrade savePlannedTrade(PlannedTrade plannedTrade) {
        return plannedTradeRepository.save(plannedTrade);
    }

    public void deletePlannedTrade(long plannedTradeId) {
        plannedTradeRepository.deleteById(plannedTradeId);
    }

    public Trade confirmPlannedTrade(PlannedTradeConfirmationDTO plannedTradeConfirmationDTO) {
        if(plannedTradeConfirmationDTO.getTrade() == null || plannedTradeConfirmationDTO.getPlannedTrade() == null){
            throw new RuntimeException("Either trade or planned trade to be converted is not present!");
        }
        PlannedTrade plannedTrade = plannedTradeConfirmationDTO.getPlannedTrade();
        // removing IDs so that a fresh entry is created
        plannedTrade.getTargets().forEach(tgt -> tgt.setId(0));
        plannedTrade.getStoploss().forEach(sl -> sl.setId(0));
        Trade trade = Trade.builder()
                .averageBuyingPrice(plannedTradeConfirmationDTO.getTrade().getAverageBuyingPrice())
                .shares(plannedTradeConfirmationDTO.getTrade().getShares())
                .openingDate(plannedTradeConfirmationDTO.getTrade().getOpeningDate())
                .buildWithPlannedTrade(plannedTradeConfirmationDTO.getPlannedTrade());
        deletePlannedTrade(plannedTradeConfirmationDTO.getPlannedTrade().getId());
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
