package com.company;

import com.oocourse.uml3.interact.common.AttributeClassInformation;
import com.oocourse.uml3.interact.common.OperationParamInformation;
import com.oocourse.uml3.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.MethodDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.MethodWrongTypeException;
import com.oocourse.uml3.models.common.Direction;
import com.oocourse.uml3.models.common.NameableType;
import com.oocourse.uml3.models.common.NamedType;
import com.oocourse.uml3.models.common.ReferenceType;
import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlAssociation;
import com.oocourse.uml3.models.elements.UmlAssociationEnd;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlClass;
import com.oocourse.uml3.models.elements.UmlClassOrInterface;
import com.oocourse.uml3.models.elements.UmlParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyUmlClass {
    private UmlClass umlClass;
    private ArrayList<UmlAttribute> umlAttributes = new ArrayList<>();
    private ArrayList<MyOperation> umlOperations = new ArrayList<>();
    private ArrayList<MyInterface> umlInterfaces = new ArrayList<>();//接口实现
    private ArrayList<MyUmlClass> umlClasses = new ArrayList<>();//关联
    private ArrayList<MyInterface> umlInterfacesAssociate = new ArrayList<>();
    private MyUmlClass umlClassFather = null;//继承
    private ArrayList<MyUmlStateMachine> umlStateMachines = new ArrayList<>();
    private ArrayList<UmlAssociationEnd> umlAssociationEnds = new ArrayList<>();
    private static ArrayList<MyUmlClass> umlClassFathers = new ArrayList<>();

    public String getId() {
        return umlClass.getId();
    }

    public ArrayList<MyInterface> getUmlInterfacesAssociate() {
        return umlInterfacesAssociate;
    }

    public ArrayList<UmlAssociationEnd> getUmlAssociationEnds() {
        return umlAssociationEnds;
    }

    public MyUmlClass(UmlClass umlClass) {
        this.umlClass = umlClass;
    }

    public ArrayList<MyUmlStateMachine> getUmlStateMachines() {
        return umlStateMachines;
    }

    public ArrayList<UmlAttribute> getUmlAttributes() {
        return umlAttributes;
    }

    public UmlClass getUmlClass() {
        return umlClass;
    }

    public void addAttribute(UmlAttribute umlAttribute) {
        umlAttributes.add(umlAttribute);
    }

    public void addOperation(MyOperation umlOperation) {
        umlOperations.add(umlOperation);
    }

    public void setUmlClassFather(MyUmlClass umlClassFather) {
        this.umlClassFather = umlClassFather;
    }

    public void addInterfaceGeneral(MyInterface myInterface) {
        umlInterfaces.add(myInterface);
    }

    public void addUmlClass(MyUmlClass myUmlClass) {
        umlClasses.add(myUmlClass);
    }

    public int operationSum() {
        return umlOperations.size();
    }

    public int attributeSum() {
        if (umlClassFather == null) {
            return umlAttributes.size();
        } else {
            return umlAttributes.size() + umlClassFather.attributeSum();
        }
    }

    public Map<Visibility, Integer> getClassOperationVisibility(String operationName) {
        HashMap<Visibility, Integer> map = new HashMap<>();
        map.put(Visibility.PUBLIC, 0);
        map.put(Visibility.PACKAGE, 0);
        map.put(Visibility.PRIVATE, 0);
        map.put(Visibility.PROTECTED, 0);
        for (MyOperation myOperation : umlOperations) {
            if (myOperation.getUmlOperation().getName().equals(operationName)) {
                int temp = map.get(myOperation.getUmlOperation().getVisibility()) + 1;
                map.put(myOperation.getUmlOperation().getVisibility(), temp);
            }
        }
        return map;
    }

    public Visibility getClassAttributeVisibility(String className, String attributeName)
        throws AttributeNotFoundException, AttributeDuplicatedException {
        int label;
        label = countAttributes(attributeName, 0);
        if (label == 0) {
            throw new AttributeNotFoundException(className, attributeName);
        } else if (label > 1) {
            throw new AttributeDuplicatedException(className, attributeName);
        } else {
            return getAttributeVisibility(attributeName);
        }
    }

    public int countAttributes(String attributeName, int label) {
        for (UmlAttribute umlAttribute : umlAttributes) {
            if (umlAttribute.getName().equals(attributeName)) {
                label++;
            }
        }
        if (umlClassFather != null) {
            label = umlClassFather.countAttributes(attributeName, label);
        }
        return label;
    }

    public Visibility getAttributeVisibility(String attributeName) {
        int num = -1;
        for (int i = 0; i < umlAttributes.size(); i++) {
            if (umlAttributes.get(i).getName().equals(attributeName)) {
                num = i;
            }
        }
        if (num != -1) {
            return umlAttributes.get(num).getVisibility();
        } else {
            return umlClassFather.getAttributeVisibility(attributeName);
        }
    }

    public NameableType getClassAttributeType(String className, String attributeName)
        throws AttributeNotFoundException, AttributeDuplicatedException {
        int label;
        label = countAttributes(attributeName, 0);
        if (label == 0) {
            throw new AttributeNotFoundException(className, attributeName);
        } else if (label > 1) {
            throw new AttributeDuplicatedException(className, attributeName);
        } else {
            return getAttributeType(attributeName);
        }
    }

    public NameableType getAttributeType(String attributeName) {
        int num = -1;
        NameableType nameableType = null;
        for (int i = 0; i < umlAttributes.size(); i++) {
            if (umlAttributes.get(i).getName().equals(attributeName)) {
                num = i;
                nameableType = umlAttributes.get(i).getType();
                break;
            }
        }
        if (num != -1) {
            return nameableType;
        } else {
            return umlClassFather.getAttributeType(attributeName);
        }
    }

    public List<OperationParamInformation> getClassOperationParamType(String className,
                                                                      String operationName,
                                                                      ArrayList<String> javaType,
                                                                      HashMap<String, MyUmlClass> umlClassHashMap,
                                                                      HashMap<String, MyInterface> umlInterfaceHashMap)
        throws MethodWrongTypeException, MethodDuplicatedException {
        ArrayList<OperationParamInformation> operationParamInformation = new ArrayList<>();
        ArrayList<OperationParamInformation> result = new ArrayList<>();
        for (MyOperation umlOperation : umlOperations) {
            if (umlOperation.getUmlOperation().getName().equals(operationName)) {
                ArrayList<String> parameterTypes = new ArrayList<>();
                String returnType = null;
                for (UmlParameter item : umlOperation.getUmlParameters()) {
                    if (item.getDirection().equals(Direction.RETURN)) {
                        if (item.getType() instanceof NamedType) {
                            if (javaType.contains(((NamedType) item.getType()).getName()) ||
                                ((NamedType) item.getType()).getName().equals("void")) {
                                returnType = ((NamedType) item.getType()).getName();
                            } else {
                                throw new MethodWrongTypeException(className, operationName);
                            }
                        } else if (item.getType() instanceof ReferenceType) {
                            if (umlClassHashMap
                                .containsKey(((ReferenceType) item.getType()).getReferenceId())) {
                                returnType = umlClassHashMap
                                    .get(((ReferenceType) item.getType()).getReferenceId())
                                    .getUmlClass().getName();
                            } else if (umlInterfaceHashMap
                                .containsKey(((ReferenceType) item.getType()).getReferenceId())) {
                                returnType = umlInterfaceHashMap
                                    .get(((ReferenceType) item.getType()).getReferenceId())
                                    .getUmlInterface().getName();
                            } else {
                                throw new MethodWrongTypeException(className, operationName);
                            }
                        }
                    } else {
                        if (item.getType() instanceof NamedType) {
                            if (javaType.contains(((NamedType) item.getType()).getName())) {
                                parameterTypes.add(((NamedType) item.getType()).getName());
                            } else {
                                throw new MethodWrongTypeException(className, operationName);
                            }
                        } else if (item.getType() instanceof ReferenceType) {
                            if (umlClassHashMap
                                .containsKey(((ReferenceType) item.getType()).getReferenceId())) {
                                parameterTypes.add(umlClassHashMap
                                    .get(((ReferenceType) item.getType()).getReferenceId())
                                    .getUmlClass().getName());
                            } else if (umlInterfaceHashMap
                                .containsKey(((ReferenceType) item.getType()).getReferenceId())) {
                                parameterTypes.add(umlInterfaceHashMap
                                    .get(((ReferenceType) item.getType()).getReferenceId())
                                    .getUmlInterface().getName());
                            } else {
                                throw new MethodWrongTypeException(className, operationName);
                            }
                        }
                    }
                }
                OperationParamInformation information =
                    new OperationParamInformation(parameterTypes, returnType);
                operationParamInformation.add(information);
            }
        }
        for (OperationParamInformation information : operationParamInformation) {
            if (result.contains(information)) {
                throw new MethodDuplicatedException(className, operationName);
            } else {
                result.add(information);
            }
        }
        return result;
    }

    public String getTopParentClass() {
        if (umlClassFather == null) {
            return umlClass.getName();
        } else {
            return umlClassFather.getTopParentClass();
        }
    }

    public List<String> getImplementInterfaceList(List<MyInterface> myInterfaces) {
        List<String> result = new ArrayList<>();
        for (MyInterface myInterface : umlInterfaces) {
            if (!myInterfaces.contains(myInterface)) {
                myInterfaces.add(myInterface);
                myInterfaces = myInterface.getImplementInterfaceList(myInterfaces);
                myInterface.clear();
            }
        }
        if (umlClassFather == null) {
            for (MyInterface item : myInterfaces) {
                result.add(item.getUmlInterface().getName());
            }
            return result;
        } else {
            return umlClassFather.getImplementInterfaceList(myInterfaces);
        }
    }

    public List<AttributeClassInformation> getInformationNotHidden(String className) {
        List<AttributeClassInformation> result = new ArrayList<>();
        for (UmlAttribute attribute : umlAttributes) {
            if (!attribute.getVisibility().equals(Visibility.PRIVATE)) {
                AttributeClassInformation attributeClassInformation =
                    new AttributeClassInformation(attribute.getName(), className);
                result.add(attributeClassInformation);
            }
        }
        if (umlClassFather == null) {
            return result;
        } else {
            result.addAll(
                umlClassFather.getInformationNotHidden(umlClassFather.getUmlClass().getName()));
            return result;
        }
    }

    public List<String> getClassAssociatedClassList(List<MyUmlClass> umlClassList) {
        List<String> result = new ArrayList<>();
        for (MyUmlClass item : umlClasses) {
            if (!umlClassList.contains(item)) {
                umlClassList.add(item);
                result.add(item.getUmlClass().getName());
            }
        }
        if (umlClassFather == null) {
            return result;
        } else {
            result.addAll(umlClassFather.getClassAssociatedClassList(umlClassList));
            return result;
        }
    }

    public List<AttributeClassInformation> R001() {
        List<AttributeClassInformation> attributeClassInformations = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (UmlAttribute item : umlAttributes) {
            if (item.getName() != null && names.contains(item.getName())) {
                AttributeClassInformation information =
                    new AttributeClassInformation(item.getName(), getUmlClass().getName());
                attributeClassInformations.add(information);
            } else if (item.getName() != null) {
                names.add(item.getName());
            }
        }
        for (UmlAssociationEnd item : umlAssociationEnds) {
            if (item.getName() != null && names.contains(item.getName())) {
                AttributeClassInformation information =
                    new AttributeClassInformation(item.getName(), getUmlClass().getName());
                attributeClassInformations.add(information);
            } else if (item.getName() != null) {
                names.add(item.getName());
            }
        }
        return attributeClassInformations;
    }

    public Boolean R002(MyUmlClass umlClassFather) {
        //      System.out.println(umlClassFather.getId());
        umlClassFathers.add(this);
        if (umlClassFather.equals(this.umlClassFather)) {
            return true;
        } else {
            if (this.umlClassFather == null) {
                return false;
            } else {
                if (umlClassFathers.contains(this.umlClassFather)) {
                    return false;
                } else {
                    return this.umlClassFather.R002(umlClassFather);
                }
            }
        }
    }

    public Boolean R003() {
        umlClassFathers.add(this);
        if (umlClassFather == null) {
            return false;
        } else {
            if (umlClassFathers.contains(this.umlClassFather)) {
                return true;
            } else {
                return umlClassFather.R003();
            }
        }
    }

    public Boolean R004(List<MyInterface> myInterfaces) {
        for (MyInterface myInterface : umlInterfaces) {
            if (myInterfaces.contains(myInterface)) {
                return true;
            } else {
                myInterfaces.add(myInterface);
                MyInterface.setDe(false);
                myInterfaces = myInterface.getImplementInterfaceList(myInterfaces);
                myInterface.clear();
                if (MyInterface.getDe()) {
                    return true;
                }
            }
        }
        if (umlClassFather == null) {
            return false;
        } else {
            return umlClassFather.R004(myInterfaces);
        }
    }

    public void clear() {
        umlClassFathers.clear();
    }
}
