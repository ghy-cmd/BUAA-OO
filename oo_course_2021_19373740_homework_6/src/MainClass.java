import com.oocourse.TimableOutput;
import com.oocourse.elevator2.ElevatorInput;
import objects.WaitQueue;

public class MainClass {
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();
        WaitQueue waitQueue = new WaitQueue();
        /*String arrivePattern = new String();*/
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        String arrivePattern = elevatorInput.getArrivingPattern();

        InputThread inputThread = new InputThread(elevatorInput, waitQueue, arrivePattern);
        inputThread.start();
        for (int i = 1; i <= 3; i++) {
            Elevator elevator = new Elevator(waitQueue, arrivePattern, String.valueOf(i));
            elevator.start();
        }
    }
}
