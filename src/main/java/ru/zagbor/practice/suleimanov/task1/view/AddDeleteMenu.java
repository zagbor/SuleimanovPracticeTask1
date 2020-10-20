package ru.zagbor.practice.suleimanov.task1.view;

import ru.zagbor.practice.suleimanov.task1.controller.CustomerController;
import ru.zagbor.practice.suleimanov.task1.controller.JavaIOCustomerControllerImpl;
import ru.zagbor.practice.suleimanov.task1.controller.JavaIOSpecialtyControllerImpl;
import ru.zagbor.practice.suleimanov.task1.controller.SpecialtyController;
import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.utils.Utils;
import ru.zagbor.practice.suleimanov.task1.utils.UtilsPrint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddDeleteMenu {
    private final static BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private final UtilsPrint utilsPrint = new UtilsPrint();
    private final CustomerController customerController = new JavaIOCustomerControllerImpl();
    private final SpecialtyController specialtyController = new JavaIOSpecialtyControllerImpl();

    public AddDeleteMenu() throws IOException {
    }

    public void chooseAddOrDelete() throws IOException {
        while (true) {
            System.out.println("Если вы хотите добавить клиента, то введите 1. " +
                    "\nЕсли удалить то введите 2.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад");
            String choice = BUFFERED_READER.readLine();
            switch (choice) {
                case ("1"):
                    addCustomerPanel();
                    continue;
                case ("2"):
                    deleteCustomerPanel();
                    continue;
                case ("e"):
                    break;
                default:
                    System.err.println("Вы ввели несуществующий вариант, попробуйте еще раз.");
                    continue;
            }
            break;
        }
    }

    private void addCustomerPanel() throws IOException {
        while (true) {
            Customer customer = new Customer();
            System.out.println("Чтобы создать пользователя введите его имя.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String maybeName = BUFFERED_READER.readLine();
            if (maybeName.equals("e")) {
                break;
            }
            customer.setName(maybeName);
            System.out.println("Вы добавили имя клиенту.");

            if (addSpecialties(customer)) {
                break;
            }

        }
    }

    private boolean addSpecialties(Customer customer) throws IOException {
        Set<Specialty> specialties = new HashSet<>();
        while (true) {
            System.out.println("Теперь необходимо добавить пользователю специальности.\nУ клиента их может быть несколько, но не меньше одной. Вводить их нужно по одному. g" +
                    "Вот список специальностей, которые Вы можете добавить:");
            if (customer.getSpecialties() == null) {
                utilsPrint.showSetSpecialties(specialtyController.findAll());
            } else {
                utilsPrint.showSetSpecialties(specialtyController.findWhichCanAdd(customer.getSpecialties()));
            }
            System.out.println("Введите ID специальности, которую хотите добавить");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад");
            System.out.println("Введите \"g\" на английской раскладке, чтобы пропустить данный пункт меню");
            String maybeId = BUFFERED_READER.readLine();
            if (maybeId.equals("e")) {
                return false;
            }
            if (maybeId.equals("g") && customer.getSpecialties() != null) {
                if (addAccountStatus(customer)) {
                    return true;
                } else {
                    continue;
                }

            }
            long id = Utils.parseLong(maybeId);
            if (id == -1 || !specialtyController.isSpecialtyExist(id)) {
                System.err.println("Вы выбрали вариант, которого не существует или не добавили ни одной специальности, попробуйте еще раз.");
                continue;
            }
            specialties.add(specialtyController.getSpecialtyForId(id).get());
            customer.setSpecialties(specialties);
            System.out.println("Вы добавили специальность клиенту.");
        }
    }

    private void deleteCustomerPanel() throws IOException {
        while (true) {
            System.out.println("Вы хотите удалить пользвателя. Вот список пользователей:");
            utilsPrint.showAllListCustomers();
            System.out.println("Чтобы удалить пользователя введите номер его ID.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String maybeId = BUFFERED_READER.readLine();
            long id = Utils.parseLong(maybeId);
            if (maybeId.equals("e")) {
                break;
            }
            if (id == -1 || !customerController.isCustomerExist(id)) {
                System.err.println("Вы выбрали вариант, которого не существует, попробуйте еще раз.");
                continue;
            }
            customerController.deleteCustomerForID(id);
            System.out.println("Вы удалили клиента");
        }
    }

    private boolean addAccountStatus(Customer customer) throws IOException {
        while (true) {
            System.out.println("Теперь необходимо добавить пользователю статус аккаунта. " +
                    "Вот список специальностей, которые Вы можете добавить:");
            utilsPrint.showListAccountStatus(List.of(Account.AccountStatus.values()));
            System.out.println("Введите в панель название статуса большими английскими буквами без пробелов.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад");
            String maybeAccountStatus = BUFFERED_READER.readLine();
            if (maybeAccountStatus.equals("e")) {
                return false;
            }
            Account.AccountStatus accountEnum;
            try {
                accountEnum = Account.AccountStatus.valueOf(maybeAccountStatus);
            } catch (IllegalArgumentException e) {
                System.err.println("Вы ввели неверный статус, попробуйте еще раз.");
                continue;
            }
            Account account = new Account();
            account.setAccountStatus(accountEnum);
            customer.setAccount(account);
            System.out.println("Вы присвоили статус аккаунта клиенту");
            if (saveClientPanel(customer)) {
                return true;
            }
        }
    }

    private boolean saveClientPanel(Customer customer) throws IOException {
        while (true) {
            System.out.println("Если хотите сохранить данного клиента в базу введите на английской раскладке \"y\", если хотите выйти назад, то \"e\"");
            utilsPrint.showCustomerLessId(customer);
            String choice = BUFFERED_READER.readLine();
            if (choice.equals("e")) {
                return false;
            }
            if (choice.equals("y")) {
                customerController.create(customer);
                System.out.println("Вы успешно добавили клиента.");
                return true;
            }
        }
    }
}

