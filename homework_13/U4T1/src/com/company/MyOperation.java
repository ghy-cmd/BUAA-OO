package com.company;

import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlParameter;

import java.util.ArrayList;

public class MyOperation {
    private UmlOperation umlOperation;
    private ArrayList<UmlParameter> umlParameters = new ArrayList<>();

    public MyOperation(UmlOperation umlOperation) {
        this.umlOperation = umlOperation;
    }

    public void addParameter(UmlParameter umlParameter) {
        umlParameters.add(umlParameter);
    }

    public ArrayList<UmlParameter> getUmlParameters() {
        return umlParameters;
    }

    public UmlOperation getUmlOperation() {
        return umlOperation;
    }
}
