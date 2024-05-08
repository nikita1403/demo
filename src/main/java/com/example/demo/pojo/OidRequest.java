package com.example.demo.pojo;

import java.util.List;

public class OidRequest {
    private List<String> entryNames;
    private String mibFileName;
    private String ip;
    private int port;

    public List<String> getEntryNames() {
        return entryNames;
    }

    public void setEntryNames(List<String> entryNames) {
        this.entryNames = entryNames;
    }

    public String getMibFileName() {
        return mibFileName;
    }

    public void setMibFileName(String mibFileName) {
        this.mibFileName = mibFileName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}