package conference;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class ServerCongressImpl extends UnicastRemoteObject implements ServerCongress {

    static Logger logger = Logger.getLogger("global");
    private ArrayList<Program> listProgram = new ArrayList<>();

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
    public boolean dateSession(Date date) throws RemoteException {
        for (Program p : listProgram) {
            if (p.getDate().equals(date))
                return true;
        }
        return false;
    }

    @Override
    public boolean registration(Date date, int session, String speaker) throws RemoteException {
        int i = 0;
        for (Program p : listProgram) {
            if (p.getDate().equals(date))
                break;
            i++;
        }
        Program program = listProgram.get(i);
        listProgram.remove(i);
        boolean ok = program.addSpeakerForTheSession(session, speaker);
        listProgram.add(program);
        return ok;
    }

    @Override
    public Program program(Date date) throws RemoteException {
        for (Program p : listProgram) {
            if (p.getDate().equals(date))
                return p;
        }
        return null;
    }
}
