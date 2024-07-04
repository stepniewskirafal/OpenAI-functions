package pl.stepniewski.stockagent.services;

import pl.stepniewski.stockagent.model.Answer;
import pl.stepniewski.stockagent.model.Question;

public interface OpenAIService {
    Answer getStockPrice(Question question);
}
