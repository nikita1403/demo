package com.example.demo.service.snmp;

import org.antlr.v4.runtime.misc.Pair;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SNMPManager {

    private Snmp snmp = null;
    private String address = null;
    private TableUtils tableUtils = null;
    public SNMPManager(String address) {
        this.address = address;
    }
    public void start() throws IOException {
        TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
        tableUtils = new TableUtils(snmp, new DefaultPDUFactory());
    }
    public void stop() throws IOException {
        snmp.close();
    }

    public List<Pair<String, String>> getValueByTable(List<Pair<OID, String>> oids, String community) throws IOException {
        List<Pair<String, String>> list = new ArrayList<>();
        OID[] oidArray = new OID[oids.size()];
        for (int i = 0; i < oids.size(); i++) {
            oidArray[i] = oids.get(i).a;
        }
        List<TableEvent> events = tableUtils.getTable(getTarget(community), oidArray, null, null);
        String lastOID = null;
        for (TableEvent event : events) {
            if(event.isError())
            {
                list.add(new Pair<>("null","Ошибка: " + event.getErrorMessage()));
                System.out.println();
            }
            else
            {
                for (VariableBinding vb : event.getColumns())
                {
                    if(vb != null)
                    {
                        String oid = vb.getOid().toString();
                        list.add(new Pair<>(getNameByOID(oids, new OID(oid.substring(0, oid.length()-event.getIndex().toString().length()))), vb.getVariable().toString()));
                    }
                }
            }
        }
        return list;
    }

    public List<Pair<String, String>> getAsString(List<Pair<OID, String>> OIDs, String community) throws IOException {
        List<Pair<String, String>> list = new ArrayList<>();
        for (Pair<OID, String> oid : OIDs) {
            PDU pdu = new PDU();
            String oid2 = oid.a.toString();
            oid2+=".0";

            pdu.add(new VariableBinding(new OID(oid2)));
            pdu.setType(PDU.GET);
            ResponseEvent event = snmp.send(pdu, getTarget(community));
            if (event != null && event.getResponse() != null) {
                list.add(new Pair<>(oid.b, event.getResponse().get(0).getVariable().toString()));

            }
            else list.add(new Pair<>(oid.b,"Ответ от агента не получен!"));
        }
        return list;

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

    private String getNameByOID(List<Pair<OID, String>> oids, OID oid) {
        for (Pair<OID, String> pair : oids) {
            if(oid.equals(pair.a)) return pair.b;
        }
        return null;
    }


}
