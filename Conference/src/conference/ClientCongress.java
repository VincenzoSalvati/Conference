package conference;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientCongress {

    static final Logger logger = Logger.getLogger("global");
    private static final Scanner scan = new Scanner(System.in);

    private ClientCongress() {
    }

    public static void main(String[] args) {

        try {

            logger.info("Creating the remote object");
            Registry registry = LocateRegistry.getRegistry("localhost");
            ServerCongress stub = (ServerCongress) registry.lookup("rmi://localhost/ServerCongress");

            logger.info("Object found!");

            String choice;
            String insertChoice;

            // Check choice
            do {
                System.out.println("Chose:\n 0.Exit\n 1.Registration\n 2.Watch Sessions\n ");
                choice = scan.next();

                if (choice.equalsIgnoreCase("1")) {
                    Date date = null;
                    boolean ok;
                    Program program;
                    String speaker;
                    int session = 0;
                    String stringDate;
                    do {
                        System.out.println("\nChose:\n 0.Back\n 1.Insert data");
                        insertChoice = scan.next();
                        if (insertChoice.equalsIgnoreCase("1")) {
                            // Check a session
                            do {
                                // Check a date
                                do {
                                    do {
                                        // Fetch data
                                        System.out.println("\nInsert:\n - date [gg/mm/yyyy] to continue\n - 0 to back");
                                        stringDate = scan.next();
                                        if (stringDate.equalsIgnoreCase("0")) break;
                                        // Convert string into an object Data
                                        try {
                                            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                                            // Set that date conversion calculations are rigorous
                                            dateFormat.setLenient(false);
                                            date = dateFormat.parse(stringDate);
                                            ok = true;
                                        } catch (ParseException e) {
                                            ok = false;
                                            System.out.println("\nRetry... date format invalid.");
                                        }
                                    } while (!ok);
                                    if (stringDate.equals("0")) break;
                                } while (!stub.dateSession(date));
                                if (stringDate.equals("0")) break;
                                // Print Sessions by a date
                                program = stub.program(date);
                                System.out.println("\nSelect a session : ");
                                int i = 0;
                                for (Session s : program.getListSessions()) {
                                    System.out.println(i + "." + s.getNameSession());
                                    i++;
                                }
                                // Select specific session
                                session = Integer.parseInt(scan.next());
                            } while ((session > program.getListSessions().size() - 1) || (session < 0));
                            if (!stringDate.equals("0")) {
                                System.out.println("\nSpeaker's name for the session: ");
                                speaker = scan.next();
                                // Check speaker
                                if (stub.registration(date, session, speaker))
                                    System.out.println("Registered correctly.");
                                else
                                    System.out.println("Registration not carried out: session full.");
                            }
                        } else if (insertChoice.equalsIgnoreCase("0")) {
                            System.out.println("\nBack\n");
                            break;
                        }
                    } while (!insertChoice.equalsIgnoreCase("0"));

                } else if (choice.equalsIgnoreCase("2")) {
                    Date date = null;
                    boolean ok;
                    Program program;
                    String stringDate;
                    do {
                        System.out.println("\nChose:\n 0.Back\n 1.Insert data");
                        insertChoice = scan.next();
                        if (insertChoice.equalsIgnoreCase("1")) {
                            // Check a date
                            do {
                                do {
                                    // Fetch data
                                    System.out.println("\nInsert:\n - date [gg/mm/yyyy] to continue\n - 0 to back");
                                    stringDate = scan.next();
                                    if (stringDate.equalsIgnoreCase("0")) break;
                                    // Convert string into an object Data
                                    try {
                                        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                                        // Set that date conversion calculations are rigorous
                                        dateFormat.setLenient(false);
                                        date = dateFormat.parse(stringDate);
                                        ok = true;
                                    } catch (ParseException e) {
                                        ok = false;
                                        System.out.println("\nRetry... date format invalid.");
                                    }
                                } while (!ok);
                                if (stringDate.equals("0")) break;
                            } while (!stub.dateSession(date));
                            if (!stringDate.equals("0")) {
                                // Print Sessions by a date
                                program = stub.program(date);
                                System.out.println("\n --- Sessions ---- ");
                                for (Session s : program.getListSessions()) {
                                    System.out.println("Name Session: " + s.getNameSession());
                                    int i = 1;
                                    for (String nameSpeaker : s.getSpeakers()) {
                                        System.out.println(i + "° speaker: " + nameSpeaker);
                                        i++;
                                    }
                                    System.out.println("\n");
                                }
                            }
                        } else if (insertChoice.equalsIgnoreCase("0")) {
                            System.out.println("\nBack\n");
                            break;
                        }
                    } while (!insertChoice.equalsIgnoreCase("0"));
                } else if (choice.equalsIgnoreCase("0")) {
                    System.out.println("\nExit\n");
                } else {
                    System.out.println("Illegal expression");
                }

            } while (!choice.equalsIgnoreCase("0"));

            System.out.println("\nClose\n");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}