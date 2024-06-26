package com.example.demo.repository;

import com.example.demo.model.OidDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OIDDetailRepository extends JpaRepository<OidDetail, Long> {
    public OidDetail findByMibFileFileNameAndName(String mibFile, String name);
}
