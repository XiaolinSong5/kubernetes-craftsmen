package nl.craftsmen.company;

import nl.craftsmen.company.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CompanyRepository {
    public Optional<Company> getCompany(String companyName) {
        return Stream.of(
                        new Company("Bitter bier", true, false))
                .filter(it -> it.name().equals(companyName))
                .findFirst();
    }
}
