# foreign_currency_converter
An API that gives exchange rates and behaves like exchange currency converter.

You can join my Postman team in order to see the requests via this link: https://app.getpostman.com/join-team?invite_code=ff4a976fa3f1f5025b90a6da0f23d21a

First of all, I used https://currencylayer.com/ API for getting the live exchange rates. Beside this, in order to test this project, Postman app is used. 

There is 2 services in this project:

  1. getExchangeRate  
    In order to use that service, you must provide an JSON input to the http://localhost:8080/currency/rate like that:
      { 
        "currencyPair": "GBPTRY" 
      }
    Via this request, you can reach "GBPTRY" live exchange rate. In other words, the above example gives the money on the basis of TRY that equals to 1 GBP at that moment. Source currency is the first 3 characters of the currencyPair and target currency is the remaning 3.
    The response of above example is like that:
      {
        "resultCode": 200,
        "resultDesc": "Operation Successful",
        "exchangeRate": 1.30457187
      }
      
  2. findConversion
    In order to use that service, you must provide an JSON input to the http://localhost:8080/currency/exchange like that:
      {
	      "sourceCurrency":"GBP",
	      "targetCurrency":"TRY",
	      "amount":"1000"
      }
    Via this request, you can acquire how much TRY you get in exchange with 1000 GBP. The response of above is like that:
      {
        "resultCode": 200,
        "resultDesc": "Operation Successful",
        "transactionId": 1549291736315,
        "targetAmount": 6813.44622
      }
