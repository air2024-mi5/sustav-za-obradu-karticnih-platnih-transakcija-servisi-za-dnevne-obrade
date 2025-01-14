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

    private String generateTerminalParameterRecord(String tid, Mid mid) {
        return new StringBuilder()
                .append("5", 0, 1)
                .append("3", 0, 1)
                .append("0", 0, 1)
                .append(tid, 0, 8)
                .append(String.format("%-25s", mid.getMerchant().getName()), 0, 25)
                .append(String.format("%-13s", mid.getCity().toUpperCase()), 0, 13)
                .append(String.format("%-2s", mid.getStateCode()), 0, 2)
                .append(String.format("%-4s", mid.getTypeCode()), 0, 4)
                .append(String.format("%-4s", mid.getLocationCode()), 0, 4)
                .append(String.format("%-8s", ""), 0, 8)
                .append(String.format("%-5s", mid.getPostalCode()), 0, 5)
                .append(String.format("%-3s", mid.getCountryCode()), 0, 3)
                .append(String.format("%-5s", mid.getSecurityCode()), 0, 5)
                .toString();
    }

    private String generateTransactionRecord(Transaction transaction) {
        String dataCaptureTransactionCode = " ";
        String date = transaction.getTransactionTimestamp()
                .format(DateTimeFormatter.ofPattern("ddMM"));
        String accountDataSourceCode = " ";
        String cardholderIdentificationCode = transaction.isPinUsed() ? "A" : "@";
        String voidIndicator = transaction.getTrxType().equals("void") ? "V" : " ";
        String time = transaction.getTransactionTimestamp()
                .format(DateTimeFormatter.ofPattern("HHmm"));
        String installmentsNumber = transaction.getInstallmentsCreditor().equals("no_installments") ?
                "  " : String.valueOf(transaction.getInstallmentsNumber());
        String installmentCreditor = getCreditorTypeSymbol(transaction.getInstallmentsCreditor());

        return new StringBuilder()
                .append("5", 0, 1)
                .append("4", 0, 1)
                .append(dataCaptureTransactionCode, 0, 1)
                .append(transaction.getTid().getPosTid(), 0, 8)
                .append(date, 0, 4)
                .append(String.format("%-22s", transaction.getMaskedPan()), 0, 22)
                .append(accountDataSourceCode, 0, 1)
                .append(cardholderIdentificationCode, 0, 1)
                .append(String.format("%012d", transaction.getAmount().movePointRight(2).longValue()), 0, 12)
                .append(voidIndicator, 0, 1)
                .append("1", 0, 1)
                .append(String.format("%-6s", transaction.getApprovalCode()), 0, 6)
                .append(String.format("%-4s", ""), 0, 4)
                .append(time, 0, 4)
                .append(installmentsNumber, 0, 2)
                .append(installmentCreditor, 0, 1)
                .repeat("0", 10)
                .toString();
    }

    private String generateTrailer(int transactionCount,
                                   BigDecimal amountTotal,
                                   BigDecimal cashbackTotal,
                                   BigDecimal netDeposit) {
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("ddMM"));
        String fileNumber = "001";

        return new StringBuilder()
                .append("5", 0, 1)
                .append("5", 0, 1)
                .append("0", 0, 1)
                .append(date, 0, 4)
                .append("00", 0, 2)
                .append(fileNumber, 0, 3)
                .append(String.format("%09d", transactionCount), 0, 9)
                .append(String.format("%016d", amountTotal.movePointRight(2).longValue()), 0, 16)
                .append(String.format("%016d", cashbackTotal.movePointRight(2).longValue()), 0, 16)
                .append(String.format("%016d", netDeposit.movePointRight(2).longValue()), 0, 16)
                .repeat("0", 11)
                .toString();
    }

    private String getCreditorTypeSymbol(String creditor) {
        return switch (creditor) {
            case "bank" -> "A";
            case "merchant" -> "B";
            default -> " ";
        };
    }

    private class BatchSummary {
        private final int recordCount;
        private final BigDecimal totalAmount;
        private final BigDecimal batchNetDeposit;
        private final BigDecimal cashbackTotal;

        public BatchSummary(int recordCount, BigDecimal totalAmount, BigDecimal batchNetDeposit, BigDecimal cashbackTotal) {
            this.recordCount = recordCount;
            this.totalAmount = totalAmount;
            this.batchNetDeposit = batchNetDeposit;
            this.cashbackTotal = cashbackTotal;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public BigDecimal getBatchNetDeposit() {
            return batchNetDeposit;
        }

        public BigDecimal getCashbackTotal() {
            return cashbackTotal;
        }
    }
}
