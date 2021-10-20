package conference;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class ServerCongressImpl extends UnicastRemoteObject implements ServerCongress {

    static Logger logger = Logger.getLogger("global");

    public ServerCongressImpl() throws RemoteException {
    }

    public static void main(String args[]) {

        try {

            logger.info("Creating the remote object");
            Registry registry = LocateRegistry.createRegistry(1099);
            ServerCongressImpl serverCongress = new ServerCongressImpl();

            logger.info("rebinding...");
            registry.rebind("rmi://localhost/ServerCongress", serverCongress);

            logger.info("...Ready!");
            System.out.println("Server ready!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String program(Program program) throws RemoteException {


    }

    @Override
    public String registration(Program program) throws RemoteException {
    }
}
