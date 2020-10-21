package ru.zagbor.practice.suleimanov.task1.repository;


import ru.zagbor.practice.suleimanov.task1.model.Account;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {
    private final String ACCOUNTS = ".\\src\\main\\resources\\Accounts";

    public AccountRepositoryImpl() {
    }

    public void deleteAll() throws IOException {
        Files.newBufferedWriter(Paths.get(ACCOUNTS), StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public Optional<Account> getById(Long id) throws IOException {
        List<Account> specialties = getAll();
        return specialties.stream()
                .filter(specialty -> specialty.getId() == id)
                .findFirst();
    }


    @Override
    public Account update(Account account) throws IOException {
        if (account.getId() <= 0) {
            account.setId(findMaxId() + 1);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS, true))) {
            if (new File(ACCOUNTS).length() != 0) {
                writer.newLine();
            }
            writer.write(account.getId() + ";" + account.getAccountStatus());
            writer.flush();
        }
        return account;
    }


    @Override
    public void deleteById(Long id) throws IOException {
        if (!getById(id).isPresent()) {
            return;
        }
        List<Account> accounts = this.getAll();
        Files.newBufferedWriter(Paths.get(ACCOUNTS), StandardOpenOption.TRUNCATE_EXISTING);
        accounts.stream()
                .filter(account -> account.getId() != id)
                .forEach(account -> {
                    try {
                        this.update(account);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public List<Account> getAll() throws IOException {
        return Files.lines(Paths.get(ACCOUNTS), StandardCharsets.UTF_8)
                .map(this::parseAccountFromBase)
                .collect(Collectors.toList());
    }

    @Override
    public Account create(Account account) throws IOException {
        if (account.getId() != 0) {
            throw new IllegalStateException();
        }
        account.setId(findMaxId() + 1);
        writeAccount(account);
        return account;
    }

    private void writeAccount(Account account) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS, true))) {
            if (new File(ACCOUNTS).length() != 0) {
                writer.write("\n");
            }

            writer.write(
                    account.getId() + ";"
                            + account.getAccountStatus().toString() + ";");
            writer.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private Long findMaxId() throws IOException {
        return getAll().stream()
                .map(account -> account.getId())
                .max(Comparator.naturalOrder()).orElse(10000L);
    }

    private Account parseAccountFromBase(String line) {
        String[] result = line.split(";");
        return new Account(Long.parseLong(result[0]), Account.AccountStatus.valueOf(result[1]));
    }


}
