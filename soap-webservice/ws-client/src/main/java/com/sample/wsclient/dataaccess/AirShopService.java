package com.sample.wsclient.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import ws.service.ShopRequest;
import ws.service.ShopResponse;

@Component
public class AirShopService {

    @Autowired
    private WebServiceTemplate wsTemplate;

    public AirShopResponse shop(String destination) {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setDestination(destination);
        //Call back is used to set the action name and same is mapped to SOAP method at server side.
        //This is required for the server to handle encrypted SOAP messages.
        RequestCallBack requestCallBack = new RequestCallBack(
                "http://ws-service/shop");
        ShopResponse shopResponse =
                (ShopResponse) wsTemplate.marshalSendAndReceive("http://localhost:8080/ws", shopRequest,
                        requestCallBack);
        AirShopResponse result = new AirShopResponse();
        result.setAirline(shopResponse.getAirline().getName());
        return result;
    }

    private class RequestCallBack implements WebServiceMessageCallback {
        private String soapAction;

        public RequestCallBack(final String action) {
            this.soapAction = action;
        }

        @Override
        public void doWithMessage(WebServiceMessage message) {
            if (message instanceof SoapMessage) {
                SoapMessage soapMessage = (SoapMessage) message;
                soapMessage.setSoapAction(soapAction);
            }

        }
    }
}
