package foi.air.szokpt.transproc.clients;

import foi.air.szokpt.transproc.dtos.Transaction;
import foi.air.szokpt.transproc.dtos.responses.TransactionFetchResponse;
import foi.air.szokpt.transproc.exceptions.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionClient {
    private final WebClient.Builder webClientBuilder;

    @Value("${api.base.url}")
    private String baseUrl;

    public TransactionClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Transaction> fetchTransactions(List<UUID> guids) {

        if (guids.isEmpty())
            return new ArrayList<>();

        String url = baseUrl + "/detailed-transactions?";

        for (UUID guid : guids) {
            url += "guid=" + guid + "&";
        }
        try {
            WebClient webClient = webClientBuilder.baseUrl(url).build();

            TransactionFetchResponse response = webClient.get()
                    .retrieve()
                    .bodyToMono(TransactionFetchResponse.class)
                    .block();

            if (response != null)
                return response.getTransactions();

            return new ArrayList<>();
        } catch (Exception e) {
            throw new ExternalServiceException(e.getMessage());
        }
    }

    public void sendProcessedTransactions(List<UUID> transactions) {
        if (transactions.isEmpty())
            return;
        String url = baseUrl + "/processed-transactions";
        try {
            WebClient webClient = webClientBuilder.baseUrl(url).build();
            webClient.put()
                    .bodyValue(transactions)
                    .retrieve()
                    .onStatus(
                            status -> !status.is2xxSuccessful(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
                    )
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("Error while sending processed transactions: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ExternalServiceException("Unexpected error occurred: " + e.getMessage());
        }
    }
}
