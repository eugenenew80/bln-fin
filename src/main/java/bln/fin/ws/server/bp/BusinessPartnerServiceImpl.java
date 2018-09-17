package bln.fin.ws.server.bp;

import bln.fin.entity.*;
import bln.fin.entity.enums.BatchStatusEnum;
import bln.fin.entity.enums.DirectionEnum;
import bln.fin.repo.BusinessPartnerInterfaceRepo;
import bln.fin.ws.SessionService;
import bln.fin.ws.server.MessageDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.getCause;
import static bln.fin.common.Util.toLocalDate;


@RequiredArgsConstructor
@Service
@WebService(endpointInterface = "bln.fin.ws.server.bp.BusinessPartnerService", portName = "BusinessPartnerPort", serviceName = "BusinessPartnerService", targetNamespace = "http://bis.kegoc.kz/soap")
public class BusinessPartnerServiceImpl implements BusinessPartnerService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerService.class);
    private final BusinessPartnerInterfaceRepo businessPartnerInterfaceRepo;
    private final SessionService sessionService;


    @Override
    public List<MessageDto> createBusinessPartners(List<BusinessPartnerDto> list) {
        if (list == null) {
            logger.warn("Input data list is empty");
            MessageDto msg = new MessageDto();
            msg.setSystem("BIS");
            msg.setMsgType("W");
            msg.setMsgNum("1");
            msg.setSapId(null);
            msg.setMsg("Input data list is empty");
            return Arrays.asList(msg);
        }

        logger.info("started");

        debugRequest(list);
        SoapSession session = sessionService.createSession("BP", DirectionEnum.IMPORT);
        List<MessageDto> messages = new ArrayList<>();

        try {
            for (BusinessPartnerDto businessPartnerDto : list) {
                logger.debug("Creating line:: bpNum = " + businessPartnerDto.getBpNum());

                MessageDto msg = new MessageDto();
                try {
                    BusinessPartnerInterface bp = businessPartnerInterfaceRepo.findByBpNum(businessPartnerDto.getBpNum())
                        .stream()
                        .filter(t -> t.getStatus() == BatchStatusEnum.W)
                        .findFirst()
                        .orElse(new BusinessPartnerInterface());

                    if (bp.getId() == null) {
                        bp.setCreateDate(LocalDateTime.now());
                        bp.setLastUpdateDate(null);
                    }

                    if (bp.getId() != null)
                        bp.setLastUpdateDate(LocalDateTime.now());

                    bp.setBpNum(businessPartnerDto.getBpNum());
                    bp.setGroup(businessPartnerDto.getGroup());
                    bp.setSearchCriteria(businessPartnerDto.getSearchCriteria());
                    bp.setFoundDate(toLocalDate(businessPartnerDto.getFoundDate()));
                    bp.setLiqDate(toLocalDate(businessPartnerDto.getLiqDate()));
                    bp.setIndustry(businessPartnerDto.getIndustry());
                    bp.setTaxNumberType(businessPartnerDto.getTaxNumberType());
                    bp.setTaxNumber(businessPartnerDto.getTaxNumber());
                    bp.setStatus(BatchStatusEnum.W);
                    bp.setSession(session);

                    if (bp.getTranslates()==null)
                        bp.setTranslates(new ArrayList<>());

                    for (BusinessPartnerTranslateDto businessPartnerTranslateDto : businessPartnerDto.getTranslates()) {
                        BusinessPartnerTranslateInterface tl = bp.getTranslates()
                            .stream()
                            .filter(t -> t.getLang().equals(businessPartnerTranslateDto.getLang()))
                            .findFirst()
                            .orElse(new BusinessPartnerTranslateInterface());

                        if (tl.getId() == null)  {
                            tl.setCreateDate(LocalDateTime.now());
                            tl.setLastUpdateDate(null);
                        }

                        if (tl.getId() != null)
                            tl.setLastUpdateDate(LocalDateTime.now());

                        tl.setBusinessPartner(bp);
                        tl.setLang(businessPartnerTranslateDto.getLang());
                        tl.setName(businessPartnerTranslateDto.getName());
                        tl.setFullName(businessPartnerTranslateDto.getFullName());
                        bp.getTranslates().add(tl);
                    }


                    if (bp.getBankAccounts()==null)
                        bp.setBankAccounts(new ArrayList<>());

                    for (BankAccountDto bankAccountDto : businessPartnerDto.getBankAccounts()) {
                        BusinessPartnerBankAccountInterface ba = bp.getBankAccounts()
                            .stream()
                            .filter(t -> t.getAccount().equals(bankAccountDto.getAccount()))
                            .findFirst()
                            .orElse(new BusinessPartnerBankAccountInterface());

                        if (ba.getId() == null)  {
                            ba.setCreateDate(LocalDateTime.now());
                            ba.setLastUpdateDate(null);
                        }

                        if (ba.getId() != null)
                            ba.setLastUpdateDate(LocalDateTime.now());

                        ba.setBusinessPartner(bp);
                        ba.setBik(bankAccountDto.getBik());
                        ba.setAccount(bankAccountDto.getAccount());
                        ba.setIban(bankAccountDto.getIban());
                        ba.setCountry(bankAccountDto.getCountry());
                        bp.getBankAccounts().add(ba);
                    }


                    if (bp.getRelations()==null)
                        bp.setRelations(new ArrayList<>());

                    for (RelationDto relationDto : businessPartnerDto.getRelations()) {
                        BusinessPartnerRelationInterface rel = bp.getRelations()
                            .stream()
                            .filter(t -> t.getBpNum().equals(relationDto.getBpNum()))
                            .findFirst()
                            .orElse(new BusinessPartnerRelationInterface());

                        if (rel.getId() == null)  {
                            rel.setCreateDate(LocalDateTime.now());
                            rel.setLastUpdateDate(null);
                        }

                        if (rel.getId() != null)
                            rel.setLastUpdateDate(LocalDateTime.now());

                        rel.setBusinessPartner(bp);
                        rel.setBpNum(relationDto.getBpNum());
                        rel.setRelationType(relationDto.getRelationType());
                        bp.getRelations().add(rel);
                    }


                    if (bp.getAddresses()==null)
                        bp.setAddresses(new ArrayList<>());

                    for (AddressDto addressDto : businessPartnerDto.getAddresses()) {
                        BusinessPartnerAddressInterface address = bp.getAddresses()
                            .stream()
                            .filter(t -> t.getAddressType().equals(addressDto.getAddressType()))
                            .findFirst()
                            .orElse(new BusinessPartnerAddressInterface());

                        if (address.getId() == null)  {
                            address.setCreateDate(LocalDateTime.now());
                            address.setLastUpdateDate(null);
                        }

                        if (address.getId() != null)
                            address.setLastUpdateDate(LocalDateTime.now());

                        address.setBusinessPartner(bp);
                        address.setAddressType(addressDto.getAddressType());
                        address.setCountry(addressDto.getCountry());
                        address.setRegion(addressDto.getRegion());
                        address.setHouseNum(addressDto.getHouseNum());
                        address.setBuildNum(addressDto.getBuildNum());
                        address.setPostCode(addressDto.getPostCode());
                        address.setRoom(addressDto.getRoom());
                        address.setArea(addressDto.getArea());
                        address.setPhoneNum(addressDto.getPhoneNum());
                        address.setIntPhoneNum(addressDto.getIntPhoneNum());
                        address.setCellPhoneNum(addressDto.getCellPhoneNum());
                        address.setFax(addressDto.getFax());
                        address.setEmail(addressDto.getEmail());
                        address.setArea(addressDto.getArea());
                        bp.getAddresses().add(address);

                        if (address.getTranslates() == null)
                            address.setTranslates(new ArrayList<>());

                        for (AddressTranslateDto addressTranslateDto : addressDto.getTranslates()) {
                            BusinessPartnerAddressTranslateInterface tl = address.getTranslates()
                                .stream()
                                .filter(t -> t.getLang().equals(addressTranslateDto.getLang()))
                                .findFirst()
                                .orElse(new BusinessPartnerAddressTranslateInterface());

                            if (tl.getId() == null)  {
                                tl.setCreateDate(LocalDateTime.now());
                                tl.setLastUpdateDate(null);
                            }

                            if (tl.getId() != null)
                                tl.setLastUpdateDate(LocalDateTime.now());

                            tl.setAddress(address);
                            tl.setLang(addressTranslateDto.getLang());
                            tl.setCity(addressTranslateDto.getCity());
                            tl.setStreet(addressTranslateDto.getStreet());
                            address.getTranslates().add(tl);
                        }
                    }

                    bp = businessPartnerInterfaceRepo.save(bp);

                    msg.setSystem("BIS");
                    msg.setMsgType("S");
                    msg.setMsgNum("0");
                    msg.setMsg("OK");
                    msg.setId(bp.getId().toString());
                    msg.setSapId(businessPartnerDto.getBpNum().toString());
                    logger.debug("Creating line successfully completed");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    logger.debug("Error during creating line: " + e.getMessage());

                    String err;
                    Throwable cause = getCause(e);
                    if (cause.getMessage()!=null)
                        err = cause.getMessage();
                    else
                        err = cause.getClass().getCanonicalName();

                    String sapId = businessPartnerDto.getBpNum()!=null ? businessPartnerDto.getBpNum().toString() : "";
                    msg.setSystem("BIS");
                    msg.setMsgType("E");
                    msg.setMsgNum("2");
                    msg.setSapId(sapId);
                    msg.setMsg(err);
                }
                messages.add(msg);
            }
        }
        catch (Exception e) {
            logger.debug("Error during saving entities " + e.getMessage());
            String err = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
            MessageDto msg = new MessageDto();
            msg.setSystem("BIS");
            msg.setMsgType("E");
            msg.setMsgNum("1");
            msg.setSapId(null);
            msg.setMsg(err);
            messages.add(msg);
            sessionService.errorSession(session, e);
            e.printStackTrace();
        }

        logger.info("completed");
        return messages;
    }

    private void debugRequest(List<BusinessPartnerDto> list) {
        logger.debug("List of input records");
        for (BusinessPartnerDto line: list) {
            logger.debug("-----------------------");
            logger.debug("bpNum: " + line.getBpNum());
            logger.debug("group: " + line.getGroup());
            logger.debug("searchCriteria: " + line.getSearchCriteria());
            logger.debug("foundDate: " + line.getFoundDate());
            logger.debug("liqDate: " + line.getLiqDate());
            logger.debug("industry: " + line.getIndustry());
            logger.debug("taxNumberType: " + line.getTaxNumberType());
            logger.debug("taxNumber: " + line.getTaxNumber());
            logger.debug("industry: " + line.getIndustry());
            logger.debug("-----------------------");
            logger.debug("");
        }
    }
}
