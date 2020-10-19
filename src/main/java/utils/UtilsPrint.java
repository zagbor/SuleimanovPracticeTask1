package utils;

import controller.CustomerController;
import controller.JavaIOCustomerControllerImpl;
import model.Account;
import model.Customer;
import model.Specialty;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class UtilsPrint {
    private final CustomerController customerController = new JavaIOCustomerControllerImpl();

    public UtilsPrint() throws IOException {
    }

    public void showListAccountStatus(List<Account.AccountStatus> accountStatusList) {
        System.out.printf("%-10s%n", "Имя");
        accountStatusList.stream().forEach(accountStatus -> System.out.printf("%-10s%n", accountStatus));
    }

    public void showSetSpecialties(Set<Specialty> specialties) throws IOException {
        System.out.printf("%-5s%-11s%n", "ID", "Имя");
        specialties.stream().forEach(specialty -> System.out.printf("%-5d%-11s%n", specialty.getId(), specialty.getName()));
    }

    public void showCustomer(Customer customer) throws IOException {
        System.out.printf("%-5s%-11s%-40s%-11s%n", "ID", "1-Имя", "2-Специальности", "3-Состояние аккаунта");
        System.out.printf("%-5d%-11s%-40s%-11s%n",
                customer.getId(), customer.getName(), customer.toStringSpecialties(), customer.getAccount().getAccountStatus());
    }

    public void showAllListCustomers() throws IOException {
        System.out.printf("%-5s%-11s%-11s%n", "ID", "Имя", "Состояние аккаунта");
        customerController.findAll().stream().forEach(customer -> System.out.printf("%-5d%-11s%-11s%n",
                customer.getId(), customer.getName(), customer.getAccount().getAccountStatus()));
    }
}
