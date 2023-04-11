package com.company;

import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlLifeline;

import java.util.ArrayList;

public class MyUmlLifeline {
    private UmlLifeline umlLifeline;
    private UmlAttribute umlAttribute = null;

    public MyUmlLifeline(UmlLifeline umlLifeline) {
        this.umlLifeline = umlLifeline;
    }

    public UmlAttribute getUmlAttribute() {
        return umlAttribute;
    }

    public UmlLifeline getUmlLifeline() {
        return umlLifeline;
    }

    public void setUmlAttribute(UmlAttribute umlAttribute) {
        this.umlAttribute = umlAttribute;
    }
}
