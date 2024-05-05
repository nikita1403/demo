package com.example.demo.parser;

import com.example.demo.model.MibFile;
import com.example.demo.model.OidDetail;
import com.example.demo.repository.MIBFileRepository;
import net.percederberg.mibble.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MIBParser {

    private static final MibLoader mibLoader = new MibLoader();
    public static MIBData parseMIBFile(File MIBFile, String originalFileName)
    {
        MibFile mibFile = new MibFile(originalFileName);
        List<OidDetail> oidDetails = new ArrayList<>();
        OidDetail oidDetail = null;
        try {
            // Загрузка MIB-файла
            MibLoader loader = new MibLoader();
            Mib mib = loader.load(MIBFile);
            for (MibSymbol symbol : mib.getAllSymbols()) {
                if (symbol instanceof MibValueSymbol) {
                    MibValueSymbol value = (MibValueSymbol) symbol;
                    String OID = value.getOid().toString();
                    String description = getDescription(value);
                    String name = value.getName();
                    int kind = getKind(value);
                    oidDetail = new OidDetail(mibFile, OID, name, description, kind);
                    oidDetails.add(oidDetail);
                }
            }
            mibFile.setOidDetails(oidDetails);
        } catch (MibLoaderException | IOException e) {
            e.printStackTrace();
        }
        return new MIBData(mibFile);
    }

    private static int getKind(MibValueSymbol symbol) {
        if(symbol.isScalar()) return 0;
        else if(symbol.isTable()) return 1;
        else if(symbol.isTableColumn()) return 2;
        else return 3;

    }

    private static String getDescription(MibValueSymbol symbol) {
        String rawTextFromMib = symbol.getText();
        String regex = "DESCRIPTION\\s+\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(rawTextFromMib);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("\\s+", " ").trim();
        }
        else
          return null;
    }
}
