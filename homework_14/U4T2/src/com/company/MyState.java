package com.company;

import com.oocourse.uml2.interact.exceptions.user.TransitionNotFoundException;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlState;

import java.util.ArrayList;
import java.util.List;

public class MyState {
    private UmlState umlState = null;
    private UmlPseudostate umlPseudostate = null;
    private UmlFinalState umlFinalState = null;
    private ArrayList<MyTransition> transition1 = new ArrayList<>();
    private ArrayList<MyTransition> transition2 = new ArrayList<>();
    private static ArrayList<MyState> dfs = new ArrayList<>();
    private static int temp = 0;

    public MyState(UmlState umlState) {
        this.umlState = umlState;
    }

    public MyState(UmlPseudostate umlPseudostate) {
        this.umlPseudostate = umlPseudostate;
    }

    public MyState(UmlFinalState umlFinalState) {
        this.umlFinalState = umlFinalState;
    }

    public String getParentId() {
        if (umlState != null) {
            return umlState.getParentId();
        } else if (umlPseudostate != null) {
            return umlPseudostate.getParentId();
        } else {
            return umlFinalState.getParentId();
        }
    }

    public String getName() {
        if (umlState != null) {
            return umlState.getName();
        } else {
            return null;
        }
    }

    public UmlState getUmlState() {
        return umlState;
    }

    public ArrayList<MyTransition> getTransition1() {
        return transition1;
    }

    public ArrayList<MyTransition> getTransition2() {
        return transition2;
    }

    public int getSubsequentStateCount(MyState myState) {
        int num = 0;
        dfs.add(this);
        num++;
        if (transition2.isEmpty()) {
            return num;
        } else {
            for (MyTransition transition : transition2) {
                if (transition.getTarget().equals(myState)) {
                    temp++;
                }
                if (!dfs.contains(transition.getTarget())) {
                    num += transition.getTarget().getSubsequentStateCount(myState);
                }
            }
        }
        return num;
    }

    public static int getTemp() {
        return temp;
    }

    public static void setTemp(int temp) {
        MyState.temp = temp;
    }

    public void dfsClear() {
        dfs.clear();
    }

    public List<String> getTransitionTrigger(String stateMachineName, String sourceStateName,
                                             String targetStateName, MyState state)
        throws TransitionNotFoundException {
        int label = 0;
        List<String> result = new ArrayList<>();
        for (MyTransition transition : transition2) {
            if (transition.getTarget().equals(state)) {
                label = 1;
                result.addAll(transition.getEvent());
            }
        }
        if (label == 0) {
            throw new TransitionNotFoundException(stateMachineName, sourceStateName,
                targetStateName);
        }
        return result;
    }
}
