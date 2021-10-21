package conference;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Logger;

@SuppressWarnings("DuplicatedCode")
public class ClientCongress {
    static final Logger logger = Logger.getLogger("global");
    private static final Scanner scan = new Scanner(System.in);

    private ClientCongress() {
    }

    public static void main(String[] args) {
        try {
            // Create remote object
            logger.info("Creating the remote object");
            Registry registry = LocateRegistry.getRegistry("localhost");
            ServerCongress stub = (ServerCongress) registry.lookup("rmi://localhost/ServerCongress");
            logger.info("Object found!");
            // Initialize variables
            String choice, secondChoice, speaker, stringDate;
            Date date = null;
            boolean ok;
            ArrayList<String> arrayListOfSessions;
            int numSessions = -1;
            int chosenSession = -1;
            // Fetch choice
            do {
                System.out.println("Chose:\n -1.Exit\n  0.Registration\n  1.Watch Sessions");
                choice = scan.next();
                // Verify the choice
                if (choice.equalsIgnoreCase("-1")) { //Exit
                    System.out.println("\nExit\n");
                } else if (choice.equalsIgnoreCase("0")) { // Registration
                    // Fetch second choice
                    do {
                        System.out.println("\nChose:\n -1.Back\n  0.Insert a date to have a registration");
                        secondChoice = scan.next();
                        if (secondChoice.equalsIgnoreCase("0")) { // Insert a date
                            do { // Check a session
                                do { // Check a date
                                    do { // Fetch date
                                        System.out.println("\nInsert:\n - date [gg/mm/yyyy] (or -1 to exit)):");
                                        stringDate = scan.next();
                                        if (stringDate.equalsIgnoreCase("-1")) // Go away from the while if it fetches -1
                                            break;
                                        try {
                                            // Convert string into an object Date
                                            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                                            // Set that date conversion calculations are rigorous
                                            dateFormat.setLenient(false);
                                            date = dateFormat.parse(stringDate);
                                            ok = true;
                                        } catch (ParseException e) {
                                            ok = false;
                                            System.out.println("\nRetry... date format invalid.");
                                        }
                                    } while (!ok); // Repeat until the date is valid
                                    if (stringDate.equalsIgnoreCase("-1")) // Go away from the while if it fetches -1
                                        break;
                                } while (!stub.dateSession(date)); // Repeat until the date is not present into the server
                                if (stringDate.equalsIgnoreCase("-1")) // Go away from the while if it fetches -1
                                    break;
                                // Print sessions by a date
                                System.out.println("\nSelect a session (or -1 to exit): ");
                                // Fetch ArrayList of the sessions
                                arrayListOfSessions = stub.program(date, false);
                                // Check if the number of sessions is fetched
                                boolean numSessionsChecked = false;
                                for (String s : arrayListOfSessions) {
                                    if (s.contains("Num_Session:") && !numSessionsChecked) {
                                        numSessions = Integer.parseInt(s.substring(12));
                                        numSessionsChecked = true;
                                    } else
                                        System.out.println(s);
                                }
                                // Select specific session
                                try {
                                    chosenSession = Integer.parseInt(scan.next());
                                } catch (NumberFormatException e) { // Check the correct Integer
                                    chosenSession = -2;
                                }
                                if (chosenSession == -1) // Go away from the while if it fetches -1
                                    break;
                            } while ((chosenSession > numSessions) || (chosenSession < 0)); // Repeat until the selected session is not correct
                            // Add speaker to a specific session
                            if (!stringDate.equalsIgnoreCase("-1") && chosenSession != -1) {
                                System.out.println("\nInsert speaker's name for the session (or -1 to exit):");
                                speaker = scan.next();
                                if (!speaker.equalsIgnoreCase("-1")) {
                                    // Check the success of the speaker addition
                                    if (stub.registration(date, chosenSession, speaker))
                                        System.out.println("Registered correctly.");
                                    else
                                        System.out.println("Registration not carried out: session full.");
                                }
                            }
                        } else if (secondChoice.equalsIgnoreCase("-1")) { // Go away from the while if it fetches -1
                            System.out.println("\nBack\n");
                            break;
                        }
                    } while (!secondChoice.equalsIgnoreCase("-1")); // Stop the addition of the speaker if it fetches -1
                } else if (choice.equalsIgnoreCase("1")) { // Watch Session
                    // Fetch second choice
                    do {
                        System.out.println("\nChose:\n -1.Back\n  0.Insert a date to analyze specific session");
                        secondChoice = scan.next();
                        if (secondChoice.equalsIgnoreCase("0")) { // Insert a date
                            do { // Check a date
                                do { // Fetch date
                                    System.out.println("\nInsert:\n - date [gg/mm/yyyy] (or -1 to exit)):");
                                    stringDate = scan.next();
                                    if (stringDate.equalsIgnoreCase("-1")) // Go away from the while if it fetches -1
                                        break;
                                    try {
                                        // Convert string into an object Date
                                        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                                        // Set that date conversion calculations are rigorous
                                        dateFormat.setLenient(false);
                                        date = dateFormat.parse(stringDate);
                                        ok = true;
                                    } catch (ParseException e) {
                                        ok = false;
                                        System.out.println("\nRetry... date format invalid.");
                                    }
                                } while (!ok); // Repeat until the date is valid
                                if (stringDate.equalsIgnoreCase("-1")) // Go away from the while if it fetches -1
                                    break;
                            } while (!stub.dateSession(date)); // Repeat until the date is not present into the server
                            if (!stringDate.equalsIgnoreCase("-1")) { // Go away from the while if it fetches -1
                                // Print sessions and their speakers by a date
                                System.out.println("\n--- Sessions ---");
                                // Fetch ArrayList of the sessions
                                arrayListOfSessions = stub.program(date, true);
                                for (String s : arrayListOfSessions)
                                    System.out.println(s);
                            }
                        } else if (secondChoice.equalsIgnoreCase("-1")) {
                            System.out.println("\nBack\n");
                            break;
                        }
                    } while (!secondChoice.equalsIgnoreCase("-1")); // Stop the addition of the speaker if it fetches -1
                } else {  // Choice not allowed
                    System.out.println("\nIllegal expression\n");
                }
            } while (!choice.equalsIgnoreCase("-1")); // Repeat until the choice is not -1
            System.out.println("Close");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}