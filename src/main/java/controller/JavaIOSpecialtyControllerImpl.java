package controller;

import model.Specialty;
import repository.SpecialtyRepository;
import repository.SpecialtyRepositoryImpl;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaIOSpecialtyControllerImpl implements SpecialtyController {

    private final SpecialtyRepository specialtyRepository;

    public JavaIOSpecialtyControllerImpl() throws IOException {
        specialtyRepository = new SpecialtyRepositoryImpl();
    }

    public void create(Specialty specialty) throws IOException {
        specialty = new Specialty();
        specialtyRepository.save(specialty);
    }

    public Set<Specialty> findAll() throws IOException {
        return specialtyRepository.findAll();
    }

    public Set<Specialty> deleteSpecialtyForCustomer(Set<Specialty> specialties, long idOfspecialty) {
        return specialties.stream().filter(specialty -> specialty.getId() != idOfspecialty).collect(Collectors.toSet());
    }

    public Optional<Specialty> getSpecialtyForId(long id) throws IOException {
        return specialtyRepository.getSpecialtyForId(id);
    }

    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException {
        return specialtyRepository.findWhichCanAdd(specialtiesCustomer);
    }

    public boolean isSpecialtyExist(long id) throws IOException {
        return specialtyRepository.isSpecialtyExist(id);

    }


}