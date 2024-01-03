package nl.craftsmen.company;

import nl.craftsmen.company.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Service
public class EmployeeClient {

    private RestClient restClient;

    public EmployeeClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Employee> getEmployees(String companyName) {
        return Arrays.stream(restClient
                .get()
                .uri(format("http://localhost:8080/%s", companyName))
                .retrieve()
                .body(Employee[].class)).toList();
    }
}
