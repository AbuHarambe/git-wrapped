package de.muenchen.gitwrapped.api.github;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class ApiRequests {

    private ApiRequests() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings({ "resource", "SystemOutPrintln", "PMD.DoNotUseThreads" })
    public static void main(String[] args) {
        final String user = "AbuHarambe";
        final String url = "https://api.github.com/users/" + user;

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        System.out.println(url);

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException e) {
            System.err.println("Fehler bei der API-Anfrage:" + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Die API-Anfrage wurde abgebrochen:" + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
