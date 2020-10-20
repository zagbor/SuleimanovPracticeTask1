package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private final String SPECIALTIES = ".\\src\\main\\resources\\Specialties";

    public SpecialtyRepositoryImpl() {
    }

    @Override
    public Optional<Specialty> getSpecialtyForId(long id) throws IOException {
        Set<Specialty> specialties = findAll();
        return specialties.stream()
                .filter(specialty -> specialty.getId() == id)
                .findFirst();
    }

    @Override
    public String specialtiesToStringForBase(Set<Specialty> specialties) {
        return specialties.stream()
                .map(s -> String.valueOf(s.getId()))
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException {
        Set<Specialty> allSpecialties = findAll();
        return allSpecialties.stream()
                .filter(specialty -> !specialtiesCustomer.contains(specialty))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isSpecialtyExist(long id) throws IOException {
        return findAll().stream()
                .anyMatch(specialty -> specialty.getId() == id);
    }


    @Override
    public Set<Specialty> findAll() throws IOException {
        return Files.lines(Paths.get(SPECIALTIES), StandardCharsets.UTF_8)
                .map(line -> parseSpecialtyFromBase(line))
                .collect(Collectors.toSet());
    }

    private Specialty parseSpecialtyFromBase(String line) {
        String[] result = line.split(";");
        return new Specialty(Long.parseLong(result[0]), result[1]);
    }
}

