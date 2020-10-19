package repository;

import java.io.IOException;

public interface GenericRepository<T, ID> {

    void save(T t) throws IOException;
}
