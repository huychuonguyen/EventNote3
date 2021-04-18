package com.example.event_note_3.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.io.Serializable;

public class Event implements Serializable {
    private int eventId;
    private String activityName;
    private String location;
    private String eventDate;
    private String attendingTime;
    private String reporterName;

    public Event(int eventId, String activityName, String location, String eventDate, String attendingTime, String reporterName) {
        this.eventId = eventId;
        this.activityName = activityName;
        this.location = location;
        this.eventDate = eventDate;
        this.attendingTime = attendingTime;
        this.reporterName = reporterName;
    }

    public Event( String activityName, String location, String eventDate, String attendingTime, String reporterName) {
        this.activityName = activityName;
        this.location = location;
        this.eventDate = eventDate;
        this.attendingTime = attendingTime;
        this.reporterName = reporterName;
    }


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getAttendingTime() {
        return attendingTime;
    }

    public void setAttendingTime(String attendingTime) {
        this.attendingTime = attendingTime;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }


    // dùng cho list adapter
    public static DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {

        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.eventId == newItem.eventId;
        }

    };
}
