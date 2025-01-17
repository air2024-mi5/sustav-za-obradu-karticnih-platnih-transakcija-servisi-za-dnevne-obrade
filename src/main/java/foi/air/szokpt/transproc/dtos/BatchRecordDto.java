package foi.air.szokpt.transproc.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import foi.air.szokpt.transproc.models.TransactionRecord;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class BatchRecordDto {

        @JsonProperty("batch_header")
        private String batchHeader;

        @JsonProperty("terminal_parameter_record")
        private String terminalParameterRecord;

        @JsonProperty("batch_trailer")
        private String batchTrailer;

        @JsonProperty("transaction_records")
        @OneToMany(mappedBy = "batchRecord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<TransactionRecord> transactionRecords;

        public BatchRecordDto(String batchTrailer,
                           String batchHeader,
                           String terminalParameterRecord
        ) {
            this.batchTrailer = batchTrailer;
            this.batchHeader = batchHeader;
            this.terminalParameterRecord = terminalParameterRecord;
        }


        public BatchRecordDto() {
        }

        public BatchRecordDto(String batchHeader,
                           String terminalParameterRecord,
                           String batchTrailer,
                           List<TransactionRecord> transactionRecords) {
            this.transactionRecords = transactionRecords;
            this.batchTrailer = batchTrailer;
            this.batchHeader = batchHeader;
            this.terminalParameterRecord = terminalParameterRecord;
        }

        public String getTerminalParameterRecord() {
            return terminalParameterRecord;
        }

        public void setTerminalParameterRecord(String terminalParameterRecord) {
            this.terminalParameterRecord = terminalParameterRecord;
        }

        public String getBatchHeader() {
            return batchHeader;
        }

        public void setBatchHeader(String batchHeader) {
            this.batchHeader = batchHeader;
        }

        public String getBatchTrailer() {
            return batchTrailer;
        }

        public void setBatchTrailer(String batchTrailer) {
            this.batchTrailer = batchTrailer;
        }

        public List<TransactionRecord> getTransactionRecords() {
            return transactionRecords;
        }

        public void setTransactionRecords(List<TransactionRecord> transactionRecords) {
            this.transactionRecords = transactionRecords;
        }
    }
