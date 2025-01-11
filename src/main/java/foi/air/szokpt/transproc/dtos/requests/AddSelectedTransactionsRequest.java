package foi.air.szokpt.transproc.dtos.requests;

import java.util.List;
import java.util.UUID;

public class AddSelectedTransactionsRequest {
    List<UUID> transactions;

    public List<UUID> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<UUID> transactions) {
        this.transactions = transactions;
    }
}
