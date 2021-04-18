package com.example.event_note_3.listener;

import com.example.event_note_3.model.Event;

public interface EventListAdapterListener {
    void onUpdateClicked(Event event, int position);
    void onDeleteClicked(Event event, int position);
}