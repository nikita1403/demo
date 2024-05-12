package com.example.demo.pojo;

import org.antlr.v4.runtime.misc.Pair;
import org.snmp4j.smi.OID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OIDValueResponse {
    private List<Pair<String, String>> data;
    private String error;

    public OIDValueResponse(String error) {
        this.error = error;
    }

    public OIDValueResponse(List<Pair<String, String>> data) {
        this.data = data;
    }

    public List<Pair<String, String>> getData() {
        return data;
    }

    public void setData(List<Pair<String, String>> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "OIDValueResponse{" +
                "data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
