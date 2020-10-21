package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private final String SPECIALTIES = ".\\src\\main\\resources\\Specialties";


    public SpecialtyRepositoryImpl() throws IOException {
    }

    @Override
    public Optional<Specialty> getById(Long id) throws IOException {
        Set<Specialty> specialties = getAll();
        return specialties.stream()
                .filter(specialty -> specialty.getId() == id)
                .findFirst();
    }

    @Override
    public Specialty update(Specialty specialty) throws IOException {
        if (specialty.getId() == 0) {
            throw new IllegalStateException();
        }
        deleteById(specialty.getId());
        writeSpecialty(specialty);
        return specialty;
    }

    @Override
    public Specialty create(Specialty specialty) throws IOException {
        if (specialty.getId() != 0) {
            throw new IllegalStateException();
        }
        specialty.setId(findMaxId() + 1);
        writeSpecialty(specialty);
        return specialty;
    }


    @Override
    public void deleteById(Long id) throws IOException {
        if (!getById(id).isPresent()) {
            return;
        }
        deleteSpecialtyFromAllCustomers(id);
        Set<Specialty> specialties = this.getAll();
        Files.newBufferedWriter(Paths.get(SPECIALTIES), StandardOpenOption.TRUNCATE_EXISTING);
        specialties.stream()
                .filter(specialty -> specialty.getId() != id)
                .forEach(specialty -> {
                    try {
                        this.update(specialty);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }


    @Override
    public String specialtiesToStringForBase(Set<Specialty> specialties) {
        if (specialties == null) {
            return "";
        }
        return specialties.stream()
                .map(s -> String.valueOf(s.getId()))
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException {
        Set<Specialty> allSpecialties = getAll();
        return allSpecialties.stream()
                .filter(specialty -> !specialtiesCustomer.contains(specialty))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isSpecialtyExist(long id) throws IOException {
        return getAll().stream()
                .anyMatch(specialty -> specialty.getId() == id);
    }


    @Override
    public Set<Specialty> getAll() throws IOException {
        return Files.lines(Paths.get(SPECIALTIES), StandardCharsets.UTF_8)
                .map(line -> parseSpecialtyFromBase(line))
                .collect(Collectors.toSet());
    }

    private void writeSpecialty(Specialty specialty) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SPECIALTIES, true))) {
            if (new File(SPECIALTIES).length() != 0) {
                writer.write("\n");
            }

            writer.write(
                    specialty.getId() + ";"
                            + specialty.getName() + ";");
            writer.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }


    private Specialty parseSpecialtyFromBase(String line) {
        String[] result = line.split(";");
        return new Specialty(Long.parseLong(result[0]), result[1]);
    }

    private Long findMaxId() throws IOException {
        return this.getAll().stream()
                .map(specialty -> specialty.getId())
                .max(Comparator.naturalOrder()).orElse(0L);
    }

    private void deleteSpecialtyFromAllCustomers(Long specialtyId) throws IOException {
        CustomerRepository customerRepository = new JavaIOCustomerRepositoryImpl();
        new JavaIOCustomerRepositoryImpl().getAll().forEach(customer -> {
            customer.setSpecialties(customer.getSpecialties().stream().filter(specialty -> specialty.getId() != specialtyId).collect(Collectors.toSet()));
            try {
                customerRepository.update(customer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}