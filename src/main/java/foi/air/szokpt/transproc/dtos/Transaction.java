package foi.air.szokpt.transproc.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    @JsonProperty("guid")
    private UUID guid;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("trx_type")
    private String trxType;

    @JsonProperty("installments_number")
    private int installmentsNumber;

    @JsonProperty("installments_creditor")
    private String installmentsCreditor;

    @JsonProperty("transaction_timestamp")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime transactionTimestamp;

    @JsonProperty("masked_pan")
    private String maskedPan;

    @JsonProperty("pin_used")
    private boolean pinUsed;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("approval_code")
    private String approvalCode;

    @JsonProperty("processed")
    private Boolean processed;

    @JsonProperty("tid")
    private Tid tid;

    public Tid getTid() {
        return tid;
    }

    public void setTid(Tid tid) {
        this.tid = tid;
    }

    public Transaction() {
    }

    public Transaction(UUID guid,
                       BigDecimal amount,
                       String currency,
                       String trxType,
                       int installmentsNumber,
                       String installmentsCreditor,
                       LocalDateTime transactionTimestamp,
                       String maskedPan,
                       boolean pinUsed,
                       String responseCode,
                       String approvalCode,
                       Boolean processed,
                       Tid tid) {
        this.guid = guid;
        this.amount = amount;
        this.currency = currency;
        this.trxType = trxType;
        this.installmentsNumber = installmentsNumber;
        this.installmentsCreditor = installmentsCreditor;
        this.transactionTimestamp = transactionTimestamp;
        this.maskedPan = maskedPan;
        this.pinUsed = pinUsed;
        this.responseCode = responseCode;
        this.approvalCode = approvalCode;
        this.processed = processed;

    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public int getInstallmentsNumber() {
        return installmentsNumber;
    }

    public void setInstallmentsNumber(int installmentsNumber) {
        this.installmentsNumber = installmentsNumber;
    }

    public String getInstallmentsCreditor() {
        return installmentsCreditor;
    }

    public void setInstallmentsCreditor(String installmentsCreditor) {
        this.installmentsCreditor = installmentsCreditor;
    }


    public LocalDateTime getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(LocalDateTime transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }

    public boolean isPinUsed() {
        return pinUsed;
    }

    public void setPinUsed(boolean pinUsed) {
        this.pinUsed = pinUsed;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

}
