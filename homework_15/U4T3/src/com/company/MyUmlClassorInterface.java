package com.company;

import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlClass;
import com.oocourse.uml3.models.elements.UmlClassOrInterface;

import java.util.Map;

public class MyUmlClassorInterface implements UmlClassOrInterface {

    private MyUmlClass umlClass = null;
    private MyInterface umlInterface = null;

    public MyUmlClassorInterface(MyUmlClass umlClass) {
        this.umlClass = umlClass;
    }

    public MyUmlClassorInterface(MyInterface umlInterface) {
        this.umlInterface = umlInterface;
    }

    @Override
    public Visibility getVisibility() {
        if (this.umlClass != null) {
            return umlClass.getUmlClass().getVisibility();
        } else if (this.umlInterface != null) {
            return umlInterface.getUmlInterface().getVisibility();
        }
        return null;
    }

    @Override
    public ElementType getElementType() {
        if (this.umlClass != null) {
            return umlClass.getUmlClass().getElementType();
        } else if (this.umlInterface != null) {
            return umlInterface.getUmlInterface().getElementType();
        }
        return null;
    }

    @Override
    public String getId() {
        if (this.umlClass != null) {
            return umlClass.getUmlClass().getId();
        } else if (this.umlInterface != null) {
            return umlInterface.getUmlInterface().getId();
        }
        return null;
    }

    @Override
    public String getName() {
        if (this.umlClass != null) {
            return umlClass.getUmlClass().getName();
        } else if (this.umlInterface != null) {
            return umlInterface.getUmlInterface().getName();
        }
        return null;
    }

    @Override
    public String getParentId() {
        if (this.umlClass != null) {
            return umlClass.getUmlClass().getParentId();
        } else if (this.umlInterface != null) {
            return umlInterface.getUmlInterface().getParentId();
        }
        return null;
    }

    @Override
    public Map<String, Object> toJson() {
        return null;
    }
}
