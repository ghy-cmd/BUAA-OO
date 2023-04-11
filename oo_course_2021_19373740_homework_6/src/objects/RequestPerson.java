package objects;

public class RequestPerson {
    private int personId;
    private int leaveFloor;
    private int arriveFloor;

    public RequestPerson(int personId, int leaveFloor, int arriveFloor) {
        this.personId = personId;
        this.leaveFloor = leaveFloor;
        this.arriveFloor = arriveFloor;
    }

    public int getPersonId() {
        return personId;
    }

    public int getArriveFloor() {
        return arriveFloor;
    }

    public int getLeaveFloor() {
        return leaveFloor;
    }
}
