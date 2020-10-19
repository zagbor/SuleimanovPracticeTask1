package controller;

import model.Account;
import model.Customer;
import model.Specialty;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CustomerController extends GenericController<Customer, Long> {

    void create(Customer customer) throws IOException;

    List<Customer> findAll() throws IOException;

    Optional<Customer> getCustomerForID(long id) throws IOException;

    boolean isCustomerExist(long id) throws IOException;

    boolean deleteCustomerForID(long id) throws IOException;

    void changeName(Customer customer, String name) throws IOException;

    void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException;

    void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException;

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException;
}
