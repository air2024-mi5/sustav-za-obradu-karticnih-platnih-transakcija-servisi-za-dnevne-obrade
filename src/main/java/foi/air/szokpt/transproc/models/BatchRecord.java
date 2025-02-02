package foi.air.szokpt.transproc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "batch_records")
public class BatchRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "batch_header")
    private String batchHeader;

    @Column(name = "terminal_parameter_record")
    private String terminalParameterRecord;

    @Column(name = "batch_trailer")
    private String batchTrailer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_processing_id")
    @JsonIgnore
    private TransactionProcessing transactionProcessing;


    @OneToMany(mappedBy = "batchRecord", cascade = CascadeType.PERSIST)
    private List<TransactionRecord> transactionRecords;

    public BatchRecord(String batchTrailer,
                       String batchHeader,
                       String terminalParameterRecord,
                       TransactionProcessing transactionProcessing) {
        this.batchTrailer = batchTrailer;
        this.batchHeader = batchHeader;
        this.terminalParameterRecord = terminalParameterRecord;
        this.transactionProcessing = transactionProcessing;
    }


    public BatchRecord() {
    }

    public BatchRecord(String batchHeader,
                       String terminalParameterRecord,
                       String batchTrailer,
                       List<TransactionRecord> transactionRecords) {
        this.transactionRecords = transactionRecords;
        this.batchTrailer = batchTrailer;
        this.batchHeader = batchHeader;
        this.terminalParameterRecord = terminalParameterRecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TransactionProcessing getTransactionProcessing() {
        return transactionProcessing;
    }

    public void setTransactionProcessing(TransactionProcessing transactionProcessing) {
        this.transactionProcessing = transactionProcessing;
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
