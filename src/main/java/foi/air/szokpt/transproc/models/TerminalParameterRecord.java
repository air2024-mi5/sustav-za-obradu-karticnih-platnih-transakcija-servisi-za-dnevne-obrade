package foi.air.szokpt.transproc.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "terminal_parameter_records")
public class TerminalParameterRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_processing_id")
    private TransactionProcessing transactionProcessing;

    @Column(name = "record")
    private String record;

    @OneToMany(mappedBy = "terminalParameterRecord")
    private List<DetailTransactionRecord> detailTransactionRecords;
}
