package bln.fin.ws.client;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@SuppressWarnings("Duplicates")
@Component
public class CustomClientInterceptor implements ClientInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CustomClientInterceptor.class);
    //private String username = "PIAPPLBIS_D";
    //private String password = "Qwer!11111";

    private String username = "PIAPPLBIS_Q";
    private String password = "qwerty12";

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        String authString = username + ":" + password;
        String authHeader =  "Basic " + new String(Base64.encodeBase64(authString.getBytes()));

        HttpUrlConnection connection = (HttpUrlConnection) TransportContextHolder.getTransportContext().getConnection();
        try {
            connection.addRequestHeader("Authorization", authHeader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            messageContext.getRequest().writeTo(os);
        }
        catch (IOException e) {
            throw new WebServiceIOException(e.getMessage(), e);
        }
        String request = new String(os.toByteArray());
        logger.trace(request);

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            messageContext.getResponse().writeTo(os);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String response = new String(os.toByteArray());
        logger.trace(response);

        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            messageContext.getResponse().writeTo(os);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String response = new String(os.toByteArray());
        logger.trace(response);

        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException { }
}
