package com.swang.server;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class HelloCoAPServer {

    public static void main(String[] args) {
        CoapServer server = new CoapServer();

        // Create a resource
        CoapResource helloResource = new CoapResource("hello") {
            @Override
            public void handleGET(CoapExchange exchange) {
                exchange.respond("Hello, CoAP client!");
            }
        };

        // Add the resource to the server
        server.add(helloResource);

        // Start the server
        server.start();
        System.out.println("CoAP server started.");

        // Wait for termination
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Stop the server
        server.stop();
        System.out.println("CoAP server stopped.");
    }
}

