package pl.stepniewski.stockagent.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.stepniewski.stockagent.model.Answer;
import pl.stepniewski.stockagent.model.Question;
import pl.stepniewski.stockagent.services.OpenAIService;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/stock")
    public Answer getStockPrice(@RequestBody Question question) {
        return openAIService.getStockPrice(question);
    }

}
