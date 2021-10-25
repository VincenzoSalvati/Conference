package conference;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface ServerCongress extends Remote {
    ArrayList<String> getRegisteredDates() throws RemoteException; // Return the registered dates

    boolean dateSession(Date date) throws RemoteException; // Return if a session is found

    boolean registration(Date date, int session, String speaker) throws RemoteException; // Register a speaker to a specific session

    ArrayList<String> program(Date date, boolean speaker) throws RemoteException; // Return the specific object Program by date
}
