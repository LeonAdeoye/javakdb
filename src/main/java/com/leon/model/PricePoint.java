package com.leon.model;

import java.time.LocalDateTime;

public class PricePoint
{
	private String symbol;
	private double ask;
	private double bid;
	private double high;
	private double low;
	private double close;
	private double open;
	private LocalDateTime timeStamp;

	public PricePoint()
	{

	}

	public PricePoint(String symbol, double ask, double bid, double high, double low, double close, double open, LocalDateTime timeStamp)
	{
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
		this.high = high;
		this.low = low;
		this.close = close;
		this.open = open;
		this.timeStamp = timeStamp;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	public double getAsk()
	{
		return ask;
	}

	public void setAsk(double ask)
	{
		this.ask = ask;
	}

	public double getBid()
	{
		return bid;
	}

	public void setBid(double bid)
	{
		this.bid = bid;
	}

	public double getHigh()
	{
		return high;
	}

	public void setHigh(double high)
	{
		this.high = high;
	}

	public double getLow()
	{
		return low;
	}

	public void setLow(double low)
	{
		this.low = low;
	}

	public double getClose()
	{
		return close;
	}

	public void setClose(double close)
	{
		this.close = close;
	}

	public double getOpen()
	{
		return open;
	}

	public void setOpen(double open)
	{
		this.open = open;
	}

	public LocalDateTime getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString()
	{
		return "PricePoint{" + "symbol='" + symbol + '\'' + ", ask=" + ask + ", bid=" + bid + ", high=" + high + ", low=" + low + ", close=" + close + ", open=" + open + ", timeStamp=" + timeStamp + '}';
	}
}
