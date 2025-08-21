package com.example.datasetapi.controller;

import com.example.datasetapi.dto.InsertRecordResponse;
import com.example.datasetapi.service.DatasetService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/dataset")
public class DatasetController
{
    private final DatasetService service;

    public DatasetController(DatasetService service)
    {
        this.service = service;
    }
    @PostMapping("/{datasetName}/record")
    public ResponseEntity<InsertRecordResponse> insertRecord(@PathVariable String datasetName, @RequestBody JsonNode record) {
        InsertRecordResponse resp = service.insertRecord(datasetName, record);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}