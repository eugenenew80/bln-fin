package bln.fin.ws.client.bems;

import bln.fin.ws.client.plan.SalePlanClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BemsClientServiceImpl implements BemsClientService {
    private static final Logger logger = LoggerFactory.getLogger(SalePlanClientService.class);
    private static final String objectCode = "BEMS";

    @Override
    public void send() {
    }
}