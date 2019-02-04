package com.currency.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.currency.constants.ResultCodes;
import com.currency.exception.CurrencyException;
import com.currency.exception.ExternalServiceException;
import com.currency.model.ConversionRequest;
import com.currency.model.ConversionResponse;
import com.currency.model.ExchangeRateRequest;
import com.currency.model.ExchangeRateResponse;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

	private final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	
	// list currencies service
	@Value("${exchange.list.service.url}")
	protected String listExchangeUrl;
	
	// live exchange service
	@Value("${exchange.live.service.url}")
	protected String liveExchangeUrl;

	// access key
	@Value("${access.key}")
	private String accessKey;

	@Override
	public ExchangeRateResponse getExchangeRate(ExchangeRateRequest exchangeRateRequest) {
		LOGGER.info("getExchangeRate begins for currency: {}", exchangeRateRequest.getCurrencyPair());
		ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
		try {
			if (exchangeRateRequest.getCurrencyPair().length() == 6) {
				String sourceCurrency = exchangeRateRequest.getCurrencyPair().substring(0, 3);
				String targetCurrency = exchangeRateRequest.getCurrencyPair().substring(3);
				BigDecimal rate = getRateAndCheckService(sourceCurrency, targetCurrency);
				exchangeRateResponse.setExchangeRate(rate);
				exchangeRateResponse.fillResponseHeader(ResultCodes.OPERATION_SUCCESSFUL.getResultCode(),
						ResultCodes.OPERATION_SUCCESSFUL.getResultDesc());
				LOGGER.info("getExchangeRate has ended successfulRy for currency: {}",
						exchangeRateRequest.getCurrencyPair());
			} else {
				throw new CurrencyException(
						"Error at the number of chars of currencyPair for currencyPair: "
								+ exchangeRateRequest.getCurrencyPair(),
						ResultCodes.MISSING_CHARACTER.getResultCode(), ResultCodes.MISSING_CHARACTER.getResultDesc());
			}
		} catch (CurrencyException e) {
			LOGGER.error(e.getMessage(), e);
			exchangeRateResponse.fillResponseHeader(e.getErrorCode(), e.getErrorDesc());
		} catch (ExternalServiceException e) {
			LOGGER.error(e.getMessage(), e);
			exchangeRateResponse.fillResponseHeader(e.getErrorCode(), e.getErrorDesc());
		} catch (Exception e) {
			LOGGER.error("Fatal error occured", e);
			exchangeRateResponse.fillResponseHeader(ResultCodes.FATAL_ERROR.getResultCode(),
					ResultCodes.FATAL_ERROR.getResultDesc());
		}

		return exchangeRateResponse;
	}

	@Override
	public ConversionResponse findConversion(ConversionRequest conversionRequest) {
		LOGGER.info("findConversion begins for currency: {}", conversionRequest.getTargetCurrency());
		ConversionResponse conversionResponse = new ConversionResponse();
		try {
			Long transactionId = new Date().getTime();
			BigDecimal rate = getRateAndCheckService(conversionRequest.getSourceCurrency(),
					conversionRequest.getTargetCurrency());
			BigDecimal targetAmount = conversionRequest.getAmount().multiply(rate);
			conversionResponse.setTransactionId(transactionId);
			conversionResponse.setTargetAmount(targetAmount);
			conversionResponse.fillResponseHeader(ResultCodes.OPERATION_SUCCESSFUL.getResultCode(),
					ResultCodes.OPERATION_SUCCESSFUL.getResultDesc());
			LOGGER.info("findConversion has ended successfully for currency: {}",
					conversionRequest.getTargetCurrency());
		} catch (CurrencyException e) {
			LOGGER.error(e.getMessage(), e);
			conversionResponse.fillResponseHeader(e.getErrorCode(), e.getErrorDesc());
		} catch (ExternalServiceException e) {
			LOGGER.error(e.getMessage(), e);
			conversionResponse.fillResponseHeader(e.getErrorCode(), e.getErrorDesc());
		} catch (Exception e) {
			LOGGER.error("fatal error occured", e);
			conversionResponse.fillResponseHeader(ResultCodes.FATAL_ERROR.getResultCode(),
					ResultCodes.FATAL_ERROR.getResultDesc());
		}

		return conversionResponse;
	}

	@SuppressWarnings("unchecked")
	private BigDecimal getRateAndCheckService(String sourceCurrency, String targetCurrency) {
		BigDecimal rate = null;
		try {
			checkCurrencies(sourceCurrency, targetCurrency);
			LOGGER.info("Initiating External service request for {}", sourceCurrency + targetCurrency);
			URI targetUrl = UriComponentsBuilder.fromUriString(liveExchangeUrl).queryParam("access_key", accessKey)
					.queryParam("currencies", sourceCurrency + "," + targetCurrency).build().toUri();
			LOGGER.info("Initiating Get request {}", targetUrl);
			Map<String, Object> response = restTemplate.getForObject(targetUrl, Map.class);
			LOGGER.info("Received parameters: {}", response);
			checkResponse(response);
			LOGGER.info("checkResponse is successful. Operation is continuing");
			Map<String, Number> rates = (Map<String, Number>) response.get("quotes");
			BigDecimal sourceRate = new BigDecimal(rates.get("USD" + sourceCurrency).doubleValue()).setScale(8,
					RoundingMode.HALF_UP);
			LOGGER.info("SourceRate: {}", sourceRate);
			BigDecimal targetRate = new BigDecimal(rates.get("USD" + targetCurrency).doubleValue()).setScale(8,
					RoundingMode.HALF_UP);
			LOGGER.info("TargetRate: {}", targetRate);
			rate = targetRate.divide(sourceRate, 8, RoundingMode.HALF_UP);
		} catch (CurrencyException e) {
			throw e;
		} catch (ExternalServiceException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			LOGGER.info("getRateAndCheckService has ended. Rate: {}", rate);
		}

		return rate;
	}
	
	@SuppressWarnings("unchecked")
	private void checkCurrencies(String sourceCurrency, String targetCurrency) {
		LOGGER.info("Initiating checkCurrencies for {}", sourceCurrency + targetCurrency);
		URI targetUrl = UriComponentsBuilder.fromUriString(listExchangeUrl).queryParam("access_key", accessKey).build()
				.toUri();
		LOGGER.info("Initiating Get request {}", targetUrl);
		Map<String, Object> response = restTemplate.getForObject(targetUrl, Map.class);
		Map<String, String> currencies = (Map<String, String>) response.get("currencies");
		if (!currencies.containsKey(sourceCurrency) || !currencies.containsKey(targetCurrency)) {
			throw new CurrencyException("There is no currency for: " + sourceCurrency + targetCurrency,
					ResultCodes.NOT_FOUND_CURRENCY.getResultCode(), ResultCodes.NOT_FOUND_CURRENCY.getResultDesc());
		}
	}

	@SuppressWarnings("rawtypes")
	private void checkResponse(Map<String, Object> rawParameters) {
		if (rawParameters.containsKey("success")) {
			if (!(Boolean) rawParameters.get("success")) {
				Map error = (Map) rawParameters.get("error");
				throw new ExternalServiceException("Error calling service " + liveExchangeUrl,
						(Integer) error.get("code"), (String) error.get("info"));
			}
		}
	}
}
