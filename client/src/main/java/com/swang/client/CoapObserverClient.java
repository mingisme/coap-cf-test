package com.swang.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class CoapObserverClient {
    public static void main(String[] args) throws ConnectorException, IOException {
        // Create a client and specify the target URI
        CoapClient client = new CoapClient("coap://localhost/temp");

        // Send a GET request to the server
        CoapObserveRelation observable = client.observe(new AsyncHelloCoAPClient.AsynchListener());

        System.out.println("Press Enter to continue...");
        System.in.read();

        observable.proactiveCancel();
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
