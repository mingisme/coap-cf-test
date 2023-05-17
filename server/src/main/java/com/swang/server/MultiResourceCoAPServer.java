package com.swang.server;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.config.CoapConfig;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.config.Configuration;
import org.eclipse.californium.elements.util.NetworkInterfacesUtil;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class MultiResourceCoAPServer extends CoapServer{

    private static final int COAP_PORT =
            Configuration.getStandard().get( CoapConfig.COAP_PORT );

    //constructor
    public MultiResourceCoAPServer() {
        super();
        addEndpoints();
        add(new HelloWorldResource());
        add(new GoodbyeResource());
    }

    private void addEndpoints() {
        Configuration config = Configuration.getStandard();
        // Add an endpoint listener for each host network interface
        for (InetAddress addr :
                NetworkInterfacesUtil.getNetworkInterfaces()) {
            InetSocketAddress bindToAddress =
                    new InetSocketAddress(addr, COAP_PORT);
            CoapEndpoint.Builder builder = new CoapEndpoint.Builder();
            builder.setInetSocketAddress(bindToAddress);
            builder.setConfiguration(config);
            addEndpoint( builder.build() );
        }
    }

    public static void main(String[] args) {
        try {
            MultiResourceCoAPServer server = new MultiResourceCoAPServer();
            server.start();
        }
        catch ( Exception e ) {
            System.err.println("Failed to initialize server: " + e.getMessage());
        }
    }

    /*
     * Definition of the Hello-World Resource
     */
    static class HelloWorldResource extends CoapResource {
        public HelloWorldResource() {
            super("helloWorld"); // set resource identifier
            getAttributes().setTitle("Hello-World Resource");
        }

        @Override
        public void handleGET(CoapExchange exchange) {
            exchange.respond("Hello, World!");
        }
    }

    static class GoodbyeResource extends CoapResource {
        public GoodbyeResource() {
            super("bye"); // set resource identifier
            getAttributes().setTitle("Goodbye Resource");
        }

        @Override
        public void handleGET(CoapExchange exchange) {
            exchange.respond("So long!");
        }
    }
}

