package com.company;

import com.oocourse.uml2.models.elements.UmlRegion;

import java.util.ArrayList;

public class MyRegion {
    private UmlRegion umlRegion;
    private ArrayList<MyState> states = new ArrayList<>();
    private ArrayList<MyTransition> transitions = new ArrayList<>();

    public MyRegion(UmlRegion umlRegion) {
        this.umlRegion = umlRegion;
    }

    public UmlRegion getUmlRegion() {
        return umlRegion;
    }

    public ArrayList<MyState> getStates() {
        return states;
    }

    public ArrayList<MyTransition> getTransitions() {
        return transitions;
    }

    public int getStateCount() {
        return states.size();
    }
}
