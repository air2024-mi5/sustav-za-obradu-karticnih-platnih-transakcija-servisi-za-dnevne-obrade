package foi.air.szokpt.transproc.services;

import foi.air.szokpt.transproc.clients.TransactionClient;
import foi.air.szokpt.transproc.dtos.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessingService {

    private TransactionClient transactionClient;

    public ProcessingService(TransactionClient transactionClient) {
        this.transactionClient = transactionClient;
    }


    public void startDailyProcessing() {

    }
}
