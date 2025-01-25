package foi.air.szokpt.transproc.controllers;

import foi.air.szokpt.transproc.dtos.responses.ApiResponse;
import foi.air.szokpt.transproc.dtos.responses.ProcessingPageData;
import foi.air.szokpt.transproc.dtos.responses.TransactionProcessingResponse;
import foi.air.szokpt.transproc.services.ProcessingService;
import foi.air.szokpt.transproc.util.ApiResponseUtil;
import foi.air.szokpt.transproc.util.Authorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionProcessingController {

    private final Authorizer authorizer;
    private final ProcessingService processingService;

    @Autowired
    public TransactionProcessingController(Authorizer authorizer,
                                           ProcessingService transactionProcessingService) {
        this.authorizer = authorizer;
        this.processingService = transactionProcessingService;
    }

    @GetMapping("/last-processing")
    public ResponseEntity<ApiResponse<TransactionProcessingResponse>> getLastProcessing(
            @RequestHeader("Authorization") String authorizationHeader) {
        authorizer.verifyToken(authorizationHeader);
        TransactionProcessingResponse lastTransactionProcessing = processingService.getLastTransactionProcessing();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseUtil.successWithData(
                        "Last processing successfully fetched",
                        lastTransactionProcessing));
    }

    @GetMapping("/processings")
    public ResponseEntity<ApiResponse<ProcessingPageData>> getProcessings(
            @RequestParam Integer page,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        authorizer.verifyToken(authorizationHeader);
        ProcessingPageData transactionProcessings = processingService.getTransactionProcessings(page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseUtil.successWithData(
                        "Transaction processings successfully fetched",
                        transactionProcessings));
    }

    @PutMapping("/revert-last-processing")
    public ResponseEntity<ApiResponse<ProcessingPageData>> revertProcessing(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        authorizer.verifyToken(authorizationHeader);
        processingService.revertLastProcessing();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseUtil.success(
                        "Transaction processing successfully reverted"));
    }
}
