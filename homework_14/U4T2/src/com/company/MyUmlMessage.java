package com.company;

import com.oocourse.uml2.models.elements.UmlEndpoint;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;

public class MyUmlMessage {
    private UmlMessage umlMessage;
    private MyUmlLifeline umlLifeline1 = null;
    private MyUmlLifeline umlLifeline2 = null;
    private UmlEndpoint umlEndpoint = null;

    public MyUmlMessage(UmlMessage umlMessage) {
        this.umlMessage = umlMessage;
    }

    public UmlMessage getUmlMessage() {
        return umlMessage;
    }

    public void setUmlEndpoint(UmlEndpoint umlEndpoint) {
        this.umlEndpoint = umlEndpoint;
    }

    public void setUmlLifeline1(MyUmlLifeline umlLifeline1) {
        this.umlLifeline1 = umlLifeline1;
    }

    public void setUmlLifeline2(MyUmlLifeline umlLifeline2) {
        this.umlLifeline2 = umlLifeline2;
    }

    public MyUmlLifeline getUmlLifeline1() {
        return umlLifeline1;
    }

    public MyUmlLifeline getUmlLifeline2() {
        return umlLifeline2;
    }

    public UmlEndpoint getUmlEndpoint() {
        return umlEndpoint;
    }
}
