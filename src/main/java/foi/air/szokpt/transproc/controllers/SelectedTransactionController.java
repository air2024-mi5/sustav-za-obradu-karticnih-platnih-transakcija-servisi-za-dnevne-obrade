package foi.air.szokpt.transproc.controllers;

import com.fasterxml.jackson.databind.annotation.NoClass;
import foi.air.szokpt.transproc.dtos.SelectedTransactionDto;
import foi.air.szokpt.transproc.dtos.requests.AddSelectedTransactionsRequest;
import foi.air.szokpt.transproc.dtos.responses.ApiResponse;
import foi.air.szokpt.transproc.services.SelectedTransactionsService;
import foi.air.szokpt.transproc.util.ApiResponseUtil;
import foi.air.szokpt.transproc.util.Authorizer;
import foi.air.szokpt.transproc.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selected-transactions")
public class SelectedTransactionController {

    private final Authorizer authorizer;
    private final SelectedTransactionsService selectedTransactionsService;

    @Autowired
    public SelectedTransactionController(Authorizer authorizer,
                                         SelectedTransactionsService selectedTransactionsService) {
        this.authorizer = authorizer;
        this.selectedTransactionsService = selectedTransactionsService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<SelectedTransactionDto>> getSelectedTransactions(
            @RequestHeader("Authorization") String authorizationHeader) {
        authorizer.verifyToken(authorizationHeader);
        List<SelectedTransactionDto> transactions = selectedTransactionsService.getSelectedTransaction();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseUtil.successWithData(
                        "Selected transactions successfully fetched",
                        transactions));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<NoClass>> addSelectedTransactions(
            @RequestBody AddSelectedTransactionsRequest body,
            @RequestHeader("Authorization") String authorizationHeader) {
        authorizer.verifyToken(authorizationHeader);
        selectedTransactionsService.AddSelectedTransactions(
                body.getTransactions(),
                JwtUtil.getUsername(authorizationHeader));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseUtil.success("Selected transactions successfully added"));
    }
}
