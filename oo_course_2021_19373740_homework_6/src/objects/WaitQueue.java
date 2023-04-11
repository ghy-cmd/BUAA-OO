package objects;

import java.util.ArrayList;

public class WaitQueue {
    private ArrayList<RequestPerson> requests;
    private boolean isEnd;

    public WaitQueue() {
        requests = new ArrayList<>();
        isEnd = false;
    }

    public void addRequest(RequestPerson request) {
        requests.add(request);
    }

    public void clearQueue() {
        requests.clear();
    }

    public ArrayList<RequestPerson> getRequests() {
        return requests;
    }

    public void close() {
        isEnd = true;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean noWaiting() {
        return requests.isEmpty();
    }

    @Override
    public String toString() {
        return "WaitQueue{" +
            "requests=" + requests +
            ", isEnd=" + isEnd +
            '}';
    }
}
