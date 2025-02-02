package foi.air.szokpt.transproc.schedulers;

import foi.air.szokpt.transproc.services.ProcessingService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessingScheduler {

    private ProcessingService processingService;

    public TransactionProcessingScheduler(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void processTransactions() {
        processingService.startDailyProcessing();
    }
}
