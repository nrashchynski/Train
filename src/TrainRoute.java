import java.io.PrintStream;
import java.io.Serializable;
import java.io.*;
import java.util.*;

public class TrainRoute implements Serializable {
    private static final long serialVersionUTD = 1L;

    private String departurePoint;
    static final String P_departurePoint = "departure point";
    private String destinationPoint;
    static final String P_destinationPoint = "destination point";
    private String trainNumber;
    static final String P_trainNumber = "train number";
    private String departureTime;
    static final String P_departureTime = "departure time";
    private int totalSeats;
    static final String P_totalSeats = "total seats";
    private int coupeSeats;
    static final String P_coupeSeats = "coupe seats";
    private int platzkartSeats;
    static final String P_platzkartSeats = "platzkart seats";
    private int luxurySeats;
    static final String P_luxurySeats = "luxury seats";

    public TrainRoute() {};

    public TrainRoute(String departurePoint, String destinationPoint, String trainNumber, String departureTime, int totalSeats, int coupeSeats, int platzkartSeats, int luxurySeats) {
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.trainNumber = trainNumber;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.coupeSeats = coupeSeats;
        this.platzkartSeats = platzkartSeats;
        this.luxurySeats = luxurySeats;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }
    public String getDestinationPoint() {
        return destinationPoint;
    }
    public String getTrainNumber() {
        return trainNumber;
    }


    public static boolean nextRead(Scanner fin, PrintStream out) {
        return nextRead(P_departurePoint, fin, out);
    }
    static boolean nextRead(final String prompt, Scanner fin, PrintStream out){
        out.print(prompt);
        out.print(": ");
        return fin.hasNextLine();
    }

    public static TrainRoute read(Scanner fin, PrintStream out) throws IOException, NumberFormatException{
        String str;
        TrainRoute route = new TrainRoute();
        route.departurePoint = fin.nextLine().trim();
        if (!nextRead(P_destinationPoint, fin, out)) {
            return null;
        }
        route.destinationPoint = fin.nextLine();
        if (!nextRead(P_trainNumber, fin, out)) {
            return null;
        }
        route.trainNumber = fin.nextLine();
        if (!nextRead(P_departureTime, fin, out)) {
            return null;
        }
        route.departureTime = fin.nextLine();
        if (!nextRead(P_totalSeats, fin, out)) {
            return null;
        }
        str = fin.nextLine();
        route.totalSeats = Integer.parseInt(str);
        if (!nextRead(P_coupeSeats, fin, out)) {
            return null;
        }
        str = fin.nextLine();
        route.coupeSeats = Integer.parseInt(str);
        if (!nextRead(P_platzkartSeats, fin, out)) {
            return null;
        }
        str = fin.nextLine();
        route.platzkartSeats = Integer.parseInt(str);
        if (!nextRead(P_luxurySeats, fin, out)) {
            return null;
        }
        str = fin.nextLine();
        route.luxurySeats = Integer.parseInt(str);

        return route;
    }


    @Override
    public String toString() {
        return "TrainRoute: " +
                "departure Point = '" + departurePoint + '\'' +
                ", destination Point = '" + destinationPoint + '\'' +
                ", train Number = " + trainNumber +
                ", departure Time = " + departureTime +
                ", total Seats = " + totalSeats +
                ", coupe Seats = " + coupeSeats +
                ", platzkart Seats = " + platzkartSeats +
                ", luxury Seats = " + luxurySeats;
    }
}
