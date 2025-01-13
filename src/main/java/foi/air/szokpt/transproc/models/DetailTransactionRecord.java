package foi.air.szokpt.transproc.models;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_transaction_records")
public class DetailTransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_parameter_record_id")
    private TerminalParameterRecord terminalParameterRecord;

    @Column(name = "record")
    private String record;
}
