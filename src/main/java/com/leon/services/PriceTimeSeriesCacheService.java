package com.leon.services;

import com.leon.model.PricePoint;
import kx.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceTimeSeriesCacheService
{
	@Value("${kdb.username}")
	String username;

	@Value("${kdb.password}")
	String password;

	@Value("${kdb.port}")
	int port = 8080;

	@Value("${kdb.hostname}")
	String host = "localhost";

	public List<PricePoint> getPrices(String symbol)
	{
		Connection connection = null;
		List<PricePoint> pricePoints = new ArrayList<>();

		try
		{
			connection = new Connection(host, port, username + ":" + password, false);

			// TODO Convert into getPrices parameters
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

				PricePoint pricePoint = new PricePoint();
			}
		}
		catch(Connection.KException ke)
		{
			ke.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			return pricePoints;
		}
	}
}
