package com.oocourse.spec3;

import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;

import java.util.ArrayList;

public class MyGroup implements Group {
    private int id;
    private ArrayList<Person> people = new ArrayList<>();

    public MyGroup(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) {
            return (((Group) obj).getId() == id);
        } else {
            return false;
        }
    }

    @Override
    public void addPerson(Person person) {
        if (!hasPerson(person)) {
            people.add(person);
        }
    }

    @Override
    public boolean hasPerson(Person person) {
        for (Person value : people) {
            if (value.equals(person)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getValueSum() {
        int sum = 0;
        for (int i = 0; i < people.size(); i++) {
            for (int j = 0; j < people.size(); j++) {
                if (people.get(i).isLinked(people.get(j))) {
                    sum = sum + people.get(i).queryValue(people.get(j));
                }
            }
        }
        return sum;
    }

    @Override
    public int getAgeMean() {
        int sum = 0;
        if (people.size() == 0) {
            return 0;
        } else {
            for (Person person : people) {
                sum += person.getAge();
            }
        }
        return sum / people.size();
    }

    @Override
    public int getAgeVar() {
        int sum = 0;
        int average = getAgeMean();
        if (people.size() == 0) {
            return 0;
        } else {
            for (Person person : people) {
                sum += (person.getAge() - average) * (person.getAge() - average);
            }
        }
        return sum / people.size();
    }

    @Override
    public void delPerson(Person person) {
        if (hasPerson(person)) {
            people.remove(person);
        }
    }

    @Override
    public int getSize() {
        return people.size();
    }
}
