package com.example.demo.service.snmp;

import org.snmp4j.smi.OID;

import java.io.IOException;
import java.util.Map;

public class SNMPClient {
    private SNMPManager snmpManager;

    public SNMPClient(String address, int port) {
        snmpManager = new SNMPManager("udp:" + address + "/" + port);
        try {
            snmpManager.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getAsString(String oid, String community) throws IOException {
        return snmpManager.getAsString(oid, community);
    }

    public Map<OID, String> getValueByTable(OID[] oids, String community) throws IOException {
        return snmpManager.getValueByTable(oids, community);
    }
}
