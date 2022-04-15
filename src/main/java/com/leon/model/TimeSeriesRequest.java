package com.leon.model;

import java.util.Objects;

public class TimeSeriesRequest
{
	private String symbol;
	private String startDate;
	private String endDate;

	public TimeSeriesRequest(String symbol, String startDate, String endDate)
	{
		this.symbol = symbol;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public TimeSeriesRequest()
	{
	}

	public String getSymbol()
	{
		return this.symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	@Override
	public String toString()
	{
		return "TimeSeriesRequest{" + "symbol='" + symbol + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + '}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TimeSeriesRequest that = (TimeSeriesRequest) o;
		return getSymbol().equals(that.getSymbol()) && getStartDate().equals(that.getStartDate()) && getEndDate().equals(that.getEndDate());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(getSymbol(), getStartDate(), getEndDate());
	}
}
