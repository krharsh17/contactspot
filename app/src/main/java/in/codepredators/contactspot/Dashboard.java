package in.codepredators.contactspot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class Dashboard extends AppCompatActivity implements RecyclerAdapterMultipleContacts.ItemClickListener, View.OnClickListener {

    RecyclerAdapterMultipleContacts adapter;
    Button button;
    Button a;
    int num;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ArrayList<String> Name = new ArrayList<>();
        Name.add("abcd");
        Name.add("HeeHEE");
        Name.add("kjasdh");
        Name.add("HeeHEE");
        Name.add("kjhsd");
        Name.add("HeeHEE");


        RecyclerView recyclerView = findViewById(R.id.ContactsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapterMultipleContacts(this, Name);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        button = (Button) findViewById(R.id.ContactsSettings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onClick(View view) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void elevateName(View v) {

                if (num == 0) {
                    v.setElevation(10.0f);
                    v.setBackground(getResources().getDrawable(R.drawable.selected_shape));
                    num = 1;
                } else {

                    v.setElevation(0.0f);
                    v.setBackground(getResources().getDrawable(R.drawable.unselected_shape));
                    num = 0;
                }

        }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void elevatePhone(View v) {

        if (num == 0) {
            v.setElevation(10.0f);
            v.setBackground(getResources().getDrawable(R.drawable.selected_shape));
            num = 1;
        } else {

            v.setElevation(0.0f);
            v.setBackground(getResources().getDrawable(R.drawable.unselected_shape));
            num = 0;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void elevateEmail(View v) {

        if (num == 0) {
//            v.setElevation(20.0f);
            v.setBackground(getResources().getDrawable(R.drawable.selected_shape));
            num = 1;
        } else {

            v.setElevation(0.0f);
            v.setBackground(getResources().getDrawable(R.drawable.unselected_shape));
            num = 0;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void elevateAddress(View v) {

        if (num == 0) {
            v.setElevation(10.0f);
            v.setBackground(getResources().getDrawable(R.drawable.selected_shape));
            num = 1;
        } else {

            v.setElevation(0.0f);
            v.setBackground(getResources().getDrawable(R.drawable.unselected_shape));
            num = 0;
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void elevateJob(View v) {

        if (num == 0) {
            v.setElevation(10.0f);
            v.setBackground(getResources().getDrawable(R.drawable.selected_shape));
            num = 1;
        } else {

            v.setElevation(0.0f);
            v.setBackground(getResources().getDrawable(R.drawable.unselected_shape));
            num = 0;
        }

    }
    }


//
//public class Dashboard extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//    }
//}