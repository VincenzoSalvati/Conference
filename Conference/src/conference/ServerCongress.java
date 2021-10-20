package conference;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServerCongress extends Remote {

    String registration(Program program) throws RemoteException;

    Program program(Date date) throws RemoteException;

}
