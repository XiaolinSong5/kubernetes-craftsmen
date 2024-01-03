package nl.craftsmen.company;

import nl.craftsmen.company.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class CompanyController {

    private CompanyRepository companyRepository;
    private EmployeeClient employeeClient;

    public CompanyController(CompanyRepository employeeRepository, EmployeeClient employeeClient) {
        this.companyRepository = employeeRepository;
        this.employeeClient = employeeClient;
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello world!";
    }

    @GetMapping("/average-monthly-salary/{companyName}")
    public double montlySalary(@PathVariable("companyName") String companyName){
        var employees = employeeClient.getEmployees(companyName);

        return employees.stream().collect(Collectors.averagingDouble(Employee::salaryInEuros));

    }
}
