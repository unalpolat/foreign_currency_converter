package com.currency.service;

import com.currency.model.ConversionRequest;
import com.currency.model.ConversionResponse;
import com.currency.model.ExchangeRateRequest;
import com.currency.model.ExchangeRateResponse;

public interface CurrencyConverterService {
	ExchangeRateResponse getExchangeRate(ExchangeRateRequest exchangeRateRequest);
	ConversionResponse findConversion(ConversionRequest conversionRequest);
}
