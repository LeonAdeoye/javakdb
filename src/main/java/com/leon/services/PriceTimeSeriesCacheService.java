package com.leon.services;

import com.leon.model.PricePoint;
import kx.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PriceTimeSeriesCacheService
{
	private static final Logger logger = LoggerFactory.getLogger(PriceTimeSeriesCacheService.class);

	@Value("${kdb.aggregate.query}")
	String aggregate_query;

	@Value("${kdb.price.query}")
	String price_query;

	@Value("${kdb.username}")
	String username;

	@Value("${kdb.password}")
	private String password;

	@Value("${kdb.port}")
	private int port;

	@Value("${kdb.hostname}")
	private String host;

	public List<PricePoint> getPrices()
	{
		Connection connection = null;
		List<PricePoint> pricePoints = new ArrayList<>();
		try
		{
			connection = new Connection(host, port, username + ":" + password, false);
			logger.info(String.format("Connecting to %s on port: %d using username: %s and password: %s", host, port, username, password));
			logger.info(String.format("Executing query: %s", price_query));
			Connection.Result result = (Connection.Result) connection.invoke(price_query);
			int symbolIndex = 0, dateIndex = 0, timeIndex = 0, priceIndex = 0, quantityIndex = 0;
			for(int index = 0; index < result.columnNames.length; ++index)
			{
				String key = result.columnNames[index];
				if(key.equalsIgnoreCase("dates"))
					dateIndex = index;
				else if (key.equalsIgnoreCase("symbols"))
					symbolIndex = index;
				else if (key.equalsIgnoreCase("times"))
					timeIndex = index;
				else if (key.equalsIgnoreCase("qtys"))
					quantityIndex = index;
				else if (key.equalsIgnoreCase("prices"))
					priceIndex = index;
			}

			String[] symbolValues = (String[]) result.columnValuesArrayOfArray[symbolIndex];
			Object[] dateValues = (Object[]) result.columnValuesArrayOfArray[dateIndex];
			long[] priceValues = (long[]) result.columnValuesArrayOfArray[priceIndex];
			java.sql.Time[] timeValues = (java.sql.Time[]) result.columnValuesArrayOfArray[timeIndex];
			long[] quantityValues = (long[]) result.columnValuesArrayOfArray[quantityIndex];

			logger.info("The first ten prices retrieved from KDB...");
			for(int index = 0; index < symbolValues.length; ++index)
			{
				if(index <= 10)
				logger.info(String.format("Symbol: %s, date: %s, price: %d, time:%s, quantity:%d",
						symbolValues[index], dateValues[index].toString(), priceValues[index], timeValues[index], quantityValues[index]));

				pricePoints.add(new PricePoint(symbolValues[index], dateValues[index].toString(),
						timeValues[index].toString(), priceValues[index], quantityValues[index]));
			}
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
				logger.info("Closed connection after executing query: " + price_query);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			return pricePoints;
		}
	}

	public List<PricePoint> getAggregates()
	{
		Connection connection = null;
		List<PricePoint> pricePoints = new ArrayList<>();

		try
		{
			connection = new Connection(host, port, username + ":" + password, false);
			logger.info(String.format("Connecting to %s on port: %d using username: %s and password: %s", host, port, username, password));
			logger.info(String.format("Executing query: %s", aggregate_query));

			final Object res = connection.invoke(aggregate_query);
			if (res instanceof Connection.Dict)
			{
				final Connection.Dict dict = (Connection.Dict) res;
				if ((dict.x instanceof Connection.Result) && (dict.y instanceof Connection.Result))
				{
					int symbolIndex = 0, dateIndex = 0, highIndex = 0, lowIndex = 0, closeIndex = 0, openIndex = 0;
					String[] keyNames = ((Connection.Result) dict.x).columnNames;
					for(int columnIndex = 0; columnIndex < keyNames.length; ++columnIndex)
					{
						if(keyNames[columnIndex].equalsIgnoreCase("dates"))
							dateIndex = columnIndex;
						else if (keyNames[columnIndex].equalsIgnoreCase("symbols"))
							symbolIndex = columnIndex;

						logger.info("column names: " + keyNames[columnIndex] + ", dateIndex: " + dateIndex + ", symbolIndex: " + symbolIndex);
					}

					String[] symbolValues = (String[]) ((Connection.Result) dict.x).columnValuesArrayOfArray[symbolIndex];
					Arrays.stream(symbolValues).forEach(logger::info);
					Object[] dateValues = (Object[]) ((Connection.Result) dict.x).columnValuesArrayOfArray[dateIndex];
					Arrays.stream(dateValues).forEach(x -> logger.info(String.valueOf(x)));


					String[] dataNames = ((Connection.Result) dict.y).columnNames;
					for(int columnIndex = 0; columnIndex < dataNames.length; ++columnIndex)
					{
						// TODO
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
				logger.info("Closed connection after executing query: " + aggregate_query);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			return pricePoints;
		}
	}
}

