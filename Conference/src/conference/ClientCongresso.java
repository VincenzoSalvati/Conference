package conference;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientCongresso {

    private static Scanner scan = new Scanner(System.in);
    private static int day;
    private static int session;
    private static String speaker;

    private ClientCongresso() {
    }

    static Logger logger = Logger.getLogger("global");

    public static void main(String[] args) {

        try {

            logger.info("Creating the remote object");
            Registry registry = LocateRegistry.getRegistry("localhost");
            ServerCongress stub = (ServerCongress) registry.lookup("rmi://localhost/ServerCongress");

            logger.info("Object found!");

            System.out.println("Chose registration or program: ");
            String chose = scan.next();

            if (chose.equalsIgnoreCase("registartion")) {
                do {
                    System.out.println("Select a day of the congress: (1-3)");
                    int day = Integer.parseInt(scan.next());
                } while (day > 3);

                do {
                    System.out.println("Select a session : (1-12)");
                    int session = Integer.parseInt(scan.next());
                } while (day > 12);

                System.out.println("Speaker name for the session: ");
                String speaker = scan.next();

                String registration = stub.registration();
                System.out.println("Registration: " + registration);

            } else if (chose.equalsIgnoreCase("program")) {
                String program = stub.program();
                System.out.println("Program: " + program);

            } else {
                System.out.println("Illegal expression");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}