package repository;

import model.Account;
import model.Customer;
import model.Specialty;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer, Long> {
    Optional<Customer> getCustomerForID(long id) throws IOException;

    List<Customer> findAll() throws IOException;

    boolean deleteCustomerForID(long id) throws IOException;

    boolean isCustomerExist(long id) throws IOException;

    void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException;

    void changeName(Customer customer, String name) throws IOException;

    void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException;

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException;

}
