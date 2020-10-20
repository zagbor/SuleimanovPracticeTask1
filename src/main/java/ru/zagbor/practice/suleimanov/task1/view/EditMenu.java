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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EditMenu {
    private final static BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private final SpecialtyController specialtyController = new JavaIOSpecialtyControllerImpl();
    private final CustomerController customerController = new JavaIOCustomerControllerImpl();
    private final UtilsPrint utilsPrint = new UtilsPrint();

    public EditMenu() throws IOException {
    }

    public void chooseCustomerPanel() throws IOException {
        while (true) {
            utilsPrint.showAllListCustomers();
            System.out.println("Чтобы увидеть и/или отредактировать всю информацию по определенному клиенту введите его ID.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String maybeId = BUFFERED_READER.readLine();
            if (maybeId.equals("e")) {
                break;
            }
            long id = Utils.parseLong(maybeId);
            if (id == -1 || !customerController.isCustomerExist(id)) {
                System.err.println("Вы выбрали вариант, которого не существует, попробуйте еще раз.");
                continue;
            }
            editPanel(id);
        }
    }


    private void editPanel(long id) throws IOException {
        while (true) {
            Customer customer = customerController.getCustomerForID(id).get();
            utilsPrint.showCustomer(customer);
            System.out.println("Чтобы отредактировать данные введите номер колонки, которую хотите изменить.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String choice = BUFFERED_READER.readLine();
            switch (choice) {
                case ("1"):
                    changeNamePanel(id);
                    continue;
                case ("2"):
                    editSpecialtyPanel(id);
                    continue;
                case ("3"):
                    editAccountStatusPanel(id);
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

    private void editAccountStatusPanel(long id) throws IOException {
        while (true) {
            Customer customer = customerController.getCustomerForID(id).get();
            System.out.println("Вы хотите изменить статус аккаунта данного клиента, вот список возможных вариантов статусов," +
                    " которые могут быть применены для данного клиента:");
            List<Account.AccountStatus> availableAccountStatuses = new ArrayList<>(Arrays.asList(Account.AccountStatus.values()));
            availableAccountStatuses.remove(customer.getAccount().getAccountStatus());
            utilsPrint.showListAccountStatus(availableAccountStatuses);
            System.out.println("Введите в панель название статуса большими английскими буквами без пробелов.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String maybeAccountStatus = BUFFERED_READER.readLine();
            if (maybeAccountStatus.equals("e")) {
                break;
            }
            Enum<Account.AccountStatus> accountEnum;
            try {
                accountEnum = Account.AccountStatus.valueOf(maybeAccountStatus);
            } catch (IllegalArgumentException e) {
                System.err.println("Вы ввели неверный статус, попробуйте еще раз.");
                continue;
            }
            if (!availableAccountStatuses.contains(accountEnum)) {
                System.err.println("Вы ввели статус, который уже присвоен клиенту.");
                continue;
            }
            customerController.changeAccountStatus(customer, Account.AccountStatus.valueOf(maybeAccountStatus));
            System.out.println("Вы изменили статус");
            break;
        }

    }

    private void editSpecialtyPanel(long id) throws IOException {
        while (true) {
            Customer customer = customerController.getCustomerForID(id).get();
            utilsPrint.showCustomer(customer);
            System.out.println("Если вы хотите добавить специальность к клиенту, то введите 1. " +
                    "\nЕсли удалить то введите 2.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String choice = BUFFERED_READER.readLine();
            switch (choice) {
                case ("1"):
                    addSpecialtyPanel(id);
                    continue;
                case ("2"):
                    deleteSpecialtyPanel(id);
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

    private void deleteSpecialtyPanel(long id) throws IOException {
        while (true) {
            Customer customer = customerController.getCustomerForID(id).get();
            System.out.println("Вы хотите удалить специальность у данного клиента, вот список специальностей, которые Вы можете удалить:");
            utilsPrint.showSetSpecialties(customer.getSpecialties());
            System.out.println("Введите ID специальности, которую хотите удалить у клиента.");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String maybeIdSpecialty = BUFFERED_READER.readLine();
            if (maybeIdSpecialty.equals("e")) {
                break;
            }
            long idSpecialty = Utils.parseLong(maybeIdSpecialty);
            if (idSpecialty == -1) {
                System.err.println("Вы ввели не число");
                continue;
            }

            if (!customer.getSpecialties().contains(specialtyController.getSpecialtyForId(idSpecialty).get())) {
                System.err.println("Вы ввели ID, который невозможно удалить");
                continue;
            }
            customerController.deleteSpecialtyCustomer(customer, specialtyController.getSpecialtyForId(idSpecialty).get());
            break;
        }
    }

    private void addSpecialtyPanel(long id) throws IOException {
        while (true) {
            Customer customer = customerController.getCustomerForID(id).get();
            System.out.println("Вы хотите добавить специальность данному клиенту, вот список специальностей, которые Вы можете добавить:");
            Set<Specialty> whichCanAdd = specialtyController.findWhichCanAdd(customer.getSpecialties());
            utilsPrint.showSetSpecialties(whichCanAdd);
            System.out.println("Введите ID специальности, которую хотите добавить клиенту");
            System.out.println("Введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String maybeIdSpecialty = BUFFERED_READER.readLine();
            if (maybeIdSpecialty.equals("e")) {
                break;
            }
            long idSpecialty = Utils.parseLong(maybeIdSpecialty);
            if (idSpecialty == -1) {
                System.err.println("Вы ввели не число");
                continue;
            }

            if (!whichCanAdd.contains(specialtyController.getSpecialtyForId(idSpecialty).get())) {
                System.err.println("Вы ввели ID, который невозможно добавить");
                continue;
            }
            customerController.addSpecialtyCustomer(customer.getId(), specialtyController.getSpecialtyForId(idSpecialty).get());
            break;
        }
    }

    private void changeNamePanel(long id) throws IOException {
        while (true) {
            Customer customer = customerController.getCustomerForID(id).get();
            System.out.println("Введите новое имя для пользователя: \nили введите \"e\" на английской раскладке, чтобы вернуться назад.");
            String newName = BUFFERED_READER.readLine();
            if (newName.equals("e")) {
                break;
            }
            customerController.changeName(customer, newName);
            customer.setName(newName);
            System.out.println("Вы успешно отредактировали клиента, вот результат:");
            break;
        }
    }
}

