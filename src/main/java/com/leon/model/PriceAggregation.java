package com.leon.model;

public class PriceAggregation
{
	private long high;
	private long low;
	private long close;
	private long open;
	private String symbol;
	private String date;

	public PriceAggregation(long high, long low, long close, long open, String symbol, String date)
	{
		this.high = high;
		this.low = low;
		this.close = close;
		this.open = open;
		this.symbol = symbol;
		this.date = date;
	}

	@Override
	public String toString()
	{
		return "{" + "\"high\": " + high + ", \"low\": " + low + ", \"close\": " + close + ", \"open\": " + open + ", \"symbol\": \"" + symbol + "\"" + ", \"date\": \"" + date + "\"" + "}";
	}
}
