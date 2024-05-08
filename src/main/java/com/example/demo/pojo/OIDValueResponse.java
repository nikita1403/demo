package com.example.demo.pojo;

import org.snmp4j.smi.OID;

import java.util.HashMap;
import java.util.Map;

public class OIDValueResponse {
    private Map<OID, String> data;
    private String error;

    public OIDValueResponse(String error) {
        this.error = error;
    }

    public OIDValueResponse(Map<OID, String> data) {
        this.data = data;
    }

    public Map<OID, String> getData() {
        return data;
    }

    public void setData(HashMap<OID, String> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
