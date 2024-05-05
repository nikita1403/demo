package com.example.demo.parser;

import com.example.demo.model.MibFile;

public class MIBData {
    private MibFile mibData;

    public MibFile getMibData() {
        return mibData;
    }

    public void setMibData(MibFile mibData) {
        this.mibData = mibData;
    }

    public MIBData(MibFile mibData) {
        this.mibData = mibData;
    }
}
