package com.company;

import com.oocourse.uml3.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.TransitionNotFoundException;
import com.oocourse.uml3.models.elements.UmlStateMachine;

import java.util.ArrayList;
import java.util.List;

public class MyUmlStateMachine {
    private UmlStateMachine umlStateMachine;
    private ArrayList<MyRegion> regions = new ArrayList<>();

    public MyUmlStateMachine(UmlStateMachine umlStateMachine) {
        this.umlStateMachine = umlStateMachine;
    }

    public ArrayList<MyRegion> getRegions() {
        return regions;
    }

    public UmlStateMachine getUmlStateMachine() {
        return umlStateMachine;
    }

    public int getStateCount() {
        int sum = 0;
        for (MyRegion myRegion : regions) {
            sum += myRegion.getStateCount();
        }
        return sum;
    }

    public int getSubsequentStateCount(String stateMachineName, String stateName)
        throws StateNotFoundException, StateDuplicatedException {
        int label = 0;
        MyState state = null;
        for (MyRegion region : regions) {
            for (MyState state1 : region.getStates()) {
                if (state1.getName() != null && state1.getName().equals(stateName)) {
                    label++;
                    state = state1;
                }
            }
        }
        if (label == 0) {
            throw new StateNotFoundException(stateMachineName, stateName);
        } else if (label > 1) {
            throw new StateDuplicatedException(stateMachineName, stateName);
        } else {
            state.dfsClear();
            MyState.setTemp(0);
            int result = state.getSubsequentStateCount(state);
            if (MyState.getTemp() == 0) {
                return result - 1;
            } else {
                return result;
            }
        }
    }

    public List<String> getTransitionTrigger(String stateMachineName, String sourceStateName,
                                             String targetStateName)
        throws StateNotFoundException, StateDuplicatedException, TransitionNotFoundException {
        int label = 0;
        MyState state1 = null;
        MyState state2 = null;
        for (MyRegion region : regions) {
            for (MyState state : region.getStates()) {
                if (state.getName() != null && state.getName().equals(sourceStateName)) {
                    label++;
                    state1 = state;
                }
            }
        }
        if (label == 0) {
            throw new StateNotFoundException(stateMachineName, sourceStateName);
        } else if (label > 1) {
            throw new StateDuplicatedException(stateMachineName, sourceStateName);
        }
        label = 0;
        for (MyRegion region : regions) {
            for (MyState state : region.getStates()) {
                if (state.getName() != null && state.getName().equals(targetStateName)) {
                    label++;
                    state2 = state;
                }
            }
        }
        if (label == 0) {
            throw new StateNotFoundException(stateMachineName, targetStateName);
        } else if (label > 1) {
            throw new StateDuplicatedException(stateMachineName, targetStateName);
        } else {
            return state1
                .getTransitionTrigger(stateMachineName, sourceStateName, targetStateName, state2);
        }
    }
}
