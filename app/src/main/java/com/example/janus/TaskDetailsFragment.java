package com.example.janus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String taskID;
    TextView titleNameView,taskSourceView,taskDueDateView,taskNotesView ;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskDetailsFragment newInstance(String param1, String param2) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        titleNameView = (TextView) view.findViewById(R.id.taskTitleName);
        titleNameView.setText(getArguments().getString("taskName"));

        taskSourceView = (TextView) view.findViewById(R.id.taskSource);
        taskSourceView.setText(getArguments().getString("taskSource"));

        taskDueDateView = (TextView) view.findViewById(R.id.taskDueDate);
        taskDueDateView.setText(getArguments().getString("taskDueDate"));

        taskNotesView = (TextView) view.findViewById(R.id.taskNotes);
        taskNotesView.setText(getArguments().getString("taskNotes"));
        taskID = getArguments().getString("taskID");

        Button editTaskButton = (Button) view.findViewById(R.id.editTaskButton);
        Button deleteTaskButton = (Button) view.findViewById(R.id.deleteTaskButton);

        NavController navController = Navigation.findNavController(view);
        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_taskDetailsFragment_to_editTaskFragment);
            }
        });

        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) requireActivity();
                // remove
                activity.user.removeTask(taskID);
                navController.navigate(R.id.action_taskDetailsFragment_to_taskFragment);
            }
        });
        MainActivity activity = (MainActivity) requireActivity();
    }
}