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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static bln.fin.common.Util.*;
import static java.util.stream.Collectors.toList;

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

    private MessageDto createBusinessPartner(BusinessPartnerDto businessPartnerDto, SoapSession session) {
        logger.debug("Creating line:: bpNum = " + businessPartnerDto.getBpNum());

        String sapId = businessPartnerDto.getBpNum()!=null ? businessPartnerDto.getBpNum() : "";
        MessageDto msg;
        try {
            BusinessPartnerInterface bp = businessPartnerInterfaceRepo.findByBpNum(businessPartnerDto.getBpNum())
                .stream()
                .filter(t -> t.getStatus() == BatchStatusEnum.W)
                .findFirst()
                .orElse(new BusinessPartnerInterface());

            addMonitoring(bp);
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

                addMonitoring(tl);
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

                addMonitoring(ba);
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

                addMonitoring(rel);
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

                addMonitoring(address);
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

                    addMonitoring(tl);
                    tl.setAddress(address);
                    tl.setLang(addressTranslateDto.getLang());
                    tl.setCity(addressTranslateDto.getCity());
                    tl.setStreet(addressTranslateDto.getStreet());
                    address.getTranslates().add(tl);
                }
            }

            bp = businessPartnerInterfaceRepo.save(bp);
            msg = createSuccessLineMessage(sapId, bp.getId().toString());
            logger.debug("Creating line successfully completed");
        }
        catch (Exception e) {
            logger.debug("Error during creating line: " + e.getMessage());
            msg = createErrorLineMessage(sapId, e);
        }
        return msg;
    }

    private void debugRequest(List<BusinessPartnerDto> list) {
        logger.debug("-----------------------");
        for (BusinessPartnerDto line: list) logger.debug(line.toString());
        logger.debug("-----------------------");
    }
}
