package foi.air.szokpt.transproc.services;

import foi.air.szokpt.transproc.clients.TransactionClient;
import foi.air.szokpt.transproc.dtos.Transaction;
import foi.air.szokpt.transproc.exceptions.TransactionProcessingException;
import foi.air.szokpt.transproc.models.SelectedTransaction;
import foi.air.szokpt.transproc.models.TransactionProcessing;
import foi.air.szokpt.transproc.repositories.SelectedTransactionRepository;
import foi.air.szokpt.transproc.repositories.TransactionProcessingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProcessingService {

    private TransactionClient transactionClient;
    private TransactionProcessingRepository transactionProcessingRepository;
    private SelectedTransactionRepository selectedTransactionRepository;

    public ProcessingService(TransactionClient transactionClient,
                             TransactionProcessingRepository transactionProcessingRepository,
                             SelectedTransactionRepository selectedTransactionRepository) {
        this.transactionClient = transactionClient;
        this.transactionProcessingRepository = transactionProcessingRepository;
        this.selectedTransactionRepository = selectedTransactionRepository;
    }


    public void startDailyProcessing() {
        TransactionProcessing scheduledProcessing;
        try {
            scheduledProcessing = getScheduledProcessing();
        } catch (Exception e) {
            return;
        }
        List<SelectedTransaction> selectedTransactions = selectedTransactionRepository
                .findByTransactionProcessingId(scheduledProcessing.getId());
        List<UUID> guids = selectedTransactions.stream()
                .map(SelectedTransaction::getGuid)
                .toList();
        
        List<Transaction> transactions = transactionClient.fetchTransactions(guids);

    }

    private TransactionProcessing getScheduledProcessing() {
        TransactionProcessing scheduledProcessing =
                transactionProcessingRepository.findScheduledTransactionProcessing()
                        .stream().
                        findFirst().
                        orElseThrow(() -> new TransactionProcessingException(
                                "No scheduled processing found"));
        return scheduledProcessing;
    }

}
