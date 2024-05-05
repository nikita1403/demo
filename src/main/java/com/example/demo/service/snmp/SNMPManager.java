package com.example.demo.service.snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

public class SNMPManager {

    private Snmp snmp = null;
    private String address = null;
    public SNMPManager(String address) {
        this.address = address;
    }
    public void start() throws IOException {
        TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }


    public String getAsString(String oid, String community) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(community));
        if (event != null && event.getResponse() != null) {
            return event.getResponse().get(0).getVariable().toString();
        }
        throw new RuntimeException("Ответ от агента не получен");
    }


    private CommunityTarget getTarget(String community) {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(GenericAddress.parse(address));
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }
}
