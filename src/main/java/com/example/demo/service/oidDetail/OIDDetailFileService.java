package com.example.demo.service.oidDetail;

import com.example.demo.model.OidDetail;
import com.example.demo.repository.OIDDetailRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.ietf.jgss.Oid;
import org.snmp4j.smi.OID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OIDDetailFileService {
    @Autowired
    OIDDetailRepository oidDetailRepository;
    public Pair<OID, Boolean> getOIDByEntryName(String entryName, String mibFileName) {
        OidDetail OIDDetail = oidDetailRepository.findByMibFileFileNameAndName(mibFileName, entryName);
        return new Pair<>(new OID(OIDDetail.getOid()), (OIDDetail.getKind() == 0));
    }

    public boolean isScalar(String entryName, String mibFileName) {
        OidDetail OIDDetail = oidDetailRepository.findByMibFileFileNameAndName(mibFileName, entryName);
        return OIDDetail.getKind() == 0;
    }

    public String getDescriptionByOID(String OID, String mibFileName) {
        OidDetail OIDDetail = oidDetailRepository.findByMibFileFileNameAndName(mibFileName, OID);
        return OIDDetail.getDescription();
    }

}
