package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "oid_details")
public class OidDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid_id")
    private Long oidId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mib_id")
    @JsonBackReference
    private MibFile mibFile;

    @Column(name = "oid")
    private String oid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "kind")
    private int kind;

    public Long getOidId() {
        return oidId;
    }

    public void setOidId(Long oidId) {
        this.oidId = oidId;
    }

    public MibFile getMibFile() {
        return mibFile;
    }

    public OidDetail() {
    }

    public void setMibFile(MibFile mibFile) {
        this.mibFile = mibFile;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "OidDetail{" +
                "oidId=" + oidId +
                ", mibFile=" + mibFile +
                ", oid='" + oid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", kind=" + kind +
                '}';
    }

    public OidDetail(MibFile mibFile, String oid, String name, String description, int kind) {
        this.mibFile = mibFile;
        this.oid = oid;
        this.name = name;
        this.description = description;
        this.kind = kind;
    }
}
