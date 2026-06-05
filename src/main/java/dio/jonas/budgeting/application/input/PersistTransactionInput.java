package dio.jonas.budgeting.application.input;

import dio.jonas.budgeting.domain.Category;

public record PersistTransactionInput(
        String description,
        long amount,
        Category category) {
}
