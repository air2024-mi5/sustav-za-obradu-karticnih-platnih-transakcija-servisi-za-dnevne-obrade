package foi.air.szokpt.transproc.services;

import foi.air.szokpt.transproc.models.SelectedTransaction;
import foi.air.szokpt.transproc.models.TransactionProcessing;
import foi.air.szokpt.transproc.repositories.SelectedTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SelectedTransactionsService {

    private final SelectedTransactionRepository selectedTransactionRepository;

    public SelectedTransactionsService(
            SelectedTransactionRepository selectedTransactionRepository) {
        this.selectedTransactionRepository = selectedTransactionRepository;
    }

    public void AddSelectedTransactions(List<UUID> transactionGuids, String username) {
        List<SelectedTransaction> transactions = new ArrayList<>();
        TransactionProcessing scheduledProcessing = new TransactionProcessing();
        scheduledProcessing.setId(1); // TODO Fetch scheduled processing

        transactionGuids.forEach(guid -> {
            transactions.add(new SelectedTransaction(
                    guid, scheduledProcessing, username, LocalDateTime.now()
            ));
        });
        selectedTransactionRepository.saveAll(transactions);
    }

}
