# Trading Diary

A terminal-based trading journal built with **Spring Boot** and **Lanterna**. Log, plan, track, and score your stock trades — all from the command line, backed by a PostgreSQL database.

---

## Features

- **Log trades** — record entries with formation type, SMA data, targets, and market cap
- **Plan trades** — save a planned trade before it triggers, then confirm it when it does
- **Close trades** — mark open trades as closed with outcome details
- **Update trades** — edit any field on an existing trade
- **View open trades** — paginated list of all active positions
- **View all trades** — combined list of regular + planned trades with virtual-scroll detail popup
- **Formation scoring** — every trade is automatically scored 0–100 based on its chart formation

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.7 |
| UI | Lanterna 3.1.2 (terminal TUI) |
| Database | PostgreSQL (production), H2 (runtime/dev) |
| ORM | Spring Data JPA / Hibernate |
| Build | Maven |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.x
- PostgreSQL running locally

### Database setup

```sql
CREATE DATABASE trading;
CREATE USER admin WITH PASSWORD 'secret';
GRANT ALL PRIVILEGES ON DATABASE trading TO admin;
```

### Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/trading
spring.datasource.username=admin
spring.datasource.password=secret
```

### Run

```bash
./mvnw spring-boot:run
```

The Lanterna TUI launches in your terminal automatically on startup.

---

## Navigation

```
Trading Journal
├── 1. Log Trade
├── 2. Plan Trade
├── 3. Confirm Planned Trade
├── 4. Close Trade
├── 5. View Open Trades
├── 6. View All Trades
├── 7. Update Trade
└── Exit
```

Use arrow keys and Enter to navigate. All list views are paginated (page size configurable via `trade.page.size` in `application.properties`, default: 5). Click any trade in a list to see its full detail popup.

---

## Formation Scoring

Every trade carries a **formation type**. The system automatically computes a **0–100 score** that reflects how textbook-quality the setup is. Higher scores mean more confluence of positive signals; lower scores flag weak or incomplete setups.

---

### Architecture — Chain of Responsibility

Each formation type is wired to a **chain of evaluator nodes** at startup (via `EvaluatorConfigs`). Each node scores exactly one criterion and delegates to the next node in the chain. The `FormationEvaluationFacade` is the single entry point:

```
FormationEvaluationFacade
  └── FormationEvaluatorFactory  (looks up chain by FormationType)
        └── Evaluator₁ → Evaluator₂ → ... → EvaluatorN → null
