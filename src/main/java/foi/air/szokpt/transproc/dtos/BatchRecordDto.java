package foi.air.szokpt.transproc.dtos;

import java.util.List;

public class BatchRecordDto {
    private String batchHeader;
    private String terminalParameterRecord;
    private String batchTrailer;
    private List<String> transactionRecords;

    public BatchRecordDto(String batchHeader,
                          String terminalParameterRecord,
                          String batchTrailer,
                          List<String> transactionRecords) {
        this.batchHeader = batchHeader;
        this.terminalParameterRecord = terminalParameterRecord;
        this.batchTrailer = batchTrailer;
        this.transactionRecords = transactionRecords;
    }

    public BatchRecordDto() {
    }

    public String getBatchHeader() {
        return batchHeader;
    }

    public void setBatchHeader(String batchHeader) {
        this.batchHeader = batchHeader;
    }

    public String getTerminalParameterRecord() {
        return terminalParameterRecord;
    }

    public void setTerminalParameterRecord(String terminalParameterRecord) {
        this.terminalParameterRecord = terminalParameterRecord;
    }

    public String getBatchTrailer() {
        return batchTrailer;
    }

    public void setBatchTrailer(String batchTrailer) {
        this.batchTrailer = batchTrailer;
    }

    public List<String> getTransactionRecords() {
        return transactionRecords;
    }

    public void setTransactionRecords(List<String> transactionRecords) {
        this.transactionRecords = transactionRecords;
    }

}
