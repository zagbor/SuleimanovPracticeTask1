package ru.zagbor.practice.suleimanov.task1.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GeneralMenu {

    private final static BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private final EditMenu editMenu = new EditMenu();
    private final AddDeleteCusomersMenu addDeleteCustomersMenu = new AddDeleteCusomersMenu();
    private final AddDeleteSpecialtiesMenu addDeleteSpecialtiesMenu = new AddDeleteSpecialtiesMenu();

    public GeneralMenu() throws IOException {
    }

    public void startPosition() throws IOException {
        System.out.println("Вы находитесь в приложении, которое может показывать и редактировать всю информацию о клиентах.");
        panelGenerator();
    }

    private void panelGenerator() throws IOException {
        while (true) {
            generalPanel();
        }
    }

    private void generalPanel() throws IOException {
        while (true) {
            System.out.println("Введите в консоль номер действия:");
            System.out.println("1. Посмотреть/отредактировать данные клиентов.");
            System.out.println("2. Добавить клиента/Удалить клиента.");
            System.out.println("3. Добавить специальность в базу/Удалить специальность из базы.");
            String choice = BUFFERED_READER.readLine();
            switch (choice) {
                case ("1"):
                    editMenu.chooseCustomerPanel();
                    continue;
                case ("2"):
                    addDeleteCustomersMenu.chooseAddOrDelete();
                    break;
                case ("3"):
                    addDeleteSpecialtiesMenu.chooseAddOrDelete();
                    break;
                default:
                    System.err.println("Вы выбрали вариант, которого не существует, попробуйте еще раз.");
                    continue;
            }
            break;
        }
    }
}


