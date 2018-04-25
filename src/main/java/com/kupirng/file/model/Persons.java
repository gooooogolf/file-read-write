package com.kupirng.file.model;

import java.util.ArrayList;
import java.util.List;

public class Persons {
    private ArrayList<Person> persons;

    public Persons() {
        this.persons = new ArrayList<Person>();
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void add(Person person) {
        this.persons.add(person);
    }

    public void addAll(ArrayList<Person> persons) {
        this.persons.addAll(persons);
    }
}
