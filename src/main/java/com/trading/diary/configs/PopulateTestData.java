package com.trading.diary.configs;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.UnexpectedMove;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.helpers.SMA;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.CompanyService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.utils.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Order(1)
public class PopulateTestData implements CommandLineRunner {

    @AllArgsConstructor
    @Getter
    private static class FormationContainer{
        private FormationType formationType;
        private long id;
    }

    private static final Random random = new Random();

    private final CompanyService companyService;

    private final PlannedTradeService plannedTradeService;

    private final FormationServiceFactory<Formation> formationServiceFactory;

    List<String> dummyCompanies = List.of(
            "AAPL",
            "MSFT",
            "GOOGL",
            "AMZN",
            "TSLA",
            "META",
            "NVDA",
            "JPM",
            "V",
            "DIS"
    );

    @Override
    public void run(String... args) throws Exception {
        populateCompanies();
        for(int i=0; i<30; i++){
            plannedTradeService.savePlannedTrade(getPlannedTrade());
        }
    }

    private void populateCompanies(){
        dummyCompanies.forEach(companyService::getOrCreateCompany);
    }

    private PlannedTrade getPlannedTrade(){
        PlannedTrade.PlannedTradeBuilder plannedTradeBuilder = PlannedTrade.builder()
                .timeFrame(getRandomTimeframe())
                .notes("Note " + UUID.randomUUID().toString().substring(5))
                .suggestedBy(new Person(UUID.randomUUID().toString().substring(5)))
                .marketCap(getRandomMarketCap())
                .company(new Company(dummyCompanies.get(random.nextInt(dummyCompanies.size()))));
        FormationContainer formationContainer = getRandomFormation();
        plannedTradeBuilder.formationType(formationContainer.getFormationType());
        plannedTradeBuilder.formationId(formationContainer.getId());
        return plannedTradeBuilder.build();
    }

    private MarketCap getRandomMarketCap() {
        return new MarketCap(random.nextBoolean(), random.nextBoolean());
    }

    private TimeFrame getRandomTimeframe(){
        int pick = random.nextInt(TimeFrame.values().length);
        return TimeFrame.values()[pick];
    }

    private CandleColor getRandomColor(){
        int pick = random.nextInt(CandleColor.values().length);
        return CandleColor.values()[pick];
    }

    private PricePosition getRandomPricePosition(){
        int pick = random.nextInt(PricePosition.values().length);
        return PricePosition.values()[pick];
    }

    private Strength getRandomStrength(){
        int pick = random.nextInt(Strength.values().length);
        return Strength.values()[pick];
    }

    private SMA getRandomSMA(){
        TrendlineDirections direction = TrendlineDirections.values()[random.nextInt(TrendlineDirections.values().length)];
        PricePosition pricePosition = PricePosition.values()[random.nextInt(PricePosition.values().length)];
        return new SMA(direction, pricePosition);
    }

    private FormationContainer createBullishEngulfing(){
        BullishEngulfing bullishEngulfing = BullishEngulfing.builder()
                .partialBottomEngulfing(random.nextBoolean())
                .partialTopEngulfing(random.nextBoolean())
                .pricePositionOnSupport(getRandomPricePosition())
                .priceSustained(random.nextBoolean())
                .retest(random.nextBoolean())
                .supportLength(random.nextInt(1000))
                .volume(getRandomStrength())
                .build();
        bullishEngulfing = formationServiceFactory
                .getFormationService(FormationType.BULLISH_ENGULFING)
                .save(bullishEngulfing);
        return new FormationContainer(bullishEngulfing.getFormation(), bullishEngulfing.getId());
    }

    private FormationContainer createHammer(){
        Hammer hammer = Hammer.builder()
                .pricePositionOnSupport(getRandomPricePosition())
                .priceSustained(random.nextBoolean())
                .retest(random.nextBoolean())
                .supportLength(random.nextInt(1000))
                .smallLowerWick(random.nextBoolean())
                .hammerColor(getRandomColor())
                .build();
        hammer = formationServiceFactory
                .getFormationService(FormationType.HAMMER)
                .save(hammer);
        return new FormationContainer(hammer.getFormation(), hammer.getId());
    }

