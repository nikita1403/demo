package com.example.demo.repository;

import com.example.demo.model.MibFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MIBFileRepository extends JpaRepository<MibFile, Long> {
}
