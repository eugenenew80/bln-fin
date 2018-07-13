package bln.fin.ws.client;

import org.springframework.stereotype.Service;

@Service
public interface SalePlanService {
    void send(Long headerId);
}
