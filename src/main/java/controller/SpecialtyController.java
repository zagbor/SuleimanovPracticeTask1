package controller;
import model.Customer;
import model.Specialty;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface SpecialtyController extends GenericController<Specialty, Long> {

    Set<Specialty> findAll() throws IOException;

    Optional<Specialty> getSpecialtyForId(long id) throws IOException;

    Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException;

    boolean isSpecialtyExist(long id) throws IOException;


}
