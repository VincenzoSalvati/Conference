package conference;

import java.util.ArrayList;
import java.util.Date;

public class Program {

    private Date date;
    private ArrayList<Session> listSessions;

    public Program(Date date) {
        this.date = date;
        this.listSessions = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Session> getListSessions() {
        return listSessions;
    }

    public void setListSessions(ArrayList<Session> listSessions) {
        this.listSessions = listSessions;
    }

    // Add Session of a date
    public boolean addSession(Session Session) {
        if (listSessions.size() <= 12) {
            listSessions.add(Session);
            return true;
        } else return false;
    }

    // Remove Session of a date
    public boolean removeSession(String nameSession) {
        if (listSessions.size() >= 1) {
            int i = 0;
            for (Session s : listSessions) {
                if (s.getNameSession().equalsIgnoreCase(nameSession))
                    break;
                i++;
            }
            listSessions.remove(i);
            return true;
        } else return false;
    }

    // Remove all session of a date
    public boolean removeAllSessions() {
        if (listSessions.size() >= 1) {
            listSessions.clear();
            return true;
        } else return false;
    }

    // Add speaker for specific session
    public boolean addSpeakerForTheSession(int sessionIndex, String speaker) {
        Session s = listSessions.get(sessionIndex);
        return s.addSpeakers(speaker);
    }

}
