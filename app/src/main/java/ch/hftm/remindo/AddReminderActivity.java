package ch.hftm.remindo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        // ------ Datepicker Logic
        Button mPickDateButton = findViewById(R.id.reminder_pick_date_btn);
        TextView reminderDate = findViewById(R.id.reminder_selected_date_data);

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> reminderDate.setText(materialDatePicker.getHeaderText()));

        // ---- Save Data

        Button saveBtn = findViewById(R.id.reminder_save_changes_btn);
        saveBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userId = FirebaseAuth.getInstance().getUid();

                        String reminderTitle = ((TextView)findViewById(R.id.edit_reminder_title)).getText().toString();
                        String reminderNotes = ((TextView)findViewById(R.id.reminder_notes)).getText().toString();
                        String reminderDate =  ((TextView)findViewById(R.id.reminder_selected_date_data)).getText().toString();


                        savedataother(reminderTitle, reminderNotes, reminderDate);
                    }
                }
        );


    }


    private void savedataother(String reminderTitle, String reminderNotes, String reminderDate){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://remindo-hftm-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference databaseReference = firebaseDatabase.getReference("ReminderInfo");

        String userId = FirebaseAuth.getInstance().getUid();

        Reminder reminder = new Reminder(userId
                , reminderTitle, reminderNotes, reminderDate, Timestamp.now());
//        Reminder reminder = new Reminder(userId
//                , reminderTitle.getText().toString(), reminderNotes.getText().toString(), reminderDate.getText().toString(), Timestamp.now());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.setValue(reminder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utility.showToast(AddReminderActivity.this, "Failed to add Reminder");
            }
        });

    }

//    private void saveData(){
//        TextView reminderTitle = findViewById(R.id.edit_reminder_title);
//        TextView reminderNotes = findViewById(R.id.reminder_notes);
//        TextView reminderDate = findViewById(R.id.reminder_selected_date_data);
//
//        if(reminderTitle == null || reminderDate == null){
//            Utility.showToast(AddReminderActivity.this, "Failed to add Reminder");
//        } else {
//            String userId = FirebaseAuth.getInstance().getUid();
//
//            Reminder reminder = new Reminder(userId
//                    , reminderTitle.getText().toString(), reminderNotes.getText().toString(), reminderDate.getText().toString(), Timestamp.now());
//            saveReminderToFirebase(reminder);
//        }
//    }
//
//    private void saveReminderToFirebase(Reminder reminder){
//        DocumentReference documentReference;
//        documentReference = Utility.getCollectionReferenceForReminders().document();
//
//        documentReference.set(reminder).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(task.isSuccessful()){
//                    // reminder saved
//                    Utility.showToast(AddReminderActivity.this, "Reminder was added successfully");
//                    finish();
//                } else{
//                    Utility.showToast(AddReminderActivity.this, "Failed to add Reminder");
//                }
//            }
//        });
//    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}