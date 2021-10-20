package conference;

import java.util.ArrayList;
import java.util.Date;

public class Sessions {

    private Date date;
    private ArrayList<Session> sessions;

    public Sessions(Date date) {
        this.date = date;
        this.sessions = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    // Add Session of a date
    public boolean addSession(Session session) {
        if (sessions.size() <= 12) {
            sessions.add(session);
            return true;
        } else return false;
    }

    // Remove Session of a date
    public boolean removeSession(String nameSession) {
        if (sessions.size() >= 1) {
            int i = 0;
            for (Session s : sessions) {
                if (s.getNameSession().equalsIgnoreCase(nameSession))
                    break;
                i++;
            }
            sessions.remove(i);
            return true;
        } else return false;
    }

    //Remove all session of a date
    public boolean removeAllSessions() {
        if (sessions.size() >= 1) {
            sessions.clear();
            return true;
        } else return false;
    }

}
