package com.company;

import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlCollaboration;

import java.util.ArrayList;

public class MyUmlCollaboration {
    private UmlCollaboration umlCollaboration;
    private ArrayList<MyUmlInteraction> umlInteractions = new ArrayList<>();
    private ArrayList<UmlAttribute> umlAttributes = new ArrayList<>();

    public MyUmlCollaboration(UmlCollaboration umlCollaboration) {
        this.umlCollaboration = umlCollaboration;
    }

    public ArrayList<UmlAttribute> getUmlAttributes() {
        return umlAttributes;
    }

    public ArrayList<MyUmlInteraction> getUmlInteractions() {
        return umlInteractions;
    }
}
