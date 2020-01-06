package fr.arolla.karma;

import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID event_id;
    private Number id;
    private String label;
    private Date begin_date;
    private Date end_date;
    private Number price;
    private Number duration;

    public UUID getEventId() {
        return event_id;
    }
    public Number getId() { return id; }
    public String getLabel() { return label; }
    public Date getBeginDate() { return begin_date; }
    public Date getEndDate() { return end_date; }
    public Number getPrice() { return price; }
    public Number getDuration() { return duration; }
}
