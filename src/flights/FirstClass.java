package flights;
import logging.AssignmentLogger;

public class FirstClass extends Flights{
    public FirstClass(String origin, String destination){
        super(origin, destination);
    }

    public String getFlightsClass(){
        AssignmentLogger.logMethodEntry(this);
        AssignmentLogger.logMethodExit(this);
        return "First Class";
    }
}
