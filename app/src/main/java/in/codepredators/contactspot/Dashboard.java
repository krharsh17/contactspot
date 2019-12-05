package in.codepredators.contactspot;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    EditText Name;
    Button NameCancel;

    EditText MobilePhone;
    Button MobilePhoneAdd;
    Button MobilePhoneCancel;

    EditText WorkPhone;
    Button WorkPhoneAdd;
    Button WorkPhoneCancel;

    EditText HomePhone;
    Button HomePhoneCancel;

    EditText Email;
    Button EmailCancel;

    EditText Address;
    Button AddressCancel;

    EditText Job;
    Button JobCancel;

    EditText Company;
    Button CompanyCancel;

    EditText Note;
    Button NoteCancel;

    TextView SingleMore;
    TextView MultipleMore;

    Button Save;
    Button SaveAll;
    Button Settings;
    Button Back;

    TabLayout Tabs;

    RecyclerView MultipleContactsRecycler;

    LinearLayout Single;
    LinearLayout Multiple;

    AsyncTask<Integer, Integer, Integer> SaveTask;
    AsyncTask<Integer, Integer, Integer> ListenerCreatorTask;
    ArrayList<DetailedContact> ContactsList;

    RelativeLayout.LayoutParams VisibleFieldParams;
    RelativeLayout.LayoutParams GoneFieldParams;

    NestedScrollView SaveView;
    NestedScrollView SettingsView;

    RecyclerAdapterMultipleContacts Adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    boolean isEmail;
    boolean isAddress;
    boolean isJob;
    boolean isCompany;
    boolean isNotes;

    TextView CodePredators;

    LinearLayout KumarHarsh;
    LinearLayout RakshitaJain;

    Button Coffee;
    Button Rate;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        CodePredators = findViewById(R.id.ContactsCodePredators);

        SaveView = findViewById(R.id.ScrollViewSave);
        SettingsView = findViewById(R.id.ScrollViewSettings);

        Save = findViewById(R.id.ContactsSave);
        SaveAll = findViewById(R.id.ContactsSaveAll);

        MobilePhone = findViewById(R.id.ContactSingleMobilePhone);
        MobilePhoneAdd = findViewById(R.id.CoontactSinglePhoneAdd);
        MobilePhoneCancel = findViewById(R.id.CoontactSinglePhoneCancel);

        WorkPhone = findViewById(R.id.ContactSingleWorkPhone);
        WorkPhoneAdd = findViewById(R.id.CoontactSingleWorkPhoneAdd);
        WorkPhoneCancel = findViewById(R.id.CoontactSingleWorkPhoneCancel);

        HomePhone = findViewById(R.id.ContactSingleHomePhone);
        HomePhoneCancel = findViewById(R.id.CoontactSingleHomePhoneCancel);

        Name = findViewById(R.id.ContactSingleName);
        NameCancel = findViewById(R.id.CoontactSingleNameCancel);

        Email = findViewById(R.id.ContactSingleEmail);
        EmailCancel = findViewById(R.id.CoontactSingleEmailCancel);

        Address = findViewById(R.id.ContactSingleAddress);
        AddressCancel = findViewById(R.id.CoontactSingleAddressCancel);

        Job = findViewById(R.id.ContactSingleJobTitle);
        JobCancel = findViewById(R.id.CoontactSingleJobTitleCancel);

        Company = findViewById(R.id.ContactSingleCompany);
        CompanyCancel = findViewById(R.id.CoontactSingleCompanyCancel);

        Note = findViewById(R.id.ContactSingleNotes);
        NoteCancel = findViewById(R.id.CoontactSingleNotesCancel);

        SingleMore = findViewById(R.id.ContactSingleMore);
        MultipleMore = findViewById(R.id.ContactMultipleMore);

        Single = findViewById(R.id.ContactsSingleLayout);
        Multiple = findViewById(R.id.MultipleContactsPalette);

        Tabs = findViewById(R.id.ContactstabLayout);

        Settings = findViewById(R.id.ContactsSettings);
        Back = findViewById(R.id.ContactsBack);

        MultipleContactsRecycler = findViewById(R.id.ContactsRecycler);
        ContactsList = new ArrayList<>();
        Adapter = new RecyclerAdapterMultipleContacts(Dashboard.this);

        KumarHarsh = findViewById(R.id.ContactsKumarHarsh);
        RakshitaJain = findViewById(R.id.ContactsRakshitaJain);

        Coffee = findViewById(R.id.ContactsBuyCoffee);

        Rate = findViewById(R.id.ConatctsRateUs);


        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.permission_popup);

        dialog.findViewById(R.id.popupLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://contactspotpolicy.000webhostapp.com/Privacy_policy.html")));
            }
        });

        dialog.findViewById(R.id.popupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    askPermissions();
                } else {
                    dialog.cancel();
                }
            }
        });
        if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            dialog.show();

        }


        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveView.animate().y(-2000).alpha(0).setDuration(1200);
                SettingsView.setY(2000);
                SettingsView.setAlpha(0f);
                SettingsView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveView.setVisibility(View.GONE);
                        SettingsView.animate().y(0).alpha(1).setDuration(800);
                        Back.setVisibility(View.VISIBLE);
                    }
                }, 800);
                Settings.setVisibility(View.GONE);

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsView.animate().y(2000).alpha(0).setDuration(1200);
                SaveView.setY(-2000);
                SaveView.setAlpha(0f);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveView.setVisibility(View.VISIBLE);
                        SettingsView.setVisibility(View.GONE);
                        SaveView.animate().y(0).alpha(1).setDuration(800);
                        Settings.setVisibility(View.VISIBLE);
                    }
                }, 800);
                Back.setVisibility(View.GONE);
            }
        });

        ListenerCreatorTask = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                LinearLayoutManager Manager = new LinearLayoutManager(Dashboard.this);
                Manager.setOrientation(RecyclerView.VERTICAL);
                MultipleContactsRecycler.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                MultipleContactsRecycler.setAdapter(Adapter);


                Tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getText().toString().equals("Multiple")) {
                            Multiple.animate().alpha(1).setDuration(300);
                            Single.animate().alpha(0).setDuration(300);
                            Multiple.setVisibility(View.VISIBLE);
                            Single.setVisibility(View.GONE);

                        } else {
                            Multiple.animate().alpha(0).setDuration(300);
                            Single.animate().alpha(1).setDuration(300);
                            Multiple.setVisibility(View.GONE);
                            Single.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                VisibleFieldParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                GoneFieldParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                VisibleFieldParams.setMargins(0, 10, 0, 0);
                GoneFieldParams.setMargins(0, 0, 0, 0);

                SingleMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SingleMore.getText().toString().equals("- Less")) {
                            if (!isEmail) {
                                Email.setVisibility(View.GONE);
                                EmailCancel.setVisibility(View.GONE);
                                Email.setLayoutParams(GoneFieldParams);
                            }
                            if (!isAddress) {
                                Address.setVisibility(View.GONE);
                                AddressCancel.setVisibility(View.GONE);
                                Address.setLayoutParams(GoneFieldParams);
                            }
                            if (!isJob) {
                                Job.setVisibility(View.GONE);
                                JobCancel.setVisibility(View.GONE);
                                Job.setLayoutParams(GoneFieldParams);
                            }

                            if (!isCompany) {
                                Company.setVisibility(View.GONE);
                                CompanyCancel.setVisibility(View.GONE);
                                Company.setLayoutParams(GoneFieldParams);
                            }
                            if (!isNotes) {
                                Note.setVisibility(View.GONE);
                                NoteCancel.setVisibility(View.GONE);
                                Note.setLayoutParams(GoneFieldParams);
                            }
                            SingleMore.setText("+ More");
                        } else {
                            if (!isEmail) {
                                Email.setVisibility(View.VISIBLE);
                                EmailCancel.setVisibility(View.VISIBLE);
                                Email.setLayoutParams(VisibleFieldParams);
                            }

                            if (!isAddress) {
                                Address.setVisibility(View.VISIBLE);
                                AddressCancel.setVisibility(View.VISIBLE);
                                Address.setLayoutParams(VisibleFieldParams);
                            }

                            if (!isJob) {
                                Job.setVisibility(View.VISIBLE);
                                JobCancel.setVisibility(View.VISIBLE);
                                Job.setLayoutParams(VisibleFieldParams);
                            }

                            if (!isCompany) {
                                Company.setVisibility(View.VISIBLE);
                                CompanyCancel.setVisibility(View.VISIBLE);
                                Company.setLayoutParams(VisibleFieldParams);
                            }

                            if (!isNotes) {
                                Note.setVisibility(View.VISIBLE);
                                NoteCancel.setVisibility(View.VISIBLE);
                                Note.setLayoutParams(VisibleFieldParams);
                            }
                            SingleMore.setText("- Less");
                        }
                    }
                });

                MultipleMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<DetailedContact> newList = Adapter.getContactsList();
                        newList.add(new DetailedContact(Adapter.getItemCount()));
                        Adapter.setContactsList(newList);
                        Adapter.notifyItemInserted(Adapter.getItemCount());

                        ArrayList<RecyclerAdapterMultipleContacts.ViewHolder> viewHolders = Adapter.getViewHolders();
                        for (int i = 0; i < viewHolders.size(); i++) {
                            viewHolders.get(i).ID.setText("" + i);
                        }
                    }
                });

                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckEntries(Name.getText().toString(), MobilePhone.getText().toString())) {
                            if (!CheckIfNumberExistsAlready(MobilePhone.getText().toString())) {
                                if (WorkPhone.getText().toString().equals("")) {
                                    if (HomePhone.getText().toString().equals("")) {
                                        AddContact(Name.getText().toString(), MobilePhone.getText().toString(), null, null, Email.getText().toString(), Address.getText().toString(), Job.getText().toString(), Company.getText().toString(), Note.getText().toString());
                                        Name.setText("");
                                        MobilePhone.setText("");
                                        WorkPhone.setText("");
                                        HomePhone.setText("");
                                        Email.setText("");
                                        Address.setText("");
                                        Job.setText("");
                                        Company.setText("");
                                        Note.setText("");

                                        if (SingleMore.getText().equals("- Less")) {
                                            SingleMore.callOnClick();
                                        }
                                    } else {
                                        if (!CheckIfNumberExistsAlready(HomePhone.getText().toString())) {
                                            AddContact(Name.getText().toString(), MobilePhone.getText().toString(), null, HomePhone.getText().toString(), Email.getText().toString(), Address.getText().toString(), Job.getText().toString(), Company.getText().toString(), Note.getText().toString());
                                            Name.setText("");
                                            MobilePhone.setText("");
                                            WorkPhone.setText("");
                                            HomePhone.setText("");
                                            Email.setText("");
                                            Address.setText("");
                                            Job.setText("");
                                            Company.setText("");
                                            Note.setText("");

                                            if (SingleMore.getText().equals("- Less")) {
                                                SingleMore.callOnClick();
                                            }
                                        } else {
                                            Toast.makeText(Dashboard.this, HomePhone.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    if (HomePhone.getText().toString().equals("")) {
                                        if (!CheckIfNumberExistsAlready(WorkPhone.getText().toString())) {
                                            AddContact(Name.getText().toString(), MobilePhone.getText().toString(), WorkPhone.getText().toString(), null, Email.getText().toString(), Address.getText().toString(), Job.getText().toString(), Company.getText().toString(), Note.getText().toString());
                                            Name.setText("");
                                            MobilePhone.setText("");
                                            WorkPhone.setText("");
                                            HomePhone.setText("");
                                            Email.setText("");
                                            Address.setText("");
                                            Job.setText("");
                                            Company.setText("");
                                            Note.setText("");

                                            if (SingleMore.getText().equals("- Less")) {
                                                SingleMore.callOnClick();
                                            }
                                        } else {
                                            Toast.makeText(Dashboard.this, WorkPhone.getText().toString() + " exists already!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        if (!CheckIfNumberExistsAlready(WorkPhone.getText().toString())) {
                                            if (!CheckIfNumberExistsAlready(HomePhone.getText().toString())) {
                                                AddContact(Name.getText().toString(), MobilePhone.getText().toString(), WorkPhone.getText().toString(), HomePhone.getText().toString(), Email.getText().toString(), Address.getText().toString(), Job.getText().toString(), Company.getText().toString(), Note.getText().toString());
                                                Name.setText("");
                                                MobilePhone.setText("");
                                                WorkPhone.setText("");
                                                HomePhone.setText("");
                                                Email.setText("");
                                                Address.setText("");
                                                Job.setText("");
                                                Company.setText("");
                                                Note.setText("");

                                                if (SingleMore.getText().equals("- Less")) {
                                                    SingleMore.callOnClick();
                                                }
                                            } else {
                                                Toast.makeText(Dashboard.this, HomePhone.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(Dashboard.this, WorkPhone.getText().toString() + " exists already!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(Dashboard.this, "Please enter correct data!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                SaveAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < Adapter.getViewHolders().size(); i++) {
                            RecyclerAdapterMultipleContacts.ViewHolder viewHolder = Adapter.getViewHolders().get(i);
                            if (CheckEntries(viewHolder.Name.getText().toString(), viewHolder.MobilePhone.getText().toString())) {
                                if (!CheckIfNumberExistsAlready(viewHolder.MobilePhone.getText().toString())) {
                                    if (viewHolder.WorkPhone.getText().toString().equals("")) {
                                        if (viewHolder.HomePhone.getText().toString().equals("")) {
                                            AddContact(viewHolder.Name.getText().toString(), viewHolder.MobilePhone.getText().toString(), null, null, viewHolder.Email.getText().toString(), viewHolder.Address.getText().toString(), viewHolder.Job.getText().toString(), viewHolder.Company.getText().toString(), viewHolder.Note.getText().toString());
                                            viewHolder.Name.setText("");
                                            viewHolder.MobilePhone.setText("");
                                            viewHolder.WorkPhone.setText("");
                                            viewHolder.HomePhone.setText("");
                                            viewHolder.Email.setText("");
                                            viewHolder.Address.setText("");
                                            viewHolder.Job.setText("");
                                            viewHolder.Company.setText("");
                                            viewHolder.Note.setText("");

                                            if (viewHolder.More.getText().equals("- Less")) {
                                                viewHolder.More.callOnClick();
                                            }
                                        } else {
                                            if (!CheckIfNumberExistsAlready(viewHolder.HomePhone.getText().toString())) {
                                                AddContact(viewHolder.Name.getText().toString(), viewHolder.MobilePhone.getText().toString(), null, viewHolder.HomePhone.getText().toString(), viewHolder.Email.getText().toString(), viewHolder.Address.getText().toString(), viewHolder.Job.getText().toString(), viewHolder.Company.getText().toString(), viewHolder.Note.getText().toString());
                                                viewHolder.Name.setText("");
                                                viewHolder.MobilePhone.setText("");
                                                viewHolder.WorkPhone.setText("");
                                                viewHolder.HomePhone.setText("");
                                                viewHolder.Email.setText("");
                                                viewHolder.Address.setText("");
                                                viewHolder.Job.setText("");
                                                viewHolder.Company.setText("");
                                                viewHolder.Note.setText("");

                                                if (viewHolder.More.getText().equals("- Less")) {
                                                    viewHolder.More.callOnClick();
                                                }
                                            } else {
                                                Toast.makeText(Dashboard.this, viewHolder.HomePhone.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        if (viewHolder.HomePhone.getText().toString().equals("")) {
                                            if (!CheckIfNumberExistsAlready(viewHolder.WorkPhone.getText().toString())) {
                                                AddContact(viewHolder.Name.getText().toString(), viewHolder.MobilePhone.getText().toString(), viewHolder.WorkPhone.getText().toString(), null, viewHolder.Email.getText().toString(), viewHolder.Address.getText().toString(), viewHolder.Job.getText().toString(), viewHolder.Company.getText().toString(), viewHolder.Note.getText().toString());
                                                viewHolder.Name.setText("");
                                                viewHolder.MobilePhone.setText("");
                                                viewHolder.WorkPhone.setText("");
                                                viewHolder.HomePhone.setText("");
                                                viewHolder.Email.setText("");
                                                viewHolder.Address.setText("");
                                                viewHolder.Job.setText("");
                                                viewHolder.Company.setText("");
                                                viewHolder.Note.setText("");

                                                if (viewHolder.More.getText().equals("- Less")) {
                                                    viewHolder.More.callOnClick();
                                                }
                                            } else {
                                                Toast.makeText(Dashboard.this, viewHolder.WorkPhone.getText().toString() + " exists already!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            if (!CheckIfNumberExistsAlready(viewHolder.WorkPhone.getText().toString())) {
                                                if (!CheckIfNumberExistsAlready(viewHolder.HomePhone.getText().toString())) {
                                                    AddContact(viewHolder.Name.getText().toString(), viewHolder.MobilePhone.getText().toString(), viewHolder.WorkPhone.getText().toString(), viewHolder.HomePhone.getText().toString(), viewHolder.Email.getText().toString(), viewHolder.Address.getText().toString(), viewHolder.Job.getText().toString(), viewHolder.Company.getText().toString(), viewHolder.Note.getText().toString());
                                                    viewHolder.Name.setText("");
                                                    viewHolder.MobilePhone.setText("");
                                                    viewHolder.WorkPhone.setText("");
                                                    viewHolder.HomePhone.setText("");
                                                    viewHolder.Email.setText("");
                                                    viewHolder.Address.setText("");
                                                    viewHolder.Job.setText("");
                                                    viewHolder.Company.setText("");
                                                    viewHolder.Note.setText("");

                                                    if (viewHolder.More.getText().equals("- Less")) {
                                                        viewHolder.More.callOnClick();
                                                    }
                                                } else {
                                                    Toast.makeText(Dashboard.this, viewHolder.HomePhone.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(Dashboard.this, viewHolder.WorkPhone.getText().toString() + " exists already!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(Dashboard.this, "Please enter correct data!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


                WorkPhoneAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomePhone.setVisibility(View.VISIBLE);
                        HomePhoneCancel.setVisibility(View.VISIBLE);
                        HomePhone.setLayoutParams(VisibleFieldParams);
                        WorkPhoneAdd.setVisibility(View.GONE);
                    }
                });

                MobilePhoneAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WorkPhone.setVisibility(View.VISIBLE);
                        WorkPhoneAdd.setVisibility(View.VISIBLE);
                        WorkPhone.setLayoutParams(VisibleFieldParams);
                        WorkPhoneCancel.setVisibility(View.VISIBLE);
                        MobilePhoneAdd.setVisibility(View.GONE);
                    }
                });

                WorkPhoneCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (WorkPhone.getText().toString().equals("")) {
                            WorkPhone.setVisibility(View.GONE);
                            WorkPhoneAdd.setVisibility(View.GONE);
                            WorkPhoneCancel.setVisibility(View.GONE);
                            WorkPhone.setLayoutParams(GoneFieldParams);
                            MobilePhoneAdd.setVisibility(View.VISIBLE);
                        } else {
                            WorkPhone.setText("");
                        }
                    }
                });

                HomePhoneCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (HomePhone.getText().toString().equals("")) {
                            HomePhone.setVisibility(View.GONE);
                            HomePhone.setLayoutParams(GoneFieldParams);
                            HomePhoneCancel.setVisibility(View.GONE);
                            if (WorkPhone.getVisibility() == View.VISIBLE) {
                                WorkPhoneAdd.setVisibility(View.VISIBLE);
                            } else {
                                MobilePhoneAdd.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

                NameCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Name.setText("");
                    }
                });

                MobilePhoneCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MobilePhone.setText("");
                    }
                });

                EmailCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Email.setText("");
                    }
                });

                AddressCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Address.setText("");
                    }
                });

                JobCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Job.setText("");
                    }
                });

                CompanyCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Company.setText("");
                    }
                });

                NoteCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Note.setText("");
                    }
                });

                findViewById(R.id.ContactsChooseName).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Dashboard.this, "Name can not be hidden", Toast.LENGTH_SHORT).show();
                    }
                });

                findViewById(R.id.ContactsChoosePhoneNumber).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Dashboard.this, "Number can not be hidden", Toast.LENGTH_SHORT).show();
                    }
                });

                return null;
            }
        };

        Coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ko-fi.com/krharsh17")));
            }
        });

        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + Dashboard.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Dashboard.this.getPackageName())));
                }
            }
        });

        KumarHarsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://kumar-harsh-0a119716b"));
                final PackageManager packageManager = Dashboard.this.getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=kumar-harsh-0a119716b"));
                }
                startActivity(intent);
            }
        });

        RakshitaJain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/rakshita.jain_");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/rakshita.jain_")));
                }
            }
        });

        CodePredators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                String uri;
                try {
                    Dashboard.this.getPackageManager()
                            .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                    int versionCode = Dashboard.this.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) { //newer versions of fb app
                        uri = "fb://facewebmodal/f?href=https://www.facebook.com/CodePredators6";
                    } else { //older versions of fb app
                        uri = "fb://page/CodePredators6";
                    }
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(uri)); //Trys to make intent with FB's URI
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/CodePredators6")); //catches and opens a url to the desired page
                } finally {
                    startActivity(intent);
                }
            }
        });

        ReadPreferences();
        UpdateColors();
        VisibilityChanger();
        ListenerCreatorTask.execute();
    }

    void VisibilityChanger() {
        if (isEmail) {
            Email.setVisibility(View.VISIBLE);
            EmailCancel.setVisibility(View.VISIBLE);
        } else {
            Email.setVisibility(View.GONE);
            EmailCancel.setVisibility(View.GONE);
        }

        if (isAddress) {
            Address.setVisibility(View.VISIBLE);
            AddressCancel.setVisibility(View.VISIBLE);
        } else {
            Address.setVisibility(View.GONE);
            AddressCancel.setVisibility(View.GONE);
        }

        if (isCompany) {
            Company.setVisibility(View.VISIBLE);
            CompanyCancel.setVisibility(View.VISIBLE);
        } else {
            Company.setVisibility(View.GONE);
            CompanyCancel.setVisibility(View.GONE);
        }

        if (isJob) {
            Job.setVisibility(View.VISIBLE);
            JobCancel.setVisibility(View.VISIBLE);
        } else {
            Job.setVisibility(View.GONE);
            JobCancel.setVisibility(View.GONE);
        }

        if (isNotes) {
            Note.setVisibility(View.VISIBLE);
            NoteCancel.setVisibility(View.VISIBLE);
        } else {
            Note.setVisibility(View.GONE);
            NoteCancel.setVisibility(View.GONE);
        }


    }

    public boolean CheckEntries(String Name, String MobilePhone) {
        if (Name.length() == 0) {
            Toast.makeText(Dashboard.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (MobilePhone.length() == 0) {
            Toast.makeText(Dashboard.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (MobilePhone.length() < 6) {
            Toast.makeText(Dashboard.this, "The number seems invalid", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public boolean CheckIfNumberExistsAlready(String number) {
        if (number != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
            Cursor cur = Dashboard.this.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
            try {
                if (cur != null && cur.moveToFirst()) {
                    return true;
                }
            } finally {
                if (cur != null)
                    cur.close();
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean CheckIfNameExistsAlready(String name) {
        if (name != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(name));
            String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
            Cursor cur = Dashboard.this.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
            try {
                if (cur != null && cur.moveToFirst()) {
                    return true;
                }
            } finally {
                if (cur != null)
                    cur.close();
            }
            return false;
        } else {
            return false;
        }
    }


    @SuppressLint("StaticFieldLeak")
    public void AddContact(final String Name, final String MobilePhone, final String WorkPhone, final String HomePhone, final String Email, final String Address, final String Job, final String Company, final String Note) {

        SaveTask = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {

                ArrayList<ContentProviderOperation> ops =
                        new ArrayList<>();

                int rawContactID = ops.size();

                // Adding insert operation to operations list
                // to insert a new raw contact in the table ContactsContract.RawContacts
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                // Adding insert operation to operations list
                // to insert display name in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, Name)
                        .build());

                // Adding insert operation to operations list
                // to insert Mobile Number in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobilePhone)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());

                if (WorkPhone != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkPhone)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                            .build());
                }

                if (HomePhone != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomePhone)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                            .build());
                }

                // Adding insert operation to operations list
                // to insert Email in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, Email)
                        .build());

                // Adding insert operation to operations list
                // to insert Address in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, Address)
                        .build());

                // Adding insert operation to operations list
                // to insert Company & Job in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, Company)
                        .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, Job)
                        .build());

                // Adding insert operation to operations list
                // to insert Notes in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Note.NOTE, Note)
                        .build());

                try {
                    // Executing all the insert operations as a single database transaction
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), MobilePhone + " is successfully added", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        SaveTask.execute();

    }

    void ReadPreferences() {
        sharedPreferences = getSharedPreferences("Contactspot", 0);
        editor = sharedPreferences.edit();

        String isEmail = sharedPreferences.getString("email", "");
        String isAddress = sharedPreferences.getString("address", "");
        String isJob = sharedPreferences.getString("job title", "");
        String isCompany = sharedPreferences.getString("company", "");
        String isNotes = sharedPreferences.getString("notes", "");

        if (isEmail != null && isEmail.equals("true")) {
            Dashboard.this.isEmail = true;
        } else {
            Dashboard.this.isEmail = false;
        }

        if (isAddress != null && isAddress.equals("true")) {
            Dashboard.this.isAddress = true;
        } else {
            Dashboard.this.isAddress = false;
        }

        if (isJob != null && isJob.equals("true")) {
            Dashboard.this.isJob = true;
        } else {
            Dashboard.this.isJob = false;
        }

        if (isCompany != null && isCompany.equals("true")) {
            Dashboard.this.isCompany = true;
        } else {
            Dashboard.this.isCompany = false;
        }

        if (isNotes != null && isNotes.equals("true")) {
            Dashboard.this.isNotes = true;
        } else {
            Dashboard.this.isNotes = false;
        }
    }

    void WritePreferences(final TextView textView) {
        if (textView != null) {
            switch (textView.getText().toString().toLowerCase()) {
                case "email":
                    if (isEmail) {
                        editor.putString(textView.getText().toString().toLowerCase(), "false");
                    } else {
                        editor.putString(textView.getText().toString().toLowerCase(), "true");
                    }
                    break;

                case "address":
                    if (isAddress) {
                        editor.putString(textView.getText().toString().toLowerCase(), "false");
                    } else {
                        editor.putString(textView.getText().toString().toLowerCase(), "true");
                    }
                    break;

                case "job title":
                    if (isJob) {
                        editor.putString(textView.getText().toString().toLowerCase(), "false");
                    } else {
                        editor.putString(textView.getText().toString().toLowerCase(), "true");
                    }
                    break;

                case "company":
                    if (isCompany) {
                        editor.putString(textView.getText().toString().toLowerCase(), "false");
                    } else {
                        editor.putString(textView.getText().toString().toLowerCase(), "true");
                    }
                    break;

                case "notes":
                    if (isNotes) {
                        editor.putString(textView.getText().toString().toLowerCase(), "false");
                    } else {
                        editor.putString(textView.getText().toString().toLowerCase(), "true");
                    }
                    break;

                default:
                    break;
            }

            editor.apply();
            ReadPreferences();
        }
    }

    public void flipChoice(View view) {
        ReadPreferences();
        WritePreferences((TextView) view);
        UpdateColors();
        VisibilityChanger();
        for (RecyclerAdapterMultipleContacts.ViewHolder V : Adapter.getViewHolders()) {
            V.VisibilityChanger();
        }
    }

    void UpdateColors() {
        TextView EmailButton = findViewById(R.id.ContactsChooseEmail);
        TextView AddressButton = findViewById(R.id.ContactsChooseAddress);
        TextView JobButton = findViewById(R.id.ContactsChooseJobTitle);
        TextView CompanyButton = findViewById(R.id.ContactsChooseCompany);
        TextView NotesButton = findViewById(R.id.ContactsChooseNotes);
        if (!isEmail) {
            EmailButton.setBackgroundResource(R.drawable.unselected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                EmailButton.setElevation(0);
            }
            EmailButton.setTextColor(getResources().getColor(R.color.blue));
        } else {
            EmailButton.setBackgroundResource(R.drawable.selected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                EmailButton.setElevation(15);
            }
            EmailButton.setTextColor(getResources().getColor(R.color.white));
        }

        if (!isAddress) {
            AddressButton.setBackgroundResource(R.drawable.unselected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AddressButton.setElevation(0);
            }
            AddressButton.setTextColor(getResources().getColor(R.color.blue));
        } else {
            AddressButton.setBackgroundResource(R.drawable.selected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AddressButton.setElevation(15);
            }
            AddressButton.setTextColor(getResources().getColor(R.color.white));
        }

        if (!isJob) {
            JobButton.setBackgroundResource(R.drawable.unselected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                JobButton.setElevation(0);
            }
            JobButton.setTextColor(getResources().getColor(R.color.blue));
        } else {
            JobButton.setBackgroundResource(R.drawable.selected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                JobButton.setElevation(15);
            }
            JobButton.setTextColor(getResources().getColor(R.color.white));
        }

        if (!isCompany) {
            CompanyButton.setBackgroundResource(R.drawable.unselected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CompanyButton.setElevation(0);
            }
            CompanyButton.setTextColor(getResources().getColor(R.color.blue));
        } else {
            CompanyButton.setBackgroundResource(R.drawable.selected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CompanyButton.setElevation(15);
            }
            CompanyButton.setTextColor(getResources().getColor(R.color.white));
        }

        if (!isNotes) {
            NotesButton.setBackgroundResource(R.drawable.unselected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                NotesButton.setElevation(0);
            }
            NotesButton.setTextColor(getResources().getColor(R.color.blue));
        } else {
            NotesButton.setBackgroundResource(R.drawable.selected_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                NotesButton.setElevation(15);
            }
            NotesButton.setTextColor(getResources().getColor(R.color.white));
        }

    }

    void askPermissions() {
        if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(Dashboard.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    24);

        if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(Dashboard.this,
                    new String[]{Manifest.permission.WRITE_CONTACTS},
                    24);
    }

    @Override
    protected void onDestroy() {
        ListenerCreatorTask.cancel(true);
        if (SaveTask != null)
            SaveTask.cancel(true);
        super.onDestroy();
    }
}
