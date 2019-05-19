package com.sample.wsclient;

import lombok.val;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;

@SpringBootApplication
public class WsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsClientApplication.class, args);
	}

	@Bean
	public WebServiceTemplate webServiceTemplate() throws Exception {
        val webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        ClientInterceptor clientInterceptors[] = {securityInterceptor()};
        webServiceTemplate.setInterceptors(clientInterceptors);
        return webServiceTemplate;
	}

    private Jaxb2Marshaller marshaller() {
        val marshaller = new Jaxb2Marshaller();
        //Package of generated stubs for web-service invocation
        String[] packagesToScan= {"ws.service"};
        marshaller.setPackagesToScan(packagesToScan);
        return marshaller;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor security = new Wss4jSecurityInterceptor();
        //both encrypt and sign the SOAP message
        security.setSecurementActions(WSHandlerConstants.ENCRYPT + " " + WSHandlerConstants.SIGNATURE);
        //private key alias used to sign message
        security.setSecurementUsername("ws-client");
        //certificate alias used to encrypt the message (usually the server certificate)
        security.setSecurementEncryptionUser("ws-server");
        //private key password... same key is used to sign the message
        security.setSecurementPassword("key-password");
        Crypto crypto = cryptoFactoryBean().getObject();
        security.setSecurementEncryptionCrypto(crypto);
        security.setSecurementSignatureCrypto(crypto);
        return security;
    }

    private CryptoFactoryBean cryptoFactoryBean() throws Exception {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("keystore.jks"));
        //keystore password
        cryptoFactoryBean.setKeyStorePassword("changeit");
        cryptoFactoryBean.afterPropertiesSet();
        return cryptoFactoryBean;
    }
}
