package com.leon.services;

import com.leon.model.PriceAggregation;
import com.leon.model.PricePoint;
import com.leon.model.TimeSeriesRequest;
import kx.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceRetrievalService
{
	private static final Logger logger = LoggerFactory.getLogger(PriceRetrievalService.class);

	@Value("${kdb.username}")
	String username;

	@Value("${kdb.password}")
	private String password;

	@Value("${kdb.port}")
	private int port;

	@Value("${kdb.hostname}")
	private String host;


	private static LocalDate convertToLocalDate(java.sql.Date dateToConvert)
	{
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}

	public List<PricePoint> getPrices(TimeSeriesRequest request)
	{
		Connection connection = null;
		List<PricePoint> pricePoints = new ArrayList<>();
		String price_query = String.format("select from .basics.prices where symbols = `%s, dates >= %s, dates <= %s", request.getSymbol(), request.getStartDate(), request.getEndDate());
		try
		{
			connection = new Connection(host, port, username + ":" + password, false);
			logger.info(String.format("Connecting to %s on port: %d using username: %s and password: %s", host, port, username, password));
			logger.info(String.format("Executing query:\n%s", price_query));
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
			java.sql.Date[] dateValues = (java.sql.Date[]) result.columnValuesArrayOfArray[dateIndex];
			long[] priceValues = (long[]) result.columnValuesArrayOfArray[priceIndex];
			java.sql.Time[] timeValues = (java.sql.Time[]) result.columnValuesArrayOfArray[timeIndex];
			long[] quantityValues = (long[]) result.columnValuesArrayOfArray[quantityIndex];

			logger.info("The first five prices retrieved from KDB...");
			for(int index = 0; index < symbolValues.length; ++index)
			{
				if(index < 5)
				logger.info(String.format("Symbol: %s, date: %s, price: %d, time:%s, quantity:%d",
						symbolValues[index], dateValues[index].toString(), priceValues[index], timeValues[index].toLocalTime(), quantityValues[index]));

				pricePoints.add(new PricePoint(symbolValues[index], convertToLocalDate(dateValues[index]),
						timeValues[index].toLocalTime(), priceValues[index], quantityValues[index]));
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
				logger.info("Closed connection after a total of " + pricePoints.size() + " record(s) were returned from KDB.");
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
	public List<PriceAggregation> getAggregates(TimeSeriesRequest request)
	{
		Connection connection = null;
		List<PriceAggregation> priceAggregations = new ArrayList<>();
		String aggregate_query = String.format("select high: max prices, low: min prices, open: first prices, close: last prices by symbols, dates from .basics.prices where symbols = `%s, dates >= %s, dates <= %s", request.getSymbol(), request.getStartDate(), request.getEndDate());
		try
		{
			connection = new Connection(host, port, username + ":" + password, false);
			logger.info(String.format("Connecting to %s on port: %d using username: %s and password: %s", host, port, username, password));
			logger.info(String.format("Executing query:\n%s", aggregate_query));

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
					java.sql.Date[] dateValues = (java.sql.Date[]) ((Connection.Result) dict.keys).columnValuesArrayOfArray[dateIndex];
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
								closeValues[index], openValues[index], symbolValues[index], convertToLocalDate(dateValues[index])));
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
				logger.info("Closed connection after a total of " + priceAggregations.size() + " record(s) were returned from KDB.");
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			return priceAggregations;
		}
	}
}

