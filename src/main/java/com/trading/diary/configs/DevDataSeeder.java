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
import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.pojo.dto.CloseTradeDTO;
import com.trading.diary.pojo.dto.PlannedTradeConfirmationDTO;
import com.trading.diary.services.CompanyService;
import com.trading.diary.services.PersonService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.TradeService;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.emums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Command-line runner that exercises every major trade workflow (plan, update plan,
 * confirm, log, update trade, close) using dummy data. Enable the calls inside
 * {@link #run} during local development to seed and verify the full lifecycle.
 */
@Service
@RequiredArgsConstructor
@Order(1)
public class DevDataSeeder implements CommandLineRunner {

    @AllArgsConstructor
    @Getter
    private static class FormationContainer {
        private FormationType formationType;
        private long id;
    }

    private static final Random random = new Random();

    private final CompanyService companyService;
    private final PlannedTradeService plannedTradeService;
    private final TradeService tradeService;
    private final FormationServiceFactory<Formation> formationServiceFactory;
    private final PersonService personService;

    private static final List<String> DUMMY_COMPANIES = List.of(
            "AAPL", "MSFT", "GOOGL", "AMZN", "TSLA",
            "META", "NVDA", "JPM", "V", "DIS"
    );

    /**
     * Uncomment the calls below to seed the database on startup.
     * {@code runTradeLifecycleDemo()} walks through every menu item end-to-end.
     * {@code populateBulkPlannedTrades(n)} inserts n random planned trades.
     */
    @Override
    public void run(String... args) {
        runTradeLifecycleDemo();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Full lifecycle demo — mirrors every item in the main menu
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Exercises all menu flows in sequence and prints a summary line for each step:
     * <ol>
     *   <li>Plan Trade</li>
     *   <li>Update Planned Trade (append notes)</li>
     *   <li>Confirm Planned Trade → live trade</li>
     *   <li>Log Trade (direct entry, no prior plan)</li>
     *   <li>Update Trade (adjust shares / average price)</li>
     *   <li>Close Trade</li>
     * </ol>
     */
    public void runTradeLifecycleDemo() {
        System.out.println("\n====== DevDataSeeder: trade lifecycle demo ======");

        // 1. Plan Trade
        FormationContainer formation = createHorizontalSupport();
        PlannedTrade plan = plannedTradeService.savePlannedTrade(
                buildPlannedTrade("AAPL", "Analyst1", formation));
        System.out.printf("[1] Plan Trade       → planned trade id=%d  company=%s%n",
                plan.getId(), plan.getCompany());

        // 2. Update Planned Trade
        plan = plannedTradeService.updatePlannedTrade(
                plan.getId(), "Support held on retest — setup confirmed.");
        System.out.printf("[2] Update Plan      → notes appended for id=%d%n", plan.getId());

        // 3. Confirm Planned Trade → live trade
        PlannedTradeConfirmationDTO confirmDTO = new PlannedTradeConfirmationDTO(
                plan.getId(), 150.00f, 10, LocalDateTime.now());
        Trade confirmedTrade = plannedTradeService.confirmPlannedTrade(confirmDTO);
        System.out.printf("[3] Confirm Trade    → live trade id=%d  state=%s  shares=%d  @%.2f%n",
                confirmedTrade.getId(), confirmedTrade.getState(),
                confirmedTrade.getShares(), confirmedTrade.getAverageBuyingPrice());

        // 4. Log Trade (direct — no prior plan)
        FormationContainer directFormation = createBullishEngulfing();
        Trade directTrade = tradeService.addTrade(
                buildDirectTrade("MSFT", "Analyst2", directFormation));
        System.out.printf("[4] Log Trade        → direct trade id=%d  company=%s  @%.2f%n",
                directTrade.getId(), directTrade.getCompany(),
                directTrade.getAverageBuyingPrice());

        // 5. Update Trade (add shares / new average)
        Trade updatedTrade = tradeService.updateTrade(
                confirmedTrade.getId(), 15, 145.00f, "Averaged down on intra-day dip.");
        System.out.printf("[5] Update Trade     → id=%d  shares=%d  avgPrice=%.2f%n",
                updatedTrade.getId(), updatedTrade.getShares(),
                updatedTrade.getAverageBuyingPrice());

        // 6. Close Trade
        CloseTradeDTO closeDTO = new CloseTradeDTO(
                updatedTrade.getId(), 175.00f, LocalDateTime.now());
        Trade closedTrade = tradeService.closeTrade(closeDTO);
        System.out.printf("[6] Close Trade      → id=%d  state=%s  closingPrice=%.2f%n",
                closedTrade.getId(), closedTrade.getState(),
                closedTrade.getAverageClosingPrice());

        System.out.printf("%nActive trades: %d   Total trades: %d   Planned: %d%n",
                tradeService.countAllActiveTrade(),
                tradeService.countAllTrade(),
                plannedTradeService.getCount());
        System.out.println("=================================================\n");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Bulk seed
    // ─────────────────────────────────────────────────────────────────────────

    public void populateBulkPlannedTrades(int count) {
        DUMMY_COMPANIES.forEach(companyService::getOrCreateCompany);
        for (int i = 0; i < count; i++) {
            plannedTradeService.savePlannedTrade(getRandomPlannedTrade());
        }
        System.out.printf("[Seeder] Inserted %d planned trades.%n", count);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Builders
    // ─────────────────────────────────────────────────────────────────────────

    private PlannedTrade buildPlannedTrade(String symbol, String analyst,
                                           FormationContainer formation) {
        return PlannedTrade.builder()
                .company(new Company(symbol))
                .suggestedBy(new Person(analyst))
                .formationType(formation.getFormationType())
                .formationId(formation.getId())
                .marketCap(new MarketCap(true, false))
                .timeFrame(TimeFrame.DAILY)
                .notes("Initial plan — " + symbol)
                .addTarget(Arrays.asList(Target.getTarget(160f), Target.getTarget(180f)))
                .addStoploss(List.of(Target.getStoploss(140f)))
                .build();
    }

    private Trade buildDirectTrade(String symbol, String analyst,
                                   FormationContainer formation) {
        return Trade.builder()
                .company(new Company(symbol))
                .suggestedBy(new Person(analyst))
                .formationType(formation.getFormationType())
                .formationId(formation.getId())
                .marketCap(new MarketCap(false, true))
                .timeFrame(TimeFrame.WEEKLY)
                .notes("Directly logged — " + symbol)
                .shares(5)
                .averageBuyingPrice(310.00f)
                .openingDate(LocalDateTime.now())
                .addTarget(List.of(Target.getTarget(340f)))
                .addStoploss(List.of(Target.getStoploss(290f)))
                .build();
    }

    private PlannedTrade getRandomPlannedTrade() {
        FormationContainer formation = getRandomFormation();
        return PlannedTrade.builder()
                .timeFrame(getRandomTimeframe())
                .notes("Note " + UUID.randomUUID().toString().substring(5))
                .suggestedBy(new Person(UUID.randomUUID().toString().substring(5)))
                .marketCap(new MarketCap(random.nextBoolean(), random.nextBoolean()))
                .company(new Company(DUMMY_COMPANIES.get(random.nextInt(DUMMY_COMPANIES.size()))))
                .formationType(formation.getFormationType())
                .formationId(formation.getId())
                .addTarget(Arrays.asList(Target.getTarget(100), Target.getTarget(200)))
                .addStoploss(Arrays.asList(Target.getStoploss(50), Target.getStoploss(30)))
                .build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Formation factories (fixed dummy values)
    // ─────────────────────────────────────────────────────────────────────────

    private FormationContainer createBullishEngulfing() {
        BullishEngulfing f = BullishEngulfing.builder()
                .partialBottomEngulfing(true)
                .partialTopEngulfing(false)
                .pricePositionOnSupport(PricePosition.ON)
                .timeFrame(TimeFrame.DAILY)
                .priceSustained(true)
                .retest(true)
                .supportLength(120)
                .volume(Strength.STRONG)
                .build();
        f = formationServiceFactory.getFormationService(FormationType.BULLISH_ENGULFING).save(f);
        return new FormationContainer(f.getFormation(), f.getId());
    }

    private FormationContainer createHammer() {
        Hammer f = Hammer.builder()
                .pricePositionOnSupport(PricePosition.ON)
                .priceSustained(true)
                .retest(false)
                .timeFrame(TimeFrame.DAILY)
                .supportLength(90)
                .smallLowerWick(false)
                .hammerColor(CandleColor.GREEN)
                .build();
        f = formationServiceFactory.getFormationService(FormationType.HAMMER).save(f);
        return new FormationContainer(f.getFormation(), f.getId());
    }

    private FormationContainer createMorningStar() {
        MorningStar f = MorningStar.builder()
                .pricePositionOnSupport(PricePosition.ON)
                .priceSustained(true)
                .timeFrame(TimeFrame.WEEKLY)
                .retest(false)
                .supportLength(200)
                .volume(Strength.NORMAL)
                .dogiColor(CandleColor.RED)
                .build();
        f = formationServiceFactory.getFormationService(FormationType.MORNING_STAR).save(f);
        return new FormationContainer(f.getFormation(), f.getId());
    }

    private FormationContainer createHorizontalSupport() {
        HorizontalResistanceBreakout f = HorizontalResistanceBreakout.builder()
                .confirmBreakout(true)
                .breakoutVolume(Strength.STRONG)
                .timeFrame(TimeFrame.DAILY)
                .resistanceLength(180L)
                .breakoutPercentage(3.5f)
                .allTimeHigh(false)
                .higherLows(true)
                .rsi(62.5f)
                .sma20(new SMA(TrendlineDirections.RISING, PricePosition.ABOVE))
                .sma50(new SMA(TrendlineDirections.RISING, PricePosition.ABOVE))
                .sma200(new SMA(TrendlineDirections.RISING, PricePosition.ABOVE))
                .build();
        f = formationServiceFactory.getFormationService(FormationType.HORIZONTAL_RESISTANCE_BREAKOUT).save(f);
        return new FormationContainer(f.getFormation(), f.getId());
    }

    private FormationContainer createFallingSupport() {
        FallingResistanceBreakout f = FallingResistanceBreakout.builder()
                .confirmBreakout(true)
                .breakoutVolume(Strength.NORMAL)
                .timeFrame(TimeFrame.WEEKLY)
                .resistanceLength(250L)
                .breakoutPercentage(5.0f)
                .allTimeHigh(false)
                .higherLows(true)
                .rsi(58.0f)
                .sma20(new SMA(TrendlineDirections.RISING, PricePosition.ABOVE))
                .sma50(new SMA(TrendlineDirections.RISING, PricePosition.ABOVE))
                .sma200(new SMA(TrendlineDirections.RISING, PricePosition.ABOVE))
                .priorUptrend(true)
                .priceDiffPercentage(12.0f)
                .build();
        f = formationServiceFactory.getFormationService(FormationType.FALLING_RESISTANCE_BREAKOUT).save(f);
        return new FormationContainer(f.getFormation(), f.getId());
    }

    private FormationContainer createUnexpectedMove() {
        UnexpectedMove f = UnexpectedMove.builder()
                .percentageMove(8.5f)
                .days(3)
                .timeFrame(TimeFrame.DAILY)
                .build();
        f = formationServiceFactory.getFormationService(FormationType.UNEXPECTED_MOVE).save(f);
        return new FormationContainer(f.getFormation(), f.getId());
    }

    private FormationContainer getRandomFormation() {
        FormationType type = FormationType.values()[random.nextInt(FormationType.values().length)];
        return switch (type) {
            case BULLISH_ENGULFING -> createBullishEngulfing();
            case HAMMER -> createHammer();
            case MORNING_STAR -> createMorningStar();
            case HORIZONTAL_RESISTANCE_BREAKOUT -> createHorizontalSupport();
            case FALLING_RESISTANCE_BREAKOUT -> createFallingSupport();
            case UNEXPECTED_MOVE -> createUnexpectedMove();
            default -> createHorizontalSupport();
        };
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Random helpers
    // ─────────────────────────────────────────────────────────────────────────

    private TimeFrame getRandomTimeframe() {
        return TimeFrame.values()[random.nextInt(TimeFrame.values().length)];
    }
}
