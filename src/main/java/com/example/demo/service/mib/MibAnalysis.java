package com.example.demo.service.mib;

import com.example.demo.repository.MIBFileRepository;
import net.percederberg.mibble.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MibAnalysis {
    private static final MibLoader mibLoader = new MibLoader();
    private static HashMap<String, HashMap<String, String>> fileNameToOidToDescription = new HashMap<>();
    public static void oidToDescription(File MIBFile, String originalFileName)
    {
        HashMap<String, String> oidAndDecs = null;
        try {
            // Загрузка MIB-файла
            MibLoader loader = new MibLoader();
            Mib mib = loader.load(MIBFile);
            oidAndDecs = new HashMap<>();
            for (MibSymbol symbol : mib.getAllSymbols()) {
                if (symbol instanceof MibValueSymbol) {
                    String OID = ((MibValueSymbol) symbol).getOid().toString();
                    String rawTextFromMib = symbol.getText();
                    String regex = "DESCRIPTION\\s+\"([^\"]*)\"";
                    Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(rawTextFromMib);
                    if (matcher.find()) {
                        // Обработка строки, удаляя переводы строк и возвращая чистое описание
                        oidAndDecs.put("."+OID+".0", matcher.group(1).replaceAll("\\s+", " ").trim()) ;
                    }
                    else
                        oidAndDecs.put(OID, null);
                }
            }
        } catch (MibLoaderException | IOException e) {
            e.printStackTrace();
        }
        oidAndDecs.forEach((x, y) -> System.out.println(x + "\t" + y));
        fileNameToOidToDescription.put(originalFileName, oidAndDecs);
    }
    public static String getDescription(String oid, String originalFileName) {
        return fileNameToOidToDescription.get(originalFileName).get(oid);
    }
}
