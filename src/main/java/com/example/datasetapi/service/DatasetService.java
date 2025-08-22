package com.example.datasetapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.datasetapi.dto.InsertRecordResponse;
import com.example.datasetapi.dto.QueryResponse;
import com.example.datasetapi.model.DatasetRecord;
import com.example.datasetapi.repository.DatasetRecordRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    public QueryResponse query(String datasetName, String groupBy, String sortBy, String order) {
    if (!StringUtils.hasText(datasetName)) {
        throw new IllegalArgumentException("datasetName must not be blank");
    }

    var rows = repository.findByDatasetName(datasetName);

    var items = rows.stream().map(r -> {
        try {
            JsonNode n = objectMapper.readTree(r.getRecordJson());
            if (!n.isObject()) throw new IllegalArgumentException("Stored record is not a JSON object");
            return (ObjectNode) n;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to parse stored JSON", e);
        }
    }).toList();

    QueryResponse resp = new QueryResponse();

    if (StringUtils.hasText(groupBy)) {
        Map<String, List<JsonNode>> grouped = new java.util.LinkedHashMap<>();
        for (ObjectNode obj : items) {
            JsonNode v = obj.path(groupBy);
            String key = (v == null || v.isMissingNode() || v.isNull()) ? "null" : v.asText();
            grouped.computeIfAbsent(key, k -> new java.util.ArrayList<>()).add(obj);
        }
        resp.setGroupedRecords(grouped);
        return resp;
    }

    if (StringUtils.hasText(sortBy)) {
        String ord = (order == null) ? "asc" : order.toLowerCase();
        java.util.Comparator<ObjectNode> cmp = java.util.Comparator.comparing(
                o -> extractComparable(o.path(sortBy)),
                DatasetService::safeCompare
        );
        if ("desc".equals(ord)) cmp = cmp.reversed();
        var sorted = items.stream().sorted(cmp).map(x -> (JsonNode) x).toList();
        resp.setSortedRecords(sorted);
        return resp;
    }

    resp.setRecords(items.stream().map(x -> (JsonNode) x).toList());
    return resp;
}

private static Comparable<?> extractComparable(JsonNode node) {
    if (node == null || node.isMissingNode() || node.isNull()) return NullComparable.INSTANCE;
    if (node.isNumber()) return node.asDouble();
    if (node.isTextual()) return node.asText();
    if (node.isBoolean()) return node.asBoolean();
    return node.toString();
}

@SuppressWarnings({ "rawtypes", "unchecked" })
private static int safeCompare(Comparable a, Comparable b) {
    if (a == b) return 0;
    if (a == NullComparable.INSTANCE) return 1;   
    if (b == NullComparable.INSTANCE) return -1;
    if (a instanceof Number || b instanceof Number) {
        double da = (a instanceof Number) ? ((Number) a).doubleValue() : tryParseDouble(a.toString());
        double db = (b instanceof Number) ? ((Number) b).doubleValue() : tryParseDouble(b.toString());
        return Double.compare(da, db);
    }
    try { 
        return a.compareTo(b); 
    }
    catch (ClassCastException ex) { 
        return a.toString().compareTo(b.toString()); 
    }
}

private static double tryParseDouble(String s) {
    try { 
        return Double.parseDouble(s); 
    } 
    catch (Exception e) { 
        return Double.NaN; 
    }
}

private static final class NullComparable implements Comparable<NullComparable> {
    static final NullComparable INSTANCE = new NullComparable();
    @Override public int compareTo(NullComparable o) { 
        return 0; 
    }
    @Override public String toString() { 
        return "null"; 
    }
}

}