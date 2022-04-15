package com.leon.model;

import java.time.LocalDate;
import java.time.LocalTime;

public final class PricePoint
{
	private String symbol;
	private LocalDate date;
	private LocalTime time;
	private long price;
	private long qty;

	public PricePoint(String symbol, LocalDate date, LocalTime time, long price, long qty)
	{
		this.symbol = symbol;
		this.date = date;
		this.time = time;
		this.price = price;
		this.qty = qty;
	}

	@Override
	public String toString()
	{
		return "{" + "\"symbol\": \"" + symbol + "\"" + ", \"date\": \"" + date + "\"" + ", \"time\": \""
				+ time + "\"" + ", \"price\": " + price + ", \"qty\": " + qty + "}";
	}
}
