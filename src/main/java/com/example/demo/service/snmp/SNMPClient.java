package com.example.demo.service.snmp;

import org.antlr.v4.runtime.misc.Pair;
import org.snmp4j.smi.OID;

import java.io.IOException;
import java.util.List;
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

    public List<Pair<String, String>> getAsString(List<Pair<OID, String>> OIDs, String community) throws IOException {
        return snmpManager.getAsString(OIDs, community);
    }

    public List<Pair<String, String>> getValueByTable(List<Pair<OID, String>> oids, String community) throws IOException {
        return snmpManager.getValueByTable(oids, community);
    }
    public void stop() throws IOException {
        snmpManager.stop();
    }
}