```

**Final score formula:**
```
finalScore = (Σ rawScore across all nodes / Σ weightage across all nodes) × 100
```

Raw scores never leave their evaluator — the normalization happens once at the top, giving a clean 0–100 result regardless of how many evaluators are in the chain.

---

### Score Interpretation

| Score | Signal Quality |
|---|---|
| **80 – 100** | Strong setup — high confluence, fits all key criteria |
| **60 – 79** | Good setup — most criteria met, minor gaps |
| **40 – 59** | Moderate — some important criteria missing |
| **20 – 39** | Weak — significant criteria absent |
| **0 – 19** | Very weak / invalid setup |

---

### Sigmoid Scaling (Length-based Criteria)

Support and resistance **length** use a sigmoid (S-curve) instead of a linear scale. This avoids punishing moderately long levels while still rewarding very long ones.

```
score(x) = asymptote / (1 + e^(-smoothness × (x − center)))
```

The `center` is set so that the **minimum viable length** (per timeframe, per the trading rules) already scores **~60% of the maximum weight**. Lengths well beyond the minimum asymptotically approach the full weight.

**Support length sigmoid centers (days):**

| Timeframe | Rule from notes | Sigmoid center | Meaning |
|---|---|---|---|
| Daily | 1M – 1.5Y | **30 days** | 30-day support → ~60% max weight |
| Weekly | 5Y – 6Y | **1825 days** | 5Y support → ~60% max weight |
| Monthly | 5Y+ | **1825 days** | 5Y support → ~60% max weight |

**Resistance length sigmoid centers (days):**

| Timeframe | Rule from notes | Sigmoid center | Meaning |
|---|---|---|---|
| Daily | 1M – 1.5Y | **30 days** | 30-day resistance → ~60% max weight |
| Weekly | 1Y – 5Y | **365 days** | 1Y resistance → ~60% max weight |
| Monthly | 6Y+ | **2190 days** | 6Y resistance → ~60% max weight |

---

### Supported Formations

#### 1. Support Reversals

These formations signal a bullish reversal at a support level. All three share a **common base evaluator chain**, with a pattern-specific evaluator prepended that scores the candlestick pattern itself.

##### Common Base Chain — All Support Reversals

| # | Evaluator | Weight | Criterion | Scoring Detail |
|---|---|---|---|---|
| 1 | `SupportLengthFormationEvaluator` | **50** | Length of the support zone | Sigmoid per timeframe (see table above). Longer support = more significant level |
| 2 | `PriceSustainedFormationEvaluator` | **15** | Price held above support after reversal | `true` = 15pts, `false` = 0 |
| 3 | `PricePositionOnSupportFormationEvaluator` | **5** | Where price is relative to support | `ON`=5, `THROUGH`=2.5, `ABOVE`/`BELOW`=0 |
| 4 | `RetestEvaluatorFormation` | **15** | Support was retested before entry | `true` = 15pts, `false` = 0 |
| | **Base total** | **85** | | |

> **Note on price position:** `ON` = price touching support from above (ideal); `THROUGH` = wick pierced support but closed above (still acceptable); `ABOVE` = price hasn't reached support (too early); `BELOW` = support already broken (invalid).

---

##### Hammer

A single candle with a small real body and a long lower wick, rejecting a support level.

**Rules (from notes):** Lower wick must be ≥ 2× the real body. Buy above the hammer's high only after price sustains for 15 min on the next day. Stop loss = min(hammer's low, support).

| # | Evaluator | Weight | Criterion | Scoring Detail |
|---|---|---|---|---|
| 0 | `HammerFormationEvaluator` | **30** | Wick length + candle color | See below |
| 1–4 | Base chain | **85** | Support quality | See table above |
| | **Total** | **115** | | |

**Hammer-specific breakdown (weight = 30):**

| Sub-criterion | Weight | Scoring |
|---|---|---|
| Lower wick length | 20 | Full wick (≥2× real body) = 20pts; small wick = 0 |
| Candle color | 10 | Green = 10pts (bullish close); Red = 5pts (bearish but still valid) |

---

##### Bullish Engulfing

A large bullish green candle that engulfs the prior red candle at support, on above-average volume.

**Rules (from notes):** Best applied on weekly/monthly timeframe for top 100–200 stocks. Buy above the engulfing candle's high only after price sustains. If engulfing's low breaks → fake engulfing, do not buy even if high is later crossed.

| # | Evaluator | Weight | Criterion | Scoring Detail |
|---|---|---|---|---|
| 0 | `BullishEngulfingFormationEvaluator` | **30** | Engulfing extent + volume | See below |
| 1–4 | Base chain | **85** | Support quality | See table above |
| | **Total** | **115** | | |

**Bullish Engulfing-specific breakdown (weight = 30):**

| Sub-criterion | Weight | Scoring |
|---|---|---|
| Engulfing extent | 15 | Full engulf = 15pts; one-side partial (top XOR bottom) = 7.5pts; no engulf = 0 |
| Volume on engulfing candle | 15 | `VERY_STRONG`=15, `STRONG`=9, `NORMAL`=6, `WEAK`=3, `VERY_WEAK`=0 |

---

##### Morning Star

A 3-candle reversal: large red candle → small doji (indecision) → large green candle that closes above the midpoint of the first red candle.

**Rules (from notes):** Buy at the high of the green candle. Stop loss = lowest low among all three candles. A green doji is more bullish than a red doji.

| # | Evaluator | Weight | Criterion | Scoring Detail |
|---|---|---|---|---|
| 0 | `MorningStarFormationEvaluator` | **30** | Volume + doji color | See below |
| 1–4 | Base chain | **85** | Support quality | See table above |
| | **Total** | **115** | | |

**Morning Star-specific breakdown (weight = 30):**

| Sub-criterion | Weight | Scoring |
|---|---|---|
| Volume on green candle | 25 | `VERY_STRONG`=25, `STRONG`=15, `NORMAL`=10, `WEAK`=5, `VERY_WEAK`=0 |
| Doji candle color | 5 | Green = 5pts (slight bullish lean); Red = 2.5pts (slight bearish lean) |

---

#### 2. Resistance Breakouts

These formations signal a bullish breakout through a resistance level. Both types share a **common base chain of 7 evaluators**, with a formation-specific evaluator prepended.

##### Common Base Chain — All Resistance Breakouts

| # | Evaluator | Weight | Criterion | Scoring Detail |
|---|---|---|---|---|
| 1 | `ConfirmBreakoutEvaluatorFormation` | **20** | Candle closed above resistance | `true` = 20pts, `false` = 0 |
| 2 | `BreakoutVolumeEvaluatorFormation` | **20** | Volume on breakout candle | `VERY_STRONG`=20, `STRONG`=12, `NORMAL`=8, `WEAK`=4, `VERY_WEAK`=0 |
| 3 | `ResistanceLengthEvaluatorFormation` | **15** | Duration of the resistance zone | Sigmoid per timeframe (see table above) |
| 4 | `TouchesEvaluatorFormation` | **10** | Number of times price touched resistance | ≥4 touches=10, 3=7, 2=4 **(only if ATH, else 0)** |
| 5 | `SMAAlignmentEvaluatorFormation` | **15** | Price position relative to SMAs | Weighted per SMA period; see detail below |
| 6 | `TrendContextEvaluatorFormation` | **10** | Prior trend context flags | Additive; see detail below |
| 7 | `BreakoutQualityEvaluatorFormation` | **10** | RSI + breakout close % above resistance | See detail below |
| | **Base total** | **100** | | |

> **Touches rule:** Per trading notes, a minimum of 3 touches is required. 2 touches is only acceptable when the stock is at an **all-time high** — in that case overhead supply is absent, making 2 touches a valid setup.

---

**SMA Alignment detail (weight = 15):**

Each SMA is scored as: `weight × positionScore × directionMultiplier`

| SMA | Weight | Price Position Score | Direction Multiplier |
|---|---|---|---|
| SMA 20 | 3 | `ABOVE`=1.0, `ON`=0.6, `THROUGH`=0.5, `BELOW`=0 | `RISING`=1.0, `HORIZONTAL`=0.7, `FALLING`=0.3 |
| SMA 50 | 5 | Same as above | Same as above |
| SMA 200 | 7 | Same as above | Same as above |

> **Rule from notes (Session 15):** At the time of breakout, price should be above all SMAs. "If price is below SMA 200, 60% of the time the breakout may fail." The higher weight on SMA 200 (7 vs 3 for SMA 20) reflects this importance.

---

**Trend Context detail (weight = 10):**

| Flag | Points | Rationale |
|---|---|---|
| Higher lows before breakout | +4 | Shows accumulation; buyers willing to pay more each time |
| Prior uptrend | +4 | Breakout is a continuation, not a fight against the main trend |
| All-time high breakout | +2 | Removes all overhead supply — very bullish |

---

**Breakout Quality detail (weight = 10):**

| Sub-criterion | Weight | Scoring |
|---|---|---|
| RSI at breakout | 5 | 65–80 = full (ideal momentum); 50–64 = 2.5pts (below ideal); 80–82 = 1.5pts (overbought warning); ≥83 or <50 = 0 (do not buy per notes) |
| Close % above resistance | 5 | >7% = 5pts; ≥3% = 4pts; ≥1% = 2.5pts; <1% = 1pt |

> **RSI rules from notes (Session 17):** "At the time of B/O, RSI should be above 65." "If RSI is above 83–85, do not buy."

---

##### Horizontal Resistance Breakout (MOTHER)

A flat resistance line with multiple price rejections at the same level, eventually broken with a bullish candle and volume. No additional fields beyond the base — entirely scored by the common chain.

**Rules from notes:** Min 3 touches (2 if ATH). Volume 2–4× average. Buying range: Daily 1–4%, Weekly 7–8%, Monthly 12–15%.

| # | Evaluator | Weight | Criterion |
|---|---|---|---|
| 0 | `HorizontalResistanceBreakoutEvaluatorFormation` | **0** | *(no extra criteria)* |
| 1–7 | Base chain | **100** | See common chain above |
| | **Total** | **100** | |

---

##### Falling Resistance Breakout (FATHER)

A downward-sloping resistance trendline (connecting lower swing highs) that is broken. Requires a **prior uptrend** — without it, the setup is considered invalid per the trading rules.

**Rules from notes:** Prior uptrend mandatory. Trendline must NOT be too steep (max "4 o'clock" = ~30°). Min 3 touches (2 if ATH). Higher lows before breakout.

| # | Evaluator | Weight | Criterion |
|---|---|---|---|
| 0 | `FallingResistanceBreakoutEvaluatorFormation` | **50** | Prior uptrend (mandatory) + angle + price compression |
| 1–7 | Base chain | **100** | See common chain above |
| | **Total** | **150** | |

**FATHER-specific breakdown (weight = 50):**

| Sub-criterion | Weight | Scoring | Rationale |
|---|---|---|---|
| Prior uptrend | **30** | `true` = 30pts, `false` = **0** (mandatory) | Notes: "For FATHER uptrend is mandatory but for MOTHER it is not" |
| Trendline angle | **10** | <5° = 2pts (too flat); 5–20° = 10pts (ideal); 20–30° = 5pts (steep but acceptable) | Notes: "not very steep, max 4 o'clock (~30°)" |
| Price compression % | **10** | Sigmoid — larger drop along falling line before breakout = deeper reversal potential | Center at 15% drop = ~60% of max weight |

> **Angle rule:** 0° would be a horizontal line (not falling), 30° is the "4 o'clock" maximum. The sweet spot is 5°–20° — a clearly falling line without being alarmingly steep.

---

### Formation Score Summary

| Formation | Total Weight | Key Differentiators |
|---|---|---|
| Hammer | 115 | Wick length is pass/fail (no wick = 0 pts on 20-weight criterion) |
| Bullish Engulfing | 115 | Volume on engulfing candle heavily weighted |
| Morning Star | 115 | Volume is the dominant specific criterion (25 of 30) |
| Horizontal Breakout | 100 | Entirely determined by base chain |
| Falling Breakout | 150 | Prior uptrend is mandatory (30-weight, all-or-nothing) |

---

## Project Structure

```
src/main/java/com/trading/diary/
├── configs/            # Spring config, Lanterna setup, shutdown manager
├── explainers/         # Human-readable descriptions of trades and formations
├── formations/
│   ├── impls/          # Formation POJOs (ResistanceBreakout, SupportReversal, etc.)
│   ├── score/          # Evaluation chain — all scoring logic lives here
│   │   ├── config/     # EvaluatorConfigs — wires chains together as Spring beans
│   │   ├── resistance/ # Resistance breakout evaluators (general + extensions)
│   │   └── support/    # Support reversal evaluators (general + extensions)
│   └── visitor/        # Visitor pattern for formation display
├── helpers/            # SMA, Target domain helpers
├── pojo/               # JPA entities (Company, Person, etc.)
├── repositories/       # Spring Data repositories
├── scale/              # Sigmoid + SteepGrowthCenterCalculator
├── services/           # Business logic layer
├── terminalui/         # All Lanterna windows and components
│   └── components/     # Reusable UI panels (SMAPanel, MarketCapPanel)
├── trade/              # Trade + PlannedTrade domain models
└── utils/              # Enums (Strength, TimeFrame, PricePosition, etc.)
```

---

## Configuration Reference

| Property | Default | Description |
|---|---|---|
| `trade.page.size` | `5` | Number of trades shown per page in list views |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/trading` | Database URL |
| `spring.jpa.hibernate.ddl-auto` | `update` | Schema management strategy |
| `logging.file.name` | `logs/app.log` | Log output file |
