import com.oocourse.TimableOutput;
import objects.RequestPerson;
import objects.WaitQueue;

import java.util.ArrayList;

public class Elevator extends Thread {
    private final WaitQueue waitQueue;
    private final int max = 6;
    private boolean flag = true;
    private int floor = 1;
    private int type;
    private int num = 0;
    private String elevatorId;
    private ArrayList<RequestPerson> requests = new ArrayList<>();

    public Elevator(WaitQueue waitQueue, String arrivePattern, String elevatorId) {
        this.waitQueue = waitQueue;
        if (arrivePattern.equals("Random")) {
            type = 0;
        } else if (arrivePattern.equals("Morning")) {
            type = 1;
        } else {
            type = 2;
        }
        this.elevatorId = elevatorId;
    }

    @Override
    public void run() {
        /*TimableOutput.initStartTimestamp();*/
        while (true) {
            synchronized (waitQueue) {
                if (waitQueue.noWaiting() && waitQueue.isEnd()) {
                    return;
                }
                if (waitQueue.noWaiting()) {
                    try {
                        waitQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            switch (type) {
                case 0:
                    dealRandomRequest();
                    break;
                case 1:
                    dealMoriningRequest();
                    break;
                case 2:
                    dealNightRequest();
                    break;
                default:
                    break;
            }
        }
    }

    public void dealRandomRequest() {
        ArrayList<RequestPerson> temp = waitQueue.getRequests();
        while (true) {
            if (requests.isEmpty()) {
                synchronized (waitQueue) {
                    if (temp.isEmpty()) {
                        break;
                    } else {
                        RequestPerson request =
                            new RequestPerson(temp.get(0).getPersonId(),
                                temp.get(0).getLeaveFloor(),
                                temp.get(0).getArriveFloor());
                        requests.add(request);
                        temp.remove(0);
                    }
                }
                searchMain(temp);
            }
            setFlag();
            while (requests.size() != 0) {
                if (flag) {
                    upFloor();
                    Boolean open;
                    synchronized (waitQueue) {
                        open = judgeOpenUp(temp);
                    }
                    if (open) {
                        openAndOut();
                        synchronized (waitQueue) {
                            upIn(temp);
                        }
                        TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                    }
                } else {
                    downFloor();
                    Boolean open;
                    synchronized (waitQueue) {
                        open = judgeOpenDown(temp);
                    }
                    if (open) {
                        openAndOut();
                        synchronized (waitQueue) {
                            downIn(temp);
                        }
                        TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                    }
                }
            }
            synchronized (waitQueue) {
                if (temp.isEmpty()) {
                    break;
                }
            }
        }
    }

    public void dealMoriningRequest() {
        ArrayList<RequestPerson> temp = waitQueue.getRequests();
        while (true) {
            if (requests.isEmpty()) {
                synchronized (waitQueue) {
                    if (temp.isEmpty()) {
                        break;
                    } else {
                        RequestPerson request =
                            new RequestPerson(temp.get(0).getPersonId(),
                                temp.get(0).getLeaveFloor(),
                                temp.get(0).getArriveFloor());
                        requests.add(request);
                        temp.remove(0);
                    }
                }
                searchMainMorning(temp);
            }
            setFlag();
            while (requests.size() != 0) {
                if (flag) {
                    upFloor();
                    Boolean open;
                    synchronized (waitQueue) {
                        open = judgeOpenUp(temp);
                    }
                    if (open) {
                        openAndOut();
                        synchronized (waitQueue) {
                            upIn(temp);
                        }
                        TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                    }
                } else {
                    downFloor();
                    Boolean open;
                    synchronized (waitQueue) {
                        open = judgeOpenDown(temp);
                    }
                    if (open) {
                        openAndOut();
                        synchronized (waitQueue) {
                            downIn(temp);
                        }
                        TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                    }
                }
            }
            synchronized (waitQueue) {
                if (temp.isEmpty()) {
                    break;
                }
            }
        }
    }

    public void dealNightRequest() {
        ArrayList<RequestPerson> temp = waitQueue.getRequests();
        while (true) {
            if (requests.isEmpty()) {
                synchronized (waitQueue) {
                    if (temp.isEmpty()) {
                        break;
                    } else {
                        nightF(temp);
                    }
                }
                searchMain(temp);
            }
            setFlag();
            while (requests.size() != 0) {
                if (flag) {
                    upFloor();
                    Boolean open;
                    synchronized (waitQueue) {
                        open = judgeOpenUp(temp);
                    }
                    if (open) {
                        openAndOut();
                        synchronized (waitQueue) {
                            upIn(temp);
                        }
                        TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                    }
                } else {
                    downFloor();
                    Boolean open;
                    synchronized (waitQueue) {
                        open = judgeOpenDown(temp);
                    }
                    if (open) {
                        openAndOut();
                        synchronized (waitQueue) {
                            downIn(temp);
                        }
                        TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                    }
                }
            }
            synchronized (waitQueue) {
                if (temp.isEmpty()) {
                    break;
                }
            }
        }
    }

    public void setFlag() {
        if (requests.get(0).getArriveFloor() > floor) {
            flag = true;
        } else {
            flag = false;
        }
    }

    public void upFloor() {
        floor++;
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimableOutput.println(String.format("ARRIVE-" + floor + "-" + elevatorId));
    }

    public void downFloor() {
        floor--;
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimableOutput.println(String.format("ARRIVE-" + floor + "-" + elevatorId));
    }

    public Boolean judgeOpenUp(ArrayList<RequestPerson> temp) {
        Boolean open = false;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getArriveFloor() == floor) {
                open = true;
                break;
            }
        }
        synchronized (waitQueue) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getLeaveFloor() == floor && requests.size() < 6 &&
                    temp.get(i).getArriveFloor() > floor) {
                    open = true;
                    break;
                }
            }
        }
        return open;
    }

    public Boolean judgeOpenDown(ArrayList<RequestPerson> temp) {
        Boolean open = false;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getArriveFloor() == floor) {
                open = true;
                break;
            }
        }
        synchronized (waitQueue) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getLeaveFloor() == floor && requests.size() < 6 &&
                    temp.get(i).getArriveFloor() < floor) {
                    open = true;
                    break;
                }
            }
        }
        return open;
    }

    public void openAndOut() {
        TimableOutput.println(String.format("OPEN-" + floor + "-" + elevatorId));
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getArriveFloor() == floor) {
                TimableOutput.println(String.format(
                    "OUT-" + requests.get(i).getPersonId() + "-" + floor + "-" +
                        elevatorId));
                requests.remove(i);
                i--;
            }
        }
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void upIn(ArrayList<RequestPerson> temp) {
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getLeaveFloor() == floor && requests.size() < 6 &&
                temp.get(i).getArriveFloor() > floor) {
                RequestPerson request1 =
                    new RequestPerson(temp.get(i).getPersonId(),
                        temp.get(i).getLeaveFloor(),
                        temp.get(i).getArriveFloor());
                requests.add(request1);
                TimableOutput.println(
                    String
                        .format(
                            "IN-" + request1.getPersonId() + "-" + floor + "-" +
                                elevatorId));
                temp.remove(i);
                i--;
            }
        }
    }

    public synchronized void downIn(ArrayList<RequestPerson> temp) {
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getLeaveFloor() == floor && requests.size() < 6 &&
                temp.get(i).getArriveFloor() < floor) {
                RequestPerson request1 =
                    new RequestPerson(temp.get(i).getPersonId(),
                        temp.get(i).getLeaveFloor(),
                        temp.get(i).getArriveFloor());
                requests.add(request1);
                TimableOutput.println(
                    String
                        .format(
                            "IN-" + request1.getPersonId() + "-" + floor + "-" +
                                elevatorId));
                temp.remove(i);
                i--;
            }
        }
    }

    public synchronized void fIn(ArrayList<RequestPerson> temp) {
        Boolean ahh =
            requests.get(0).getArriveFloor() > requests.get(0).getLeaveFloor();
        for (int i = 0; i < temp.size(); i++) {
            Boolean ass =
                temp.get(i).getArriveFloor() > temp.get(i).getLeaveFloor();
            if (temp.get(i).getLeaveFloor() == floor && requests.size() < 6 &&
                ahh.equals(ass)) {
                RequestPerson request1 =
                    new RequestPerson(temp.get(i).getPersonId(),
                        temp.get(i).getLeaveFloor(),
                        temp.get(i).getArriveFloor());
                requests.add(request1);
                TimableOutput.println(
                    String
                        .format(
                            "IN-" + request1.getPersonId() + "-" + floor + "-" +
                                elevatorId));
                temp.remove(i);
                i--;
            }
        }
    }

    public void fOpen() {
        TimableOutput.println(String.format("OPEN-" + floor + "-" + elevatorId));
        TimableOutput.println(
            String.format(
                "IN-" + requests.get(0).getPersonId() + "-" + floor + "-" +
                    elevatorId));
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void searchMain(ArrayList<RequestPerson> temp) {
        while (true) {
            if (floor < requests.get(0).getLeaveFloor()) {
                upFloor();
                flag = true;
            } else if (floor > requests.get(0).getLeaveFloor()) {
                downFloor();
                flag = false;
            } else {
                fOpen();
                synchronized (waitQueue) {
                    fIn(temp);
                }
                TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                break;
            }
        }
    }

    public void searchMainMorning(ArrayList<RequestPerson> temp) {
        while (true) {
            if (floor < requests.get(0).getLeaveFloor()) {
                upFloor();
                flag = true;
            } else if (floor > requests.get(0).getLeaveFloor()) {
                downFloor();
                flag = false;
            } else {
                fOpen();
                try {
                    Thread.sleep(1600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (waitQueue) {
                    fIn(temp);
                }
                TimableOutput.println(String.format("CLOSE-" + floor + "-" + elevatorId));
                break;
            }
        }
    }

    public synchronized void nightF(ArrayList<RequestPerson> temp) {
        int m = 0;
        int lab = 0;
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getLeaveFloor() > m) {
                m = temp.get(i).getLeaveFloor();
                lab = i;
            }
        }
        RequestPerson request =
            new RequestPerson(temp.get(lab).getPersonId(),
                temp.get(lab).getLeaveFloor(),
                temp.get(lab).getArriveFloor());
        requests.add(request);
        temp.remove(lab);
    }
}
