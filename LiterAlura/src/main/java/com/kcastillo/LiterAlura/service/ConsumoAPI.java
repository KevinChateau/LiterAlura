package com.kcastillo.LiterAlura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String ObtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient(); //Create client
        HttpRequest request = HttpRequest.newBuilder() //Create request and adding url
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null; //Create response
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }

        return response.body();
    }


}
