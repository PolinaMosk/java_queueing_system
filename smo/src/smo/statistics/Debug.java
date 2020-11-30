package smo.statistics;

import smo.Buffer;
import smo.statistics.CustomEvent;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    private List<CustomEvent> calendar = new ArrayList<>();
    public void add_event(CustomEvent event) {
        calendar.add(event);
    }

    public List<CustomEvent> getCalendar() {
        return calendar;
    }
}
