package com.car.castel.CustomerValidation.event;

import java.util.ArrayList;
import java.util.List;


public class EventInvoker {
    private List<Event> events;

    public EventInvoker() {
        this.events = new ArrayList<>();;
    }

    public void addEvent(Event iPostEvent){
        events.add(iPostEvent);
    }

    public void invoke(){
        events.forEach(Event::execute);
    }
}
