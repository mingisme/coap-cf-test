package com.swang.server;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CREATED;

public class CoAPPostServer {

    public static void main(String[] args) {
        CoapServer server = new CoapServer();

        // Create a resource
        CoapResource helloResource = new CoapResource("data") {
            //handle POST request
            public void handlePOST(CoapExchange exchange) {
                exchange.accept();

                int format = exchange.getRequestOptions().getContentFormat();
                if (format == MediaTypeRegistry.APPLICATION_XML) {
                    String xml = exchange.getRequestText();
                    String responseTxt = "Received XML: '" + xml + "'";
                    System.out.println(responseTxt);
                    exchange.respond(CREATED, responseTxt);

                }
                else if (format == MediaTypeRegistry.TEXT_PLAIN) {
                    // ...
                    String plain = exchange.getRequestText();
                    String responseTxt = "Received text: '" + plain + "'";
                    System.out.println(responseTxt);
                    exchange.respond(CREATED, responseTxt );
                }
                else {
                    // ...
                    byte[] bytes = exchange.getRequestPayload();
                    System.out.println("Received bytes: " + bytes);
                    exchange.respond(CREATED);
                }
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

