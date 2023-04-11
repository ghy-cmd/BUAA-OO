package com.company;

import com.oocourse.uml1.models.elements.UmlInterface;

import java.util.ArrayList;
import java.util.List;

public class MyInterface {
    private UmlInterface umlInterface;
    private ArrayList<MyOperation> umlOperations = new ArrayList<>();
    private ArrayList<MyInterface> umlInterfaceFather = new ArrayList<>();//继承
    private static ArrayList<MyInterface> dfs = new ArrayList<>();

    public MyInterface(UmlInterface umlInterface) {
        this.umlInterface = umlInterface;
    }

    public UmlInterface getUmlInterface() {
        return umlInterface;
    }

    public void addUmlOperation(MyOperation umlOperation) {
        umlOperations.add(umlOperation);
    }

    public ArrayList<MyInterface> getUmlInterfaceFather() {
        return umlInterfaceFather;
    }

    public void setUmlInterfaceFather(MyInterface umlInterfaceFather) {
        this.umlInterfaceFather.add(umlInterfaceFather);
    }

    public List<MyInterface> getImplementInterfaceList(List<MyInterface> myInterfaces) {
        for (MyInterface item : umlInterfaceFather) {
            if (!myInterfaces.contains(item)) {
                myInterfaces.add(item);
            }
        }
        if (umlInterfaceFather.isEmpty()) {
            return myInterfaces;
        } else {
            for (MyInterface myInterface : umlInterfaceFather) {
                if (!dfs.contains(myInterface)) {
                    myInterfaces = myInterface.getImplementInterfaceList(myInterfaces);
                    dfs.add(myInterface);
                }
            }
            return myInterfaces;
        }
    }

    public void clear() {
        dfs.clear();
    }
}
