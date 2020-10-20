package ru.zagbor.practice.suleimanov.task1.repository;


import ru.zagbor.practice.suleimanov.task1.model.Account;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {
    private final String ACCOUNTS = ".\\src\\main\\resources\\Accounts";

    public AccountRepositoryImpl() {
    }

    public void deleteAll() throws IOException {
        Files.newBufferedWriter(Paths.get(ACCOUNTS), StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void save(Account account) throws IOException {
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
    }

    public Set<Account> findAll() throws IOException {
        return Files.lines(Paths.get(ACCOUNTS), StandardCharsets.UTF_8)
                .map(line -> parseSpecialtyFromBase(line))
                .collect(Collectors.toSet());
    }

    private Long findMaxId() throws IOException {
        return findAll().stream()
                .map(account -> account.getId())
                .max(Comparator.naturalOrder()).orElse(10000L);
    }

    private Account parseSpecialtyFromBase(String line) {
        String[] result = line.split(";");
        return new Account(Long.parseLong(result[0]), Account.AccountStatus.valueOf(result[1]));
    }
}
