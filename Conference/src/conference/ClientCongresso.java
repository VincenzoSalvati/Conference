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

    static Logger logger = Logger.getLogger("global");
    private static Scanner scan = new Scanner(System.in);
    private static int day;
    private static int session;
    private static String speaker;

    private ClientCongresso() {
    }

    public static void main(String[] args) {

        try {

            logger.info("Creating the remote object");
            Registry registry = LocateRegistry.getRegistry("localhost");
            ServerCongress stub = (ServerCongress) registry.lookup("rmi://localhost/ServerCongress");

            logger.info("Object found!");

            String choice;
            do {
                System.out.println("Chose:\n 0.Exit\n 1.Registration\n 2.Watch Sessions\n ");
                choice = scan.next();

                if (choice.equalsIgnoreCase("1")) {
                    Date date = null;
                    boolean ok = true;
                    Program program;
                    String speaker;
                    // Check a speaker
                    do {
                        // Check a session
                        do {
                            // Check a date
                            do {
                                do {
                                    // Fetch data
                                    System.out.println("Insert: \n - date [gg/mm/yyyy]\n - 0 to exit  ");
                                    choice = scan.nextLine();
                                    // Convert string into an object Data
                                    try {
                                        DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                                        // Set that date conversion calculations are rigorous
                                        formatoData.setLenient(false);
                                        date = formatoData.parse(choice);
                                    } catch (ParseException e) {
                                        ok = false;
                                        System.out.println("\nRiprova... formato data non valido.\n");
                                    }
                                } while (ok);
                            } while (stub.dateSession(date));

                            // Print Sessions by a date
                            program = stub.program(date);
                            int i = 0;
                            for (Session s : program.getListSessions()) {
                                System.out.println(i + "." + s.getNameSession());
                            }
                            // Select specific session
                            int session;
                            System.out.println("Select a session : (1-" + program.getListSessions().size() + ")");
                            session = Integer.parseInt(scan.next());
                        } while (session <= program.getListSessions().size());

                        System.out.println("Speaker name for the session: ");
                        speaker = scan.next();
                    } while (choice.equals("0"));
                    if (stub.registration(date, session - 1, speaker))
                        System.out.println("Registered correctly.");
                    else
                        System.out.println("Registration not carried out: session full.");

                } else if (choice.equalsIgnoreCase("2")) {
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

            } while (choice.equalsIgnoreCase("3"));

            System.out.println("\nClose\n");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}