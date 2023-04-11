package com.company;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.OperationParamInformation;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.AttributeWrongTypeException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.MethodDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.MethodWrongTypeException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.TransitionNotFoundException;
import com.oocourse.uml2.interact.format.UmlGeneralInteraction;
import com.oocourse.uml2.models.common.MessageSort;
import com.oocourse.uml2.models.common.NameableType;
import com.oocourse.uml2.models.common.NamedType;
import com.oocourse.uml2.models.common.ReferenceType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlCollaboration;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlEndpoint;
import com.oocourse.uml2.models.elements.UmlEvent;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;
import com.oocourse.uml2.models.elements.UmlOpaqueBehavior;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlState;
import com.oocourse.uml2.models.elements.UmlStateMachine;
import com.oocourse.uml2.models.elements.UmlTransition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUmlGeneralInteraction implements UmlGeneralInteraction {
    private ArrayList<UmlAssociation> umlAssociations = new ArrayList<>();
    private HashMap<String, UmlAssociationEnd> umlAssociationEndHashMap = new HashMap<>();
    private HashMap<String, UmlAttribute> umlAttributes = new HashMap<>();
    private HashMap<String, MyUmlClass> umlClassHashMap = new HashMap<>();
    private ArrayList<UmlGeneralization> umlGeneralizations = new ArrayList<>();
    private HashMap<String, MyInterface> umlInterfaceHashMap = new HashMap<>();
    private ArrayList<UmlInterfaceRealization> umlInterfaceRealizations = new ArrayList<>();
    private HashMap<String, MyOperation> umlOperations = new HashMap<>();
    private ArrayList<UmlParameter> umlParameters = new ArrayList<>();
    private ArrayList<String> javaType = new ArrayList<>();
    private HashMap<String, MyUmlCollaboration> umlCollaborationHashMap = new HashMap<>();
    private HashMap<String, MyUmlInteraction> umlInteractionHashMap = new HashMap<>();
    private HashMap<String, UmlEndpoint> umlEndpointHashMap = new HashMap<>();
    private HashMap<String, MyUmlLifeline> umlLifelineHashMap = new HashMap<>();
    private HashMap<String, MyUmlMessage> umlMessageHashMap = new HashMap<>();
    private HashMap<String, MyUmlStateMachine> umlStateMachineHashMap = new HashMap<>();
    private HashMap<String, MyRegion> regionHashMap = new HashMap<>();
    private HashMap<String, MyState> stateHashMap = new HashMap<>();
    private HashMap<String, MyTransition> transitionHashMap = new HashMap<>();
    private HashMap<String, UmlEvent> umlEventHashMap = new HashMap<>();
    private HashMap<String, UmlOpaqueBehavior> umlOpaqueBehaviorHashMap = new HashMap<>();

    public MyUmlGeneralInteraction(UmlElement... elements) {
        for (UmlElement element : elements) {
            if (element instanceof UmlAssociation) {
                umlAssociations.add((UmlAssociation) element);
            } else if (element instanceof UmlAssociationEnd) {
                umlAssociationEndHashMap.put(element.getId(), (UmlAssociationEnd) element);
            } else if (element instanceof UmlAttribute) {
                umlAttributes.put(element.getId(), (UmlAttribute) element);
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
            } else if (element instanceof UmlCollaboration) {
                MyUmlCollaboration umlCollaboration =
                    new MyUmlCollaboration((UmlCollaboration) element);
                umlCollaborationHashMap.put(element.getId(), umlCollaboration);
            } else if (element instanceof UmlInteraction) {
                MyUmlInteraction umlInteraction = new MyUmlInteraction((UmlInteraction) element);
                umlInteractionHashMap.put(element.getId(), umlInteraction);
            } else if (element instanceof UmlMessage) {
                MyUmlMessage umlMessage = new MyUmlMessage((UmlMessage) element);
                umlMessageHashMap.put(element.getId(), umlMessage);
            } else if (element instanceof UmlLifeline) {
                MyUmlLifeline umlLifeline = new MyUmlLifeline((UmlLifeline) element);
                umlLifelineHashMap.put(element.getId(), umlLifeline);
            } else if (element instanceof UmlEndpoint) {
                umlEndpointHashMap.put(element.getId(), (UmlEndpoint) element);
            } else if (element instanceof UmlStateMachine) {
                MyUmlStateMachine umlStateMachine =
                    new MyUmlStateMachine((UmlStateMachine) element);
                umlStateMachineHashMap.put(element.getId(), umlStateMachine);
            } else if (element instanceof UmlRegion) {
                MyRegion region = new MyRegion((UmlRegion) element);
                regionHashMap.put(element.getId(), region);
            } else if (element instanceof UmlPseudostate) {
                MyState pseudostate = new MyState((UmlPseudostate) element);
                stateHashMap.put(element.getId(), pseudostate);
            } else if (element instanceof UmlState) {
                MyState state = new MyState((UmlState) element);
                stateHashMap.put(element.getId(), state);
            } else if (element instanceof UmlFinalState) {
                MyState finalState = new MyState((UmlFinalState) element);
                stateHashMap.put(element.getId(), finalState);
            } else if (element instanceof UmlTransition) {
                MyTransition transition = new MyTransition((UmlTransition) element);
                transitionHashMap.put(element.getId(), transition);
            } else if (element instanceof UmlEvent) {
                umlEventHashMap.put(element.getId(), (UmlEvent) element);
            } else if (element instanceof UmlOpaqueBehavior) {
                umlOpaqueBehaviorHashMap.put(element.getId(), (UmlOpaqueBehavior) element);
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
        for (Map.Entry<String, UmlAttribute> entry : umlAttributes.entrySet()) {
            if (umlClassHashMap.containsKey(entry.getValue().getParentId())) {
                umlClassHashMap.get(entry.getValue().getParentId())
                    .addAttribute(entry.getValue());
            }
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

        for (Map.Entry<String, MyUmlInteraction> entry : umlInteractionHashMap.entrySet()) {
            if (umlCollaborationHashMap
                .containsKey(entry.getValue().getUmlInteraction().getParentId())) {
                umlCollaborationHashMap.get(entry.getValue().getUmlInteraction().getParentId())
                    .getUmlInteractions().add(entry.getValue());
            }
        }

        for (Map.Entry<String, MyUmlLifeline> entry : umlLifelineHashMap.entrySet()) {
            umlInteractionHashMap.get(entry.getValue().getUmlLifeline().getParentId())
                .getMyUmlLifelines().add(entry.getValue());
            entry.getValue().setUmlAttribute(
                umlAttributes.get(entry.getValue().getUmlLifeline().getRepresent()));
        }

        for (Map.Entry<String, UmlEndpoint> entry : umlEndpointHashMap.entrySet()) {
            umlInteractionHashMap.get(entry.getValue().getParentId()).getUmlEndpoints()
                .add(entry.getValue());
        }

        for (Map.Entry<String, MyUmlMessage> entry : umlMessageHashMap.entrySet()) {
            umlInteractionHashMap.get(entry.getValue().getUmlMessage().getParentId())
                .getMyUmlMessages().add(entry.getValue());
            String source = entry.getValue().getUmlMessage().getSource();
            String target = entry.getValue().getUmlMessage().getTarget();
            if (umlLifelineHashMap.containsKey(source)) {
                entry.getValue().setUmlLifeline1(umlLifelineHashMap.get(source));
            } else if (umlEndpointHashMap.containsKey(source)) {
                entry.getValue().setUmlEndpoint(umlEndpointHashMap.get(source));
            }
            if (umlLifelineHashMap.containsKey(target)) {
                entry.getValue().setUmlLifeline2(umlLifelineHashMap.get(target));
            } else if (umlEndpointHashMap.containsKey(target)) {
                entry.getValue().setUmlEndpoint(umlEndpointHashMap.get(target));
            }
        }

        for (Map.Entry<String, MyUmlStateMachine> entry : umlStateMachineHashMap.entrySet()) {
            if (umlClassHashMap.containsKey(entry.getValue().getUmlStateMachine().getParentId())) {
                umlClassHashMap.get(entry.getValue().getUmlStateMachine().getParentId())
                    .getUmlStateMachines().add(entry.getValue());
            }
        }

        for (Map.Entry<String, MyRegion> entry : regionHashMap.entrySet()) {
            umlStateMachineHashMap.get(entry.getValue().getUmlRegion().getParentId()).getRegions()
                .add(entry.getValue());
        }

        for (Map.Entry<String, MyState> entry : stateHashMap.entrySet()) {
            regionHashMap.get(entry.getValue().getParentId()).getStates()
                .add(entry.getValue());
        }

        for (Map.Entry<String, UmlEvent> entry : umlEventHashMap.entrySet()) {
            transitionHashMap.get(entry.getValue().getParentId()).getUmlEvents()
                .add(entry.getValue());
        }

        for (Map.Entry<String, UmlOpaqueBehavior> entry : umlOpaqueBehaviorHashMap.entrySet()) {
            transitionHashMap.get(entry.getValue().getParentId()).getUmlOpaqueBehaviors()
                .add(entry.getValue());
        }

        for (Map.Entry<String, MyTransition> entry : transitionHashMap.entrySet()) {
            regionHashMap.get(entry.getValue().getUmlTransition().getParentId()).getTransitions()
                .add(entry.getValue());
            String source = entry.getValue().getUmlTransition().getSource();
            String target = entry.getValue().getUmlTransition().getTarget();
            if (stateHashMap.containsKey(source)) {
                stateHashMap.get(source).getTransition2().add(entry.getValue());
                entry.getValue().setSource(stateHashMap.get(source));
            }
            if (stateHashMap.containsKey(target)) {
                stateHashMap.get(target).getTransition1().add(entry.getValue());
                entry.getValue().setTarget(stateHashMap.get(target));
            }
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

    @Override
    public int getParticipantCount(String interactionName)
        throws InteractionNotFoundException, InteractionDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlInteraction> entry : umlInteractionHashMap.entrySet()) {
            if (entry.getValue().getUmlInteraction().getName().equals(interactionName)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new InteractionNotFoundException(interactionName);
        } else if (temp > 1) {
            throw new InteractionDuplicatedException(interactionName);
        } else {
            return umlInteractionHashMap.get(id).getParticipantCount();
        }
    }

    @Override
    public int getIncomingMessageCount(String interactionName, String lifelineName)
        throws InteractionNotFoundException, InteractionDuplicatedException,
        LifelineNotFoundException, LifelineDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlInteraction> entry : umlInteractionHashMap.entrySet()) {
            if (entry.getValue().getUmlInteraction().getName().equals(interactionName)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new InteractionNotFoundException(interactionName);
        } else if (temp > 1) {
            throw new InteractionDuplicatedException(interactionName);
        } else {
            return umlInteractionHashMap.get(id).getIncomingMessageCount(lifelineName);
        }
    }

    @Override
    public int getSentMessageCount(String interactionName, String lifelineName, MessageSort sort)
        throws InteractionNotFoundException, InteractionDuplicatedException,
        LifelineNotFoundException, LifelineDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlInteraction> entry : umlInteractionHashMap.entrySet()) {
            if (entry.getValue().getUmlInteraction().getName().equals(interactionName)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new InteractionNotFoundException(interactionName);
        } else if (temp > 1) {
            throw new InteractionDuplicatedException(interactionName);
        } else {
            return umlInteractionHashMap.get(id).getSentMessageCount(lifelineName, sort);
        }
    }

    @Override
    public int getStateCount(String stateMachineName)
        throws StateMachineNotFoundException, StateMachineDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlStateMachine> entry : umlStateMachineHashMap.entrySet()) {
            if (entry.getValue().getUmlStateMachine().getName().equals(stateMachineName)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (temp > 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        } else {
            return umlStateMachineHashMap.get(id).getStateCount();
        }
    }

    @Override
    public int getSubsequentStateCount(String stateMachineName, String stateName)
        throws StateMachineNotFoundException, StateMachineDuplicatedException,
        StateNotFoundException, StateDuplicatedException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlStateMachine> entry : umlStateMachineHashMap.entrySet()) {
            if (entry.getValue().getUmlStateMachine().getName().equals(stateMachineName)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (temp > 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        } else {
            return umlStateMachineHashMap.get(id)
                .getSubsequentStateCount(stateMachineName, stateName);
        }
    }

    @Override
    public List<String> getTransitionTrigger(String stateMachineName, String sourceStateName,
                                             String targetStateName)
        throws StateMachineNotFoundException, StateMachineDuplicatedException,
        StateNotFoundException, StateDuplicatedException, TransitionNotFoundException {
        int temp = 0;
        String id = null;
        for (Map.Entry<String, MyUmlStateMachine> entry : umlStateMachineHashMap.entrySet()) {
            if (entry.getValue().getUmlStateMachine().getName().equals(stateMachineName)) {
                temp++;
                id = entry.getKey();
            }
        }
        if (temp == 0) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (temp > 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        } else {
            return umlStateMachineHashMap.get(id)
                .getTransitionTrigger(stateMachineName, sourceStateName, targetStateName);
        }
    }
}
