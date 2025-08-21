package com.example.datasetapi.service;

import com.example.datasetapi.dto.InsertRecordResponse;
import com.example.datasetapi.model.DatasetRecord;
import com.example.datasetapi.repository.DatasetRecordRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DatasetService
{

    private final DatasetRecordRepository repository;
    private final ObjectMapper objectMapper;

    public DatasetService(DatasetRecordRepository repository, ObjectMapper objectMapper)
    {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public InsertRecordResponse insertRecord(String datasetName, JsonNode record)
    {
        if(!StringUtils.hasText(datasetName))
        {
            throw new IllegalArgumentException("datasetName must not be blank");
        }
        if(record == null || !record.isObject())
        {
            throw new IllegalArgumentException("Request body must be a JSON object");
        }

        try{
            String json = objectMapper.writeValueAsString(record);
            DatasetRecord saved = repository.save(new DatasetRecord(datasetName, json));
            return new InsertRecordResponse("Record added successfully", datasetName, saved.getId());
        }catch( JsonProcessingException e){
            throw new IllegalArgumentException("Invalid JSON Payload");
        }
    }

}