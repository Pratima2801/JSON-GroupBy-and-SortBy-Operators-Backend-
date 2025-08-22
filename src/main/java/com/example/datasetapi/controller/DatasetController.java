package com.example.datasetapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.datasetapi.dto.InsertRecordResponse;
import com.example.datasetapi.dto.QueryResponse;
import com.example.datasetapi.service.DatasetService;
import com.fasterxml.jackson.databind.JsonNode;

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

    @GetMapping("/{datasetName}/query")
public ResponseEntity<QueryResponse> query(
        @PathVariable String datasetName,
        @RequestParam(name = "groupBy", required = false) String groupBy,
        @RequestParam(name = "sortBy", required = false) String sortBy,
        @RequestParam(name = "order", required = false) String order
) {
    QueryResponse resp = service.query(datasetName, groupBy, sortBy, order);
    return ResponseEntity.ok(resp);
}
}