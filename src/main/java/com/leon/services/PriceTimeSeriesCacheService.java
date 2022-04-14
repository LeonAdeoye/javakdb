package com.leon.services;

import kx.Connection;
import java.io.IOException;
import java.sql.Date;

public class PriceTimeSeriesCacheService
{
	static String username = "adeoyel";
	static String password = "password";
	static int port = 8080;
	static String host = "localhost";

	public static void main(String[] args) throws IOException
	{
		Connection connection = null;

		try
		{
			connection = new Connection(host, port, username + ":" + password, false);
			String[] snapTime = new String[] {"13:30:00.000", "13:30:18.7555"};
			Date[] snapDate = new Date[] {Date.valueOf("2022-03-01"), Date.valueOf("2022-03-01")};
			String[] symbols = new String[] {"ETC", "BTC"};
			String[] columns = new String[] {"bid", "ask"};
			Connection.Dict dictionary = new Connection.Dict(new String[] {"symbols", "snapDate", "snapTime", "columns"}, new Object[] { symbols, snapDate, snapTime, columns});
			Connection.Result result = (Connection.Result) connection.invoke("getPrices", dictionary);
			int symbolIndex = 0, dateIndex = 0, timeIndex = 0, bidIndex = 0, askIndex = 0, highIndex = 0, lowIndex = 0, closeIndex = 0, openIndex = 0;

			for(int index = 0; index < result.columnNames.length; ++index)
			{
				String key = result.columnNames[index];
				if(key.equalsIgnoreCase("date"))
					dateIndex = index;
				else if (key.equalsIgnoreCase("time"))
					timeIndex = index;
				else if (key.equalsIgnoreCase("symbol"))
					symbolIndex = index;
				else if (key.equalsIgnoreCase("bid"))
					bidIndex = index;
				else if (key.equalsIgnoreCase("ask"))
					askIndex = index;
				else if (key.equalsIgnoreCase("high"))
					highIndex = index;
				else if (key.equalsIgnoreCase("low"))
					lowIndex = index;
				else if (key.equalsIgnoreCase("close"))
					closeIndex = index;
				else if (key.equalsIgnoreCase("open"))
					openIndex = index;
			}

			String[] symbolValues = (String[]) result.columnValuesArrayOfArray[symbolIndex];
			Object[] dateValues = (Object[]) result.columnValuesArrayOfArray[dateIndex];
			Object[] timeValues = (Object[]) result.columnValuesArrayOfArray[timeIndex];
			double[] bidValues = (double[]) result.columnValuesArrayOfArray[bidIndex];
			double[] askValues = (double[]) result.columnValuesArrayOfArray[askIndex];
			double[] closeValues = (double[]) result.columnValuesArrayOfArray[closeIndex];
			double[] openValues = (double[]) result.columnValuesArrayOfArray[openIndex];
			double[] highValues = (double[]) result.columnValuesArrayOfArray[highIndex];
			double[] lowValues = (double[]) result.columnValuesArrayOfArray[lowIndex];

			for(int index = 0; index < symbolValues.length; ++index)
			{
				Connection.Timespan timespan = (Connection.Timespan) timeValues[index];

				System.out.println(String.format("Symbol: %s, date: %s, time: %s, ask: %f, bid: %f, close: %f, open:%f, high:%f, low:%f",
						symbolValues[index], dateValues[index].toString(), timespan.toString(), askValues[index],
						bidValues[index], closeValues[index], openValues[index], highValues[index], lowValues[index]));
			}
		}
		catch(Connection.KException ke)
		{
			ke.printStackTrace();
		}
		finally
		{
			connection.close();
		}
	}

	public String getPrices(String symbol)
	{
		return "{}";
	}
}
