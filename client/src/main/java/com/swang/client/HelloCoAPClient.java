package com.swang.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class HelloCoAPClient {

    public static void main(String[] args) throws ConnectorException, IOException {
        // Create a client and specify the target URI
        CoapClient client = new CoapClient("coap://localhost/hello");

        // Send a GET request to the server
        CoapResponse response = client.get();

        // Print the response
        if (response != null) {
            byte[] bytes = response.getPayload();

            System.out.println(response.getCode());
            System.out.println(response.getOptions());
            System.out.println(response.getResponseText());
            System.out.println("\nDETAILED RESPONSE:");
            System.out.println(Utils.prettyPrint(response));
        } else {
            System.out.println("Request failed");
        }
    }
}

