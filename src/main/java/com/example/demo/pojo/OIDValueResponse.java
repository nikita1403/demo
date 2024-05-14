package com.example.demo.pojo;

import org.antlr.v4.runtime.misc.Pair;
import org.snmp4j.smi.OID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OIDValueResponse {
    private List<Pair<String, String>> dataFromTable;
    private List<Pair<String, String>> dataFromScalar;

    private String error;

    public OIDValueResponse(String error) {
        this.error = error;
    }

    public OIDValueResponse(List<Pair<String, String>> dataFromTable, List<Pair<String, String>> dataFromScalar) {
        this.dataFromTable = dataFromTable;
        this.dataFromScalar = dataFromScalar;
    }

    public List<Pair<String, String>> getDataFromScalar() {
        return dataFromScalar;
    }

    public void setDataFromScalar(List<Pair<String, String>> dataFromScalar) {
        this.dataFromScalar = dataFromScalar;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Pair<String, String>> getDataFromTable() {
        return dataFromTable;
    }

    public void setDataFromTable(List<Pair<String, String>> dataFromTable) {
        this.dataFromTable = dataFromTable;
    }

    @Override
    public String toString() {
        return "OIDValueResponse{" +
                "dataFromTable=" + dataFromTable +
                ", dataFromScalar=" + dataFromScalar +
                ", error='" + error + '\'' +
                '}';
    }
}
