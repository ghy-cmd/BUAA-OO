package com.company;

import com.oocourse.uml2.models.elements.UmlCollaboration;

import java.util.ArrayList;

public class MyUmlCollaboration {
    private UmlCollaboration umlCollaboration;
    private ArrayList<MyUmlInteraction> umlInteractions = new ArrayList<>();

    public MyUmlCollaboration(UmlCollaboration umlCollaboration) {
        this.umlCollaboration = umlCollaboration;
    }

    public ArrayList<MyUmlInteraction> getUmlInteractions() {
        return umlInteractions;
    }
}
