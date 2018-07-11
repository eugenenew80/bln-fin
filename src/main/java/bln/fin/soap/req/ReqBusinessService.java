package bln.fin.soap.req;

import bln.fin.entity.ReqLine;
import org.springframework.stereotype.Service;

@Service
public interface ReqBusinessService {
    ReqLine createReqLine(ReqLineDto lineDto);
}
