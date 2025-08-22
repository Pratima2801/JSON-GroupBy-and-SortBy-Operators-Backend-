### Application Overview
A Spring Boot backend to store JSON records in datasets and query them with groupBy and sortBy.
Supports PostgreSQL for persistence and Postman for testing.

### Setup Instructions

**Pre-requisities**
   1. Java 17+
   2. Maven installed
   3. PostgreSQL running locally
 
 **Steps**
   1. Clone the project
         -> git clone https://github.om<your-username>/datasetapi.git
         -> cd datasetapi

   2. Create a database in PostgreSQL
         -> CREATE DATABASE datasetapi_db;

   3. Build and run the application
         -> mvn spring-boot:run
   4. The API runs at http://localhost:8080/api/dataset/...
   5. Test using Postman.

**Steps to Test**
   1. Insert API 
       --> POST /api/dataset/{datasetName}/record → Insert a new JSON record into the specified dataset.

   2. Query API 
       --> GET /api/dataset/{datasetName}/query → Retrieve all records from the dataset (default, no filters).

       --> GET /api/dataset/{datasetName}/query?groupBy=field → Group dataset records by the given JSON field.

       --> GET /api/dataset/{datasetName}/query?sortBy=field&order=asc|desc → Sort dataset records by given field in ascending/descending order.



 