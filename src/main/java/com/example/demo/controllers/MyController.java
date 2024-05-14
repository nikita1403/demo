package com.example.demo.controllers;

import com.example.demo.model.OidDetail;
import com.example.demo.pojo.OIDValueResponse;
import com.example.demo.pojo.OidRequest;
import com.example.demo.service.mib.MIBFileService;
import com.example.demo.service.mib.MibAnalysis;
import com.example.demo.service.oidDetail.OIDDetailFileService;
import com.example.demo.service.snmp.SNMPClient;
import com.example.demo.service.snmp.SNMPInitializationService;
import org.antlr.v4.runtime.misc.Pair;
import org.ietf.jgss.Oid;
import org.snmp4j.Snmp;
import org.snmp4j.smi.OID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController()
@RequestMapping("/api/snmp/")
//Переименовать контроллер
public class MyController {
    @Autowired
    private OIDDetailFileService oidDetailFileService;
    @Autowired
    private MIBFileService mibFileService;
    @Autowired
    private SNMPInitializationService snmpInitializationService;

    @GetMapping("/getDescription")
    public String getDescriptionByOID(@RequestParam String oid, String originalFileName)
    {
        return oidDetailFileService.getDescriptionByOID(oid, originalFileName);
    }
    @PostMapping("/test")
    public String apiTest()
    {
        return "данные получены";
    }

    @GetMapping("/getMibFileNames")
    public List<String> getMibFileNames()
    {
        return mibFileService.getAllMIBFileNames();
    }
    @PostMapping("/getValueOfTable")
    public OIDValueResponse getValueOfTable(@RequestBody OidRequest oidRequest)
    {
        Map<Map<OID, String>, Boolean> OIDs = getOidsByEntryName(oidRequest.getEntryNames(), oidRequest.getMibFileName());
        SNMPClient snmpClient = snmpInitializationService.getSnmpManager(oidRequest.getIp(), oidRequest.getPort());
        if(snmpClient == null)
        {
            return new OIDValueResponse("Ошибка, такой SNMP клиент не найден");
        }
        Map<String, String> OIDValues = null;
        List<Pair<OID, String>> isScalarOIDs = new ArrayList<>();
        List<Pair<OID, String>> isTableEntryOIDs = new ArrayList<>();
        try {
            OIDs.forEach((k, v) -> {
                if(v) isScalarOIDs.add(mapToPair(k));
                else  isTableEntryOIDs.add(mapToPair(k));

            });
            List<Pair<String, String>> responseOfTable = null;
            List<Pair<String, String>> responseOfScalar = null;
            if(!isScalarOIDs.isEmpty())
            {
                responseOfScalar = snmpClient.getAsString(isScalarOIDs, "public");
            }
            else if(!isTableEntryOIDs.isEmpty())
            {
                responseOfTable = snmpClient.getValueByTable(isTableEntryOIDs, "public");
            }
            else return new OIDValueResponse("Ошибка, нет ответа от клиента");
            return new OIDValueResponse(responseOfTable, responseOfScalar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Map<OID, String>, Boolean> getOidsByEntryName(List<String> names, String mibFileName)
    {
        Map<Map<OID, String>, Boolean> OIDs = new HashMap<>();
        for (String name : names) {
            Pair<OID, Boolean> pair = oidDetailFileService.getOIDByEntryName(name.trim(), mibFileName);
            OIDs.put(Map.of(pair.a, name), pair.b);
        }
        return OIDs;
    }

    private Pair<OID, String> mapToPair(Map<OID, String> OIDValues)
    {
        final OID[] oid = new OID[1];
        final String[] name = new String[1];
        OIDValues.forEach((x, y) -> {
            oid[0] = new OID(x);
            name[0] = y;
        });
        return new Pair<>(oid[0], name[0]);

    }






}
