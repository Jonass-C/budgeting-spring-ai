package dio.jonas.budgeting.application;

import dio.jonas.budgeting.application.input.PersistTransactionInput;
import dio.jonas.budgeting.application.output.TransactionOutput;
import dio.jonas.budgeting.domain.Transaction;
import dio.jonas.budgeting.domain.TransactionRepository;

public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionOutput execute(PersistTransactionInput input) {
        var transaction = transactionRepository.save(
                new Transaction(
                        input.description(),
                        input.amount(),
                        input.category()
                )
        );
        return TransactionOutput.from(transaction);
    }
}
