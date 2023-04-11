package com.oocourse.spec2;

import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Message;
import com.oocourse.spec2.main.Person;

public class MyMessage implements Message {
    private int id;
    private int socialValue;
    private int type;
    private Person person1;
    private Person person2;
    private Group group;

    public MyMessage(int messageId, int messageSocialValue, Person messagePerson1,
                     Person messagePerson2) {
        this.type = 0;
        this.group = null;
        this.id = messageId;
        this.socialValue = messageSocialValue;
        this.person1 = messagePerson1;
        this.person2 = messagePerson2;
    }

    public MyMessage(int messageId, int messageSocialValue, Person messagePerson1,
                     Group messageGroup) {
        this.type = 1;
        this.person2 = null;
        this.id = messageId;
        this.socialValue = messageSocialValue;
        this.person1 = messagePerson1;
        this.group = messageGroup;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSocialValue() {
        return socialValue;
    }

    @Override
    public Person getPerson1() {
        return person1;
    }

    @Override
    public Person getPerson2() {
        return person2;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            return (((Message) obj).getId() == id);
        } else {
            return false;
        }
    }
}
