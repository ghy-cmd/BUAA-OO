package com.company;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.OperationParamInformation;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.AttributeWrongTypeException;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.MethodDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.MethodWrongTypeException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.NameableType;
import com.oocourse.uml1.models.common.NamedType;
import com.oocourse.uml1.models.common.ReferenceType;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlInterface;
import com.oocourse.uml1.models.elements.UmlInterfaceRealization;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUmlInteraction implements UmlInteraction {
    private ArrayList<UmlAssociation> umlAssociations = new ArrayList<>();
    private HashMap<String, UmlAssociationEnd> umlAssociationEndHashMap = new HashMap<>();
    private ArrayList<UmlAttribute> umlAttributes = new ArrayList<>();
    private HashMap<String, MyUmlClass> umlClassHashMap = new HashMap<>();
    private ArrayList<UmlGeneralization> umlGeneralizations = new ArrayList<>();
    private HashMap<String, MyInterface> umlInterfaceHashMap = new HashMap<>();
    private ArrayList<UmlInterfaceRealization> umlInterfaceRealizations = new ArrayList<>();
    private HashMap<String, MyOperation> umlOperations = new HashMap<>();
    private ArrayList<UmlParameter> umlParameters = new ArrayList<>();
    private ArrayList<String> javaType = new ArrayList<>();

    public MyUmlInteraction(UmlElement... elements) {
        for (UmlElement element : elements) {
            if (element instanceof UmlAssociation) {
                umlAssociations.add((UmlAssociation) element);
            } else if (element instanceof UmlAssociationEnd) {
                umlAssociationEndHashMap.put(element.getId(), (UmlAssociationEnd) element);
            } else if (element instanceof UmlAttribute) {
                umlAttributes.add((UmlAttribute) element);
            } else if (element instanceof UmlClass) {
                MyUmlClass umlClass = new MyUmlClass((UmlClass) element);
                umlClassHashMap.put(element.getId(), umlClass);
            } else if (element instanceof UmlGeneralization) {
                umlGeneralizations.add((UmlGeneralization) element);
            } else if (element instanceof UmlInterface) {
                MyInterface umlInterface = new MyInterface((UmlInterface) element);
                umlInterfaceHashMap.put(element.getId(), umlInterface);
            } else if (element instanceof UmlInterfaceRealization) {
                umlInterfaceRealizations.add((UmlInterfaceRealization) element);
            } else if (element instanceof UmlOperation) {
                MyOperation umlOperation = new MyOperation((UmlOperation) element);
                umlOperations.put(element.getId(), umlOperation);
            } else if (element instanceof UmlParameter) {
                umlParameters.add((UmlParameter) element);
            }
        }
        javaType.add("byte");
        javaType.add("short");
        javaType.add("int");
        javaType.add("long");
        javaType.add("float");
        javaType.add("double");
        javaType.add("char");
        javaType.add("boolean");
        javaType.add("String");
        for (UmlParameter umlParameter : umlParameters) {
            umlOperations.get(umlParameter.getParentId())
                .addParameter(umlParameter);
        }
        for (UmlAttribute umlAttribute : umlAttributes) {
            umlClassHashMap.get(umlAttribute.getParentId())
                .addAttribute(umlAttribute);
        }
        for (Map.Entry<String, MyOperation> entry : umlOperations.entrySet()) {
            if (umlClassHashMap.containsKey(entry.getValue().getUmlOperation().getParentId())) {
                umlClassHashMap.get(entry.getValue().getUmlOperation().getParentId())
                    .addOperation(entry.getValue());
            } else if (umlInterfaceHashMap
                .containsKey(entry.getValue().getUmlOperation().getParentId())) {
                umlInterfaceHashMap.get(entry.getValue().getUmlOperation().getParentId())
                    .addUmlOperation(entry.getValue());
            }
        }
        for (UmlAssociation umlAssociation : umlAssociations) {
            String end1 = umlAssociation.getEnd1();
            String end2 = umlAssociation.getEnd2();
            UmlAssociationEnd umlAssociationEnd1 = umlAssociationEndHashMap.get(end1);
            UmlAssociationEnd umlAssociationEnd2 = umlAssociationEndHashMap.get(end2);
            MyUmlClass umlClass1 = umlClassHashMap.get(umlAssociationEnd1.getReference());
            MyUmlClass umlClass2 = umlClassHashMap.get(umlAssociationEnd2.getReference());
            if (umlClass1 != null && umlClass2 != null) {
                if (umlClass1.equals(umlClass2)) {
                    umlClass1.addUmlClass(umlClass2);
                } else {
                    umlClass1.addUmlClass(umlClass2);
                    umlClass2.addUmlClass(umlClass1);
                }
            }
        }
        for (UmlGeneralization umlGeneralization : umlGeneralizations) {
            if (umlClassHashMap.containsKey(umlGeneralization.getSource())) {
                umlClassHashMap.get(umlGeneralization.getSource())
                    .setUmlClassFather(umlClassHashMap.get(umlGeneralization.getTarget()));
            } else if (umlInterfaceHashMap.containsKey(umlGeneralization.getSource())) {
                umlInterfaceHashMap.get(umlGeneralization.getSource())
                    .setUmlInterfaceFather(umlInterfaceHashMap.get(umlGeneralization.getTarget()));
            }
        }
        for (UmlInterfaceRealization umlInterfaceRealization : umlInterfaceRealizations) {
            umlClassHashMap.get(umlInterfaceRealization.getSource())
                .addInterfaceGeneral(umlInterfaceHashMap.get(umlInterfaceRealization.getTarget()));
        }
    }

    public int getClassCount() {
        return umlClassHashMap.size();
    }

    public int getClassOperationCount(String className)
        throws ClassNotFoundException, ClassDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            return umlClassHashMap.get(id).operationSum();
        }
    }

    public int getClassAttributeCount(String className)
        throws ClassNotFoundException, ClassDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            return umlClassHashMap.get(id).attributeSum();
        }
    }

    public Map<Visibility, Integer> getClassOperationVisibility(String className,
                                                                String operationName)
        throws ClassNotFoundException, ClassDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            return umlClassHashMap.get(id).getClassOperationVisibility(operationName);
        }
    }

    public Visibility getClassAttributeVisibility(String className, String attributeName)
        throws ClassNotFoundException, ClassDuplicatedException, AttributeNotFoundException,
        AttributeDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            return umlClassHashMap.get(id).getClassAttributeVisibility(className, attributeName);
        }
    }

    public String getClassAttributeType(String className, String attributeName)
        throws ClassNotFoundException, ClassDuplicatedException, AttributeNotFoundException,
        AttributeDuplicatedException, AttributeWrongTypeException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            NameableType nameableType = null;
            nameableType = umlClassHashMap.get(id).getClassAttributeType(className, attributeName);
            if (nameableType instanceof NamedType) {
                if (javaType.contains(((NamedType) nameableType).getName())) {
                    return ((NamedType) nameableType).getName();
                } else {
                    throw new AttributeWrongTypeException(className, attributeName);
                }
            } else if (nameableType instanceof ReferenceType) {
                if (umlClassHashMap.containsKey(((ReferenceType) nameableType).getReferenceId())) {
                    return umlClassHashMap.get(((ReferenceType) nameableType).getReferenceId())
                        .getUmlClass().getName();
                } else if (umlInterfaceHashMap
                    .containsKey(((ReferenceType) nameableType).getReferenceId())) {
                    return umlInterfaceHashMap.get(((ReferenceType) nameableType).getReferenceId())
                        .getUmlInterface().getName();
                } else {
                    throw new AttributeWrongTypeException(className, attributeName);
                }
            } else {
                throw new AttributeWrongTypeException(className, attributeName);
            }
        }
    }

    public List<OperationParamInformation> getClassOperationParamType(String className,
                                                                      String operationName)
        throws ClassNotFoundException, ClassDuplicatedException, MethodWrongTypeException,
        MethodDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            return umlClassHashMap.get(id)
                .getClassOperationParamType(className, operationName, javaType, umlClassHashMap,
                    umlInterfaceHashMap);
        }
    }

    public List<String> getClassAssociatedClassList(String className)
        throws ClassNotFoundException, ClassDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            List<MyUmlClass> myUmlClasses = new ArrayList<>();
            return umlClassHashMap.get(id).getClassAssociatedClassList(myUmlClasses);
        }
    }

    public String getTopParentClass(String className)
        throws ClassNotFoundException, ClassDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            return umlClassHashMap.get(id).getTopParentClass();
        }
    }

    public List<String> getImplementInterfaceList(String className)
        throws ClassNotFoundException, ClassDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            List<MyInterface> myInterfaces = new ArrayList<>();
            return umlClassHashMap.get(id).getImplementInterfaceList(myInterfaces);
        }
    }

    public List<AttributeClassInformation> getInformationNotHidden(String className)
        throws ClassNotFoundException, ClassDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlClass> entry : umlClassHashMap.entrySet()) {
            if (entry.getValue().getUmlClass().getName().equals(className)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new ClassNotFoundException(className);
        } else if (temp > 1) {
            throw new ClassDuplicatedException(className);
        } else {
            return umlClassHashMap.get(id).getInformationNotHidden(className);
        }
    }
}
