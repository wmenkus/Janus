package com.example.janus;
import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Task implements Comparable<Task>{
    private String name, note, source, dueDate, taskID;
    private int weight;
    public FirebaseAuth mAuth;
    public FirebaseFirestore db;

    public Task(String name, String note, int weight, String dueDate, String source) {
        this.name = name;
        this.note = note;
        this.weight = weight;
        this.dueDate = dueDate;
        this.source = source;
        mAuth = FirebaseAuth.getInstance();
    }

    public void addTaskToFireStore(){
        String id = mAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("Task").document();
        Map<String, Object> task = new HashMap<>();
        task.put("taskName", name);
        task.put("taskDueDate", dueDate);
        task.put("taskNote", note);
        task.put("taskWeight", weight);
        task.put("taskSource", source);
        task.put("userID", id);
        taskID = documentReference.getId();
        documentReference.set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Success: Added Task to FireStore");
            }
        });
    }
    public void removeTaskFromFireStore(){

        db = FirebaseFirestore.getInstance();
        db.collection("Task").document(taskID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public int getPriorityColor() {
        double priority = 0;
        try {
            priority = getPriority();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int colorInt = 0;

        if(priority >= 2 * (4.0/4)) {
            colorInt = 0xFFFF0000;
        }
        else if(priority > 2 * (3.0/4)) {
            colorInt = 0xFFFF8000;
        }
        else if(priority > 2 * (2.0)/4) {
            colorInt = 0xFFFFFF00;
        }
        else if(priority > 2 * (1.0)/4) {
            colorInt = 0xFFC5FF00;
        }
        else {
            colorInt = 0xFF00FF00;
        }

        //TODO replace
        return colorInt;
    }

    public double getPriority() throws ParseException {
        Date now = new Date(System.currentTimeMillis());
        Date due = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate); //duedate - now
        double timeDiff = Math.abs(due.getTime() - now.getTime());
        return weight/(timeDiff/8.64e+7);
    }

    public int compareTo(Task task) {
        int result = 0;
        try {
            if (getPriority() > task.getPriority()) {
                result = 1;
            }
            else {
                result = -1;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            Log.d("PARSE", "Failed to parse string into date");
        }
        return result;
    }

    public String getTaskName(){
        return this.name;
    }
    public String getTaskNote(){
        return this.note;
    }
    public String getTaskDueDate(){
        return this.dueDate;
    }
    public String getTaskSource(){
        return this.source;
    }
    public String getTaskID(){
        return this.taskID;
    }
    public int getTaskWeight(){
        return this.weight;
    }
    public void setTaskID(String id){
        this.taskID = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
