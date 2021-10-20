package conference;

import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Serializable {

    private final String nameSession;
    private final ArrayList<String> speakers;

    public Session(String nameSession) {
        this.nameSession = nameSession;
        this.speakers = new ArrayList<>();
    }

    public String getNameSession() {
        return nameSession;
    }


    public ArrayList<String> getSpeakers() {
        return speakers;
    }

    // Add speaker into a session
    public boolean addSpeakers(String nameSpeaker) {
        //if (!speakers.contains(nameSpeaker) && speakers.size() <= 5) {
        if (speakers.size() < 5) {
            speakers.add(nameSpeaker);
            return true;
        } else return false;
    }
}
