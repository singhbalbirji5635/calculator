package com.balbir.calculator;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    private final HistoryService historyService;

    public CalculatorController(HistoryService historyService) {
        this.historyService = historyService;
    }

    private BigDecimal performOperation(BigDecimal op1, BigDecimal op2, String operator) {
        BigDecimal result = null;
        switch (operator) {
            case "+":
                result = op1.add(op2);
                break;
            case "-":
                result = op1.subtract(op2);
                break;
            case "*":
                result = op1.multiply(op2);
                break;
            case "/":
                try {
                    result = op1.divide(op2);
                } catch (ArithmeticException e) {
                    historyService.addHistory(op1, op2, "/", "Error: Division by Zero");
                    throw e;
                }
                break;
        }
        if (result != null)
            historyService.addHistory(op1, op2, operator, result.toString());
        return result;
    }

    @GetMapping("/add")
    public BigDecimal add(@RequestParam BigDecimal op1, @RequestParam BigDecimal op2) {
        return performOperation(op1, op2, "+");
    }

    @GetMapping("/minus")
    public BigDecimal minus(@RequestParam BigDecimal op1, @RequestParam BigDecimal op2) {
        return performOperation(op1, op2, "-");
    }

    @GetMapping("/product")
    public BigDecimal product(@RequestParam BigDecimal op1, @RequestParam BigDecimal op2) {
        return performOperation(op1, op2, "*");
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal op1, @RequestParam BigDecimal op2) {
        return performOperation(op1, op2, "/");
    }

    @GetMapping("/history")
    public String history() {
        return historyService.getHistory();
    }

    @GetMapping("/clear")
    public String clear() {
        historyService.clearHistory();
        return "History cleared!";
    }
}
