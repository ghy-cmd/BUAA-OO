package com.company;

import com.oocourse.uml2.models.elements.UmlEvent;
import com.oocourse.uml2.models.elements.UmlOpaqueBehavior;
import com.oocourse.uml2.models.elements.UmlTransition;

import java.util.ArrayList;
import java.util.List;

public class MyTransition {
    private UmlTransition umlTransition;
    private ArrayList<UmlEvent> umlEvents = new ArrayList<>();
    private ArrayList<UmlOpaqueBehavior> umlOpaqueBehaviors = new ArrayList<>();
    private MyState source = null;
    private MyState target = null;

    public MyTransition(UmlTransition umlTransition) {
        this.umlTransition = umlTransition;
    }

    public UmlTransition getUmlTransition() {
        return umlTransition;
    }

    public ArrayList<UmlEvent> getUmlEvents() {
        return umlEvents;
    }

    public ArrayList<UmlOpaqueBehavior> getUmlOpaqueBehaviors() {
        return umlOpaqueBehaviors;
    }

    public MyState getSource() {
        return source;
    }

    public MyState getTarget() {
        return target;
    }

    public void setSource(MyState source) {
        this.source = source;
    }

    public void setTarget(MyState target) {
        this.target = target;
    }

    public List<String> getEvent() {
        List<String> result = new ArrayList<>();
        for (UmlEvent umlEvent : umlEvents) {
            result.add(umlEvent.getName());
        }
        return result;
    }
}
