package com.oocourse.spec1;

import com.oocourse.spec1.exceptions.EqualPersonIdException;
import com.oocourse.spec1.exceptions.EqualRelationException;
import com.oocourse.spec1.exceptions.PersonIdNotFoundException;
import com.oocourse.spec1.exceptions.RelationNotFoundException;
import com.oocourse.spec1.main.Network;
import com.oocourse.spec1.main.Person;

import java.util.ArrayList;

public class MyNetwork implements Network {
    private ArrayList<Person> people = new ArrayList<>();
    private static ArrayList<Integer> touch = new ArrayList<>();

    public MyNetwork() {
    }

    @Override
    public boolean contains(int id) {
        for (Person person : people) {
            if (person.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Person getPerson(int id) {
        for (Person person : people) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    @Override
    public void addPerson(Person person) throws EqualPersonIdException {
        for (Person value : people) {
            if (value.equals(person)) {
                throw new MyEqualPersonIdException(person.getId());
            }
        }
        people.add(person);
    }

    @Override
    public void addRelation(int id1, int id2, int value)
        throws PersonIdNotFoundException, EqualRelationException {
        if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!contains(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (getPerson(id1).isLinked(getPerson(id2))) {
            throw new MyEqualRelationException(id1, id2);
        } else {
            ((MyPerson) getPerson(id1)).getAcquaintance().add(getPerson(id2));
            ((MyPerson) getPerson(id1)).getValue().add(value);
            ((MyPerson) getPerson(id2)).getAcquaintance().add(getPerson(id1));
            ((MyPerson) getPerson(id2)).getValue().add(value);
        }
    }

    @Override
    public int queryValue(int id1, int id2)
        throws PersonIdNotFoundException, RelationNotFoundException {
        if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!contains(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (!getPerson(id1).isLinked(getPerson(id2))) {
            throw new MyRelationNotFoundException(id1, id2);
        } else {
            return getPerson(id1).queryValue(getPerson(id2));
        }
    }

    @Override
    public int compareName(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!contains(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else {
            return getPerson(id1).getName().compareTo(getPerson(id2).getName());
        }
    }

    @Override
    public int queryPeopleSum() {
        return people.size();
    }

    @Override
    public int queryNameRank(int id) throws PersonIdNotFoundException {
        int temp = 1;
        if (!contains(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            for (Person person : people) {
                if (compareName(id, person.getId()) > 0) {
                    temp++;
                }
            }
            return temp;
        }
    }

    @Override
    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!contains(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else {
            touch.add(id1);
            if (id1 == id2) {
                touch.clear();
                return true;
            }
            for (int i = 0; i < ((MyPerson) getPerson(id1)).getAcquaintance().size(); i++) {
                if (((MyPerson) getPerson(id1)).getAcquaintance().get(i).getId() == id2) {
                    touch.clear();
                    return true;
                } else {
                    if (!touch.contains(
                        ((MyPerson) getPerson(id1)).getAcquaintance().get(i).getId()) &&
                        isCircle(((MyPerson) getPerson(id1)).getAcquaintance().get(i).getId(),
                            id2)) {
                        touch.clear();
                        return true;
                    }
                }
            }
            touch.remove(touch.size() - 1);
            return false;
        }
    }

    @Override
    public int queryBlockSum() {
        int sum = 0;
        for (int i = 0; i < people.size(); i++) {
            int temp = 0;
            for (int j = 0; j < i; j++) {
                try {
                    touch.clear();
                    if (isCircle(people.get(i).getId(), people.get(j).getId())) {
                        temp = 1;
                        break;
                    }
                } catch (PersonIdNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (temp == 0) {
                sum++;
            }
        }
        return sum;
    }
}
