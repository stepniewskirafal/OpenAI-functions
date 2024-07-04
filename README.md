# Spring AI Functions

## Overview

This project combines the power of OpenAI's language model with Spring Boot to provide an API that fetches and returns stock prices. 
The application uses OpenAI to interpret natural language questions related to stock prices and interacts with an external API to fetch the requested data.

## Features

- **Natural Language Processing**: Utilizes OpenAI's GPT-4 model to understand and process natural language questions.
- **Stock Price Retrieval**: Fetches current stock prices using the API Ninjas service.
- **Spring Boot Integration**: Built with Spring Boot for robust and scalable performance.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher
- An OpenAI API key
- An API Ninjas API key

### Installation

1. **Clone the Repository**
   ```sh
   git clone https://github.com/your-repo/spring-ai-functions.git
   cd spring-ai-functions
   ```

2. **Configure API Keys**
   Update the `application.yml` file with your OpenAI and API Ninjas keys.
   ```yaml
   spring:
     application:
       name: spring-ai-functions
     ai:
       openai:
         api-key: ${OPENAI_API_KEY}
         chat:
           options:
             model: gpt-4
   api-ninjas:
     api-key: ${API_NINJAS_KEY}
     api-url:
       stock-price: https://api.api-ninjas.com/v1/stockprice
   ```

3. **Build the Project**
   ```sh
   mvn clean install
   ```

4. **Run the Application**
   ```sh
   mvn spring-boot:run
   ```

### API Endpoints

#### Get Stock Price

- **URL**: `/stock`
- **Method**: `POST`
- **Request Body**: 
  ```json
  {
    "question": "What is the current price of AAPL stock?"
  }
  ```
- **Response**:
  ```json
  {
    "answer": "The current price of AAPL stock is $XXX.XX."
  }
  ```

## Code Structure

### Controllers

- **QuestionController**: Handles HTTP requests and invokes the OpenAI service to get the stock price.
  ```java
  @RestController
  @RequiredArgsConstructor
  public class QuestionController {
  
      private final OpenAIService openAIService;
  
      @PostMapping("/stock")
      public Answer getStockPrice(@RequestBody Question question) {
          return openAIService.getStockPrice(question);
      }
  }
  ```

### Services

- **OpenAIService**: Interface for defining methods to interact with OpenAI.
- **OpenAIServiceImpl**: Implementation of OpenAIService, processes the question and fetches stock price using OpenAI and StockQuoteFunction.
  ```java
  @Service
  @RequiredArgsConstructor
  public class OpenAIServiceImpl implements OpenAIService {
  
      final OpenAiChatClient openAiChatClient;
      final StockQuoteFunction stockQuoteFunction;
  
      @Override
      public Answer getStockPrice(Question question) {
          // Implementation details
      }
  }
  ```

### Models

- **Question**: Represents a question asked by the user.
  ```java
  public record Question(String question) {
  }
  ```
- **Answer**: Represents an answer returned to the user.
  ```java
  public record Answer(String answer) {
  }
  ```
- **StockPriceRequest**: Represents a request for stock price.
  ```java
  @JsonClassDescription("Stock price request")
  public record StockPriceRequest(@JsonPropertyDescription("ticker name of the stock to quote") String ticker) {
  }
  ```
- **StockPriceResponse**: Represents a response containing stock price details.
  ```java
  @JsonClassDescription("Stock price response")
  public record StockPriceResponse(@JsonPropertyDescription("ticker symbol of stock") String ticker,
                                   @JsonPropertyDescription("Company name") String name,
                                   @JsonPropertyDescription("Price of stock in USD") BigDecimal price,
                                   @JsonPropertyDescription("The exchange the stock is traded on") String exchange,
                                   @JsonPropertyDescription("Epoch Time of quote") Integer updated) {
  }
  ```

### Components

- **StockQuoteFunction**: Fetches stock price from API Ninjas.
  ```java
  @Component
  @RequiredArgsConstructor
  public class StockQuoteFunction implements Function<StockPriceRequest, StockPriceResponse> {
  
      @Value("${api-ninjas.api-url.stock-price}")
      private String stockUrl;
  
      @Value("${api-ninjas.api-key}")
      private String apiNinjasKey;
  
      @Override
      public StockPriceResponse apply(StockPriceRequest stockPriceRequest) {
          // Implementation details
      }
  }
  ```

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests.

## Acknowledgements

- OpenAI for the GPT-4 model
- API Ninjas for the stock price API
- Spring Framework for providing a robust foundation
