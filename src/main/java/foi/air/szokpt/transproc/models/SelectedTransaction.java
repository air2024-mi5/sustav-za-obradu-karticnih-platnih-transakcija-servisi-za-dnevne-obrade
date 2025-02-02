package foi.air.szokpt.transproc.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "selected_transactions")
public class SelectedTransaction {
    @Id
    @Column(name = "guid", nullable = false, unique = true)
    private UUID guid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_processing_id", nullable = false)
    private TransactionProcessing transactionProcessing;

    @Column(name = "selected_by", nullable = false)
    private String selectedBy;

    @Column(name = "selected_at", nullable = false)
    private LocalDateTime selectedAt;

    public SelectedTransaction(UUID guid,
                               TransactionProcessing transactionProcessing,
                               String selectedBy,
                               LocalDateTime selectedAt) {
        this.guid = guid;
        this.transactionProcessing = transactionProcessing;
        this.selectedBy = selectedBy;
        this.selectedAt = selectedAt;
    }

    public SelectedTransaction() {
    }


    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public TransactionProcessing getTransactionProcessing() {
        return transactionProcessing;
    }

    public void setTransactionProcessing(TransactionProcessing transactionProcessing) {
        this.transactionProcessing = transactionProcessing;
    }

    public String getSelectedBy() {
        return selectedBy;
    }

    public void setSelectedBy(String selectedBy) {
        this.selectedBy = selectedBy;
    }

    public LocalDateTime getSelectedAt() {
        return selectedAt;
    }

    public void setSelectedAt(LocalDateTime selectedAt) {
        this.selectedAt = selectedAt;
    }
}
