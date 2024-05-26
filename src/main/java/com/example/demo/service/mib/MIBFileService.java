package com.example.demo.service.mib;


import com.example.demo.model.MibFile;
import com.example.demo.model.OidDetail;
import com.example.demo.parser.MIBData;
import com.example.demo.parser.MIBParser;
import com.example.demo.repository.MIBFileRepository;
import com.example.demo.repository.OIDDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;


@Service
public class MIBFileService {
    @Autowired
    MIBFileRepository mibFileRepository;
    @Autowired
    OIDDetailRepository oidDetailRepository;
    public void parseAndSaveMIBFile(String originalFileName, File mibFile){
        MIBData mibData = MIBParser.parseMIBFile(mibFile, originalFileName);
        saveMIBFile(mibData);
    }
    @Transactional
    protected void saveMIBFile(MIBData mibData){
       mibFileRepository.saveAndFlush(mibData.getMibData());
    }
    public List<String> getAllMIBFileNames() {
       return mibFileRepository.findAll().stream().map(MibFile::getFileName).toList();
    }
    public List<OidDetail> getOIDDetailByMIBFileName(String mibFileName) {
        return mibFileRepository.findByFileName(mibFileName).getOidDetails();
    }
}
