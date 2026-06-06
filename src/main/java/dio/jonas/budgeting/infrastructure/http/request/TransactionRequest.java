package dio.jonas.budgeting.infrastructure.http.request;

import dio.jonas.budgeting.application.input.PersistTransactionInput;
import dio.jonas.budgeting.domain.Category;

public record TransactionRequest(
        String description,
        Category category,
        long amount
) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(
                description,
                amount,
                category
        );
    }
}
