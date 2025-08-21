package com.example.datasetapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "dataset_records",indexes = {@Index(name = "idx_dataset_name", columnList = "dataset_name")})

public class DatasetRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dataset_name", nullable = false, length = 100)
    private String datasetName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "record_json", columnDefinition = "jsonb", nullable = false)
    private  String recordJson;

    public DatasetRecord() {}

    public DatasetRecord(String datasetName, String recordJson) 
    {
        this.datasetName = datasetName;
        this.recordJson = recordJson;
    }

    public Long getId() 
    {
        return id;
    }
    public String getDatasetName()
    {
        return datasetName;    
    }
    public String getRecordJson()
    {
        return recordJson;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public void setDatasetName(String datasetName)
    {
        this.datasetName = datasetName;    
    }
     public void setRecordJson(String recordJson) 
    {
        this.recordJson = recordJson;    
    }
    


}
