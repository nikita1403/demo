package com.example.demo.service.snmp;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SNMPInitializationService {
    private final Map<String, SNMPClient> snmpClients = new HashMap<>();

    public SNMPClient createSnmpManager(String ip, int port) {

        //Добавить проверку на сущестовования такого SNMP-клиента в мапе
        SNMPClient snmpClient = new SNMPClient(ip, port);
        snmpClients.put(generateKey(ip, port), snmpClient);
        return snmpClient;
    }

    private String generateKey(String ip, int port) {
        return ip + ":" + port;
    }

    public SNMPClient getSnmpManager(String ip, int port) {
        return snmpClients.get(generateKey(ip, port));
    }
}
