import java.util.ArrayList;

public class Scheduler extends Thread {
    private final WaitQueue waitQueue;
    private final ArrayList<ProcessingQueue> processingQueues;

    public Scheduler(WaitQueue waitQueue,
                     ArrayList<ProcessingQueue> processingQueues) {
        this.waitQueue = waitQueue;
        this.processingQueues = processingQueues;
    }

    @Override
    public void run() {
        ArrayList<Request> temp = new ArrayList<>();
        while (true) {
            synchronized (waitQueue) {
                if (waitQueue.isEnd() && waitQueue.noWaiting()) {
                    System.out.println("Schedule over");
                    for (ProcessingQueue processingQueue : processingQueues) {
                        synchronized (processingQueue) {
                            processingQueue.notifyAll();
                        }
                    }
                    return;
                }
                if (waitQueue.noWaiting()) {
                    try {
                        waitQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    temp.addAll(waitQueue.getRequests());
                    for (int i = 0; i < temp.size(); i++) {
                        Request request = temp.get(i);
                        if (request.getDestination().equals("Beijing")) {
                            synchronized (processingQueues.get(0)) {
                                processingQueues.get(0).notifyAll();
                                processingQueues.get(0).addRequest(request);
                            }
                        } else if (request.getDestination().equals("Domestic")) {
                            synchronized (processingQueues.get(1)) {
                                processingQueues.get(1).notifyAll();
                                processingQueues.get(1).addRequest(request);
                            }
                        } else {
                            synchronized (processingQueues.get(2)) {
                                processingQueues.get(2).notify();
                                processingQueues.get(2).addRequest(request);
                            }
                        }
                        temp.remove(request);
                        i--;
                    }
                    waitQueue.clearQueue();
                }
            }
        }
    }
}


