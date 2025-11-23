package com.trading.diary.services;

import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.dao.PlannedTradeConversionDTO;
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

    public Trade confirmPlannedTrade(PlannedTradeConversionDTO plannedTradeConversionDTO) {
        if(plannedTradeConversionDTO.getTrade() == null || plannedTradeConversionDTO.getPlannedTrade() == null){
            throw new RuntimeException("Either trade or planned trade to be converted is not present!");
        }
        PlannedTrade plannedTrade = plannedTradeConversionDTO.getPlannedTrade();
        // removing IDs so that a fresh entry is created
        plannedTrade.getTargets().forEach(tgt -> tgt.setId(0));
        plannedTrade.getStoploss().forEach(sl -> sl.setId(0));
        Trade trade = Trade.builder()
                .averageBuyingPrice(plannedTradeConversionDTO.getTrade().getAverageBuyingPrice())
                .shares(plannedTradeConversionDTO.getTrade().getShares())
                .openingDate(plannedTradeConversionDTO.getTrade().getOpeningDate())
                .buildWithPlannedTrade(plannedTradeConversionDTO.getPlannedTrade());
        deletePlannedTrade(plannedTradeConversionDTO.getPlannedTrade().getId());
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
}
