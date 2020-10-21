package ru.zagbor.practice.suleimanov.task1.repository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> getById(ID id) throws IOException;

   Collection<T> getAll() throws IOException;

    T update(T t) throws IOException;

    T create(T t) throws IOException;

    void deleteById(ID id) throws IOException;
}
