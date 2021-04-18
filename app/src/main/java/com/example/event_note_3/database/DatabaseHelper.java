package com.example.event_note_3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.event_note_3.model.Event;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";

    private static final String TABLE_NAME = "event_table";
    private static final String COLUMN_EVENT_ID = "ID";
    private static final String COLUMN_ACTIVITY_NAME = "activity_name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_EVENT_DATE = "event_date";
    private static final String COLUMN_ATTENDING_TIME = "attending_time";
    private static final String COLUMN_REPORTER_NAME = "reporter_name";

   public DatabaseHelper(Context context) {
       super(context, TABLE_NAME, null,1);
   }


    @Override
    public void onCreate(SQLiteDatabase db) {
       String createTableStatement = "CREATE TABLE " + TABLE_NAME +
               "(" +
               COLUMN_EVENT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
               COLUMN_ACTIVITY_NAME + " TEXT," +
               COLUMN_LOCATION + " TEXT," +
               COLUMN_EVENT_DATE + " TEXT," +
               COLUMN_ATTENDING_TIME + " TEXT," +
               COLUMN_REPORTER_NAME + " TEXT" +
               ")";

       db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ACTIVITY_NAME, event.getActivityName());
        contentValues.put(COLUMN_LOCATION, event.getLocation());
        contentValues.put(COLUMN_EVENT_DATE, event.getEventDate());
        contentValues.put(COLUMN_ATTENDING_TIME, event.getAttendingTime());
        contentValues.put(COLUMN_REPORTER_NAME, event.getReporterName());

        long results = db.insert(TABLE_NAME, null, contentValues);

        return results != -1;
    }

    public Boolean updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ACTIVITY_NAME, event.getActivityName());
        contentValues.put(COLUMN_LOCATION, event.getLocation());
        contentValues.put(COLUMN_EVENT_DATE, event.getEventDate());
        contentValues.put(COLUMN_ATTENDING_TIME, event.getAttendingTime());
        contentValues.put(COLUMN_REPORTER_NAME, event.getReporterName());

        int results = db.update(TABLE_NAME, contentValues, COLUMN_EVENT_ID + "=" + event.getEventId(), null);

        return results != -1;
    }

    public Boolean deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, COLUMN_EVENT_ID + "=" + event.getEventId(), null) > 0;
    }

    public List<Event> getEventsByName(String eventName) {
        List<Event> returnList = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {
                        COLUMN_EVENT_ID ,
                        COLUMN_ACTIVITY_NAME,
                        COLUMN_LOCATION,
                        COLUMN_EVENT_DATE ,
                        COLUMN_ATTENDING_TIME,
                        COLUMN_REPORTER_NAME
                },
                COLUMN_ACTIVITY_NAME + " LIKE ?", new String[] {"%" + eventName + "%"},
                null, null, null);

        if(cursor.moveToFirst()) {
            do {
                int eventId = cursor.getInt(0);
                String activityName = cursor.getString(1);
                String location = cursor.getString(2);
                String eventDate = cursor.getString(3);
                String attendingTime = cursor.getString(4);
                String reporterName = cursor.getString(5);

                Event event = new Event(
                        eventId,
                        activityName,
                        location,
                        eventDate,
                        attendingTime,
                        reporterName
                );

                returnList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return returnList;
    }

   public List<Event> getAllEvents() {
       List<Event> returnList = new ArrayList<>();

       String query = "SELECT * FROM " + TABLE_NAME;

       SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = db.rawQuery(query, null);

       if(cursor.moveToFirst()) {
           do {
               int eventId = cursor.getInt(0);
               String activityName = cursor.getString(1);
               String location = cursor.getString(2);
               String eventDate = cursor.getString(3);
               String attendingTime = cursor.getString(4);
               String reporterName = cursor.getString(5);

               Event event = new Event(
                       eventId,
                       activityName,
                       location,
                       eventDate,
                       attendingTime,
                       reporterName
               );

               returnList.add(event);
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();

       return returnList;
   }
}
