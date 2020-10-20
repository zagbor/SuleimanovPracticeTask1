package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
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
    private final AccountRepository accountRepository;

    public JavaIOCustomerRepositoryImpl() throws IOException {
        specialtyRepository = new SpecialtyRepositoryImpl();
        accountRepository = new AccountRepositoryImpl();
    }

    public void save(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS, true))) {
            if (customer.getId() <= 0) {
                customer.setId(findMaxId() + 1);
            }
            if (new File(CUSTOMERS).length() != 0) {
                writer.write("\n");
            }
            accountRepository.save(customer.getAccount());
            writer.write(
                    customer.getId() + ";"
                            + customer.getName() + ";"
                            + specialtyRepository.specialtiesToStringForBase(customer.getSpecialties()) + ";"
                            + customer.getAccount().getId() + ";"
                            + customer.getAccount().getAccountStatus());
            writer.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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

    public void deleteCustomerForID(long id) throws IOException {
        if (!getCustomerForID(id).isPresent()) {
            return;
        }
        List<Customer> customers = findAll();
        Files.newBufferedWriter(Paths.get(CUSTOMERS), StandardOpenOption.TRUNCATE_EXISTING);
        accountRepository.deleteAll();
        customers.stream()
                .filter(customer -> customer.getId() != id)
                .forEach(customer -> save(customer));
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
            Account account = new Account(Long.parseLong(result[3]), Account.AccountStatus.valueOf(result[4]));
            Customer customer = new Customer(Long.parseLong(result[0]), result[1], parseSpecialties(result[2]), account);
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
}
