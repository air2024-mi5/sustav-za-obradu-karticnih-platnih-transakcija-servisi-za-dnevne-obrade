package foi.air.szokpt.transproc.dtos.responses;

import java.util.List;

public class ProcessingPageData {
    private List<TransactionProcessingResponse> transactionProcessings;
    private int currentPage;
    private int totalPages;

    public ProcessingPageData(List<TransactionProcessingResponse> transactions, int currentPage, int totalPages) {
        this.transactionProcessings = transactions;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public List<TransactionProcessingResponse> getTransactions() {
        return transactionProcessings;
    }

    public void setTransactions(List<TransactionProcessingResponse> transactions) {
        this.transactionProcessings = transactions;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
