package ui;

import model.Event;
import model.EventLog;

public class PrintLog {
    //Print logged events to the console
    public PrintLog(EventLog el) {
        for (Event ev : el) {
            System.out.println(ev.getDescription() + " on " + ev.getDate());
        }
    }
}
