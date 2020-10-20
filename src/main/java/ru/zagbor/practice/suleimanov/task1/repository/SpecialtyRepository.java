package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long> {

    Optional<Specialty> getSpecialtyForId(long id) throws IOException;

    Set<Specialty> findAll() throws IOException;

    String specialtiesToStringForBase(Set<Specialty> specialties);

    Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException;

    boolean isSpecialtyExist(long id) throws IOException;


}
