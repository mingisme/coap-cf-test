package com.swang.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MultiResourceCoAPClient {
    public static void main(String[] args) throws ConnectorException, IOException, URISyntaxException {
        doRequest("helloWorld");
        System.out.println("---");
        doRequest("bye");
    }

    private static void doRequest(String action) throws URISyntaxException, ConnectorException, IOException {
        URI uri = new URI("coap://localhost:5683/" + action);

        // synchronous CoAP GET request
        //
        CoapClient client = new CoapClient(uri);
        CoapResponse response = client.get();

        // Print the response
        if (response != null) {
            System.out.println("\nDETAILED RESPONSE:");
            System.out.println(Utils.prettyPrint(response));
        } else {
            System.out.println("Request failed");
        }
    }
}
