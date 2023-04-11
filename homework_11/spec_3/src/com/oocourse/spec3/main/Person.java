package com.oocourse.spec3.main;

import java.util.List;

public interface Person extends Comparable<Person> {

    /*@ public instance model non_null int id;
      @ public instance model non_null String name;
      @ public instance model non_null int age;
      @ public instance model non_null Person[] acquaintance;
      @ public instance model non_null int[] value;
      @ public instance model non_null int money;
      @ public instance model non_null int socialValue;
      @ public instance model non_null Message[] messages;
      @*/

    //@ ensures \result == id;
    public /*@pure@*/ int getId();

    //@ ensures \result.equals(name);
    public /*@pure@*/ String getName();

    //@ ensures \result == age;
    public /*@pure@*/ int getAge();

    /*@ also
      @ public normal_behavior
      @ requires obj != null && obj instanceof Person;
      @ assignable \nothing;
      @ ensures \result == (((Person) obj).getId() == id);
      @ also
      @ public normal_behavior
      @ requires obj == null || !(obj instanceof Person);
      @ assignable \nothing;
      @ ensures \result == false;
      @*/
    public /*@pure@*/ boolean equals(Object obj);

    /*@ public normal_behavior
      @ assignable \nothing;
      @ ensures \result == (\exists int i; 0 <= i && i < acquaintance.length; 
      @                     acquaintance[i].getId() == person.getId()) || person.getId() == id;
      @*/
    public /*@pure@*/ boolean isLinked(Person person);

    /*@ public normal_behavior
      @ requires (\exists int i; 0 <= i && i < acquaintance.length; 
      @          acquaintance[i].getId() == person.getId());
      @ assignable \nothing;
      @ ensures (\exists int i; 0 <= i && i < acquaintance.length; 
      @         acquaintance[i].getId() == person.getId() && \result == value[i]);
      @ also
      @ public normal_behavior
      @ requires (\forall int i; 0 <= i && i < acquaintance.length; 
      @          acquaintance[i].getId() != person.getId());
      @ ensures \result == 0;
      @*/
    public /*@pure@*/ int queryValue(Person person);

    //@ ensures \result == name.compareTo(p2.getName());
    public /*@pure@*/ int compareTo(Person p2);

    /*@ public normal_behavior
      @ assignable socialValue;
      @ ensures socialValue == \old(socialValue) + num;
      @*/
    public void addSocialValue(int num);

    //@ ensures \result == socialValue;
    public /*@pure@*/ int getSocialValue();

    //@ ensures \result == messages;
    public /*@pure@*/ List<Message> getMessages();

    /*@ public normal_behavior
      @ assignable \nothing;
      @ ensures (\forall int i; 0 <= i && i < messages.length && i <= 3;
      @           \result.contains(messages[i]) && \result[i] = messages[i]);
      @ ensures \result.length == (messages.length < 4)? messages.length: 4;
     */
    public /*@pure@*/ List<Message> getReceivedMessages();

    /*@ public normal_behavior
      @ assignable money;
      @ ensure money == \old(money) + num;
      @*/
    public void addMoney(int num);

    //@ ensures \result == money;
    public /*@pure@*/ int getMoney();

}
