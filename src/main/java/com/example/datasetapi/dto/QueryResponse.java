package com.example.datasetapi.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class QueryResponse {
    private Map<String, List<JsonNode>> groupedRecords; 
    private List<JsonNode> sortedRecords;             
    private List<JsonNode> records;                    

    public Map<String, List<JsonNode>> getGroupedRecords() { 
        return groupedRecords; 
    }
    public void setGroupedRecords(Map<String, List<JsonNode>> groupedRecords) { 
        this.groupedRecords = groupedRecords; 
    }

    public List<JsonNode> getSortedRecords() { 
        return sortedRecords; 
    }
    public void setSortedRecords(List<JsonNode> sortedRecords) { 
        this.sortedRecords = sortedRecords; 
    }

    public List<JsonNode> getRecords() { 
        return records; 
    }
    public void setRecords(List<JsonNode> records) { 
        this.records = records; 
    }
}
