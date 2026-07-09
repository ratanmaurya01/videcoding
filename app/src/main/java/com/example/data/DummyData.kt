package com.example.data

import com.example.data.model.Blog
import com.example.data.model.NotificationItem
import com.example.data.model.Stock

object DummyData {

    val dummyIndianStocks = listOf(
        Stock("RELIANCE", "Reliance Industries Ltd.", 2452.50, 1.25, 2465.00, 2430.10, "5.2M", "Indian"),
        Stock("TCS", "Tata Consultancy Services Ltd.", 3412.00, -0.85, 3450.00, 3401.00, "1.8M", "Indian"),
        Stock("INFY", "Infosys Ltd.", 1485.40, 2.10, 1495.00, 1452.30, "3.4M", "Indian"),
        Stock("HDFCBANK", "HDFC Bank Ltd.", 1645.00, -1.15, 1668.00, 1638.00, "7.1M", "Indian"),
        Stock("ICICIBANK", "ICICI Bank Ltd.", 945.80, 0.45, 955.00, 938.20, "4.8M", "Indian"),
        Stock("SBIN", "State Bank of India", 582.30, 1.65, 585.00, 572.10, "12.4M", "Indian"),
        Stock("BHARTIAIRTEL", "Bharti Airtel Ltd.", 875.20, -0.20, 882.00, 871.00, "2.9M", "Indian"),
        Stock("WIPRO", "Wipro Ltd.", 385.40, 3.45, 389.00, 372.00, "6.1M", "Indian"),
        Stock("KOTAKBANK", "Kotak Mahindra Bank Ltd.", 1845.00, -0.50, 1865.00, 1836.00, "1.5M", "Indian"),
        Stock("AXISBANK", "Axis Bank Ltd.", 962.00, 1.10, 968.00, 948.50, "3.2M", "Indian"),
        Stock("HINDUNILVR", "Hindustan Unilever Ltd.", 2520.00, -1.40, 2565.00, 2511.00, "1.1M", "Indian"),
        Stock("ITC", "ITC Ltd.", 445.60, 0.85, 448.50, 441.20, "15.2M", "Indian"),
        Stock("BAJFINANCE", "Bajaj Finance Ltd.", 7120.00, 1.95, 7150.00, 6980.00, "1.2M", "Indian"),
        Stock("LT", "Larsen & Toubro Ltd.", 2350.00, 0.60, 2370.00, 2335.00, "1.9M", "Indian"),
        Stock("ASIANPAINT", "Asian Paints Ltd.", 3150.00, -2.10, 3215.00, 3132.00, "0.9M", "Indian"),
        Stock("HCLTECH", "HCL Technologies Ltd.", 1142.00, 2.80, 1150.00, 1111.00, "2.2M", "Indian"),
        Stock("MARUTI", "Maruti Suzuki India Ltd.", 9210.00, 0.40, 9290.00, 9150.00, "0.4M", "Indian"),
        Stock("SUNPHARMA", "Sun Pharmaceutical Industries", 985.20, -0.30, 995.00, 980.00, "1.7M", "Indian"),
        Stock("TITAN", "Titan Company Ltd.", 2740.00, 1.75, 2765.00, 2691.00, "1.3M", "Indian"),
        Stock("ULTRACEMCO", "UltraTech Cement Ltd.", 7820.00, -0.95, 7930.00, 7780.00, "0.3M", "Indian"),
        Stock("TATAMOTORS", "Tata Motors Ltd.", 562.40, 4.20, 568.00, 539.00, "18.5M", "Indian"),
        Stock("ADANIPORTS", "Adani Ports & SEZ Ltd.", 720.50, -1.80, 735.00, 715.00, "4.2M", "Indian"),
        Stock("ADANIENT", "Adani Enterprises Ltd.", 2435.00, -3.10, 2510.00, 2411.00, "3.1M", "Indian"),
        Stock("ONGC", "Oil & Natural Gas Corp.", 165.40, 1.50, 167.00, 163.00, "8.9M", "Indian"),
        Stock("NTPC", "NTPC Ltd.", 188.30, 0.90, 190.20, 186.50, "5.6M", "Indian"),
        Stock("COALINDIA", "Coal India Ltd.", 232.15, -0.40, 234.80, 230.10, "4.1M", "Indian"),
        Stock("JSWSTEEL", "JSW Steel Ltd.", 742.00, 1.15, 748.00, 732.50, "2.0M", "Indian"),
        Stock("TATASTEEL", "Tata Steel Ltd.", 112.45, 1.85, 113.50, 110.20, "24.5M", "Indian"),
        Stock("HINDALCO", "Hindalco Industries Ltd.", 415.60, 0.30, 419.00, 412.00, "3.8M", "Indian"),
        Stock("GRASIM", "Grasim Industries Ltd.", 1752.00, -0.65, 1770.00, 1742.00, "0.5M", "Indian"),
        Stock("POWERGRID", "Power Grid Corp. of India", 242.10, 0.25, 244.50, 240.20, "6.2M", "Indian"),
        Stock("BPCL", "Bharat Petroleum Corp. Ltd.", 365.80, -1.20, 372.00, 363.00, "3.1M", "Indian"),
        Stock("IOC", "Indian Oil Corp. Ltd.", 89.40, 0.10, 90.20, 88.90, "7.9M", "Indian"),
        Stock("HPCL", "Hindustan Petroleum Corp.", 255.40, -1.50, 261.00, 253.20, "2.8M", "Indian"),
        Stock("TECHM", "Tech Mahindra Ltd.", 1085.00, 2.05, 1098.00, 1061.00, "1.6M", "Indian"),
        Stock("M&M", "Mahindra & Mahindra Ltd.", 1385.00, 1.60, 1398.00, 1361.00, "2.4M", "Indian"),
        Stock("NESTLEIND", "Nestle India Ltd.", 21850.00, -0.45, 22010.00, 21740.00, "0.1M", "Indian"),
        Stock("BRITANNIA", "Britannia Industries Ltd.", 4580.00, -0.80, 4640.00, 4550.00, "0.3M", "Indian"),
        Stock("INDUSINDBK", "IndusInd Bank Ltd.", 1285.00, 1.15, 1298.00, 1269.00, "2.1M", "Indian"),
        Stock("APOLLOHOSP", "Apollo Hospitals Enterprise", 4850.00, 0.70, 4890.00, 4792.00, "0.4M", "Indian"),
        Stock("EICHERMOT", "Eicher Motors Ltd.", 3380.00, 1.95, 3410.00, 3311.00, "0.8M", "Indian"),
        Stock("CIPLA", "Cipla Ltd.", 948.50, -0.15, 955.00, 942.00, "1.4M", "Indian"),
        Stock("DIVISLAB", "Divi's Laboratories Ltd.", 3250.00, -1.10, 3290.00, 3230.00, "0.5M", "Indian"),
        Stock("BAJAJFINSV", "Bajaj Finserv Ltd.", 1450.00, 2.30, 1462.00, 1415.00, "1.8M", "Indian"),
        Stock("DRREDDY", "Dr. Reddy's Laboratories", 4680.00, 0.40, 4710.00, 4650.00, "0.4M", "Indian"),
        Stock("HEROMOTOCO", "Hero MotoCorp Ltd.", 2845.00, 1.25, 2870.00, 2805.00, "0.6M", "Indian"),
        Stock("BAJAJ-AUTO", "Bajaj Auto Ltd.", 4420.00, 0.95, 4460.00, 4375.00, "0.4M", "Indian"),
        Stock("UPL", "UPL Ltd.", 685.20, -1.70, 698.00, 680.00, "1.8M", "Indian"),
        Stock("SBILIFE", "SBI Life Insurance Co.", 1185.00, 0.80, 1195.00, 1172.00, "1.0M", "Indian"),
        Stock("HDFCLIFE", "HDFC Life Insurance Co.", 565.40, -0.35, 571.00, 561.00, "2.2M", "Indian")
    )

