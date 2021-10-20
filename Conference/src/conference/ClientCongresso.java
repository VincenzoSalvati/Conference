package conference;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
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

            String chose;
            do {
                System.out.println("Chose\n 1.Registration\n 2.Watch Sessions\n 3.exit: ");
                chose = scan.next();

                if (chose.equalsIgnoreCase("1")) {
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

                } else if (chose.equalsIgnoreCase("2")) {
                    Date date = null;
                    boolean ok = true;
                    do {
                        String s;
                        // Fetch data
                        System.out.println("Inserisci la data [gg/mm/yyyy]: ");
                        s = scan.nextLine();
                        // Convert string into an object Data
                        try {
                            DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                            // Set that date conversion calculations are rigorous
                            formatoData.setLenient(false);
                            date = formatoData.parse(s);
                        } catch (ParseException e) {
                            ok = false;
                            System.out.println("\nRiprova... formato data non valido.\n");
                        }
                    } while (ok);
                    Program program = stub.program(date);
                    for (Session s : program.getListSessions()) {
                        System.out.println("Name Session: " + s.getNameSession());
                        int i = 1;
                        for (String nameSpeaker : s.getSpeakers()) {
                            System.out.println(i + "° speaker: " + nameSpeaker);
                            i++;
                        }
                    }

                } else {
                    System.out.println("Illegal expression");
                }

            } while (chose.equalsIgnoreCase("3"));

            System.out.println("\nClose\n");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}