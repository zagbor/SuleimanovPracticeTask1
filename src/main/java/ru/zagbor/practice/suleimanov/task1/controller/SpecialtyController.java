package ru.zagbor.practice.suleimanov.task1.controller;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface SpecialtyController {

    Set<Specialty> getAll() throws IOException;

    Optional<Specialty> getSpecialtyForId(long id) throws IOException;

    Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException;

    boolean isSpecialtyExist(long id) throws IOException;

    Specialty create(Specialty specialty) throws IOException;


    void deleteById(Long id) throws IOException;
}
