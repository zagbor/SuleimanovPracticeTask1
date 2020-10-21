package ru.zagbor.practice.suleimanov.task1.model;

import java.util.Set;

import java.util.stream.Collectors;

public class Customer {

    private long id;
    private String name;
    private Set<Specialty> specialties;
    private Account account;

    public Customer() {
    }

    public Customer(long id, String name, Set<Specialty> specialties, Account account) {
        this.id = id;
        this.name = name;
        this.specialties = specialties;
        this.account = account;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialties=" + specialties +
                ", account=" + account +
                '}';
    }

    public String toStringSpecialties() {
        if(specialties==null){
            return "";
        }
        return specialties.stream()
                .map(specialties -> specialties.getName())
                .collect(Collectors.joining(", "));
    }
}
