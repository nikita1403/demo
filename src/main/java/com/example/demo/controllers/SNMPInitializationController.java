package com.example.demo.controllers;

import com.example.demo.pojo.SNMPInitializeRequest;
import com.example.demo.service.snmp.SNMPClient;
import com.example.demo.service.snmp.SNMPInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/snmp/")
@RestController()
public class SNMPInitializationController {
    private final SNMPInitializationService snmpInitializationService;

    @Autowired
    public SNMPInitializationController(SNMPInitializationService snmpInitializationService) {
        this.snmpInitializationService = snmpInitializationService;
    }
    @PostMapping("/initialize")
    public String initializeSNMPClient(@RequestBody SNMPInitializeRequest SNMPInitializeRequest) {
        SNMPClient snmpClient = snmpInitializationService.createSnmpManager(SNMPInitializeRequest.getIp(), SNMPInitializeRequest.getPort());
        if(snmpClient != null) {return "Клиент успешно инициализирован";}
        return "Произошла ошибка в инициализации";
    }
    @PostMapping("/delete")
    public String deleteSNMPClient(@RequestBody SNMPInitializeRequest SNMPInitializeRequest) throws IOException {
        String response = snmpInitializationService.destroySnmpManager(SNMPInitializeRequest.getIp(), SNMPInitializeRequest.getPort());
        return response;
    }
    @GetMapping("/getSNMPClients")
    public List<String> getAllSNMPClients(){
        return snmpInitializationService.getSNMPClients();
    }
}
