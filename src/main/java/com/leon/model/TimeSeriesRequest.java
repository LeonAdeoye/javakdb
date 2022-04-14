package com.leon.model;

public class TimeSeriesRequest
{
	private String symbol;

	public String getSymbol()
	{
		return this.symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	@Override
	public String toString()
	{
		return "TimeSeriesRequest{" + "symbol='" + symbol + '\'' + '}';
	}
}
