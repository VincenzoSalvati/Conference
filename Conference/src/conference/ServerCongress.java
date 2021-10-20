package conference;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServerCongress extends Remote {

    boolean dateSession(Date date) throws RemoteException;

    boolean registration(Date date, int session, String speaker) throws RemoteException;

    Program program(Date date) throws RemoteException;

}
