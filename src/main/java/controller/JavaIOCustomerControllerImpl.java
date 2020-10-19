package controller;

import model.Account;
import model.Customer;
import model.Specialty;
import repository.CustomerRepository;
import repository.JavaIOCustomerRepositoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JavaIOCustomerControllerImpl implements CustomerController {

    private final CustomerRepository customerRepository = new JavaIOCustomerRepositoryImpl();

    public JavaIOCustomerControllerImpl() throws IOException {
    }

    public void create(Customer customer) throws IOException {
        customerRepository.save(customer);
    }


    @Override
    public List<Customer> findAll() throws IOException {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerForID(long id) throws IOException {
        return customerRepository.getCustomerForID(id);

    }

    public boolean editNameOfCustomer(long id, String name) throws IOException {
        return editNameOfCustomer(id, name);
    }

    public void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException {
        customerRepository.addSpecialtyCustomer(customerId, specialty);
    }


    public boolean isCustomerExist(long id) throws IOException {
        return customerRepository.isCustomerExist(id);
    }

    @Override
    public boolean deleteCustomerForID(long id) throws IOException {
        return customerRepository.deleteCustomerForID(id);
    }

    public void changeName(Customer customer, String name) throws IOException {
        customerRepository.changeName(customer, name);
    }

    public void changeAccountStatus(Customer customer, Account.AccountStatus status) throws IOException {
        customerRepository.changeAccountStatus(customer, status);
    }

    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException {
        customerRepository.deleteSpecialtyCustomer(customer, specialty);
    }



}


