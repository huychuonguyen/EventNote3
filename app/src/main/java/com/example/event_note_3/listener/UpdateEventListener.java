package com.example.event_note_3.listener;

import com.example.event_note_3.model.Event;

import java.io.Serializable;

public interface UpdateEventListener extends Serializable {
    void onUpdated(Event event);
    void onAdded(Event event);
}
