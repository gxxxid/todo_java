/**
 * Class Event
 * This is the event object class
 * An event object holds the title, detail, start date, repeat cycle info of an event
 * getter and setters for event type
 */
package ToDo.src;
import java.sql.Date;
import java.sql.Timestamp;

public class Event {

    int eventIndex;
    String eventTitle;
    String eventDetail;
    boolean repeat;
    Timestamp startDate;
    Timestamp nextOccurrence;
    String repeatPeriod;
    int repeatIndex;
    public Event(){

    }
    public Event(int eventIndex, String eventTitle, String eventDetail, boolean repeat, Timestamp startDate,
                 Timestamp nextOccurrence,String repeatPeriod, int repeatIndex){
        this.eventIndex = eventIndex;
        this.eventTitle = eventTitle;
        this.eventDetail = eventDetail;
        this.repeat = repeat;
        this.startDate = startDate;
        this.nextOccurrence = nextOccurrence;
        this.repeatPeriod = repeatPeriod;
        this.repeatIndex  = repeatIndex;

    }
    public String getEventTitle(){
        return eventTitle;
    }
    public String getEventDetail(){
        return eventDetail;
    }
    public boolean getRepeat(){
        return repeat;
    }
    public Timestamp getStartDate(){
        return startDate;
    }
    public Timestamp getNextOccurrence(){return nextOccurrence;}
    public String getRepeatPeriod(){
        return repeatPeriod;
    }
    public int getRepeatIndex(){
        return repeatIndex;
    }
    public void setEventIndex(int eventIndex){
        this.eventIndex = eventIndex;
    }
    public void setEventTitle(String eventTitle){
        this.eventTitle = eventTitle;
    }
    public void setEventDetail(String eventDetail){
        this.eventDetail = eventDetail;
    }
    public void setStartDate(Timestamp date){
        startDate = date;
    }
    public void setNextOccurrence(Timestamp nextOccurrence){this.nextOccurrence = nextOccurrence;}
    public void setRepeat(boolean repeat){
        this.repeat = repeat;
    }
    public void setRepeatPeriod(String repeatPeriod){
        this.repeatPeriod = repeatPeriod;
    }
    public void setRepeatIndex(int repeatIndex){
        this.repeatIndex = repeatIndex;
    }
}//end Event
