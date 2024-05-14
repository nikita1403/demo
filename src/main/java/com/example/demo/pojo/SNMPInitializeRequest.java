package com.example.demo.pojo;

public class SNMPInitializeRequest {
    private String ip;
    private int port;

    // Геттеры и сеттеры
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
