package foi.air.szokpt.transproc.models;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_records")
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_record_id")
    private BatchRecord batchRecord;

    @Column(name = "record")
    private String record;

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
