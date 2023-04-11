package com.company;

import com.oocourse.uml3.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml3.models.common.MessageSort;
import com.oocourse.uml3.models.elements.UmlEndpoint;
import com.oocourse.uml3.models.elements.UmlInteraction;

import java.util.ArrayList;

public class MyUmlInteraction {
    private UmlInteraction umlInteraction;
    private ArrayList<MyUmlMessage> myUmlMessages = new ArrayList<>();
    private ArrayList<MyUmlLifeline> myUmlLifelines = new ArrayList<>();
    private ArrayList<UmlEndpoint> umlEndpoints = new ArrayList<>();

    public MyUmlInteraction(UmlInteraction umlInteraction) {
        this.umlInteraction = umlInteraction;
    }

    public UmlInteraction getUmlInteraction() {
        return umlInteraction;
    }

    public ArrayList<MyUmlLifeline> getMyUmlLifelines() {
        return myUmlLifelines;
    }

    public ArrayList<MyUmlMessage> getMyUmlMessages() {
        return myUmlMessages;
    }

    public ArrayList<UmlEndpoint> getUmlEndpoints() {
        return umlEndpoints;
    }

    public int getParticipantCount() {
        return myUmlLifelines.size();
    }

    public int getIncomingMessageCount(String lifelineName)
        throws LifelineNotFoundException, LifelineDuplicatedException {
        int label = 0;
        for (MyUmlLifeline myUmlLifeline : myUmlLifelines) {
            if (myUmlLifeline.getUmlLifeline().getName().equals(lifelineName)) {
                label++;
            }
        }
        if (label == 0) {
            throw new LifelineNotFoundException(this.umlInteraction.getName(), lifelineName);
        } else if (label > 1) {
            throw new LifelineDuplicatedException(this.umlInteraction.getName(), lifelineName);
        } else {
            int count = 0;
            for (MyUmlMessage myUmlMessage : myUmlMessages) {
                if (myUmlMessage.getUmlLifeline2() != null &&
                    myUmlMessage.getUmlLifeline2().getUmlLifeline().getName()
                        .equals(lifelineName)) {
                    count++;
                }
            }
            return count;
        }
    }

    public int getSentMessageCount(String lifelineName, MessageSort sort)
        throws LifelineNotFoundException, LifelineDuplicatedException {
        int label = 0;
        for (MyUmlLifeline myUmlLifeline : myUmlLifelines) {
            if (myUmlLifeline.getUmlLifeline().getName().equals(lifelineName)) {
                label++;
            }
        }
        if (label == 0) {
            throw new LifelineNotFoundException(this.umlInteraction.getName(), lifelineName);
        } else if (label > 1) {
            throw new LifelineDuplicatedException(this.umlInteraction.getName(), lifelineName);
        } else {
            int count = 0;
            for (MyUmlMessage myUmlMessage : myUmlMessages) {
                MyUmlLifeline umlLifeline = myUmlMessage.getUmlLifeline1();
                if (umlLifeline != null &&
                    umlLifeline.getUmlLifeline().getName().equals(lifelineName) &&
                    myUmlMessage.getUmlMessage().getMessageSort().equals(sort)) {
                    count++;
                }
            }
            return count;
        }
    }
}