    val dummyUsStocks = listOf(
        Stock("AAPL", "Apple Inc.", 189.25, 1.45, 190.58, 187.85, "52.4M", "US"),
        Stock("TSLA", "Tesla Inc.", 245.50, -3.20, 254.10, 242.00, "88.1M", "US"),
        Stock("MSFT", "Microsoft Corp.", 342.10, 0.85, 344.20, 339.80, "22.5M", "US"),
        Stock("AMZN", "Amazon.com Inc.", 128.40, 1.15, 129.50, 126.80, "41.2M", "US"),
        Stock("NVDA", "Nvidia Corp.", 425.80, 4.60, 428.00, 408.20, "65.3M", "US"),
        Stock("GOOGL", "Alphabet Inc.", 122.50, -0.60, 124.10, 121.80, "28.4M", "US"),
        Stock("META", "Meta Platforms Inc.", 295.20, 2.45, 298.50, 289.10, "31.0M", "US"),
        Stock("NFLX", "Netflix Inc.", 445.00, -1.80, 452.00, 441.20, "6.4M", "US"),
        Stock("AMD", "Advanced Micro Devices", 112.30, 3.10, 114.50, 109.10, "48.2M", "US"),
        Stock("BABA", "Alibaba Group Holding", 89.50, -2.40, 91.80, 88.50, "18.1M", "US"),
        Stock("COIN", "Coinbase Global Inc.", 78.40, 6.25, 81.20, 74.00, "14.5M", "US"),
        Stock("DIS", "The Walt Disney Co.", 88.15, -0.45, 89.20, 87.50, "9.2M", "US"),
        Stock("INTC", "Intel Corp.", 33.50, 1.20, 34.10, 33.00, "34.5M", "US"),
        Stock("JPM", "JPMorgan Chase & Co.", 145.60, 0.35, 146.80, 144.50, "11.2M", "US"),
        Stock("KO", "The Coca-Cola Co.", 59.80, -0.15, 60.10, 59.50, "14.1M", "US"),
        Stock("NKE", "Nike Inc.", 108.40, -1.10, 110.20, 107.80, "7.8M", "US"),
        Stock("PYPL", "PayPal Holdings Inc.", 68.20, 1.95, 69.50, 67.10, "12.8M", "US"),
        Stock("SBUX", "Starbucks Corp.", 98.45, 0.50, 99.20, 97.80, "6.1M", "US"),
        Stock("V", "Visa Inc.", 238.50, 0.75, 240.10, 237.20, "5.9M", "US"),
        Stock("WMT", "Walmart Inc.", 155.30, -0.30, 156.40, 154.50, "8.1M", "US")
    )

