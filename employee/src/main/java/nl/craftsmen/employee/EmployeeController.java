package nl.craftsmen.employee;

import nl.craftsmen.employee.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/{companyName}")
    public List<Employee> getEmployees(@PathVariable("companyName") String companyName){
        return employeeRepository.getEmployees(companyName);
    }
}
