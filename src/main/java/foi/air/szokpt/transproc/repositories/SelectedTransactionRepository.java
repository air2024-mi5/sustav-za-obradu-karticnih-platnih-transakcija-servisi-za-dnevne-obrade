package foi.air.szokpt.transproc.repositories;

import foi.air.szokpt.transproc.models.SelectedTransaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SelectedTransactionRepository extends JpaRepository<SelectedTransaction, UUID> {
    List<SelectedTransaction> findByTransactionProcessingId(Integer transactionProcessingId);

    @Transactional
    void deleteByTransactionProcessingId(Integer transactionProcessingId);
}
