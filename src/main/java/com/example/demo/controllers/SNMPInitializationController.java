package com.example.demo.controllers;

import com.example.demo.service.snmp.SNMPClient;
import com.example.demo.service.snmp.SNMPInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/snmp/")
@RestController()
public class SNMPInitializationController {
    private final SNMPInitializationService snmpInitializationService;

    @Autowired
    public SNMPInitializationController(SNMPInitializationService snmpInitializationService) {
        this.snmpInitializationService = snmpInitializationService;
    }
    @PostMapping("/initialize")
    public String initializeSNMPClient(@RequestParam("ip") String ip, @RequestParam("port") int port) {
        SNMPClient snmpClient = snmpInitializationService.createSnmpManager(ip, port);
        if(snmpClient != null) {return "Клиент успешно инициализирован";}
        return "Произошла ошибка в инициализации";
    }
}
