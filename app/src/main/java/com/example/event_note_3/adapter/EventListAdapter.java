package com.example.event_note_3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.event_note_3.R;
import com.example.event_note_3.listener.EventListAdapterListener;
import com.example.event_note_3.model.Event;

public class EventListAdapter extends ListAdapter<Event, EventListAdapter.EventHolder> {

    private final EventListAdapterListener mListener;

    public EventListAdapter(EventListAdapterListener mListener) {
        super(Event.DIFF_CALLBACK);
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_item, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

        Event event = getItem(position);
        if(event != null) holder.bind(event, this.mListener);
    }


    static class EventHolder extends RecyclerView.ViewHolder {

        private final TextView tvActivityName;
        private final TextView tvLocation;
        private final TextView tvEventDate;
        private final TextView tvAttendingTime;
        private final TextView tvReporterName;
        private final Button btnUpdateEvent;
        private final Button btnDeleteEvent;


        public EventHolder(@NonNull View itemView) {
            super(itemView);

            tvActivityName = itemView.findViewById(R.id.tvActivityName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvAttendingTime = itemView.findViewById(R.id.tvAttendingTime);
            tvReporterName = itemView.findViewById(R.id.tvReporterName);
            btnUpdateEvent = itemView.findViewById(R.id.btnUpdateEventUpdateEventFragment);
            btnDeleteEvent = itemView.findViewById(R.id.btnDeleteEvent);
        }

        public void bind(Event event, EventListAdapterListener listener) {
            tvActivityName.setText(event.getActivityName());
            tvLocation.setText(event.getLocation());
            tvEventDate.setText(event.getEventDate());
            tvAttendingTime.setText(event.getAttendingTime());
            tvReporterName.setText(event.getReporterName());

            btnUpdateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUpdateClicked(event, getAdapterPosition());
                }
            });
            btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClicked(event, getAdapterPosition());
                }
            });
        }
    }
}





