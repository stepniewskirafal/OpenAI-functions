package pl.stepniewski.stockagent.functions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.stepniewski.stockagent.model.StockPriceRequest;
import pl.stepniewski.stockagent.model.StockPriceResponse;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class StockQuoteFunction implements Function<StockPriceRequest, StockPriceResponse> {

    @Value("${api-ninjas.api-url.stock-price}")
    private String stockUrl;

    @Value("${api-ninjas.api-key}")
    private String apiNinjasKey;

    @Override
    public StockPriceResponse apply(StockPriceRequest stockPriceRequest) {

        RestClient restClient = RestClient.builder()
                .baseUrl(stockUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();

        JsonNode jsonNode = restClient.get().uri(uriBuilder -> uriBuilder.queryParam("ticker", stockPriceRequest.ticker()).build())
                .retrieve().body(JsonNode.class);

        return jsonNode.isEmpty()
                ? new StockPriceResponse(null, null, null, null, null)
                : new ObjectMapper().convertValue(jsonNode, StockPriceResponse.class);
    }
}















