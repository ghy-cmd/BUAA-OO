package com.oocourse.spec3;

import com.oocourse.spec3.exceptions.EmojiIdNotFoundException;
import com.oocourse.spec3.exceptions.EqualEmojiIdException;
import com.oocourse.spec3.exceptions.EqualGroupIdException;
import com.oocourse.spec3.exceptions.EqualMessageIdException;
import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.GroupIdNotFoundException;
import com.oocourse.spec3.exceptions.MessageIdNotFoundException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.exceptions.RelationNotFoundException;
import com.oocourse.spec3.main.EmojiMessage;
import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Network;
import com.oocourse.spec3.main.Person;
import com.oocourse.spec3.main.RedEnvelopeMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyNetwork implements Network {
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private int block = 0;
    private ArrayList<Integer> emojiIdList = new ArrayList<>();
    private ArrayList<Integer> emojiHeatList = new ArrayList<>();

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
    public void addMessage(Message message)
        throws EqualMessageIdException, EmojiIdNotFoundException, EqualPersonIdException {
        if (containsMessage(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        }
        if (message instanceof EmojiMessage &&
            !containsEmojiId(((EmojiMessage) message).getEmojiId())) {
            throw new MyEmojiIdNotFoundException(((EmojiMessage) message).getEmojiId());
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
        Message message = getMessage(id);
        if (message.getType() == 0 &&
            !(message.getPerson1().isLinked(message.getPerson2()))) {
            throw new MyRelationNotFoundException(message.getPerson1().getId(),
                message.getPerson2().getId());
        }
        if (message.getType() == 1 &&
            !message.getGroup().hasPerson(message.getPerson1())) {
            throw new MyPersonIdNotFoundException(message.getPerson1().getId());
        }
        if (message.getType() == 0 &&
            !message.getPerson1().equals(message.getPerson2())) {
            message.getPerson1().addSocialValue(message.getSocialValue());
            message.getPerson2().addSocialValue(message.getSocialValue());
            message.getPerson2().getMessages().add(0, message);
            if (message instanceof RedEnvelopeMessage) {
                message.getPerson1().addMoney(-((RedEnvelopeMessage) message).getMoney());
                message.getPerson2().addMoney(((RedEnvelopeMessage) message).getMoney());
            }
            if (message instanceof EmojiMessage) {
                for (int i = 0; i < emojiIdList.size(); i++) {
                    if (emojiIdList.get(i) == ((EmojiMessage) message).getEmojiId()) {
                        int temp = emojiHeatList.get(i);
                        emojiHeatList.set(i, temp + 1);
                    }
                }
            }
            messages.remove(message);
        } else if (message.getType() == 1) {
            if (message instanceof RedEnvelopeMessage) {
                int temp =
                    ((RedEnvelopeMessage) message).getMoney() / message.getGroup().getSize();
                message.getPerson1().addMoney(-temp * message.getGroup().getSize());
                for (Person p : ((MyGroup) message.getGroup()).getPeople()) {
                    p.addSocialValue(message.getSocialValue());
                    p.addMoney(temp);
                }
            } else {
                for (Person p : ((MyGroup) message.getGroup()).getPeople()) {
                    p.addSocialValue(message.getSocialValue());
                }
            }
            if (message instanceof EmojiMessage) {
                for (int i = 0; i < emojiIdList.size(); i++) {
                    if (emojiIdList.get(i) == ((EmojiMessage) message).getEmojiId()) {
                        int temp = emojiHeatList.get(i);
                        emojiHeatList.set(i, temp + 1);
                    }
                }
            }
            messages.remove(message);
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

    @Override
    public boolean containsEmojiId(int id) {
        for (Integer integer : emojiIdList) {
            if (integer == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void storeEmojiId(int id) throws EqualEmojiIdException {
        if (containsEmojiId(id)) {
            throw new MyEqualEmojiIdException(id);
        } else {
            emojiIdList.add(id);
            emojiHeatList.add(0);
        }
    }

    @Override
    public int queryMoney(int id) throws PersonIdNotFoundException {
        if (!contains(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else {
            return getPerson(id).getMoney();
        }
    }

    @Override
    public int queryPopularity(int id) throws EmojiIdNotFoundException {
        if (!containsEmojiId(id)) {
            throw new MyEmojiIdNotFoundException(id);
        } else {
            for (int i = 0; i < emojiIdList.size(); i++) {
                if (emojiIdList.get(i) == id) {
                    return emojiHeatList.get(i);
                }
            }
        }
        return 0;
    }

    @Override
    public int deleteColdEmoji(int limit) {
        for (int i = 0; i < emojiHeatList.size(); i++) {
            if (emojiHeatList.get(i) < limit) {
                emojiHeatList.remove(i);
                emojiIdList.remove(i);
                i--;
            }
        }
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) instanceof EmojiMessage &&
                !containsEmojiId(((EmojiMessage) messages.get(i)).getEmojiId())) {
                messages.remove(i);
                i--;
            }
        }
        return emojiIdList.size();
    }

    @Override
    public int sendIndirectMessage(int id) throws MessageIdNotFoundException {
        if (!containsMessage(id) || (containsMessage(id) && getMessage(id).getType() == 1)) {
            throw new MyMessageIdNotFoundException(id);
        } else {
            Message message = getMessage(id);
            try {
                if (!isCircle(message.getPerson1().getId(), message.getPerson2().getId())) {
                    return -1;
                } else {
                    message.getPerson1().addSocialValue(message.getSocialValue());
                    message.getPerson2().addSocialValue(message.getSocialValue());
                    if (message instanceof RedEnvelopeMessage) {
                        message.getPerson1().addMoney(-((RedEnvelopeMessage) message).getMoney());
                        message.getPerson2().addMoney(((RedEnvelopeMessage) message).getMoney());
                    }
                    if (message instanceof EmojiMessage) {
                        for (int i = 0; i < emojiIdList.size(); i++) {
                            if (emojiIdList.get(i) == ((EmojiMessage) message).getEmojiId()) {
                                int temp = emojiHeatList.get(i);
                                emojiHeatList.set(i, temp + 1);
                            }
                        }
                    }
                    message.getPerson2().getMessages().add(0, message);
                    messages.remove(message);
                    return findShortestRoad(message.getPerson1(), message.getPerson2());
                }
            } catch (PersonIdNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int findShortestRoad(Person person1, Person person2) throws PersonIdNotFoundException {
        HashMap<Integer, Integer> dj = new HashMap<>();
        ArrayList<Person> cons = new ArrayList<>();
        for (Person person : people) {
            if (isCircle(person1.getId(), person.getId())) {
                if (person.equals(person1)) {
                    dj.put(person.getId(), 0);
                } else {
                    cons.add(person);
                    dj.put(person.getId(), -1);
                }
            }
        }
        for (int i = 0; i < ((MyPerson) person1).getAcquaintance().size(); i++) {
            dj.put(((MyPerson) person1).getAcquaintance().get(i).getId(),
                ((MyPerson) person1).getValue().get(i));
        }
        while (!cons.isEmpty()) {
            int temp = 0;
            for (int i = 0; i < cons.size(); i++) {
                if (dj.get(cons.get(i).getId()) >= 0 &&
                    (dj.get(cons.get(i).getId()) < dj.get(cons.get(temp).getId()) ||
                        dj.get(cons.get(temp).getId()) == -1)) {
                    temp = i;
                }
            }
            for (int i = 0; i < ((MyPerson) cons.get(temp)).getAcquaintance().size(); i++) {
                if (cons.contains(((MyPerson) cons.get(temp)).getAcquaintance().get(i))) {
                    if (dj.get(((MyPerson) cons.get(temp)).getAcquaintance().get(i).getId()) ==
                        -1) {
                        dj.put(((MyPerson) cons.get(temp)).getAcquaintance().get(i).getId(),
                            ((MyPerson) cons.get(temp)).getValue().get(i) +
                                dj.get(cons.get(temp).getId()));
                    } else if ((((MyPerson) cons.get(temp)).getValue().get(i) +
                        dj.get(cons.get(temp).getId())) <
                        dj.get(((MyPerson) cons.get(temp)).getAcquaintance().get(i).getId())) {
                        dj.put(((MyPerson) cons.get(temp)).getAcquaintance().get(i).getId(),
                            ((MyPerson) cons.get(temp)).getValue().get(i) +
                                dj.get(cons.get(temp).getId()));
                    }
                }
            }
            cons.remove(temp);
        }
        return dj.get(person2.getId());
    }
}
