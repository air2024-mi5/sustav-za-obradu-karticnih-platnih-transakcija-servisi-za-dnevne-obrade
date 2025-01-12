package foi.air.szokpt.transproc.repositories;

import foi.air.szokpt.transproc.models.TransactionProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionProcessingRepository extends JpaRepository<TransactionProcessing, String> {
    @Query("SELECT t FROM TransactionProcessing t " +
            "WHERE t.scheduledAt > CURRENT_TIMESTAMP AND " +
            "t.status = 'PENDING' " +
            "ORDER BY t.scheduledAt ASC"
    )
    List<TransactionProcessing> findScheduledTransactionProcessing();

}
