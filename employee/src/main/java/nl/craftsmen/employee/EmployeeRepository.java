package nl.craftsmen.employee;

import nl.craftsmen.employee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class EmployeeRepository {
    public List<Employee> getEmployees(String companyName) {
        return Stream.of(
                        new Employee("Piet Pietersen", "Bitter bier", 81000.12),
                        new Employee("Hans Hanson", "Bitter bier", 51000.12),
                        new Employee("Klaas Klaassen", "Bitter bier", 11000.12),
                        new Employee("Aart Appel", "Zoete broodjes", 51000.12),
                        new Employee("Henk Huppelschoten", "Zoete broodjes", 61000.12),
                        new Employee("Ab Rupt", "Bitter bier", 54000.12),
                        new Employee("Stanley Messy", "Zoete broodjes", 51200.12),
                        new Employee("Anne Aniek", "Vage verzekeringen", 31000.12),
                        new Employee("Berta Briljantje", "Vage verzekeringen", 45000.12)
                ).filter(it -> it.company().equals(companyName))
                .toList();
    }
}
