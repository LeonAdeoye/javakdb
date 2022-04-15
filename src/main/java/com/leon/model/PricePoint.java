package com.leon.model;

public class PricePoint
{
	private String symbol;
	private String date;
	private String time;
	private long price;
	private long qty;

	public PricePoint(String symbol, String date, String time, long price, long qty)
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
