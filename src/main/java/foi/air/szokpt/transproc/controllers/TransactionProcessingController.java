package foi.air.szokpt.transproc.controllers;

import foi.air.szokpt.transproc.dtos.responses.ApiResponse;
import foi.air.szokpt.transproc.dtos.responses.TransactionProcessingDto;
import foi.air.szokpt.transproc.services.ProcessingService;
import foi.air.szokpt.transproc.util.ApiResponseUtil;
import foi.air.szokpt.transproc.util.Authorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse<TransactionProcessingDto>> getLastProcessing(
            @RequestHeader("Authorization") String authorizationHeader) {
        authorizer.auhorize(authorizationHeader);
        TransactionProcessingDto lastTransactionProcessing = processingService.getLastTransactionProcessing();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseUtil.successWithData(
                        "Selected transactions successfully fetched",
                        lastTransactionProcessing));
    }
}
