package ru.zagbor.practice.suleimanov.task1.controller;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepository;
import ru.zagbor.practice.suleimanov.task1.repository.JavaIOCustomerRepositoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JavaIOCustomerControllerImpl implements CustomerController {

    private final CustomerRepository customerRepository = new JavaIOCustomerRepositoryImpl();

    public JavaIOCustomerControllerImpl() throws IOException {
    }
    @Override
    public void create(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() throws IOException {
        return customerRepository.findAll();
    }
    @Override
    public Optional<Customer> getCustomerForID(long id) throws IOException {
        return customerRepository.getCustomerForID(id);

    }

    @Override
    public void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException {
        customerRepository.addSpecialtyCustomer(customerId, specialty);
    }

    @Override
    public boolean isCustomerExist(long id) throws IOException {
        return customerRepository.isCustomerExist(id);
    }

    @Override
    public void deleteCustomerForID(long id) throws IOException {
        customerRepository.deleteCustomerForID(id);
    }
    @Override
    public void changeName(Customer customer, String name) throws IOException {
        customerRepository.changeName(customer, name);
    }
    @Override
    public void changeAccountStatus(Customer customer, Account.AccountStatus status) throws IOException {
        customerRepository.changeAccountStatus(customer, status);
    }
    @Override
    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException {
        customerRepository.deleteSpecialtyCustomer(customer, specialty);
    }

}


