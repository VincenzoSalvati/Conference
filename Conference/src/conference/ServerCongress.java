package conference;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface ServerCongress extends Remote {
    boolean dateSession(Date date) throws RemoteException; // Return if a session is found

    boolean registration(Date date, int session, String speaker) throws RemoteException; // Register a speaker to a specific session

    Program program(Date date) throws RemoteException; // Return the specific object Program by date
}
