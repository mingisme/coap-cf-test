package com.swang.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class AsyncHelloCoAPClient {
    public static void main(String[] args) throws ConnectorException, IOException {
        // Create a client and specify the target URI
        CoapClient client = new CoapClient("coap://localhost/hello");

        // Send a GET request to the server
        client.get(new AsynchListener());

        System.out.println("Press Enter to continue...");
        System.in.read();
    }

    public static class AsynchListener implements CoapHandler {

        public void onLoad(CoapResponse response) {
            String content = response.getResponseText();
            System.out.println("onLoad: " + content);
        }
        public void onError() {
            System.err.println("Error");
        }
    }
}
