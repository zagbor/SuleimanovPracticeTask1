package ru.zagbor.practice.suleimanov.task1.controller;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepository;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepositoryImpl;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;


public class JavaIOSpecialtyControllerImpl implements SpecialtyController {

    private final SpecialtyRepository specialtyRepository;

    public JavaIOSpecialtyControllerImpl() throws IOException {
        specialtyRepository = new SpecialtyRepositoryImpl();
    }
    @Override
    public Optional<Specialty> getSpecialtyForId(long id) throws IOException {
        return specialtyRepository.getSpecialtyForId(id);
    }

    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException {
        return specialtyRepository.findWhichCanAdd(specialtiesCustomer);
    }

    @Override
    public boolean isSpecialtyExist(long id) throws IOException {
        return specialtyRepository.isSpecialtyExist(id);
    }


    @Override
    public Set<Specialty> findAll() throws IOException {
        return specialtyRepository.findAll();
    }
}