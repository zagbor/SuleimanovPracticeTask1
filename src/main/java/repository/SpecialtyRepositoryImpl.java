package repository;

import model.Specialty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private final String SPECIALTIES = ".\\src\\main\\resources\\Specialties";
    private final static BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter BUFFERED_WRITER = new BufferedWriter(new FileWriter(SPECIALTIES, true));


    public SpecialtyRepositoryImpl() throws IOException {
    }

    private String inputName() throws IOException {
        System.out.println("Введите имя специальности");
        return BUFFERED_READER.readLine();
    }

    @Override
    public void save(Specialty specialty) throws IOException {
        String name = inputName();
        String newID = String.valueOf(findMaxId() + 1);
        BUFFERED_WRITER.write("\n" + newID + ";" + name);
        BUFFERED_WRITER.flush();
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

    public long findMaxId() throws IOException {
        return findAll().stream()
                .map(specialty -> specialty.getId())
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new RuntimeException("Что то пошло не так."));
    }

    public Optional<Specialty> getSpecialtyForId(long id) throws IOException {
        Set<Specialty> specialties = findAll();
        return specialties.stream().filter(specialty -> specialty.getId() == id).findFirst();
    }

    public String specialtiesToStringForBase(Set<Specialty> specialties) {
        return specialties.stream().map(s -> String.valueOf(s.getId())).collect(Collectors.joining(","));
    }


    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException {
        Set<Specialty> allSpecialties = findAll();
        return allSpecialties.stream()
                .filter(specialty -> !specialtiesCustomer.contains(specialty))
                .collect(Collectors.toSet());
    }

    public boolean isSpecialtyExist(long id) throws IOException {
        return findAll().stream()
                .anyMatch(specialty -> specialty.getId() == id);
    }
}

