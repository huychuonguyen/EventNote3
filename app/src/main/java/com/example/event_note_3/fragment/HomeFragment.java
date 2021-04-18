package com.example.event_note_3.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.event_note_3.R;
import com.example.event_note_3.adapter.EventListAdapter;
import com.example.event_note_3.database.DatabaseHelper;
import com.example.event_note_3.listener.EventListAdapterListener;
import com.example.event_note_3.listener.UpdateEventListener;
import com.example.event_note_3.model.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static String TAG = "HomeFragment";

    private DatabaseHelper databaseHelper;

    private EventListAdapter adapter;
    private List<Event> events;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        databaseHelper = new DatabaseHelper(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerEvent);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new EventListAdapter(new EventListAdapterListener() {
            @Override
            public void onUpdateClicked(Event event, int position) {
                openUpdateEventDialog(event, position);
            }

            @Override
            public void onDeleteClicked(Event event, int position) {
                if(databaseHelper.deleteEvent(event)) {
                    events.remove(event);
                    adapter.notifyItemRemoved(position);
                } else {
                    Toast.makeText(requireContext(),"Can't delete event", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
        events = databaseHelper.getAllEvents();
        adapter.submitList(events);

        // hứng sự kiện search
        final EditText etSearch = view.findViewById(R.id.etSearchEventHome);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String eventName = s.toString();
                events = databaseHelper.getEventsByName(eventName);
                adapter.submitList(events);
            }
        });

        final FloatingActionButton btnAddEvent = view.findViewById(R.id.fabAddEventHomeFragment);
        btnAddEvent.setOnClickListener((v) -> {
            openAddEventDialog();
        });
    }

    private void openUpdateEventDialog(Event event,int position) {
        Fragment updateEventDialogFragment = AddUpdateEventDialogFragment.newInstance(
                event,
                new UpdateEventListener() {
                    @Override
                    public void onUpdated(Event event) {
                        // cập nhật lại item đã update trong list
                        events.set(position, event);
                        adapter.notifyItemChanged(position);
                    }

                    @Override
                    public void onAdded(Event event) {
                      //
                    }
                },
                AddUpdateEventDialogFragment.EventType.Update
        );

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(updateEventDialogFragment, AddUpdateEventDialogFragment.TAG)
                .commit();
    }

    private void openAddEventDialog() {
        Fragment updateEventDialogFragment = AddUpdateEventDialogFragment.newInstance(
                null,
                new UpdateEventListener() {
                    @Override
                    public void onUpdated(Event event) {
                    }

                    @Override
                    public void onAdded(Event event) {
                        // load lại list events
                        events = databaseHelper.getAllEvents();
                        adapter.submitList(events);
                        //adapter.notifyDataSetChanged();
                    }
                },
                AddUpdateEventDialogFragment.EventType.Add
        );

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(updateEventDialogFragment, AddUpdateEventDialogFragment.TAG)
                .commit();
    }
}