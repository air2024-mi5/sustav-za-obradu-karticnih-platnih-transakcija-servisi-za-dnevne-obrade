package foi.air.szokpt.transproc.services;

import foi.air.szokpt.transproc.dtos.SelectedTransactionDto;
import foi.air.szokpt.transproc.exceptions.TransactionProcessingException;
import foi.air.szokpt.transproc.models.SelectedTransaction;
import foi.air.szokpt.transproc.models.TransactionProcessing;
import foi.air.szokpt.transproc.repositories.SelectedTransactionRepository;
import foi.air.szokpt.transproc.repositories.TransactionProcessingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SelectedTransactionsService {

    private final SelectedTransactionRepository selectedTransactionRepository;
    private final TransactionProcessingRepository transactionProcessingRepository;

    public SelectedTransactionsService(
            SelectedTransactionRepository selectedTransactionRepository,
            TransactionProcessingRepository transactionProcessingRepository) {
        this.selectedTransactionRepository = selectedTransactionRepository;
        this.transactionProcessingRepository = transactionProcessingRepository;
    }

    public void AddSelectedTransactions(List<UUID> transactionGuids, String username) {
        List<SelectedTransaction> transactions = new ArrayList<>();
        TransactionProcessing scheduledProcessing = getScheduledProcessing();
        transactionGuids.forEach(guid -> {
            transactions.add(new SelectedTransaction(
                    guid, scheduledProcessing, username, LocalDateTime.now()
            ));
        });
        selectedTransactionRepository.saveAll(transactions);
    }

    public List<SelectedTransactionDto> getSelectedTransaction() {
        TransactionProcessing scheduledProcessing = getScheduledProcessing();
        List<SelectedTransaction> transactions = selectedTransactionRepository
                .findByTransactionProcessingId(scheduledProcessing.getId());

        List<SelectedTransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(transaction -> {
            transactionDtos.add(
                    new SelectedTransactionDto(
                            transaction.getGuid(),
                            transaction.getSelectedBy()
                    ));
        });

        return transactionDtos;
    }

    private TransactionProcessing getScheduledProcessing() {
        TransactionProcessing scheduledProcessing =
                transactionProcessingRepository.findScheduledTransactionProcessing(LocalDateTime.now().minusSeconds(5))
                        .stream().
                        findFirst().
                        orElseThrow(() -> new TransactionProcessingException(
                                "No scheduled processing found"));
        return scheduledProcessing;
    }

}
