package foi.air.szokpt.transproc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction_records")
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_record_id")
    @JsonIgnore
    private BatchRecord batchRecord;

    @Column(name = "record")
    private String record;

    public TransactionRecord() {
    }

    public TransactionRecord(String record) {
        this.record = record;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BatchRecord getBatchRecord() {
        return batchRecord;
    }

    public void setBatchRecord(BatchRecord batchRecord) {
        this.batchRecord = batchRecord;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
