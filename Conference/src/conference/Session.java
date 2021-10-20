package conference;

import java.util.ArrayList;

public class Session {

    private String nameSession;
    private ArrayList<String> speakers;

    public Session(String nameSession) {
        this.nameSession = nameSession;
        this.speakers = new ArrayList<String>();
    }

    public String getNameSession() {
        return nameSession;
    }

    public void setNameSession(String nameSession) {
        this.nameSession = nameSession;
    }

    public ArrayList<String> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ArrayList<String> speakers) {
        this.speakers = speakers;
    }

    // Add speaker into a session
    public boolean addSpeakers(String nameSpeaker) {
        if (!speakers.contains(nameSpeaker) && speakers.size() <= 5) {
            speakers.add(nameSpeaker);
            return true;
        } else return false;
    }
}
