package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Account;

import java.io.IOException;

public interface AccountRepository {

    void save(Account account) throws IOException;

    void deleteAll() throws IOException;
}
