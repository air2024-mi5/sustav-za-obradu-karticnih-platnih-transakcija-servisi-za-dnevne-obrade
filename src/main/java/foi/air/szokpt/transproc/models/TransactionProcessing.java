package foi.air.szokpt.transproc.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transaction_processing")
public class TransactionProcessing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "batch_header")
    private String batchHeader;

    @Column(name = "batch_trailer")
    private String batchTrailer;


    @OneToMany(mappedBy = "transactionProcessing")
    private List<SelectedTransaction> selectedTransactions;

    public TransactionProcessing(Integer id,
                                 LocalDateTime scheduledAt,
                                 LocalDateTime processedAt,
                                 String status,
                                 String batchHeader,
                                 String batchTrailer,
                                 List<SelectedTransaction> selectedTransactions) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.processedAt = processedAt;
        this.status = status;
        this.batchHeader = batchHeader;
        this.batchTrailer = batchTrailer;
        this.selectedTransactions = selectedTransactions;
    }

    public TransactionProcessing(Integer id,
                                 LocalDateTime scheduledAt,
                                 String status) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.status = status;
    }

    public TransactionProcessing() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<SelectedTransaction> getSelectedTransactions() {
        return selectedTransactions;
    }

    public void setSelectedTransactions(List<SelectedTransaction> selectedTransactions) {
        this.selectedTransactions = selectedTransactions;
    }
}
