package foi.air.szokpt.transproc.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import foi.air.szokpt.transproc.dtos.Transaction;

import java.util.List;

public class TransactionFetchResponse {
    
    @JsonProperty("data")
    List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
