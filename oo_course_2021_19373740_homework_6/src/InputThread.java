import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.ElevatorRequest;
import com.oocourse.elevator2.PersonRequest;
import com.oocourse.elevator2.Request;
import objects.RequestPerson;
import objects.WaitQueue;

import java.io.IOException;

public class InputThread extends Thread {
    private final WaitQueue waitQueue;
    private final String arrivePattern;
    private final ElevatorInput elevatorInput;

    public InputThread(ElevatorInput elevatorInput, WaitQueue waitQueue, String arrivePattern) {
        this.waitQueue = waitQueue;
        this.elevatorInput = elevatorInput;
        this.arrivePattern = arrivePattern;
    }

    @Override
    public void run() {
        /*TimableOutput.initStartTimestamp();*/
        while (true) {
            Request stuRequest = elevatorInput.nextRequest();
            //System.out.println(stuRequest);
            //System.out.println(waitQueue);

            if (stuRequest == null) {
                synchronized (waitQueue) {
                    waitQueue.close();
                    waitQueue.notifyAll();
                    //不同点
                    try {
                        elevatorInput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            } else {
                // a new valid request
                //System.out.println(request);
                if (stuRequest instanceof PersonRequest) {
                    synchronized (waitQueue) {
                        RequestPerson request =
                            new RequestPerson(((PersonRequest) stuRequest).getPersonId(),
                                ((PersonRequest) stuRequest).getFromFloor(),
                                ((PersonRequest) stuRequest).getToFloor());
                        waitQueue.addRequest(request);
                        waitQueue.notifyAll();
                    }
                } else if (stuRequest instanceof ElevatorRequest) {
                    Elevator elevator;
                    synchronized (waitQueue) {
                        elevator =
                            new Elevator(waitQueue, arrivePattern,
                                ((ElevatorRequest) stuRequest).getElevatorId());
                    }
                    elevator.start();
                }
            }
        }
    }
}