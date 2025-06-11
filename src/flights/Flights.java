package flights;
import logging.AssignmentLogger;

public abstract class Flights {
    protected String origin;
    protected String destination;

    public Flights(String origin, String destination){
        AssignmentLogger.logConstructor(this);
        this.origin = origin;
        this.destination = destination;
        AssignmentLogger.logMethodExit(this);
    }

    public abstract String getFlightsClass();

    public String getOrigin(){
        AssignmentLogger.logMethodEntry(this);
        return origin;
    }

    public String getDestination(){
        AssignmentLogger.logMethodEntry(this);
        return destination;
    }

    public void setDestination(String destination){
        AssignmentLogger.logMethodEntry(this);
        this.destination = destination;
        AssignmentLogger.logStaticMethodExit();
    }
}
