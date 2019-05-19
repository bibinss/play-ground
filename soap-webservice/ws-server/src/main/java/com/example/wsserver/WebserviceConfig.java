package com.example.wsserver;

import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.KeyStoreCallbackHandler;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

@EnableWs
@Configuration
public class WebserviceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "airlines")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AirlinesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        try {
            interceptors.add(securityInterceptor());
        } catch (Exception e) {
            throw new RuntimeException("SecurityInterceptor initialization failed", e);
        }
        super.addInterceptors(interceptors);
    }

    @Bean
    Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        //decrypt and verify the signature.
        securityInterceptor.setValidationActions(WSHandlerConstants.ENCRYPT + " " + WSHandlerConstants.SIGNATURE);
        CryptoFactoryBean cryptoFactoryBean = cryptoFactoryBean();
        securityInterceptor.setValidationSignatureCrypto(cryptoFactoryBean.getObject());
        securityInterceptor.setValidationDecryptionCrypto(cryptoFactoryBean.getObject());
        securityInterceptor.setValidationCallbackHandler(keyStoreCallbackHandler());
        return securityInterceptor;
    }

    private CryptoFactoryBean cryptoFactoryBean() throws Exception {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("keystore.jks"));
        //keystore password
        cryptoFactoryBean.setKeyStorePassword("changeit");
        cryptoFactoryBean.afterPropertiesSet();
        return cryptoFactoryBean;
    }

    private KeyStoreCallbackHandler keyStoreCallbackHandler() {
        KeyStoreCallbackHandler keyStoreCallbackHandler = new KeyStoreCallbackHandler();
        //private key password. private key is used to decrypt the message as SOAP message is encrypted using the
        //corresponding public key at client side.
        keyStoreCallbackHandler.setPrivateKeyPassword("key-password");
        return keyStoreCallbackHandler;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("air-shop-schema.xsd"));
    }
}
