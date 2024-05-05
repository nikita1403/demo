package com.example.demo.controllers;

import com.example.demo.model.OidDetail;
import com.example.demo.service.mib.MIBFileService;
import com.example.demo.service.mib.MibAnalysis;
import com.example.demo.service.oidDetail.OIDDetailFileService;
import com.example.demo.service.snmp.SNMPClient;
import com.example.demo.service.snmp.SNMPInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public String getValueByName(@RequestParam String name, @RequestParam String mibFileName, @RequestParam String ip, @RequestParam int port) //Продумать лучший механизм, чтобы с каждым запросом не отправлять ip и port
                                                                                                             //Можно будет сделать это на фронте
    {
        SNMPClient snmpClient = snmpInitializationService.getSnmpManager(ip, port);
        if(snmpClient == null) return "SNMP клиент с таким ip + port не найдено";
        String oid = oidDetailFileService.getOIDByEntryName(name, mibFileName);
        try {
          return snmpClient.getAsString(oid, "public");
        } catch (IOException e) {
            return "не найдено";
        }
    }
    @GetMapping("/getMibFileNames")
    public List<String> getMibFileNames()
    {
        return mibFileService.getAllMIBFileNames();
    }


}
