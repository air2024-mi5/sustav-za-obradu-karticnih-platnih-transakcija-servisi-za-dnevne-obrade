package foi.air.szokpt.transproc.services;

import foi.air.szokpt.transproc.clients.TransactionClient;
import foi.air.szokpt.transproc.dtos.BatchRecordDto;
import foi.air.szokpt.transproc.dtos.Transaction;
import foi.air.szokpt.transproc.dtos.responses.ProcessingPageData;
import foi.air.szokpt.transproc.dtos.responses.TransactionProcessingResponse;
import foi.air.szokpt.transproc.exceptions.TransactionProcessingException;
import foi.air.szokpt.transproc.models.BatchRecord;
import foi.air.szokpt.transproc.models.SelectedTransaction;
import foi.air.szokpt.transproc.models.TransactionProcessing;
import foi.air.szokpt.transproc.models.TransactionRecord;
import foi.air.szokpt.transproc.repositories.BatchRecordRepository;
import foi.air.szokpt.transproc.repositories.SelectedTransactionRepository;
import foi.air.szokpt.transproc.repositories.TransactionProcessingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessingService {

    private final int pageSize = 15;
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
            System.out.println(e);
            scheduledProcessing.setStatus("FAILED");
            transactionProcessingRepository.save(scheduledProcessing);

        } finally {
            sheduleNextProcessing();
        }
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
        transactionProcessingRepository.saveAndFlush(new TransactionProcessing(
                null,
                LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT),
                "PENDING"
        ));
    }

    public TransactionProcessingResponse getLastTransactionProcessing() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<TransactionProcessing> page = transactionProcessingRepository.getLastProcessings(pageable);
        List<TransactionProcessing> results = page.getContent();
        if (!results.isEmpty()) {
            TransactionProcessing transactionProcessing = results.getFirst();
            List<BatchRecordDto> batchRecordDtos = mapToDtoList(transactionProcessing.getBatchRecords());
            TransactionProcessingResponse lastProcessing = new TransactionProcessingResponse(
                    transactionProcessing.getStatus(),
                    transactionProcessing.getScheduledAt(),
                    transactionProcessing.getProcessedAt(),
                    batchRecordDtos,
                    0
            ) {
            };

            int processedTransactionsCount = lastProcessing.getBatchRecords().stream()
                    .mapToInt(batchRecordDto -> batchRecordDto.getTransactionRecords().size())
                    .sum();

            lastProcessing.setProcessedTransactionsCount(processedTransactionsCount);
            return lastProcessing;
        }
        return null;
    }

    private static List<BatchRecordDto> mapToDtoList(List<BatchRecord> batchRecords) {
        if (batchRecords == null) {
            return null;
        }
        List<BatchRecordDto> dtos = new ArrayList<>();
        for (BatchRecord batchRecord : batchRecords) {
            dtos.add(mapToDto(batchRecord));
        }
        return dtos;
    }

    private static BatchRecordDto mapToDto(BatchRecord batchRecord) {
        if (batchRecord == null) {
            return null;
        }

        return new BatchRecordDto(
                batchRecord.getBatchHeader(),
                batchRecord.getTerminalParameterRecord(),
                batchRecord.getBatchTrailer(),
                batchRecord.getTransactionRecords() != null
                        ? batchRecord.getTransactionRecords().stream()
                        .map(TransactionRecord::getRecord)
                        .toList()
                        : null
        );
    }

    public ProcessingPageData getTransactionProcessings(int page) {
        int pageNumber = calculatePageIndex(page, pageSize);
        int pageIndex = pageNumber - 1;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<TransactionProcessing> transactionProcessingsPage = transactionProcessingRepository.getLastProcessings(pageable);
        List<TransactionProcessing> transactionProcessings = transactionProcessingsPage.getContent();

        List<TransactionProcessingResponse> transactionProcessingResponses = new ArrayList<>();
        for(TransactionProcessing transactionProcessing : transactionProcessings ){
            List<BatchRecordDto> batchRecordDtos = mapToDtoList(transactionProcessing.getBatchRecords());

            TransactionProcessingResponse response = new TransactionProcessingResponse(
                    transactionProcessing.getStatus(),
                    transactionProcessing.getScheduledAt(),
                    transactionProcessing.getProcessedAt(),
                    batchRecordDtos,
                    0
            );

            int processedTransactionsCount = batchRecordDtos.stream()
                    .mapToInt(batchRecordDto -> batchRecordDto.getTransactionRecords().size())
                    .sum();

            response.setProcessedTransactionsCount(processedTransactionsCount);
            transactionProcessingResponses.add(response);
        }

        if (pageNumber == 1 && !transactionProcessingResponses.isEmpty()) {
            transactionProcessingResponses.removeFirst();
        }

        return new ProcessingPageData(transactionProcessingResponses,
                transactionProcessingsPage.getNumber() + 1,
                transactionProcessingsPage.getTotalPages()
        );
    }

    private int calculatePageIndex(int page, int pageSize) {
        long totalItems = transactionProcessingRepository.count();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        return Math.max(1, Math.min(page, totalPages));
    }
}

