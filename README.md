# Price Service

## Description
Price Service Core is an API that retrieves product prices based on date and pricing rules. It follows **SOLID principles** and a **hexagonal architecture** for flexibility and scalability.

---

## Technologies
- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **Gradle**
- **Docker**
- **JUnit 5 & Mockito**
- **Swagger (Springdoc OpenAPI)**

---

### **Requirements**
- Java 21+
- Gradle 8+
- Docker 
### **Installation**
```sh
git clone https://github.com/your-username/price-service.git
cd price-service
```

---

##  Running the Application

## **Build & Run Docker Image Manually**

### **1. Build the Docker image**
```sh
docker build -t price-service .
```

### **2. Run the container**
```sh
docker run -p 8080:8080 --name price-service price-service
```

---

## Running Tests
```sh
./gradlew test
```

---

## **Swagger Documentation**
Access the **API documentation**:
🔗 **Swagger UI:**
```
http://localhost:8080/swagger-ui/index.html
```

---

## **Example Request**
```sh
curl -X GET "http://localhost:8080/prices?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00" -H "Content-Type: application/json"
```

** Example Response:**
```json
{
  "productId": 35455,
  "brandId": 1,
  "price": 25.45,
  "currency": "EUR"
}
```
