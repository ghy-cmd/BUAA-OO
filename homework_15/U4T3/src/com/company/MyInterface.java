package com.company;

import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlInterface;

import java.util.ArrayList;
import java.util.List;

public class MyInterface {
    private UmlInterface umlInterface;
    private ArrayList<MyOperation> umlOperations = new ArrayList<>();
    private ArrayList<UmlAttribute> umlAttributes = new ArrayList<>();
    private ArrayList<MyInterface> umlInterfaceFather = new ArrayList<>();//继承
    private static ArrayList<MyInterface> dfs = new ArrayList<>();
    private static Boolean de = false;

    public static Boolean getDe() {
        return de;
    }

    public static void setDe(Boolean de) {
        MyInterface.de = de;
    }

    public MyInterface(UmlInterface umlInterface) {
        this.umlInterface = umlInterface;
    }

    public ArrayList<UmlAttribute> getUmlAttributes() {
        return umlAttributes;
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
            } else {
                de = true;
            }
        }
        if (umlInterfaceFather.isEmpty()) {
            return myInterfaces;
        } else {
            for (MyInterface myInterface : umlInterfaceFather) {
                if (!dfs.contains(myInterface)) {
                    myInterfaces = myInterface.getImplementInterfaceList(myInterfaces);
                    dfs.add(myInterface);
                } else {
                    de = true;
                }
            }
            return myInterfaces;
        }
    }

    public void clear() {
        dfs.clear();
    }

    public Boolean R002(MyInterface myInterface) {
        dfs.add(this);
        if (umlInterfaceFather.contains(myInterface)) {
            return true;
        } else {
            if (umlInterfaceFather.isEmpty()) {
                return false;
            } else {
                for (MyInterface item : umlInterfaceFather) {
                    if (!dfs.contains(item)) {
                        Boolean result = item.R002(myInterface);
                        if (result) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
    }

    public Boolean R003() {
        dfs.add(this);
        if (umlInterfaceFather.isEmpty()) {
            return false;
        } else {
            for (MyInterface item : umlInterfaceFather) {
                if (!dfs.contains(item)) {
                    Boolean result = item.R003();
                    if (result) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
            return false;
        }
    }

    public Boolean R006() {
        for (UmlAttribute item : umlAttributes) {
            if (item.getVisibility() != Visibility.PUBLIC) {
                return true;
            }
        }
        return false;
    }
}
