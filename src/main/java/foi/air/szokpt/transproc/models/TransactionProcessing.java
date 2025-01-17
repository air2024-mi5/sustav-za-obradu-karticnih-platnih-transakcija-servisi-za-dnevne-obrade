package foi.air.szokpt.transproc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "transactionProcessing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SelectedTransaction> selectedTransactions;

    @OneToMany(mappedBy = "transactionProcessing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BatchRecord> batchRecords;

    public TransactionProcessing(Integer id,
                                 LocalDateTime scheduledAt,
                                 LocalDateTime processedAt,
                                 String status,
                                 List<SelectedTransaction> selectedTransactions) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.processedAt = processedAt;
        this.status = status;
        this.selectedTransactions = selectedTransactions;
    }


    public TransactionProcessing() {
    }

    public TransactionProcessing(Integer id,
                                 LocalDateTime scheduledAt,
                                 String status) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.status = status;
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

    public List<SelectedTransaction> getSelectedTransactions() {
        return selectedTransactions;
    }

    public void setSelectedTransactions(List<SelectedTransaction> selectedTransactions) {
        this.selectedTransactions = selectedTransactions;
    }

    public List<BatchRecord> getBatchRecords() {
        return batchRecords;
    }

    public void setBatchRecords(List<BatchRecord> batchRecords) {
        this.batchRecords = batchRecords;
    }
}
