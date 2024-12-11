package com.example.it_tech_hack.data.models

data class StockResponse(
    val AAPL: StockSymbolResponse?,
    val GOOGL: StockSymbolResponse?,
    val AMZN: StockSymbolResponse?,
    val MSFT: StockSymbolResponse?,
    val TSLA: StockSymbolResponse?,
    val META: StockSymbolResponse?,
    val NVDA: StockSymbolResponse?,
    val BRK_B: StockSymbolResponse?,
    val JNJ: StockSymbolResponse?,
    val V: StockSymbolResponse?,
    val NFLX: StockSymbolResponse?,
    val DIS: StockSymbolResponse?,
    val PYPL: StockSymbolResponse?,
    val INTC: StockSymbolResponse?,
    val AMD: StockSymbolResponse?,
    val PEP: StockSymbolResponse?,
    val BA: StockSymbolResponse?,
    val WMT: StockSymbolResponse?,
    val MCD: StockSymbolResponse?,
)

data class StockSymbolResponse(
    val meta: StockMeta,
    val values: List<StockData>
)

data class StockMeta(
    val symbol: String,
    val interval: String,
    val currency: String,
    val exchange: String
)

data class StockData(
    val datetime: String,
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    val volume: String
)
