package com.example.demo.controllers;

import com.example.demo.model.OidDetail;
import com.example.demo.pojo.OIDValueResponse;
import com.example.demo.pojo.OidRequest;
import com.example.demo.service.mib.MIBFileService;
import com.example.demo.service.mib.MibAnalysis;
import com.example.demo.service.oidDetail.OIDDetailFileService;
import com.example.demo.service.snmp.SNMPClient;
import com.example.demo.service.snmp.SNMPInitializationService;
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

    //Переписать метод, чтобы он вытягивал данные с БД
    @GetMapping("/getDescription")
    public String getDescriptionByOID(@RequestParam String oid, String originalFileName)
    {
        return MibAnalysis.getDescription(oid, originalFileName);
    }
    @GetMapping("/getValueByName")
    public OIDValueResponse getValueByName(@RequestParam String name, @RequestParam String mibFileName, @RequestParam String ip, @RequestParam int port) //Продумать лучший механизм, чтобы с каждым запросом не отправлять ip и port
                                                                                                             //Можно будет сделать это на фронте
    {
        SNMPClient snmpClient = snmpInitializationService.getSnmpManager(ip, port);
        if(snmpClient == null) return new OIDValueResponse("SNMP клиент с таким ip + port не найдено");
        String oid = oidDetailFileService.getOIDByEntryName(name, mibFileName);
        try {
          return new OIDValueResponse(new HashMap<>(Map.of(new OID(oid),snmpClient.getAsString(oid, "public"))));
        } catch (IOException e) {
            return new OIDValueResponse("не найдено");
        }
    }
    @GetMapping("/getMibFileNames")
    public List<String> getMibFileNames()
    {
        return mibFileService.getAllMIBFileNames();
    }
    @PostMapping("/getValueOfTable")
    public OIDValueResponse getValueOfTable(@RequestBody OidRequest oidRequest)
    {
        List<OID> OIDs = getOidsByEntryName(oidRequest.getEntryNames(), oidRequest.getMibFileName());
        SNMPClient snmpClient = snmpInitializationService.getSnmpManager(oidRequest.getIp(), oidRequest.getPort());
        if(snmpClient == null)
        {
            return new OIDValueResponse("Ошибка, такой SNMP клиент не найден");
        }
        Map<OID, String> OIDValues = null;
        try {
            OIDValues = snmpClient.getValueByTable(OIDs.toArray(new OID[0]), "public");
            return new OIDValueResponse(OIDValues);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private List<OID> getOidsByEntryName(List<String> names, String mibFileName)
    {
        List<OID> oids = new ArrayList<>();
        for (String name : names) {
            oids.add(new OID(oidDetailFileService.getOIDByEntryName(name.trim(), mibFileName)));
        }
        return oids;
    }





}
