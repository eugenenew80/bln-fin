package bln.fin.ws.client;

import org.apache.http.Header;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpComponentsConnection;

@Component
public class CustomEndpointInterceptor implements ClientInterceptor {
    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        System.out.println("handleRequest");
        HttpComponentsConnection connection = (HttpComponentsConnection) TransportContextHolder.getTransportContext().getConnection();
        connection.getHttpPost().addHeader("Content-Type", "text/xml;charset=UTF-8");
        connection.getHttpPost().addHeader("Authorization", "Basic UElBUFBMQklTX0Q6UXdlciExMTExMQ==");

        Header[] allHeaders = connection.getHttpPost().getAllHeaders();
        for (Header header : allHeaders) {
            System.out.println(header.getName() + "=" + header.getValue());
        }

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
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }
}
