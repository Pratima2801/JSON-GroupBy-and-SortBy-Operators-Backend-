package com.example.datasetapi.dto;

public class InsertRecordResponse
{
    private String message;
    private String dataset;
    private Long recordId;

    public InsertRecordResponse() {}

    public InsertRecordResponse(String message, String dataset, Long recordId)
    {
        this.message = message;
        this.dataset = dataset;
        this.recordId = recordId;
    }

    public String getMessage()
    {
        return message;
    }
    public String getDataset()
    {
        return dataset;
    }
    public Long getRecordId()
    {
        return recordId;
    }

    public void setMessage(String message) 
    {
        this.message = message;
    }
     public void setDataset(String dataset) 
    {
        this.dataset = dataset;
    }
    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }
}    