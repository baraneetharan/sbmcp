package com.kgisl.sbmcp.mcptools;
 
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;
 
import java.util.Map;
 
@Component
public class EmployeeTools {
 
    private final RestClient restClient;
    private static final String BASE_URL = "https://sbbot-ab70.onrender.com/api";
 
    public EmployeeTools() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
 
    // 🔹 GET ALL
    @McpTool(name = "get_all_employees",
             description = "Fetch all employees from the system")
    public String getAllEmployees() {
        return restClient.get()
                .uri("/employees")
                .retrieve()
                .body(String.class);
    }
 
    // 🔹 GET BY ID
    @McpTool(name = "get_employee_by_id",
             description = "Fetch a single employee by ID")
    public String getEmployeeById(
            @McpToolParam(description = "Employee ID", required = true)
            int id) {
 
        return restClient.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .body(String.class);
    }
 
    // 🔹 CREATE
    @McpTool(name = "create_employee",
             description = "Create a new employee")
    public String createEmployee(
            @McpToolParam(description = "Employee name", required = true) String name,
            @McpToolParam(description = "Employee email", required = true) String email,
            @McpToolParam(description = "Department", required = true) String dept,
            @McpToolParam(description = "Salary", required = true) double salary) {
 
        Map<String, Object> payload = Map.of(
                "name", name,
                "email", email,
                "dept", dept,
                "salary", salary
        );
 
        return restClient.post()
                .uri("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(String.class);
    }
 
    // 🔹 UPDATE
    @McpTool(name = "update_employee",
             description = "Update an employee by ID")
    public String updateEmployee(
            @McpToolParam(description = "Employee ID", required = true) long id,
            @McpToolParam(description = "Name", required = true) String name,
            @McpToolParam(description = "Email", required = true) String email,
            @McpToolParam(description = "Department", required = true) String dept,
            @McpToolParam(description = "Salary", required = true) double salary) {
 
        Map<String, Object> payload = Map.of(
                "name", name,
                "email", email,
                "dept", dept,
                "salary", salary
        );
 
        return restClient.put()
                .uri("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(String.class);
    }
 
    // 🔹 DELETE
    @McpTool(name = "delete_employee",
             description = "Delete employee by ID")
    public String deleteEmployee(
            @McpToolParam(description = "Employee ID", required = true)
            long id) {
 
        restClient.delete()
                .uri("/employees/{id}", id)
                .retrieve()
                .toBodilessEntity();
 
        return "Employee deleted successfully";
    }
}
 
 