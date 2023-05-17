package com.swang.server;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * @author ebruno
 */
public class CoapObserverServer extends CoapResource {
    public static void main(String[] args) {
        CoapServer server = new CoapServer();
        server.add(new CoapObserverServer("temp"));
        server.start();
    }

    public CoapObserverServer(String name) {
        super(name);
        setObservable(true); // enable observing
        setObserveType(Type.CON); // configure the notification type to CONs
        getAttributes().setObservable(); // mark observable in the Link-Format

        // schedule a periodic update task, otherwise let events call changed()
        new Timer().schedule(new UpdateTask(), 0, 1000);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            changed(); // notify all observers
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        // the Max-Age value should match the update interval
        exchange.setMaxAge(1);
        exchange.respond("Current temperature: " + (69 + new Random().nextInt(5)));
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        delete(); // will also call clearAndNotifyObserveRelations(ResponseCode.NOT_FOUND)
        exchange.respond(DELETED);
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        // ...
        exchange.respond(CHANGED);
        changed(); // notify all observers
    }


}