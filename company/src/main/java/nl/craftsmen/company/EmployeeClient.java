package nl.craftsmen.company;

import nl.craftsmen.company.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Service
public class EmployeeClient {

    private final RestClient restClient;

    @Value("company.employee.client")
    private String employeeClient;

    private final Logger logger = LoggerFactory.getLogger(EmployeeClient.class);

    public EmployeeClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Employee> getEmployees(String companyName) {
        logger.info("Getting employees from " + employeeClient);
        return Arrays.stream(restClient
                .get()
                .uri(format("http://localhost:8080/%s", companyName))
                .retrieve()
                .body(Employee[].class)).toList();
    }
}
