package repository;

import model.Account;
import model.Customer;
import model.Specialty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaIOCustomerRepositoryImpl implements CustomerRepository {

    private final String CUSTOMERS = ".\\src\\main\\resources\\Customers";
    private final SpecialtyRepository specialtyRepository;

    public JavaIOCustomerRepositoryImpl() throws IOException {
        specialtyRepository = new SpecialtyRepositoryImpl();
    }

    public void save(Customer customer) throws IOException {
        if (customer.getId() <= 0) {
            customer.setId(findMaxId() + 1);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS, true));
        if (new File(CUSTOMERS).length() != 0) {
            writer.write("\n");
        }
        writer.write(customer.getId() + ";" + customer.getName() + ";" + specialtyRepository.specialtiesToStringForBase(customer.getSpecialties()) + ";" + customer.getAccount().getAccountStatus());
        writer.flush();
    }

    public List<Customer> findAll() throws IOException {
        Path path = Paths.get(CUSTOMERS);
        return Files.lines(path, StandardCharsets.UTF_8)
                .map(this::parseCustomer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Customer> parseCustomer(String line) {
        try {
            String[] result = line.split(";");
            Customer customer =
                    new Customer(Long.parseLong(result[0]), result[1], parseSpecialties(result[2]), new Account(Account.AccountStatus.valueOf(result[3])));
            return Optional.of(customer);
        } catch (IOException e) {
            System.err.println(e);
            return Optional.empty();
        }
    }

    private Set<Specialty> parseSpecialties(String data) throws IOException {
        String[] result = data.split(",");
        Set<Specialty> specialties = new HashSet<>();
        for (String s : result) {
            specialties.add(specialtyRepository.getSpecialtyForId(Long.parseLong(s)).get());
        }
        return specialties;
    }

    private Long findMaxId() throws IOException {
        return findAll().stream()
                .map(customer -> customer.getId())
                .max(Comparator.naturalOrder()).orElse(0L);
    }

    public Optional<Customer> getCustomerForID(long id) throws IOException {
        return findAll().stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }

    public boolean isCustomerExist(long id) throws IOException {
        return findAll().stream()
                .anyMatch(customer -> customer.getId() == id);
    }

    public boolean deleteCustomerForID(long id) throws IOException {
        if (!getCustomerForID(id).isPresent()) {
            return false;
        }
        List<Customer> customers = findAll();
        Files.newBufferedWriter(Paths.get(CUSTOMERS), StandardOpenOption.TRUNCATE_EXISTING);
        customers.stream().filter(customer -> customer.getId() != id).forEach(customer -> {
            try {
                save(customer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return true;
    }


    public void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException {
        Customer customer = getCustomerForID(customerId).get();
        customer.getSpecialties().add(specialty);
        deleteCustomerForID(customerId);
        save(customer);
    }

    public void changeName(Customer customer, String name) throws IOException {
        customer.setName(name);
        deleteCustomerForID(customer.getId());
        save(customer);
    }

    public void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException {
        customer.getAccount().setAccountStatus(accountStatus);
        deleteCustomerForID(customer.getId());
        save(customer);
    }

    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException {
        customer.getSpecialties().remove(specialty);
        deleteCustomerForID(customer.getId());
        save(customer);
    }
}
