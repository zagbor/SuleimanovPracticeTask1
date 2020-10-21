package ru.zagbor.practice.suleimanov.task1.controller;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CustomerController {

    void create(Customer customer) throws IOException;

    List<Customer> getAll() throws IOException;

    Optional<Customer> getCustomerForID(long id) throws IOException;

    boolean isCustomerExist(long id) throws IOException;

    void deleteCustomerForID(long id) throws IOException;

    void changeName(Customer customer, String name) throws IOException;

    void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException;

    void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException;

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException;

}
