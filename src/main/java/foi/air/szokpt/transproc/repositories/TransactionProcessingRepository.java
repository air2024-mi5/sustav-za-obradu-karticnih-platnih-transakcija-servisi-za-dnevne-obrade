package foi.air.szokpt.transproc.repositories;

import foi.air.szokpt.transproc.models.TransactionProcessing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionProcessingRepository extends JpaRepository<TransactionProcessing, String> {
    @Query("SELECT t FROM TransactionProcessing t " +
            "WHERE t.scheduledAt >= :threshold AND " +
            "t.status = 'PENDING' " +
            "ORDER BY t.scheduledAt ASC"
    )
    List<TransactionProcessing> findScheduledTransactionProcessing(@Param("threshold") LocalDateTime threshold);

    @Query("SELECT t FROM TransactionProcessing t " +
            "WHERE t.status = 'COMPLETED' OR t.status = 'FAILED' " +
            "ORDER BY t.processedAt DESC")
    Page<TransactionProcessing> getLastProcessings(Pageable pageable);


}
