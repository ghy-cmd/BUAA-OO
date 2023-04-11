package com.oocourse.spec2;

import com.oocourse.spec2.exceptions.EqualGroupIdException;
import com.oocourse.spec2.exceptions.EqualMessageIdException;
import com.oocourse.spec2.exceptions.EqualPersonIdException;
import com.oocourse.spec2.exceptions.EqualRelationException;
import com.oocourse.spec2.exceptions.GroupIdNotFoundException;
import com.oocourse.spec2.exceptions.MessageIdNotFoundException;
import com.oocourse.spec2.exceptions.PersonIdNotFoundException;
import com.oocourse.spec2.exceptions.RelationNotFoundException;
import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Message;
import com.oocourse.spec2.main.Network;
import com.oocourse.spec2.main.Person;

import java.util.ArrayList;
import java.util.List;

public class MyNetwork implements Network {
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private int block = 0;

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
        block++;
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
            MyPerson person1 = (MyPerson) getPerson(id1);
            MyPerson person2 = (MyPerson) getPerson(id2);
            person1.getAcquaintance().add(person2);
            person1.getValue().add(value);
            person2.getAcquaintance().add(person1);
            person2.getValue().add(value);
            MyPerson person3 = (MyPerson) getPerson(findFather(id1));
            MyPerson person4 = (MyPerson) getPerson(findFather(id2));
            if (person3.getRank() <= person4.getRank()) {
                person3.setFather(person4.getId());
            } else {
                person4.setFather(person3.getId());
            }
            if (person3.getRank() == person4.getRank() && !person3.equals(person4)) {
                person4.addRank();
            }
            if (!person3.equals(person4)) {
                block--;
            }
        }
    }

    public int findFather(int id) {
        if (id == ((MyPerson) getPerson(id)).getFather()) {
            return id;
        } else {
            ((MyPerson) getPerson(id))
                .setFather(findFather(((MyPerson) getPerson(id)).getFather()));
            return ((MyPerson) getPerson(id)).getFather();
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
            return findFather(id1) == findFather(id2);
        }
    }

    @Override
    public int queryBlockSum() {
        return this.block;
    }

    public boolean groupContains(Group group) {
        for (Group value : groups) {
            if (value.equals(group)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addGroup(Group group) throws EqualGroupIdException {
        if (groupContains(group)) {
            throw new MyEqualGroupIdException(group.getId());
        } else {
            groups.add(group);
        }
    }

    @Override
    public Group getGroup(int id) {
        for (Group group : groups) {
            if (group.getId() == id) {
                return group;
            }
        }
        return null;
    }

    @Override
    public void addToGroup(int id1, int id2)
        throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        if (getGroup(id2) == null) {
            throw new MyGroupIdNotFoundException(id2);
        } else if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (getGroup(id2).hasPerson(getPerson(id1))) {
            throw new MyEqualPersonIdException(id1);
        } else if (((MyGroup) getGroup(id2)).getPeople().size() < 1111) {
            ((MyGroup) getGroup(id2)).getPeople().add(getPerson(id1));
        }
    }

    @Override
    public int queryGroupSum() {
        return groups.size();
    }

    @Override
    public int queryGroupPeopleSum(int id) throws GroupIdNotFoundException {
        if (getGroup(id) == null) {
            throw new MyGroupIdNotFoundException(id);
        } else {
            for (Group group : groups) {
                if (group.getId() == id) {
                    return ((MyGroup) group).getPeople().size();
                }
            }
        }
        return 0;
    }

    @Override
    public int queryGroupValueSum(int id) throws GroupIdNotFoundException {
        if (getGroup(id) == null) {
            throw new MyGroupIdNotFoundException(id);
        } else {
            for (Group group : groups) {
                if (group.getId() == id) {
                    return group.getValueSum();
                }
            }
        }
        return 0;
    }

    @Override
    public int queryGroupAgeMean(int id) throws GroupIdNotFoundException {
        if (getGroup(id) == null) {
            throw new MyGroupIdNotFoundException(id);
        } else {
            for (Group group : groups) {
                if (group.getId() == id) {
                    return group.getAgeMean();
                }
            }
        }
        return 0;
    }

    @Override
    public int queryGroupAgeVar(int id) throws GroupIdNotFoundException {
        if (getGroup(id) == null) {
            throw new MyGroupIdNotFoundException(id);
        } else {
            for (Group group : groups) {
                if (group.getId() == id) {
                    return group.getAgeVar();
                }
            }
        }
        return 0;
    }

    @Override
    public void delFromGroup(int id1, int id2)
        throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        if (getGroup(id2) == null) {
            throw new MyGroupIdNotFoundException(id2);
        } else if (!contains(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!getGroup(id2).hasPerson(getPerson(id1))) {
            throw new MyEqualPersonIdException(id1);
        } else {
            ((MyGroup) getGroup(id2)).getPeople().remove(getPerson(id1));
        }
    }

    @Override
    public boolean containsMessage(int id) {
        for (Message message : messages) {
            if (message.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addMessage(Message message) throws EqualMessageIdException, EqualPersonIdException {
        if (containsMessage(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        }
        if (message.getType() == 0 && message.getPerson1().equals(message.getPerson2())) {
            throw new MyEqualPersonIdException(message.getPerson1().getId());
        }
        messages.add(message);
    }

    @Override
    public Message getMessage(int id) {
        for (Message message : messages) {
            if (message.getId() == id) {
                return message;
            }
        }
        return null;
    }

    @Override
    public void sendMessage(int id)
        throws RelationNotFoundException, MessageIdNotFoundException, PersonIdNotFoundException {
        if (!containsMessage(id)) {
            throw new MyMessageIdNotFoundException(id);
        }
        if (getMessage(id).getType() == 0 &&
            !(getMessage(id).getPerson1().isLinked(getMessage(id).getPerson2()))) {
            throw new MyRelationNotFoundException(getMessage(id).getPerson1().getId(),
                getMessage(id).getPerson2().getId());
        }
        if (getMessage(id).getType() == 1 &&
            !getMessage(id).getGroup().hasPerson(getMessage(id).getPerson1())) {
            throw new MyPersonIdNotFoundException(getMessage(id).getPerson1().getId());
        }
        if (getMessage(id).getType() == 0 &&
            !getMessage(id).getPerson1().equals(getMessage(id).getPerson2())) {
            getMessage(id).getPerson1().addSocialValue(getMessage(id).getSocialValue());
            getMessage(id).getPerson2().addSocialValue(getMessage(id).getSocialValue());
            getMessage(id).getPerson2().getMessages().add(0, getMessage(id));
            messages.remove(getMessage(id));
        } else if (getMessage(id).getType() == 1) {
            for (Person p : ((MyGroup) getMessage(id).getGroup()).getPeople()) {
                p.addSocialValue(getMessage(id).getSocialValue());
            }
            messages.remove(getMessage(id));
        }
    }

    @Override
    public int querySocialValue(int id) throws PersonIdNotFoundException {
        if (!contains(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return getPerson(id).getSocialValue();
        }
    }

    @Override
    public List<Message> queryReceivedMessages(int id) throws PersonIdNotFoundException {
        if (!contains(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return getPerson(id).getReceivedMessages();
        }
    }
}