    val dummyCrypto = listOf(
        Stock("BTC", "Bitcoin", 61240.00, 2.85, 61850.00, 59450.00, "$28.4B", "Crypto"),
        Stock("ETH", "Ethereum", 3350.50, 1.95, 3390.00, 3280.00, "$15.2B", "Crypto"),
        Stock("SOL", "Solana", 142.80, 8.45, 145.20, 131.00, "$3.8B", "Crypto"),
        Stock("BNB", "BNB", 562.40, 0.65, 568.00, 555.20, "$1.1B", "Crypto"),
        Stock("ADA", "Cardano", 0.455, -1.20, 0.468, 0.448, "$310M", "Crypto"),
        Stock("XRP", "Ripple", 0.485, -0.45, 0.495, 0.478, "$840M", "Crypto"),
        Stock("DOT", "Polkadot", 5.82, 2.10, 5.95, 5.65, "$190M", "Crypto"),
        Stock("DOGE", "Dogecoin", 0.125, 4.80, 0.132, 0.118, "$1.4B", "Crypto"),
        Stock("AVAX", "Avalanche", 28.40, 3.15, 29.10, 27.20, "$280M", "Crypto"),
        Stock("MATIC", "Polygon", 0.565, -2.10, 0.582, 0.555, "$220M", "Crypto"),
        Stock("LINK", "Chainlink", 13.85, 1.75, 14.10, 13.50, "$410M", "Crypto"),
        Stock("LTC", "Litecoin", 78.40, 0.95, 79.80, 77.20, "$340M", "Crypto"),
        Stock("UNI", "Uniswap", 7.15, -1.50, 7.35, 7.02, "$180M", "Crypto"),
        Stock("ATOM", "Cosmos", 6.42, 0.35, 6.55, 6.35, "$95M", "Crypto"),
        Stock("NEAR", "Near Protocol", 5.25, 6.10, 5.42, 4.90, "$320M", "Crypto"),
        Stock("SHIB", "Shiba Inu", 0.0000174, 3.25, 0.0000182, 0.0000168, "$480M", "Crypto"),
        Stock("APT", "Aptos", 6.85, 1.15, 6.98, 6.70, "$110M", "Crypto"),
        Stock("TON", "Toncoin", 7.22, -0.85, 7.35, 7.10, "$290M", "Crypto"),
        Stock("ALGO", "Algorand", 0.135, -1.95, 0.141, 0.132, "$45M", "Crypto"),
        Stock("FTM", "Fantom", 0.585, 5.40, 0.602, 0.551, "$125M", "Crypto")
    )

    val dummyCommodities = listOf(
        Stock("GOLD", "Gold Spot (t oz)", 2345.50, 0.65, 2355.00, 2332.10, "182K", "Commodity"),
        Stock("SILVER", "Silver Spot (t oz)", 29.85, 1.45, 30.12, 29.35, "95K", "Commodity"),
        Stock("CRUDE", "WTI Crude Oil (bbl)", 81.45, -1.25, 82.60, 80.80, "310K", "Commodity"),
        Stock("BRENT", "Brent Crude Oil (bbl)", 85.20, -1.10, 86.30, 84.55, "240K", "Commodity"),
        Stock("NATGAS", "Natural Gas (MMBtu)", 2.654, 4.25, 2.712, 2.540, "150K", "Commodity"),
        Stock("COPPER", "Copper (lb)", 4.455, -0.85, 4.512, 4.410, "65K", "Commodity"),
        Stock("PLAT", "Platinum (t oz)", 985.40, 0.35, 995.00, 978.00, "12K", "Commodity"),
        Stock("PALL", "Palladium (t oz)", 915.20, -2.10, 938.00, 908.00, "8K", "Commodity"),
        Stock("CORN", "Corn Futures (bu)", 442.25, 0.95, 445.00, 438.15, "42K", "Commodity"),
        Stock("WHEAT", "Wheat Futures (bu)", 575.50, -0.50, 582.00, 570.00, "38K", "Commodity")
    )

