package bln.fin.ws.server.bp;

import bln.fin.entity.*;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.entity.pi.*;
import bln.fin.repo.BpInterfaceRepo;
import bln.fin.repo.BpRelationInterfaceRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.*;
import static bln.fin.common.Util.*;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.bp.BusinessPartnerService", portName = "BusinessPartnerPort", serviceName = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public class BusinessPartnerServiceImpl implements BusinessPartnerService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerService.class);
    private final BpInterfaceRepo bpInterfaceRepo;
    private final BpRelationInterfaceRepo bpRelationInterfaceRepo;
    private final SessionService sessionService;
    private final DozerBeanMapper mapper;

    @Override
    public List<MessageDto> createBusinessPartners(List<BusinessPartnerDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            return Arrays.asList(createErrorEmptyMessage());
        }

        logger.info("started");
        debugRequest(list);
        SoapSession session = sessionService.createSession("BP", DirectionEnum.IMPORT);

        List<MessageDto> messages = list.stream()
            .map(t -> createBusinessPartner(t, session))
            .collect(toList());

        sessionService.successSession(session, (long) list.size());
        logger.info("completed");
        return messages;
    }

    @Override
    public List<MessageDto> createRelations(List<RelationDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            return Arrays.asList(createErrorEmptyMessage());
        }

        logger.info("started");
        debugRelRequest(list);
        SoapSession session = sessionService.createSession("BP_REL", DirectionEnum.IMPORT);

        List<MessageDto> messages = list.stream()
            .map(t -> createRelation(t, session))
            .collect(toList());

        sessionService.successSession(session, (long) list.size());
        logger.info("completed");
        return messages;
    }


    private MessageDto createRelation(RelationDto relDto, SoapSession session) {
        logger.debug("Creating line:: bpNum = " + relDto.getBpNum());
        String sapId = relDto.getBpNum()!=null ? relDto.getBpNum() : "";
        MessageDto msg;

        try {
            BpRelationInterface rel = bpRelationInterfaceRepo.findByRelationTypeAndBpNumAAndBpNumRel(relDto.getRelationType(), relDto.getBpNum(), relDto.getBpNumRel())
                .stream()
                .filter(t -> t.getStatus() == BatchStatusEnum.W)
                .findFirst()
                .orElse(new BpRelationInterface());

            mapper.map(relDto, rel);
            rel.setStatus(BatchStatusEnum.W);
            rel.setSession(session);
            addMonitoring(rel);

            rel = bpRelationInterfaceRepo.save(rel);
            msg = createSuccessLineMessage(sapId, rel.getId().toString());
            logger.debug("Creating line successfully completed");
        }

        catch (Exception e) {
            logger.debug("Error during creating line: " + e.getMessage());
            msg = createErrorLineMessage(sapId, e);
        }
        return msg;
    }

    private MessageDto createBusinessPartner(BusinessPartnerDto bpDto, SoapSession session) {
        logger.debug("Creating line:: bpNum = " + bpDto.getBpNum());

        String sapId = bpDto.getBpNum()!=null ? bpDto.getBpNum() : "";
        MessageDto msg;
        try {
            BpInterface bp = bpInterfaceRepo.findByBpNum(bpDto.getBpNum())
                .stream()
                .filter(t -> t.getStatus() == BatchStatusEnum.W)
                .findFirst()
                .orElse(new BpInterface());

            mapper.map(bpDto, bp);
            bp.setStatus(BatchStatusEnum.W);
            bp.setSession(session);
            addMonitoring(bp);

            bp.setTranslates(Optional.ofNullable(bp.getTranslates()).orElse(new HashSet<>()));
            for (BusinessPartnerTranslateDto bpTranslateDto : bpDto.getTranslates()) {
                BpTranslateInterface bpTranslate = getBpTranslate(bp, bpTranslateDto);
                bp.getTranslates().add(bpTranslate);
            }

            bp.setBankAccounts(Optional.ofNullable(bp.getBankAccounts()).orElse(new HashSet<>()));
            for (BankAccountDto baDto : bpDto.getBankAccounts()) {
                BpBankAccountInterface ba = getBankAccount(bp, baDto);
                bp.getBankAccounts().add(ba);
            }

            bp.setAddresses(Optional.ofNullable(bp.getAddresses()).orElse(new HashSet<>()));
            for (AddressDto bpAddressDto : bpDto.getAddresses()) {
                BpAddressInterface address = getAddress(bp, bpAddressDto);
                bp.getAddresses().add(address);

                address.setTranslates(Optional.ofNullable(address.getTranslates()).orElse(new HashSet<>()));
                for (AddressTranslateDto bpAddressTranslateDto : bpAddressDto.getTranslates()) {
                    BpAddressTranslateInterface addressTranslate = getAddressTranslate(address, bpAddressTranslateDto);
                    address.getTranslates().add(addressTranslate);
                }
            }

            bp = bpInterfaceRepo.save(bp);
            msg = createSuccessLineMessage(sapId, bp.getId().toString());
            logger.debug("Creating line successfully completed");
        }
        catch (Exception e) {
            logger.debug("Error during creating line: " + e.getMessage());
            msg = createErrorLineMessage(sapId, e);
        }
        return msg;
    }

    private BpTranslateInterface getBpTranslate(BpInterface bp, BusinessPartnerTranslateDto bpTranslateDto) {
        BpTranslateInterface bpTranslate = bp.getTranslates()
            .stream()
            .filter(t -> t.getLang().equals(bpTranslateDto.getLang()))
            .findFirst()
            .orElse(new BpTranslateInterface());

        mapper.map(bpTranslateDto, bpTranslate);
        bpTranslate.setBusinessPartner(bp);
        addMonitoring(bpTranslate);
        return bpTranslate;
    }

    private BpBankAccountInterface getBankAccount(BpInterface bp, BankAccountDto baDto) {
        BpBankAccountInterface ba = bp.getBankAccounts()
            .stream()
            .filter(t -> t.getAccount().equals(baDto.getAccount()))
            .findFirst()
            .orElse(new BpBankAccountInterface());

        mapper.map(baDto, ba);
        ba.setBusinessPartner(bp);
        addMonitoring(ba);
        return ba;
    }

    private BpAddressInterface getAddress(BpInterface bp, AddressDto bpAddressDto) {
        BpAddressInterface address = bp.getAddresses()
            .stream()
            .filter(t -> t.getAddressType().equals(bpAddressDto.getAddressType()))
            .findFirst()
            .orElse(new BpAddressInterface());

        mapper.map(bpAddressDto, address);
        address.setBusinessPartner(bp);
        addMonitoring(address);
        return address;
    }

    private BpAddressTranslateInterface getAddressTranslate(BpAddressInterface address, AddressTranslateDto bpAddressTranslateDto) {
        BpAddressTranslateInterface addressTranslate = address.getTranslates()
            .stream()
            .filter(t -> t.getLang().equals(bpAddressTranslateDto.getLang()))
            .findFirst()
            .orElse(new BpAddressTranslateInterface());

        mapper.map(bpAddressTranslateDto, addressTranslate);
        addressTranslate.setAddress(address);
        addMonitoring(addressTranslate);
        return addressTranslate;
    }

    private void debugRequest(List<BusinessPartnerDto> list) {
        logger.debug("-----------------------");
        for (BusinessPartnerDto line: list) logger.debug(line.toString());
        logger.debug("-----------------------");
    }

    private void debugRelRequest(List<RelationDto> list) {
        logger.debug("-----------------------");
        for (RelationDto line: list) logger.debug(line.toString());
        logger.debug("-----------------------");
    }
}
