package com.company;

import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlLifeline;

import java.util.ArrayList;

public class MyUmlLifeline {
    private UmlLifeline umlLifeline;
    private UmlAttribute umlAttribute;

    public MyUmlLifeline(UmlLifeline umlLifeline) {
        this.umlLifeline = umlLifeline;
    }

    public UmlLifeline getUmlLifeline() {
        return umlLifeline;
    }

    public void setUmlAttribute(UmlAttribute umlAttribute) {
        this.umlAttribute = umlAttribute;
    }
}
