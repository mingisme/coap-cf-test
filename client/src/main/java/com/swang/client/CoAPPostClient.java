package com.swang.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class CoAPPostClient {

    public static void main(String[] args) throws ConnectorException, IOException {
        // Create a client and specify the target URI
        CoapClient client = new CoapClient("coap://localhost/data");

        // Send a POST request to the server
        CoapResponse r1 = client.post("data", MediaTypeRegistry.TEXT_PLAIN);
        CoapResponse r2 = client.post("<data>this is data</data>",
                MediaTypeRegistry.APPLICATION_XML);
        CoapResponse r3 = client.post("{\"temperature\":20.0,\"humidity\":50.0}", 
                MediaTypeRegistry.APPLICATION_JSON);

        // Print the response
        doResponse(r1);
        doResponse(r2);
        doResponse(r3);
    }

    private static void doResponse(CoapResponse response) {
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

