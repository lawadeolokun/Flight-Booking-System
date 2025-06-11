package flights;
import logging.AssignmentLogger;

public class Coach extends Flights{
    public Coach(String origin, String destination){
        super(origin, destination);
    }

    public String getFlightsClass(){
        AssignmentLogger.logMethodEntry(this);
        AssignmentLogger.logMethodExit(this);
        return "Coach Class";
    }
}
