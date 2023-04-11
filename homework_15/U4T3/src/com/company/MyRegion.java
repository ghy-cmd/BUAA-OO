package com.company;

import com.oocourse.uml3.models.elements.UmlRegion;

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

    public Boolean R008() {
        for (MyState item : states) {
            if (item.getType() == 2) {
                if (item.getTransition2().size() > 1) {
                    return true;
                }
                for (MyTransition entry : item.getTransition2()) {
                    if (entry.getUmlEvents().size() != 0 ||
                        entry.getUmlTransition().getGuard() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
