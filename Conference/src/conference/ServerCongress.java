package conference;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerCongress extends Remote {

    String registration(Program program) throws RemoteException;

    String program(Program program) throws RemoteException;

}
