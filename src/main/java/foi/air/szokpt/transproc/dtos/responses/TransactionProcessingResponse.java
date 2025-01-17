package foi.air.szokpt.transproc.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import foi.air.szokpt.transproc.dtos.BatchRecordDto;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionProcessingResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("scheduled_at")
    private LocalDateTime scheduledAt;

    @JsonProperty("processed_at")
    private LocalDateTime processedAt;

    @JsonProperty("batch_records")
    private List<BatchRecordDto> batchRecords;

    @JsonProperty("processed_transactions_count")
    private Integer processedTransactionsCount;

    public TransactionProcessingResponse(
            Integer id,
            String status,
            LocalDateTime scheduledAt,
            LocalDateTime processedAt,
            List<BatchRecordDto> batchRecords,
            Integer processedTransactionsCount
    ) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.processedAt = processedAt;
        this.status = status;
        this.batchRecords = batchRecords;
        this.processedTransactionsCount = processedTransactionsCount;
    }

    public TransactionProcessingResponse() {
    }

    public Integer getId(){
        return this.id;
    }

    public void setBatchRecords(List<BatchRecordDto> batchRecords){
        this.batchRecords = batchRecords;
    }

    public void setProcessedTransactionsCount( Integer count){
        this.processedTransactionsCount = count;
    }
}
