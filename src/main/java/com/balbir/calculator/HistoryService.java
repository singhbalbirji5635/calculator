package com.balbir.calculator;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    // Using thread-safe list to store the history
    private final List<String> history = new CopyOnWriteArrayList<>();

    public void addHistory(BigDecimal op1, BigDecimal op2, String operator, String result) {
        String record = String.format("%s %s %s = %s", op1, operator, op2, result);
        history.add(record);
    }

    public String getHistory() {
        if (history.isEmpty()) {
            return "No history yet!";
        }
        return history.stream().collect(Collectors.joining("<br>"));
    }

    public void clearHistory() {
        history.clear();
    }
}
