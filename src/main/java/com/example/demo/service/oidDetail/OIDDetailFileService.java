package com.example.demo.service.oidDetail;

import com.example.demo.model.OidDetail;
import com.example.demo.repository.OIDDetailRepository;
import org.snmp4j.smi.OID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OIDDetailFileService {
    @Autowired
    OIDDetailRepository oidDetailRepository;
    public String getOIDByEntryName(String entryName, String mibFileName) {
        OidDetail OIDDetail = oidDetailRepository.findByMibFileFileNameAndName(mibFileName, entryName);
        return OIDDetail.getOid();
    }

}
