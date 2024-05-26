package com.example.demo.repository;

import com.example.demo.model.MibFile;
import com.example.demo.model.OidDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MIBFileRepository extends JpaRepository<MibFile, Long> {
    public MibFile findByFileName(String fileName);
}
