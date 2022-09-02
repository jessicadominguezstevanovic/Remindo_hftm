package ch.hftm.remindo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReminderListViewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private GoogleSignInClient client;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list_view);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        client = GoogleSignIn.getClient(this,gso);

        ListView listView = findViewById(R.id.reminderItemsView);

        List<String> list = new ArrayList<>();
        list.add("Reminder 1");
        list.add("Reminder 2");
        list.add("Reminder 3");
        list.add("Reminder 4");

        ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(ReminderListViewActivity.this, ReminderItemViewActivity.class));
                }else if(position == 1){
                    startActivity(new Intent(ReminderListViewActivity.this, ReminderItemViewActivity.class));
                }else{
                    startActivity(new Intent(ReminderListViewActivity.this, ReminderItemViewActivity.class));
                }
            }
        });


        FloatingActionButton addReminderBtn = findViewById(R.id.addReminderItem);

        addReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(ReminderListViewActivity.this, AddReminderActivity.class));
            }
        });

        getdata();
    }

    private void getdata() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://remindo-hftm-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                TextView testing = findViewById(R.id.testing);
                testing.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utility.showToast(ReminderListViewActivity.this, "Fail to get data.");
            }
        });
    }

    // ----- Menu Methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
         super.onCreateOptionsMenu(menu);
         getMenuInflater().inflate(R.menu.main_menu, menu);

         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        if (itemId == R.id.app_bar_search) {
            action_search();
            return true;
        } else if (itemId == R.id.menu_userprofile) {
            startActivity(new Intent(ReminderListViewActivity.this, UserProfileActivity.class));
            return true;
        } else if (itemId == R.id.menu_calendarview) {
            startActivity(new Intent(ReminderListViewActivity.this, ReminderCalendarViewActivity.class));
            return true;
        } else if (itemId == R.id.menu_listview) {
            startActivity(new Intent(ReminderListViewActivity.this, ReminderListViewActivity.class));
            return true;
        } else if (itemId == R.id.menu_help) {
            startActivity(new Intent(ReminderListViewActivity.this, HelpActivity.class));
            return true;
        } else if (itemId == R.id.menu_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void action_search()
    {
        System.out.println("Heeeeey");
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(ReminderListViewActivity.this, LoginActivity.class));
            }
        });

        Intent i = new Intent(ReminderListViewActivity.this, LoginActivity.class);
        startActivity(i);
    }

}
