package foi.air.szokpt.transproc.services;

import foi.air.szokpt.transproc.dtos.Mid;
import foi.air.szokpt.transproc.dtos.Tid;
import foi.air.szokpt.transproc.dtos.Transaction;
import foi.air.szokpt.transproc.models.BatchRecord;
import foi.air.szokpt.transproc.models.TransactionRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionProcessingEngine {
    private String generateHeader(String merchantId, String tid) {
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("ddMM"));
        String fileNumber = "001";

        return new StringBuilder()
                .append("5", 0, 1)
                .append("2", 0, 1)
                .append("0", 0, 1)
                .append(date, 0, 4)
                .append("00", 0, 2)
                .append(fileNumber, 0, 3)
                .append(String.format("%-12s", merchantId).replace(' ', '0'), 0, 12)
                .append(tid, 0, 8)
                .append("0001", 0, 4)
                .append("191", 0, 3)
                .repeat("0", 24)
                .repeat(" ", 17)
                .toString();
    }
}
