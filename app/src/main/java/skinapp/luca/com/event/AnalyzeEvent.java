package skinapp.luca.com.event;

/**
 * Created by Kazimir on 11/13/2017.
 */

public class AnalyzeEvent {
    int response = 0;

    public AnalyzeEvent(int value) {
        response = value;
    }

    public int getResponse() {
        return response;
    }
}
