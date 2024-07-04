package pl.stepniewski.stockagent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import pl.stepniewski.stockagent.functions.StockQuoteFunction;
import pl.stepniewski.stockagent.model.Answer;
import pl.stepniewski.stockagent.model.Question;
import pl.stepniewski.stockagent.model.StockPriceResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    final OpenAiChatClient openAiChatClient;
    final StockQuoteFunction stockQuoteFunction;

    @Override
    public Answer getStockPrice(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(
                        List.of(
                                FunctionCallbackWrapper.builder(stockQuoteFunction)
                                    .withName("CurrentStockPrice")
                                    .withDescription("Get the current stock price for a stock symbol (ticker)")
                                    .withResponseConverter((response) -> {
                                    String schema = ModelOptionsUtils.getJsonSchema(StockPriceResponse.class, false);
                                    String json = ModelOptionsUtils.toJsonString(response);
                                    return schema + "\n" + json;
                                    })
                                .build()
                        )
                )
                .build();

        var userMessage   = new PromptTemplate(question.question()).createMessage();
        var systemMessage = new SystemPromptTemplate("You are an agent capable of returning the current stock price for" +
                                                        " a given stock symbol (ticker) and comparing their values.")
                                                        .createMessage();

        var response = openAiChatClient.call(new Prompt(List.of(userMessage, systemMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getContent());
    }
}


















