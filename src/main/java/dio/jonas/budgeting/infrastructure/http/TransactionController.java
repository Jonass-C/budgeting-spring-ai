package dio.jonas.budgeting.infrastructure.http;

import dio.jonas.budgeting.application.ListTransactionByCategoryUseCase;
import dio.jonas.budgeting.application.PersistTransactionUseCase;
import dio.jonas.budgeting.domain.Category;
import dio.jonas.budgeting.infrastructure.http.request.TransactionRequest;
import dio.jonas.budgeting.infrastructure.http.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionByCategoryUseCase listTransactionByCategoryUseCase;

    public TransactionController(
            PersistTransactionUseCase persistTransactionUseCase,
            ListTransactionByCategoryUseCase listTransactionByCategoryUseCase
    ) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseCase = listTransactionByCategoryUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        var transaction = persistTransactionUseCase.execute(request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    public List<TransactionResponse> readTransactions(@PathVariable Category category) {
        return listTransactionByCategoryUseCase.execute(category)
                .stream()
                .map(TransactionResponse::from)
                .toList();
    }
}
