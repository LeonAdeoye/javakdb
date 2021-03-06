package com.leon.controllers;

import com.leon.model.TimeSeriesRequest;
import com.leon.services.PriceRetrievalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/price")
public class PriceTimeSeriesController
{
	private static final Logger logger = LoggerFactory.getLogger(PriceTimeSeriesController.class);
	@Autowired
	PriceRetrievalService priceRetrievalService;

	@CrossOrigin
	@RequestMapping("/heartbeat")
	String heartbeat()
	{
		return "I am here!";
	}

	@CrossOrigin
	@RequestMapping(value = "/time-series", method={POST}, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE )
	String getPrices(@RequestBody TimeSeriesRequest request)
	{
		if(request.getSymbol() == null || request.getSymbol().isEmpty())
			logger.error("Time series price request's symbol cannot be null or an empty string.");

		if(request.getStartDate() == null)
			logger.error("Start date cannot be NULL.");

		if(request.getEndDate() == null)
			logger.error("End date cannot be NULL.");

		logger.info(String.format("Received request for time series prices of symbol: %s with start date: %s and end date: %s", request.getSymbol(), request.getStartDate(), request.getEndDate()));
		return priceRetrievalService.getPrices(request).toString();
	}

	@CrossOrigin
	@RequestMapping(value = "/aggregates", method={POST}, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE )
	String getAggregates(@RequestBody TimeSeriesRequest request)
	{
		if(request.getSymbol() == null || request.getSymbol().isEmpty())
			logger.error("Time series aggregation request's symbol cannot be null or an empty string.");

		if(request.getStartDate() == null)
			logger.error("Start date cannot be NULL.");

		if(request.getEndDate() == null)
			logger.error("End date cannot be NULL.");

		logger.info(String.format("Received request to aggregate prices for symbol: %s with start date: %s and end date: %s", request.getSymbol(), request.getStartDate(), request.getEndDate()));
		return priceRetrievalService.getAggregates(request).toString();
	}
}

