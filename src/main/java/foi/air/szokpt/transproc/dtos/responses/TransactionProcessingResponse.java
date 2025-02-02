package foi.air.szokpt.transproc.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import foi.air.szokpt.transproc.dtos.BatchRecordDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionProcessingResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("scheduled_at")
    private LocalDateTime scheduledAt;

    @JsonProperty("processed_at")
    private LocalDateTime processedAt;

    @JsonProperty("batch_records")
    @OneToMany(mappedBy = "transactionProcessing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BatchRecordDto> batchRecords;

    @JsonProperty("processed_transactions_count")
    private Integer processedTransactionsCount;

    public TransactionProcessingResponse(
            String status,
            LocalDateTime scheduledAt,
            LocalDateTime processedAt,
            List<BatchRecordDto> batchRecords,
            Integer processedTransactionsCount
    ) {
        this.scheduledAt = scheduledAt;
        this.processedAt = processedAt;
        this.status = status;
        this.batchRecords = batchRecords;
        this.processedTransactionsCount = processedTransactionsCount;
    }

    public TransactionProcessingResponse() {
    }

    public void setBatchRecords(List<BatchRecordDto> batchRecords){
        this.batchRecords = batchRecords;
    }

    public void setProcessedTransactionsCount( Integer count){
        this.processedTransactionsCount = count;
    }

    public List<BatchRecordDto> getBatchRecords(){
        return this.batchRecords;
    }
}
