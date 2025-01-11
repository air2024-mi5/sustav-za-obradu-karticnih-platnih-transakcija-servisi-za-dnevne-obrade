package foi.air.szokpt.transproc.repositories;

import foi.air.szokpt.transproc.models.SelectedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SelectedTransactionRepository extends JpaRepository<SelectedTransaction, UUID> {

}
