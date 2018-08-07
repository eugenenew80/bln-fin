package bln.fin.ws.client;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;
import java.io.IOException;

@Component
public class CustomEndpointInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        HttpUrlConnection connection = (HttpUrlConnection) TransportContextHolder.getTransportContext().getConnection();
        try {
            connection.addRequestHeader("Authorization", "Basic UElBUFBMQklTX0Q6UXdlciExMTExMQ==");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /*
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            messageContext.getRequest().writeTo(os);
        }
        catch (IOException e) {
            throw new WebServiceIOException(e.getMessage(), e);
        }
        String request = new String(os.toByteArray());
        System.out.println(request);
        */
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException { }
}
