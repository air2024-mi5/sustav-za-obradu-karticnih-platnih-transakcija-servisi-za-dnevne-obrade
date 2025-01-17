package foi.air.szokpt.transproc.services;

import foi.air.szokpt.transproc.clients.TransactionClient;
import foi.air.szokpt.transproc.dtos.BatchRecordDto;
import foi.air.szokpt.transproc.dtos.Transaction;
import foi.air.szokpt.transproc.dtos.responses.TransactionProcessingResponse;
import foi.air.szokpt.transproc.exceptions.TransactionProcessingException;
import foi.air.szokpt.transproc.models.BatchRecord;
import foi.air.szokpt.transproc.models.SelectedTransaction;
import foi.air.szokpt.transproc.models.TransactionProcessing;
import foi.air.szokpt.transproc.repositories.BatchRecordRepository;
import foi.air.szokpt.transproc.repositories.SelectedTransactionRepository;
import foi.air.szokpt.transproc.repositories.TransactionProcessingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessingService {

    private final TransactionClient transactionClient;
    private final TransactionProcessingRepository transactionProcessingRepository;
    private final SelectedTransactionRepository selectedTransactionRepository;
    private final TransactionProcessingEngine transactionProcessingEngine;
    private final BatchRecordRepository batchRecordRepository;

    public ProcessingService(TransactionClient transactionClient,
                             TransactionProcessingRepository transactionProcessingRepository,
                             SelectedTransactionRepository selectedTransactionRepository,
                             TransactionProcessingEngine transactionProcessingEngine,
                             BatchRecordRepository batchRecordRepository) {
        this.transactionClient = transactionClient;
        this.transactionProcessingRepository = transactionProcessingRepository;
        this.selectedTransactionRepository = selectedTransactionRepository;
        this.transactionProcessingEngine = transactionProcessingEngine;
        this.batchRecordRepository = batchRecordRepository;
    }


    public void startDailyProcessing() {
        TransactionProcessing scheduledProcessing;

        try {
            scheduledProcessing = getScheduledProcessing();
        } catch (Exception e) {
            sheduleNextProcessing();
            return;
        }
        try {
            List<SelectedTransaction> selectedTransactions = selectedTransactionRepository
                    .findByTransactionProcessingId(scheduledProcessing.getId());
            List<UUID> guids = selectedTransactions.stream()
                    .map(SelectedTransaction::getGuid)
                    .toList();

            List<Transaction> transactions = transactionClient.fetchTransactions(guids);
            List<BatchRecord> processedBatches = transactionProcessingEngine.processTransactions(transactions);
            transactionClient.sendProcessedTransactions(guids);

            for (BatchRecord record : processedBatches) {
                record.setTransactionProcessing(scheduledProcessing);
                batchRecordRepository.save(record);
            }

            scheduledProcessing.setStatus("COMPLETED");
            scheduledProcessing.setProcessedAt(LocalDateTime.now());
            transactionProcessingRepository.save(scheduledProcessing);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            scheduledProcessing.setStatus("FAILED");
            transactionProcessingRepository.save(scheduledProcessing);

        }
        sheduleNextProcessing();
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

    private void sheduleNextProcessing() {
        transactionProcessingRepository.save(new TransactionProcessing(
                null,
                LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT),
                "PENDING"
        ));
    }

    public TransactionProcessingResponse getLastTransactionProcessing() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<TransactionProcessingResponse> page = transactionProcessingRepository.getLastProcessing(pageable);
        List<TransactionProcessingResponse> results = page.getContent();

        if (!results.isEmpty()) {
            TransactionProcessingResponse lastProcessing = results.getFirst();

            List<BatchRecord> batchRecords = batchRecordRepository.getBatchRecordsByTransactionProcessingId(lastProcessing.getId());

            List<BatchRecordDto> batchRecordDtos = batchRecords.stream()
                    .map(br -> new BatchRecordDto(
                            br.getBatchHeader(),
                            br.getTerminalParameterRecord(),
                            br.getBatchTrailer(),
                            br.getTransactionRecords()
                    ))
                    .toList();

            int processedTransactionsCount = batchRecordDtos.stream()
                    .mapToInt(batchRecordDto -> batchRecordDto.getTransactionRecords().size())
                    .sum();

            lastProcessing.setBatchRecords(batchRecordDtos);
            lastProcessing.setProcessedTransactionsCount(processedTransactionsCount);

            return lastProcessing;
        }
        return null;
    }
}
