package com.example.demo.service.snmp;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SNMPInitializationService {
    private final Map<String, SNMPClient> snmpClients = new HashMap<>();

    public SNMPClient createSnmpManager(String ip, int port) {
        if(snmpClients.containsKey(generateKey(ip, port))) {
            try {
                destroySnmpManager(ip, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    public String destroySnmpManager(String ip, int port) throws IOException {
        if(snmpClients.containsKey(generateKey(ip, port))) {
            snmpClients.get(generateKey(ip,port)).stop();
            snmpClients.remove(generateKey(ip, port));
            return "Клиент успешно удален";
        }
        else return "Такого клиента не найдено";
    }
}
