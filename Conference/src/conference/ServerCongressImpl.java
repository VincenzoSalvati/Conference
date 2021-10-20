package conference;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

public class ServerCongressImpl extends UnicastRemoteObject implements ServerCongress {
    static final Logger logger = Logger.getLogger("global");
    private static final ArrayList<Program> listProgram = new ArrayList<>();

    public ServerCongressImpl() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            // Create remote object
            logger.info("Creating the remote object");
            Registry registry = LocateRegistry.createRegistry(1099);
            ServerCongressImpl serverCongress = new ServerCongressImpl();
            // Rebinding
            logger.info("rebinding...");
            registry.rebind("rmi://localhost/ServerCongress", serverCongress);
            logger.info("...Ready!");
            // Create sessions of example
            Session session = new Session("Congress proof 1");
            session.addSpeakers("Pippo");
            session.addSpeakers("Pluto");
            session.addSpeakers("Paperino");
            session.addSpeakers("Minnie");
            session.addSpeakers("Topolino");
            Date date;
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
            dateFormat.setLenient(false);
            date = dateFormat.parse("22/08/2021");
            Program program = new Program(date);
            program.addSession(session);
            listProgram.add(program);
            session = new Session("Congress proof 2");
            session.addSpeakers("Pippo");
            session.addSpeakers("Pluto");
            session.addSpeakers("Paperino");
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
            dateFormat.setLenient(false);
            date = dateFormat.parse("25/09/2022");
            program = new Program(date);
            program.addSession(session);
            listProgram.add(program);
            session = new Session("Congress proof 3");
            session.addSpeakers("Pippo");
            session.addSpeakers("Pluto");
            session.addSpeakers("Paperino");
            session.addSpeakers("Topolino");
            program.addSession(session);
            listProgram.add(program);
            //Server ready
            System.out.println("Server ready!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return if a session is found
    @Override
    public boolean dateSession(Date date) throws RemoteException {
        for (Program p : listProgram) {
            if (p.getDate().equals(date))
                return true;
        }
        return false;
    }

    // Register a speaker to a specific session
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

    // Return the specific object Program by date
    @Override
    public Program program(Date date) throws RemoteException {
        for (Program p : listProgram) {
            if (p.getDate().equals(date))
                return p;
        }
        return null;
    }
}
