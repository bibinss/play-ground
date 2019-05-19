# SOAP webservice using Spring
This is an example SOAP webservice project implemented using spring WS libraries.

It consists of
1. ws-client
2. ws-server

Refer to the code base to get basic understanding of how to build a SOAP client and server using spring WS.


# How to see this in action

Run server
1. cd ws-server
2. mvn clean verify
3. mvn spring-boot:run
4. Ensure that server is app and running and you see the WSDL at http://localhost:8080/ws/airlines.wsdl.

   Client application uses this WSDL to generate stubs.


Run Client
1. cd ws-client
2. mvn clean verify
3. mvn spring-boot:run
4. Invoke the client API - http://localhost:8090/search?destination=JFK. 
   
   This internally makes a SOAP request to server 
   to get the response. You can see the encrypted SOAP message with signature in the client application logs.

    SOAP message sent to server from client is both encrypted and signed.

    Encryption is done using the server's public key/certificate (imported to client's keystore) and signature is created 
    using client's private key.
    When Server receives the message it validates the signature using client's public key/certificate (imported to server's keystore) 
    and message is decrypted using client's private key.




