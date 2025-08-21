package com.example.datasetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.datasetapi.model.DatasetRecord;

@Repository
public interface DatasetRecordRepository extends JpaRepository<DatasetRecord, Long> {
}
