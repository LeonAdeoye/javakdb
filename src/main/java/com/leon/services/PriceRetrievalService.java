package com.leon.services;

import com.leon.model.PriceAggregation;
import com.leon.model.PricePoint;
import kx.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceRetrievalService
{
	private static final Logger logger = LoggerFactory.getLogger(PriceRetrievalService.class);

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

			logger.info("The first five prices retrieved from KDB...");
			for(int index = 0; index < symbolValues.length; ++index)
			{
				if(index < 5)
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
				logger.info("Closed connection after a total of " + pricePoints.size() + " record(s) were returned from KDB by executing the query:\n" + price_query);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			return pricePoints;
		}
	}

	// Aggregated result sets returned by KDB contain both key values and data values in the same table so
	// that you either need to use dictionary to traverse the result set or you prefix your query with 0!
	public List<PriceAggregation> getAggregates()
	{
		Connection connection = null;
		List<PriceAggregation> priceAggregations = new ArrayList<>();
		try
		{
			connection = new Connection(host, port, username + ":" + password, false);
			logger.info(String.format("Connecting to %s on port: %d using username: %s and password: %s", host, port, username, password));
			logger.info(String.format("Executing query: %s", aggregate_query));

			final Object dictionary = connection.invoke(aggregate_query);
			if (dictionary instanceof Connection.Dictionary)
			{
				final Connection.Dictionary dict = (Connection.Dictionary) dictionary;
				if ((dict.keys instanceof Connection.Result) && (dict.values instanceof Connection.Result))
				{
					int symbolIndex = 0, dateIndex = 0;
					String[] keyNames = ((Connection.Result) dict.keys).columnNames;
					for(int columnIndex = 0; columnIndex < keyNames.length; ++columnIndex)
					{
						if(keyNames[columnIndex].equalsIgnoreCase("dates"))
							dateIndex = columnIndex;
						else if (keyNames[columnIndex].equalsIgnoreCase("symbols"))
							symbolIndex = columnIndex;
					}

					int highIndex = 0, lowIndex = 0, closeIndex = 0, openIndex = 0;
					String[] dataNames = ((Connection.Result) dict.values).columnNames;
					for(int columnIndex = 0; columnIndex < dataNames.length; ++columnIndex)
					{
						if(dataNames[columnIndex].equalsIgnoreCase("high"))
							highIndex = columnIndex;
						else if (dataNames[columnIndex].equalsIgnoreCase("low"))
							lowIndex = columnIndex;
						else if (dataNames[columnIndex].equalsIgnoreCase("open"))
							openIndex = columnIndex;
						else if (dataNames[columnIndex].equalsIgnoreCase("close"))
							closeIndex = columnIndex;
					}

					String[] symbolValues = (String[]) ((Connection.Result) dict.keys).columnValuesArrayOfArray[symbolIndex];
					Object[] dateValues = (Object[]) ((Connection.Result) dict.keys).columnValuesArrayOfArray[dateIndex];
					long[] highValues = (long[]) ((Connection.Result) dict.values).columnValuesArrayOfArray[highIndex];
					long[] lowValues = (long[]) ((Connection.Result) dict.values).columnValuesArrayOfArray[lowIndex];
					long[] closeValues = (long[]) ((Connection.Result) dict.values).columnValuesArrayOfArray[closeIndex];
					long[] openValues = (long[]) ((Connection.Result) dict.values).columnValuesArrayOfArray[openIndex];

					logger.info("The first five aggregated prices retrieved from KDB...");
					for(int index = 0; index < highValues.length; ++index)
					{
						if(index < 5)
							logger.info(String.format("Symbol: %s, date: %s, high: %d, low:%d, close:%d, open:%d",
									symbolValues[index], dateValues[index].toString(), highValues[index], lowValues[index], closeValues[index], openValues[index]));

						priceAggregations.add(new PriceAggregation(highValues[index], lowValues[index],
								closeValues[index], openValues[index], symbolValues[index], dateValues[index].toString()));
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
				logger.info("Closed connection after a total of " + priceAggregations.size() + " record(s) were returned from KDB by executing the query:\n" + aggregate_query);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			return priceAggregations;
		}
	}
}

