package com.example.wsserver.endpoint;


import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;
import example.Airline;
import example.ShopRequest;
import example.ShopResponse;

@Endpoint
public class ShopEndpoint {
    @PayloadRoot(namespace = "http://shopping", localPart = "shopRequest")
    @SoapAction("http://ws-service/shop")
    @ResponsePayload
    public ShopResponse shop(@RequestPayload ShopRequest request) {
        ShopResponse response = new ShopResponse();
        Airline airline = new Airline();
        //some temp logic :)
        if (request.getDestination().equals("JFK")) {
            airline.setName("United");
        }
        else {
            airline.setName("Indigo");
        }
        response.setAirline(airline);
        return response;
    }
}