    val dummyBlogs = listOf(
        Blog(
            id = 1,
            title = "Mastering the Art of Swing Trading",
            subtitle = "How to capture medium-term market swings for consistent profits.",
            author = "Ananya Sharma",
            date = "July 05, 2026",
            readTime = "6 min read",
            imageUrl = "https://images.unsplash.com/photo-1590283603385-17ffb3a7f29f?w=600",
            category = "Swing Trading",
            description = "Swing trading bridges the gap between active day trading and long-term investing. Discover key support and resistance setups, moving average cross-overs, and risk-management principles to grow your trading capital systematically.",
            content = """
                Swing trading is one of the most popular trading styles because it offers a realistic middle ground for those who want to profit from stock market movements without staring at charts all day. Unlike intraday traders who open and close positions within a single day, swing traders hold positions for several days to a few weeks, seeking to capture short-to-medium-term price momentum or "swings."

                ### Why Swing Trading Works
                Markets rarely move in a straight line. Instead, they move in zig-zag waves. In an uptrend, prices make higher highs and higher lows. In a downtrend, they make lower highs and lower lows. Swing traders aim to buy at the bottom of a temporary pullback (a higher low) and sell at the peak of the next impulsive wave.

                ### Core Tools of the Swing Trader
                1. **Support and Resistance Zones**: These horizontal levels represent price levels where buying or selling has historically been strong. Buying near major support with a tight stop-loss is a foundational swing setup.
                2. **Moving Averages**: The 20-period and 50-period Exponential Moving Averages (EMAs) are excellent indicators of medium-term trend direction. A bullish crossover, where the shorter EMA crosses above the longer one, often flags emerging momentum.
                3. **RSI (Relative Strength Index)**: When a stock pulls back to support and its RSI dips below 30 (oversold), it signals that selling pressure might be exhausted, presenting a high-probability buying opportunity.

                ### Designing Your Risk Management Plan
                Never risk more than 1% to 2% of your total account value on any single trade. Always set a hard Stop-Loss order immediately after your trade executes. For instance, if you buy Reliance Industries at ₹2,450, you might place a stop loss at ₹2,400 (a 2% risk) and target ₹2,600 for a 1:3 risk-to-reward ratio. This discipline ensures that even if you only win 40% of your trades, you will remain highly profitable in the long run.
            """.trimIndent(),
            likes = 342,
            views = 1205
        ),
        Blog(
            id = 2,
            title = "The Psychology of Market Cycles",
            subtitle = "Why managing greed and fear is the ultimate trading edge.",
            author = "Daniel K.",
            date = "June 28, 2026",
            readTime = "8 min read",
            imageUrl = "https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?w=600",
            category = "Psychology",
            description = "The stock market is driven by human emotions. Understanding the psychological phases of market cycles—from optimism to panic—is critical for preserving capital and buying low.",
            content = """
                Have you ever wondered why most retail traders buy stocks at the absolute top and sell them at the absolute bottom? It is not because they lack intelligence; it is because they are victims of hardwired human psychology. 

                ### The Emotional Rollercoaster
                Every market cycle, whether in Indian stocks, Wall Street, or Crypto, goes through distinct psychological phases:
                - **Disbelief**: After a long bear market, initial rallies are met with suspicion. People think, "This is just another dead cat bounce."
                - **Optimism & Belief**: As prices continue to rise, confidence builds. Investors begin putting real money to work.
                - **Euphoria**: This is the peak of the cycle. Everyone is making money, taxi drivers are offering stock tips, and greed is at its maximum. Investors take on excessive leverage, convinced that "this time is different."
                - **Anxiety & Denial**: When the market begins to correct, investors dismiss it as a temporary dip. They hold on, unwilling to accept a small loss.
                - **Panic & Capitulation**: As losses mount, fear takes over. In a state of absolute despair, investors dump their portfolios at bargain-basement prices just to make the pain stop.

                ### How to Outsmart the Crowd
                To survive as a trader, you must learn to recognize these emotional cycles and consciously act against your instincts:
                1. **Adopt a Contra-Mindset**: Be fearful when others are greedy, and greedy when others are fearful.
                2. **Stop Checking Your Portfolio Every 5 Minutes**: Over-monitoring leads to emotional decision-making. Trust your long-term plan and let your stops do their job.
                3. **Write a Trading Journal**: Document your emotional state before making a trade. Over time, you will spot patterns of FOMO (Fear Of Missing Out) or revenge trading and learn to avoid them.
            """.trimIndent(),
            likes = 512,
            views = 2480
        ),
        Blog(
            id = 3,
            title = "Introduction to Options Trading",
            subtitle = "A beginner-friendly guide to Calls, Puts, and leverage.",
            author = "Vikram Aditya",
            date = "July 01, 2026",
            readTime = "10 min read",
            imageUrl = "https://images.unsplash.com/photo-1642390061910-0f11b05ee639?w=600",
            category = "Options",
            description = "Options are powerful derivatives that offer massive leverage and hedging capabilities. Demystify the world of Call Options (CE) and Put Options (PE) in this introductory guide.",
            content = """
                Options trading is often viewed as highly complex and dangerous, but with a solid foundation, it can be an invaluable addition to your trading toolkit. At its core, an option is a derivative contract that gives you the right (but not the obligation) to buy or sell an underlying asset at a pre-specified price within a certain timeframe.

                ### Calls vs. Puts
                There are only two basic types of options:
                - **Call Option (CE)**: Gives you the right to *buy* a stock. You buy a Call Option when you expect the stock price to go up.
                - **Put Option (PE)**: Gives you the right to *sell* a stock. You buy a Put Option when you expect the stock price to decline.

                ### Key Terminology
                - **Strike Price**: The fixed price at which the option contract can be exercised (e.g., NIFTY 24,000 Call).
                - **Premium**: The price you pay in the market to buy the option contract. This is your maximum risk as an option buyer.
                - **Expiry Date**: The date when the option contract becomes invalid. In India, weekly NIFTY options expire every Thursday, while monthly options expire on the last Thursday of the month.

                ### Option Buying vs. Option Selling
                - **Option Buyers** have limited risk (the premium paid) but unlimited profit potential. However, they face a silent enemy: *Time Decay (Theta)*. Every day that passes without a large move, the option premium loses value.
                - **Option Sellers (Writers)** have unlimited risk but limited profit (the premium they collect). They win if the market stays flat or moves in their favor, making it a high-probability strategy requiring significant capital.
            """.trimIndent(),
            likes = 189,
            views = 945
        ),
        Blog(
            id = 4,
            title = "Unlocking the Power of Mutual Funds",
            subtitle = "How to build a passive multi-crore portfolio using SIPs.",
            author = "Priya Mehta",
            date = "June 25, 2026",
            readTime = "5 min read",
            imageUrl = "https://images.unsplash.com/photo-1579621970563-ebec7560ff3e?w=600",
            category = "Mutual Funds",
            description = "Don't have time to analyze individual stocks? Discover how to harness the Indian growth story using Systematic Investment Plans (SIP) in diversified mutual funds.",
            content = """
                If you find reading stock charts or analyzing corporate balance sheets overwhelming, you do not have to sit out of the financial markets. Mutual funds offer a professional, highly regulated, and incredibly simple vehicle to compound your wealth over time.

                ### What is a Mutual Fund?
                A mutual fund pools money from thousands of investors and allocates it across a diversified portfolio of stocks, bonds, or other assets. It is managed by a professional Fund Manager who aims to outperform a benchmark index like the Nifty 50 or Sensex.

                ### The Power of SIP (Systematic Investment Plan)
                An SIP is not a separate investment; it is simply a disciplined *method* of investing. Instead of trying to time the market with a lump sum, you invest a fixed amount (e.g., ₹5,000) every single month.
                
                This strategy unlocks two major advantages:
                1. **Rupee Cost Averaging**: When the market falls, your ₹5,000 buys *more* units of the fund. When the market rises, it buys *fewer* units. Over time, your average purchase cost is lowered automatically.
                2. **Compounding Interest**: If you start a SIP of ₹10,000 per month at age 25, assuming a conservative 12% annual return, your portfolio will grow to over ₹1.1 Crore by the time you turn 50.

                ### How to Select the Right Mutual Fund
                - **Index Funds**: These funds simply copy the Nifty 50. They have the lowest management fees (expense ratio) and are perfect for long-term passive investors.
                - **Active Large & Mid Cap Funds**: These target high-growth companies with a proven track record.
                - **Small Cap Funds**: Highly volatile but offer explosive growth potential for young investors with a high risk tolerance and a 7+ year horizon.
            """.trimIndent(),
            likes = 422,
            views = 1560
        ),
        Blog(
            id = 5,
            title = "Decoding IPO Investing Secrets",
            subtitle = "A guide to separating hype from value in Initial Public Offerings.",
            author = "Rajesh Patel",
            date = "June 30, 2026",
            readTime = "7 min read",
            imageUrl = "https://images.unsplash.com/photo-1624996379697-f01d168b1a52?w=600",
            category = "IPO",
            description = "IPOs promise massive listing gains, but they can also be dangerous value traps. Learn how to read Red Herring Prospectuses and identify high-quality public listings.",
            content = """
                Initial Public Offerings (IPOs) are always accompanied by massive media hype and marketing campaigns. Companies going public want to create an aura of exclusivity to ensure their issue gets oversubscribed. While some IPOs deliver over 100% listing gains on day one, others crash immediately, wiping out retail capital.

                ### The IPO Evaluation Checklist
                Before applying for any IPO, spend 15 minutes analyzing these key metrics:
                1. **Promoter Holding & Integrity**: Are the founders holding a significant stake post-IPO, or are they completely exiting the company? If promoters are dumping their entire stake, it's a major red flag.
                2. **Use of Proceeds**: Read the Draft Red Herring Prospectus (DRHP). Is the company raising capital to fund future expansion, or is it merely paying off old debt? Prefer companies investing in R&D or expansion.
                3. **Valuation Multiples**: Compare the IPO's Price-to-Earnings (P/E) ratio with its listed peers. If the company is asking for a P/E of 80 while established market leaders trade at a P/E of 35, it is heavily overvalued.
                4. **Grey Market Premium (GMP)**: While unofficial, the GMP offers a reliable gauge of institutional and retail demand. A high positive GMP (above 30% of issue price) generally signals strong listing day potential.

                ### The Listing Day Strategy
                If you get an allotment, have a clear plan. If you applied purely for "listing gains," sell the stock in the pre-open session on listing day to lock in your profits. If you believe in the company's long-term business model, you can hold, but be prepared for short-term post-IPO volatility as early venture capitalists exit.
            """.trimIndent(),
            likes = 295,
            views = 1120
        ),
        Blog(
            id = 6,
            title = "The Foundation of Value Investing",
            subtitle = "How Warren Buffett finds compounders at a discount.",
            author = "Daniel K.",
            date = "July 03, 2026",
            readTime = "9 min read",
            imageUrl = "https://images.unsplash.com/photo-1559526324-4b87b5e36e44?w=600",
            category = "Investing",
            description = "Value investing is buying a dollar for fifty cents. Discover the concepts of intrinsic value, margin of safety, and economic moats.",
            content = """
                Value investing is the art of buying outstanding businesses at a price significantly below their actual worth. Developed by Benjamin Graham and popularized by Warren Buffett, it is a proven approach to creating generational wealth.

                ### Intrinsic Value vs. Market Price
                The fundamental truth of value investing is that **price is what you pay, value is what you get**. The stock market is a highly emotional voting machine in the short term, but a weighing machine in the long term. This means stocks frequently trade below their true economic value due to temporary bad news or general market panics.

                ### The Three pillars of Value Investing
                1. **Economic Moats**: A moat is a sustainable competitive advantage that protects a company from competitors. Examples include a powerful brand (Apple, Coca-Cola), high switching costs (Microsoft Windows), or low production costs (Reliance).
                2. **Margin of Safety**: Never buy a stock at its exact fair value. If you calculate a stock's intrinsic value to be ₹1,000, aim to buy it at ₹700. This 30% margin of safety protects your capital if your future projections turn out to be slightly inaccurate.
                3. **Circle of Competence**: Only invest in industries you deeply understand. If you don't know how a pharmaceutical company gets FDA approvals, stay away and stick to consumer goods or banking.
            """.trimIndent(),
            likes = 467,
            views = 1940
        ),
        Blog(
            id = 7,
            title = "A Complete Guide to Intraday Trading",
            subtitle = "Surviving the high-stakes world of day trading.",
            author = "Ananya Sharma",
            date = "June 20, 2026",
            readTime = "8 min read",
            imageUrl = "https://images.unsplash.com/photo-1618042164219-62c820f10723?w=600",
            category = "Intraday",
            description = "Day trading requires split-second execution, flawless discipline, and highly responsive technical analysis. Learn the VWAP crossover and opening range breakout setups.",
            content = """
                Day trading (Intraday) is the practice of buying and selling financial instruments within the same trading session. Because you close all your positions before the market closes, you have zero overnight risk. However, intraday trading is exceptionally fast-paced and unforgiving.

                ### Setup 1: The Opening Range Breakout (ORB)
                The first 15 minutes of the trading day see the highest volume and volatility. 
                - Identify the High and Low of the first 15-minute candle.
                - If a subsequent 5-minute candle closes *above* the 15-minute high, enter a long trade, placing your stop loss at the candle midpoint.
                - If it breaks *below* the 15-minute low, go short.

                ### Setup 2: The VWAP Bounce
                VWAP (Volume Weighted Average Price) is the holy grail indicator for day traders. It represents the true average price of the stock adjusted for volume.
                - If a stock is in a strong uptrend, do not chase it.
                - Wait for the price to pull back and touch the VWAP line.
                - Look for a bullish candlestick pattern (like a hammer) at the VWAP. Buy here with a stop loss just below VWAP.

                ### The 3 Rules of Intraday Survival
                1. **Set a Daily Max Loss**: If you lose ₹3,000 in a day, shut down your terminal and walk away. Do not attempt "revenge trading" to win it back.
                2. **Focus on Liquid Stocks**: Only trade high-volume Nifty 50 stocks. Illiquid stocks can have massive bid-ask spreads, making it impossible to exit your trade quickly.
                3. **Use Leverage Cautiously**: Brokers offer up to 5x leverage for intraday orders. While this amplifies profits, it equally amplifies losses. Start with small position sizes.
            """.trimIndent(),
            likes = 310,
            views = 1430
        ),
        Blog(
            id = 8,
            title = "Introduction to Futures Contracts",
            subtitle = "Understanding leverage, margin, and hedging with Futures.",
            author = "Vikram Aditya",
            date = "June 18, 2026",
            readTime = "7 min read",
            imageUrl = "https://images.unsplash.com/photo-1639762681485-074b7f938ba0?w=600",
            category = "Futures",
            description = "Futures contracts allow traders to bet on the future price of index and individual stocks with high leverage. Understand margins, MTM, and rollover.",
            content = """
                A futures contract is a legally binding agreement to buy or sell an asset at a predetermined price at a specified time in the future. In stock markets, futures are highly liquid, leveraged instruments used primarily by speculative traders and institutional hedgers.

                ### Understanding Margins and Leverage
                Unlike buying normal shares, where you must pay 100% of the cost, trading futures requires only a fraction of the contract value—usually 15% to 20%—known as the **Span & Exposure Margin**.
                
                For example, if you want to trade a NIFTY Futures contract worth ₹12 Lakhs, you only need around ₹2 Lakhs in your trading account. This represents 6x leverage, meaning a 1% move in Nifty delivers a 6% return (or loss) on your capital.

                ### Mark-to-Market (MTM) Settlement
                At the end of every trading day, your futures position is settled based on the closing price. If you went long and the index went up, the profits are credited to your account overnight. If the index went down, the losses are debited. If your account balance falls below the maintenance margin, your broker will issue a "Margin Call" requiring you to deposit funds immediately or face forced liquidation.

                ### When to Use Futures
                - **Leveraged Speculation**: If you are extremely bullish on a stock's near-term earnings, buying its futures contract maximizes your capital efficiency.
                - **Short Selling**: Unlike shares, which cannot be shorted overnight, futures allow you to short and hold bearish positions for weeks.
                - **Portfolio Hedging**: If you hold a long-term portfolio worth ₹50 Lakhs and expect a temporary market correction, you can sell Nifty Futures of equal value to offset any portfolio losses.
            """.trimIndent(),
            likes = 125,
            views = 680
        ),
        Blog(
            id = 9,
            title = "Personal Finance 101",
            subtitle = "The master plan to achieve financial freedom by 40.",
            author = "Priya Mehta",
            date = "July 07, 2026",
            readTime = "6 min read",
            imageUrl = "https://images.unsplash.com/photo-1554224155-8d04cb21cd6c?w=600",
            category = "Finance",
            description = "Trading is only half the battle. Master the principles of budgeting, emergency funds, debt elimination, and asset allocation.",
            content = """
                No matter how much money you make from trading or your career, you will never achieve financial independence without sound personal finance habits. Financial freedom is not about buying expensive cars; it is about having control over your time.

                ### Step 1: The Emergency Fund (Non-Negotiable)
                Before investing a single rupee in stocks, build a liquidity wall. Accumulate 6 months of your living expenses in a liquid savings account or ultra-short-term debt fund. This fund should only be touched in case of medical emergencies or job loss, ensuring you never have to sell your stocks during a market crash.

                ### Step 2: Buy Comprehensive Insurance
                Do not rely on corporate health insurance. Get a personal health insurance policy of at least ₹10 Lakhs and a Term Life Insurance policy equal to 10-15x your annual income. This ensures your family's future remains financially secure no matter what.

                ### Step 3: Master the 50/30/20 Budget
                Allocate your monthly post-tax income into three distinct buckets:
                - **50% for Needs**: Rent, groceries, utility bills, and loan repayments.
                - **30% for Wants**: Dining out, shopping, hobbies, and vacations.
                - **20% for Savings & Investing**: Automatically diverted into your SIPs, PPF, and stock accounts.

                ### Step 4: Avoid Toxic Debt
                Credit card debt and personal loans are financial cancer. They charge 15% to 40% interest, destroying your compounding. Only take loans for appreciating assets (like a home, if calculated carefully) and always pay off credit card balances in full every month.
            """.trimIndent(),
            likes = 510,
            views = 2100
        ),
        Blog(
            id = 10,
            title = "Understanding candlestick patterns",
            subtitle = "How to read the price footprints on stock charts.",
            author = "Rajesh Patel",
            date = "June 15, 2026",
            readTime = "6 min read",
            imageUrl = "https://images.unsplash.com/photo-1590283603385-17ffb3a7f29f?w=600",
            category = "Trading",
            description = "Candlesticks reveal the battle between bulls and bears. Learn how to trade the Hammer, Engulfing, and Doji patterns.",
            content = """
                A candlestick chart is a style of financial chart used to describe price movements of a security, derivative, or currency. Each candlestick represents a specific timeframe (e.g., 5 minutes, 1 hour, or 1 day) and details four crucial data points: the Open, High, Low, and Close (OHLC).

                ### Anatomy of a Candlestick
                - **The Body**: The thick rectangular part. If the close is higher than the open, it's green (bullish). If the close is lower, it's red (bearish).
                - **The Wicks (Shadows)**: The thin lines extending above and below the body. They represent the highest and lowest prices reached during that session.

                ### High-Probability Candlestick Patterns
                1. **The Hammer (Bullish Reversal)**: A small body at the top of the candle with a long lower wick (at least twice the body size). It shows that sellers pushed prices down, but buyers stepped in aggressively to drive the price back up. This pattern is highly effective at the bottom of a downtrend.
                2. **Bullish/Bearish Engulfing**: A two-candle pattern. In a Bullish Engulfing, a small red candle is completely swallowed or "engulfed" by a subsequent large green candle. It flags a dramatic shift in market control from bears to bulls.
                3. **The Doji (Indecision)**: A candle where the open and close are almost identical, resulting in a thin cross-like shape. It shows absolute equilibrium between buyers and sellers, often warning of an impending trend reversal.
            """.trimIndent(),
            likes = 398,
            views = 1540
        ),
        Blog(
            id = 11,
            title = "What is Liquidity in Stock Trading?",
            subtitle = "Why trading volume is your best protection against slippage.",
            author = "Ananya Sharma",
            date = "May 25, 2026",
            readTime = "4 min read",
            imageUrl = "https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?w=600",
            category = "Trading",
            description = "Liquidity describes how easily an asset can be converted into cash without affecting its price. Learn about spreads, market depth, and how to spot low liquidity traps.",
            content = "Liquidity is the oxygen of the financial markets. High liquidity means there are always millions of buyers and sellers active, resulting in tight bid-ask spreads and instant execution. In contrast, illiquid penny stocks can trap you, making it impossible to exit your position as prices collapse.",
            likes = 120,
            views = 520
        ),
        Blog(
            id = 12,
            title = "Growth Stocks vs. Dividend Stocks",
            subtitle = "Comparing capital appreciation with passive income.",
            author = "Daniel K.",
            date = "May 28, 2026",
            readTime = "6 min read",
            imageUrl = "https://images.unsplash.com/photo-1579621970563-ebec7560ff3e?w=600",
            category = "Investing",
            description = "Should you focus on tech giants reinvesting every dollar of profit, or stable utility companies paying reliable dividends? We break down the math for different life stages.",
            content = "Growth stocks reinvest their earnings into research, expansion, and acquisitions to fuel stock price appreciation, whereas Dividend stocks distribute profits directly to shareholders. Young investors should lean towards growth stocks for compounding, while retirees prefer stable dividend payers for passive cash flow.",
            likes = 295,
            views = 1122
        ),
        Blog(
            id = 13,
            title = "What is an Expense Ratio in Mutual Funds?",
            subtitle = "The silent fee that can cost you lakhs over 20 years.",
            author = "Priya Mehta",
            date = "May 30, 2026",
            readTime = "5 min read",
            imageUrl = "https://images.unsplash.com/photo-1554224155-8d04cb21cd6c?w=600",
            category = "Mutual Funds",
            description = "An expense ratio is the annual fee charged by mutual funds to manage your money. Discover how a seemingly small 1% difference can eat up a massive portion of your returns.",
            content = "Even a small 1.5% expense ratio in active funds can cost you lakhs over a 20-year investing journey compared to low-cost index funds with a 0.1% ratio. Always look for 'Direct' plans instead of 'Regular' plans to save on commissions.",
            likes = 312,
            views = 1205
        ),
        Blog(
            id = 14,
            title = "IPO Grey Market Premium (GMP) Explained",
            subtitle = "What is it, and is it a reliable listing predictor?",
            author = "Rajesh Patel",
            date = "June 05, 2026",
            readTime = "5 min read",
            imageUrl = "https://images.unsplash.com/photo-1624996379697-f01d168b1a52?w=600",
            category = "IPO",
            description = "The Grey Market is an unofficial OTC market where IPO applications and shares are traded before listing. Learn how to interpret GMP, Kostak rates, and Subject to Sauda.",
            content = "While GMP is not regulated by SEBI, it is a highly accurate representation of supply and demand. If a company's IPO price is ₹300 and its GMP is ₹150, the market expects it to list near ₹450. However, manipulate-prone operators can fake GMP, so never rely on it blindly.",
            likes = 205,
            views = 890
        ),
        Blog(
            id = 15,
            title = "Trading Psychology: Overcoming FOMO",
            subtitle = "The mental frameworks to stop chasing vertical charts.",
            author = "Daniel K.",
            date = "June 10, 2026",
            readTime = "7 min read",
            imageUrl = "https://images.unsplash.com/photo-1618042164219-62c820f10723?w=600",
            category = "Psychology",
            description = "FOMO (Fear of Missing Out) is the ultimate account killer. Understand why your brain craves high-risk setups and how to build systemized filters.",
            content = "When you see a stock rise 20% in a day, your brain's reward center releases dopamine, urging you to buy immediately. This is usually the exact moment institutional smart money is dumping shares. Overcome FOMO by establishing strict checklist rules: never enter a trade that has already moved more than 3% from its base.",
            likes = 452,
            views = 1890
        ),
        Blog(
            id = 16,
            title = "The Golden Rules of Swing Trading",
            subtitle = "Three rules that separate profitable swing traders from the rest.",
            author = "Ananya Sharma",
            date = "May 15, 2026",
            readTime = "5 min read",
            imageUrl = "https://images.unsplash.com/photo-1642390061910-0f11b05ee639?w=600",
            category = "Swing Trading",
            description = "Swing trading requires patience and proper timing. Learn why buying pullbacks in uptrends is safer than trying to catch falling knives.",
            content = "Rule 1: Always trade in the direction of the daily and weekly trend. Rule 2: Buy the pullbacks, never buy the breakouts. Rule 3: Hold through normal pullbacks but exit instantly if the structural support level breaks on high volume.",
            likes = 312,
            views = 1340
        ),
        Blog(
            id = 17,
            title = "Understanding Option Greeks: Delta & Theta",
            subtitle = "How price movement and time decay impact option premiums.",
            author = "Vikram Aditya",
            date = "May 10, 2026",
            readTime = "8 min read",
            imageUrl = "https://images.unsplash.com/photo-1639762681485-074b7f938ba0?w=600",
            category = "Options",
            description = "To succeed in options, you must look beyond premium. Understand how Delta measures price sensitivity and Theta acts as option buyers' silent enemy.",
            content = "Delta tells you how much your option price will move for every ₹1 change in the stock. Theta represents time decay, telling you exactly how much premium value your option contract will lose overnight if the stock price remains unchanged.",
            likes = 143,
            views = 780
        ),
        Blog(
            id = 18,
            title = "Tax-Saving Mutual Funds (ELSS)",
            subtitle = "Save tax under 80C while earning equity-linked returns.",
            author = "Priya Mehta",
            date = "May 08, 2026",
            readTime = "5 min read",
            imageUrl = "https://images.unsplash.com/photo-1554224155-8d04cb21cd6c?w=600",
            category = "Mutual Funds",
            description = "Equity Linked Savings Schemes (ELSS) offer a dual benefit of tax deduction up to ₹1.5 Lakhs and the potential of high equity compounding with a short 3-year lock-in.",
            content = "ELSS has the shortest lock-in period (3 years) among all tax-saving options like PPF (15 years) or Tax-saving FD (5 years). It historical delivers superior inflation-beating returns since it invests purely in diversified equity portfolios.",
            likes = 290,
            views = 1010
        ),
        Blog(
            id = 19,
            title = "Futures Arbitrage Strategies Explained",
            subtitle = "How institutional desks capture risk-free spreads.",
            author = "Vikram Aditya",
            date = "May 01, 2026",
            readTime = "6 min read",
            imageUrl = "https://images.unsplash.com/photo-1559526324-4b87b5e36e44?w=600",
            category = "Futures",
            description = "Learn how cash-and-carry arbitrage operates by exploiting temporary price differences between the spot share price and the futures price.",
            content = "Due to market inefficiencies, a stock's futures contract frequently trades at a premium to its cash spot price. Institutional desks buy cash shares and sell futures contracts simultaneously, capturing this risk-free spread on the day of expiry.",
            likes = 98,
            views = 540
        ),
        Blog(
            id = 20,
            title = "What is Behavioral Finance?",
            subtitle = "How cognitive biases warp our investment decisions.",
            author = "Daniel K.",
            date = "April 28, 2026",
            readTime = "6 min read",
            imageUrl = "https://images.unsplash.com/photo-1618042164219-62c820f10723?w=600",
            category = "Psychology",
            description = "Traditional finance assumes investors are rational. Behavioral finance proves we are highly irrational, guided by confirmation bias and loss aversion.",
            content = "Loss Aversion describes our psychological tendency to feel the pain of a loss twice as intensely as the pleasure of an equivalent gain. This bias causes traders to hold onto losing positions in hope of breaking even, while selling winners way too early.",
            likes = 310,
            views = 1290
        ),
        Blog(
            id = 21,
            title = "Mastering Support & Resistance",
            subtitle = "The cornerstone of structural technical analysis.",
            author = "Rajesh Patel",
            date = "April 20, 2026",
            readTime = "5 min read",
            imageUrl = "https://images.unsplash.com/photo-1590283603385-17ffb3a7f29f?w=600",
            category = "Trading",
            description = "Support and resistance are not exact lines; they are dynamic supply and demand zones. Learn to identify and trade high-volume bounces.",
            content = "Support is the floor where demand exceeds supply, preventing prices from falling further. Resistance is the ceiling where supply exceeds demand. Bounces from multi-touch zones offer the lowest risk entries in swing trading.",
            likes = 380,
            views = 1490
        )
    )

    val dummyNotifications = listOf(
        NotificationItem(1, "NIFTY crossed 25,000! 🚀", "The benchmark Nifty 50 index crossed the historic 25,000 milestone today, driven by massive IT and banking inflows.", "10 mins ago", "Market"),
        NotificationItem(2, "New IPO listing Alert 📢", "Vibe Fintech Ltd. IPO has listed at a massive 45% premium on NSE and BSE! Read our post-listing analysis.", "1 hour ago", "IPO"),
        NotificationItem(3, "New Blog Published ✍️", "Mastering the Art of Swing Trading by Ananya Sharma is now live. Learn key patterns and setup strategies.", "3 hours ago", "Blog"),
        NotificationItem(4, "Market Pre-Open Indicator 📊", "SGX Nifty is trading up by 85 points. Indian markets are poised for a gap-up opening in 15 minutes.", "15 mins ago", "Market"),
        NotificationItem(5, "Solana surges 8.4% 📈", "SOL has broken past the $140 level, leading the altcoin market rally. Volume has spiked by 125% in the last 24 hours.", "4 hours ago", "Alert")
    )
}
