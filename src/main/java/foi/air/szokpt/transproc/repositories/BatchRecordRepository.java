package foi.air.szokpt.transproc.repositories;

import foi.air.szokpt.transproc.models.BatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatchRecordRepository extends JpaRepository<BatchRecord, Integer> {

    @Query("SELECT br FROM BatchRecord br " +
            "JOIN FETCH br.transactionRecords " +
            "WHERE br.transactionProcessing.id = :id")
    List<BatchRecord> getBatchRecordsByTransactionProcessingId(@Param("id") Integer id);


}


