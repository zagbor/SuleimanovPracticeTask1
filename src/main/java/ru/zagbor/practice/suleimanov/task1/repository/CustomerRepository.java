package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository extends GenericRepository<Customer, Long> {
    List<Customer> getAll() throws IOException;

    boolean isCustomerExist(long id) throws IOException;

    void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException;

    void changeName(Customer customer, String name) throws IOException;

    void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException;

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException;

}
