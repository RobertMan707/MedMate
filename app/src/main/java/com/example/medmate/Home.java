package com.example.medmate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNav;
    RecyclerView recyclerView;
    MedicineAdapter adapter;
    List<Medicine> medicineList = new ArrayList<>();
    DatabaseReference databaseReference;
    TextView nextMedicineTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineAdapter(medicineList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("medicines");

        loadMedicines();

        bottomNav = findViewById(R.id.nav_menu);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_add) {
                startActivity(new Intent(Home.this, AddActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_chest) {
                startActivity(new Intent(Home.this, ChestActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(Home.this, Profile.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void loadMedicines() {
        DatabaseReference medicinesRef = FirebaseDatabase.getInstance().getReference("medicines");

        medicineList.clear();

        medicinesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = snapshot.getValue(Medicine.class);

                    if (medicine != null) {
                        medicineList.add(medicine);
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Failed to load medicines: " + databaseError.getMessage());
            }
        });
    }


    }


