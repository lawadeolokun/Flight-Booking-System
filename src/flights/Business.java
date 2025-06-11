package flights;
import logging.AssignmentLogger;

public class Business extends Flights{
    public Business(String origin, String destination){
        super(origin, destination);
    }

    public String getFlightsClass(){
        AssignmentLogger.logMethodEntry(this);
        AssignmentLogger.logMethodExit(this);
        return "Business Class";
    }
}
