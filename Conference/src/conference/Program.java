package conference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Program implements Serializable {

    private final Date date;
    private final ArrayList<Session> listSessions;

    public Program(Date date) {
        this.date = date;
        this.listSessions = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Session> getListSessions() {
        return listSessions;
    }

    // Add Session of a date
    @SuppressWarnings("UnusedReturnValue")
    public boolean addSession(Session Session) {
        if (listSessions.size() <= 12) {
            listSessions.add(Session);
            return true;
        } else return false;
    }

    // Add speaker for specific session
    public boolean addSpeakerForTheSession(int sessionIndex, String speaker) {
        Session s = listSessions.get(sessionIndex);
        return s.addSpeakers(speaker);
    }

}
