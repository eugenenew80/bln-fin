package bln.fin.ws.client.plan;

import org.springframework.stereotype.Service;

@Service
public interface SalePlanService {
    void send(Long headerId);
}
