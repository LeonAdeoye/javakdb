package com.leon.model;

import java.time.LocalDate;
import java.util.Objects;

public class TimeSeriesRequest
{
	private String symbol;
	private LocalDate startDate;
	private LocalDate endDate;

	public String getSymbol()
	{
		return this.symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	public LocalDate getStartDate()
	{
		return startDate;
	}

	public void setStartDate(LocalDate startDate)
	{
		this.startDate = startDate;
	}

	public LocalDate getEndDate()
	{
		return endDate;
	}

	public void setEndDate(LocalDate endDate)
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
