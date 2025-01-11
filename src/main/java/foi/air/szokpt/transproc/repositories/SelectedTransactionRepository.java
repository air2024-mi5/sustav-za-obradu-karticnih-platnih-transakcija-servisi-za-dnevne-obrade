package foi.air.szokpt.transproc.repositories;

import foi.air.szokpt.transproc.models.SelectedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedTransactionRepository extends JpaRepository<SelectedTransaction, String> {

}