    private FormationContainer createMorningStart(){
        MorningStar ms = MorningStar.builder()
                .pricePositionOnSupport(getRandomPricePosition())
                .priceSustained(random.nextBoolean())
                .retest(random.nextBoolean())
                .supportLength(random.nextInt(1000))
                .volume(getRandomStrength())
                .dogiColor(getRandomColor())
                .build();
        ms = formationServiceFactory
                .getFormationService(FormationType.MORNING_STAR)
                .save(ms)
                ;
        return new FormationContainer(ms.getFormation(), ms.getId());
    }

    private FormationContainer createHorizontalSupport(){
        HorizontalResistanceBreakout horizontalResistanceBreakout =
                HorizontalResistanceBreakout.builder()
                        .confirmBreakout(random.nextBoolean())
                        .breakoutVolume(getRandomStrength())
                        .resistanceLength(random.nextLong(1000))
                        .breakoutPercentage(random.nextFloat(30))
                        .allTimeHigh(random.nextBoolean())
                        .higherLows(random.nextBoolean())
                        .rsi(random.nextFloat(100))
                        .sma20(getRandomSMA())
                        .sma50(getRandomSMA())
                        .sma200(getRandomSMA())
                        .build();
        horizontalResistanceBreakout = formationServiceFactory
                .getFormationService(FormationType.HORIZONTAL_RESISTANCE_BREAKOUT)
                .save(horizontalResistanceBreakout)
                ;
        return new FormationContainer(horizontalResistanceBreakout.getFormation(), horizontalResistanceBreakout.getId());
    }

    private FormationContainer createFallingSupport(){
        FallingResistanceBreakout horizontalResistanceBreakout =
                FallingResistanceBreakout.builder()
                        .confirmBreakout(random.nextBoolean())
                        .breakoutVolume(getRandomStrength())
                        .resistanceLength(random.nextLong(1000))
                        .breakoutPercentage(random.nextFloat(30))
                        .allTimeHigh(random.nextBoolean())
                        .higherLows(random.nextBoolean())
                        .rsi(random.nextFloat(100))
                        .sma20(getRandomSMA())
                        .sma50(getRandomSMA())
                        .sma200(getRandomSMA())
                        .priorUptrend(random.nextBoolean())
                        .priceDiffPercentage(random.nextFloat(100))
                        .build();
        horizontalResistanceBreakout = formationServiceFactory
                .getFormationService(FormationType.FALLING_RESISTANCE_BREAKOUT)
                .save(horizontalResistanceBreakout)
                ;
        return new FormationContainer(horizontalResistanceBreakout.getFormation(), horizontalResistanceBreakout.getId());
    }

    private FormationContainer createUnexpectedMove(){
        UnexpectedMove unexpectedMove = UnexpectedMove.builder()
                .percentageMove(random.nextFloat(100))
                .days(random.nextInt(20))
                .build();
        unexpectedMove = formationServiceFactory
                .getFormationService(FormationType.UNEXPECTED_MOVE)
                .save(unexpectedMove)
                ;
        return new FormationContainer(unexpectedMove.getFormation(), unexpectedMove.getId());
    }

    private FormationContainer getRandomFormation(){
        FormationType formationType = FormationType.values()[random.nextInt(FormationType.values().length)];
        switch (formationType){
            case BULLISH_ENGULFING -> {
                return createBullishEngulfing();
            }
            case HAMMER -> {
                return createHammer();
            }
            case MORNING_STAR -> {
                return createMorningStart();
            }
            case HORIZONTAL_RESISTANCE_BREAKOUT -> {
                return createHorizontalSupport();
            }
            case FALLING_RESISTANCE_BREAKOUT -> {
                return createFallingSupport();
            }
            case UNEXPECTED_MOVE -> {
                return createUnexpectedMove();
            }
            default -> {
                return null;
            }
        }
    }
}
