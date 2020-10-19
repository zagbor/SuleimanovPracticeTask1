package controller;

import model.Customer;

import java.io.IOException;

public interface GenericController<T,ID> {
    void create(T t) throws IOException;
}
