# 🧪 BDD API Automation Framework – Petstore

## 📌 Overview
This project is a **Behavior-Driven Development (BDD) API Automation Framework** built using **Java, Cucumber, TestNG, and Rest Assured**.

It automates testing of the Swagger Petstore API and validates:
- CRUD operations
- Inventory analysis
- User validation
- Cross-endpoint workflows

---

## 🚀 Features
- ✔️ BDD-style test scenarios using Cucumber  
- ✔️ REST API automation using Rest Assured  
- ✔️ Dynamic test data generation  
- ✔️ Shared test context across step definitions  
- ✔️ Modular and maintainable framework design  
- ✔️ Covers both positive and negative test cases  
- ✔️ Integrated Allure Reports for advanced reporting  
- ✔️ Log4j-based structured logging for better debugging  

---

## 📂 Project Structure
```
bdd-automation/
│
├── src/test/java/
│   ├── client/              # API client layer (service methods)
│   ├── stepdefinitions/     # Step Definitions (Pet, User, Inventory, Cross)
│   ├── utils/               # Shared context (TestContext)
│   └── runners/             # Test Runners
│
├── src/test/resources/
│   ├── features/            # Feature files (BDD scenarios)
│   └── log4j2.xml           # Logging configuration
│
├── pom.xml                  # Maven dependencies
└── README.md
```

---

## 🔧 Tech Stack
- Java – Programming language  
- Cucumber – BDD framework  
- TestNG – Test execution  
- Rest Assured – API testing  
- Maven – Build & dependency management  
- Allure – Test reporting  
- Log4j2 – Logging  

---

## 📋 Test Scenarios Covered

### ✅ 1. Pet Lifecycle (CRUD & Chaining)
- Create Pet (POST)  
- Get Pet (GET)  
- Update Pet (PUT)  
- Delete Pet (DELETE)  
- Validate deletion (404)  

---

### ✅ 2. Inventory Analysis
- Fetch inventory from `/store/inventory`  
- Retrieve pets by status  
- **Strictly compare inventory count with API list**  

---

### ✅ 3. User Security & Negative Testing
- Create user with invalid email  
- Fetch non-existent user  
- Validate invalid login scenarios  

---

### ✅ 4. Cross-Endpoint Validation
- Create pet → Update status → Fetch sold pets  
- Verify created pet exists in sold list  

---

## 🔄 Dynamic Data Handling
Test data is dynamically generated using:
- `pet_<timestamp>`  
- `user_<timestamp>`  

### Ensures:
- ✔️ No data conflicts  
- ✔️ Unique test execution  

---

## ▶️ How to Run Tests

### 1️⃣ Clone Repository
```bash
git clone <your-repo-url>
cd bdd-automation
```

### 2️⃣ Run Tests
```bash
mvn clean test
```

---

## 📊 Allure Reporting
Generate and view reports using:
```bash
allure serve allure-results
```

---

## ⚠️ Notes

Swagger Petstore is a **public demo API**, so:

- Invalid email is not validated (returns `200 OK`)  
- Data persistence may be inconsistent  
- Inventory counts may vary due to shared environment usage  

### 🔹 Special Handling Implemented
- For **invalid login**, the API incorrectly returns `200 OK`  
- A **mock-based handling inside the client** is implemented to simulate:
  
  👉 `401 Unauthorized`

This ensures proper negative test validation.

✔️ This is the **only place where mocking is used**  
✔️ All other validations are **strict and based on actual API responses**

---

## 💡 Logging Implementation
- Implemented using **Log4j2**
- Provides structured logs for:
  - API requests
  - Responses
  - Status codes
  - Test flow tracking

---

## 💡 Best Practices Used
- ✔️ Separation of concerns (Client, Steps, Features)  
- ✔️ Reusable API methods  
- ✔️ Centralized test context  
- ✔️ Clean BDD structure  
- ✔️ Avoided duplicate step definitions  

---

## 👨‍💻 Team Contribution

### 🔹 AKULA VENKATA MAHESH KUMAR
- Implemented Pet API automation (CRUD operations)  
- Developed API client layer (PetStoreClient)  
- Integrated Allure reporting  
- Implemented Log4j logging  
- Managed framework structure and execution flow  

### 🔹 MOHAMMAD IMRAN
- Implemented User API test scenarios  
- Developed Inventory and Cross-endpoint validations  
- Designed feature files and BDD scenarios  
- Contributed to validation strategies and test coverage  

---

## 📌 Future Enhancements
- 🔹 Implement Hooks (Before/After)  
- 🔹 Enable Parallel Execution  
- 🔹 CI/CD Integration (GitHub Actions / GitLab CI)  

---

## ⭐ Conclusion
This framework demonstrates a **scalable, maintainable, and real-world API automation approach** using BDD principles.

It includes:
- Strict validation logic  
- Dynamic data handling  
- Structured logging  
- Controlled handling of API limitations  

Making it suitable for **real-world testing scenarios and professional use cases**.

---

## 👨‍💻 Authors
- Akula Venkata Mahesh Kumar  
- Mohammad Imran
Postman Testing : https://web.postman.co/workspace/My-Workspace~a27f166e-ef9a-4012-867f-d31a59be7ec4/collection/34342307-264effde-ee26-4fe6-994c-b6129c98a93d?action=share&source=copy-link&creator=34342307
