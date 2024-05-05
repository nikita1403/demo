package com.example.demo.model;



import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mib_files")
public class MibFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mib_id")
    private Long mibId;

    public MibFile() {
    }

    public MibFile(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_name")
    private String fileName;

    @OneToMany(mappedBy = "mibFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OidDetail> oidDetails;

    // Геттеры и сеттеры
    public Long getMibId() {
        return mibId;
    }

    public void setMibId(Long mibId) {
        this.mibId = mibId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<OidDetail> getOidDetails() {
        return oidDetails;
    }

    public void setOidDetails(List<OidDetail> oidDetails) {
        this.oidDetails = oidDetails;
    }
}
