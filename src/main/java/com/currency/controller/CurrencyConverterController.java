package com.currency.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency.constants.Constants;
import com.currency.constants.ResultCodes;
import com.currency.model.ConversionRequest;
import com.currency.model.ConversionResponse;
import com.currency.model.ExchangeRateRequest;
import com.currency.model.ExchangeRateResponse;
import com.currency.service.CurrencyConverterServiceImpl;

@RestController
@RequestMapping("/currency")
public class CurrencyConverterController {

	static Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterController.class);

	@Autowired
	private CurrencyConverterServiceImpl currencyConverterService;

	@GetMapping("/rate")
	public ExchangeRateResponse getExchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest) {
		ExchangeRateResponse response = new ExchangeRateResponse();
		if (exchangeRateRequest != null && exchangeRateRequest.getCurrencyPair() != null) {
			response = currencyConverterService.getExchangeRate(exchangeRateRequest);
		} else {
			ResultCodes.NULL_PARAMETER.setResultDescWithParameter(Constants.CURRECY_PAIR);
			response.fillResponseHeader(ResultCodes.NULL_PARAMETER.getResultCode(),
					ResultCodes.NULL_PARAMETER.getResultDesc());
		}

		return response;
	}

	@GetMapping("/exchange")
	public ConversionResponse findConversion(@RequestBody ConversionRequest conversionRequest) {
		ConversionResponse response = new ConversionResponse();
		if (conversionRequest != null && conversionRequest.getSourceCurrency() != null
				&& conversionRequest.getTargetCurrency() != null && conversionRequest.getAmount() != null) {
			response = currencyConverterService.findConversion(conversionRequest);
		} else {
			if (conversionRequest.getSourceCurrency() == null) {
				ResultCodes.NULL_PARAMETER.setResultDescWithParameter(Constants.SOURCE_CURRENCY);
			} else if (conversionRequest.getTargetCurrency() == null) {
				ResultCodes.NULL_PARAMETER.setResultDescWithParameter(Constants.TARGET_CURRENCY);
			} else if (conversionRequest.getAmount() == null) {
				ResultCodes.NULL_PARAMETER.setResultDescWithParameter(Constants.AMOUNT);
			}
			response.fillResponseHeader(ResultCodes.NULL_PARAMETER.getResultCode(),
					ResultCodes.NULL_PARAMETER.getResultDesc());
		}

		return response;
	}
}
