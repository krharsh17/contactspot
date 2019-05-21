package in.codepredators.contactspot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    EditText Name;
    Button NameCancel;

    EditText Phone;
    Button PhoneCancel;

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

    AsyncTask<Integer, Integer, Integer> Task;
    AsyncTask<Integer, Integer, Integer> T;
    ArrayList<DetailedContact> ContactsList;

    NestedScrollView SaveView;
    NestedScrollView SettingsView;

    RecyclerAdapterMultipleContacts Adapter;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SaveView = findViewById(R.id.ScrollViewSave);
        SettingsView = findViewById(R.id.ScrollViewSettings);

        Save = findViewById(R.id.ContactsSave);
        SaveAll = findViewById(R.id.ContactsSaveAll);

        Phone = findViewById(R.id.ContactSinglePhone);
        PhoneCancel = findViewById(R.id.CoontactSinglePhoneCancel);

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
        Adapter = new RecyclerAdapterMultipleContacts(Dashboard.this, ContactsList);

        Log.i("Thread", Thread.currentThread().getName());
//        Phone = findViewById(R.id.editText);
        //TODO findviewbyids here

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

        T = new AsyncTask<Integer, Integer, Integer>() {
            @SuppressLint("WrongThread")
            @Override
            protected Integer doInBackground(Integer... integers) {
                Log.i("Thread", Thread.currentThread().getName());
                ContactsList = new ArrayList<>();
                ContactsList.add(new DetailedContact());
                MultipleContactsRecycler.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                MultipleContactsRecycler.setAdapter(Adapter);

                Tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getText().toString().equals("Multiple")) {
                            Multiple.animate().alpha(1).setDuration(700);
                            Single.animate().alpha(0).setDuration(700);
                            Multiple.setVisibility(View.VISIBLE);
                            Single.setVisibility(View.GONE);

                        } else {
                            Multiple.animate().alpha(0).setDuration(700);
                            Single.animate().alpha(1).setDuration(700);
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

                SingleMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Email.getVisibility() == View.VISIBLE) {
                            Email.setVisibility(View.GONE);
                            EmailCancel.setVisibility(View.GONE);
                            Address.setVisibility(View.GONE);
                            AddressCancel.setVisibility(View.GONE);
                            Job.setVisibility(View.GONE);
                            JobCancel.setVisibility(View.GONE);
                            Company.setVisibility(View.GONE);
                            CompanyCancel.setVisibility(View.GONE);
                            Note.setVisibility(View.GONE);
                            NoteCancel.setVisibility(View.GONE);
                            SingleMore.setText("+ More");
                        } else {
                            Email.setVisibility(View.VISIBLE);
                            EmailCancel.setVisibility(View.VISIBLE);
                            Address.setVisibility(View.VISIBLE);
                            AddressCancel.setVisibility(View.VISIBLE);
                            Job.setVisibility(View.VISIBLE);
                            JobCancel.setVisibility(View.VISIBLE);
                            Company.setVisibility(View.VISIBLE);
                            CompanyCancel.setVisibility(View.VISIBLE);
                            Note.setVisibility(View.VISIBLE);
                            NoteCancel.setVisibility(View.VISIBLE);
                            SingleMore.setText("- Less");
                        }
                    }
                });

                MultipleMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactsList = ((RecyclerAdapterMultipleContacts) MultipleContactsRecycler.getAdapter()).getmData();
                        ContactsList.add(new DetailedContact());
                        MultipleContactsRecycler.getAdapter().notifyDataSetChanged();
                    }
                });

                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckEntriesSingle()) {
                            if (!CheckIfNumberExistsAlready(Phone.getText().toString())) {
                                if (!CheckIfNameExistsAlready(Name.getText().toString())) {
                                    AddContact(Name.getText().toString(), Phone.getText().toString(), Name.getText().toString(), Name.getText().toString(), Name.getText().toString(), Name.getText().toString(), Name.getText().toString());
                                } else {
                                    Toast.makeText(Dashboard.this, "This name already exists..", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Dashboard.this, "This number already exists..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                SaveAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Code here to save all contacts
//                        ContactsList = ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getmData();
//                        ArrayList<Integer> Removals = new ArrayList<>();
//                        for(int i=0; i<ContactsList.size(); i++){
//                            DetailedContact C = ContactsList.get(i);
////                            if (CheckEntriesMultiple()) {
//                                if (!CheckIfNumberExistsAlready(C.getPhone())) {
//                                    if (!CheckIfNameExistsAlready(C.getName())) {
//                                        AddContact(C.getName(), C.getPhone(), C.getEmail(), C.getAddress(), C.getJob(), C.getCompany(), C.getNotes());
//                                        Removals.add(i);
//                                        ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getNames()[i].setText("");
//                                        ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getPhones()[i].setText("");
//                                        ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getEmails()[i].setText("");
//                                        ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getAddresses()[i].setText("");
//                                        ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getJobs()[i].setText("");
//                                        ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getCompany()[i].setText("");
//                                        ((RecyclerAdapterMultipleContacts)MultipleContactsRecycler.getAdapter()).getNotes()[i].setText("");
//                                    } else {
//                                        Toast.makeText(Dashboard.this,  C.getName() + " already exists..", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    Toast.makeText(Dashboard.this, C.getPhone() + " already exists..", Toast.LENGTH_SHORT).show();
//                                }
////                            }
//                        }
//
//                        for(int i: Removals){
//                            ContactsList.remove(i);
//                        }
//
//                        MultipleContactsRecycler.getAdapter().notifyDataSetChanged();

                        Adapter.AddContacts();
                    }
                });


                return null;
            }
        };
        T.execute();

        TabLayout.Tab MultipleTab = Tabs.getTabAt(1);
        MultipleTab.select();
    }

    public boolean CheckEntriesSingle() {
        if (Name.getText().toString().length() == 0) {
            Toast.makeText(Dashboard.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Phone.getText().toString().length() == 0) {
            Toast.makeText(Dashboard.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Phone.getText().toString().length() < 6) {
            Toast.makeText(Dashboard.this, "The number seems invalid", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public boolean CheckEntriesMultiple(){
        if (Name.getText().toString().length() == 0) {
            return false;
        } else if (Phone.getText().toString().length() == 0) {
            return false;
        } else if (Phone.getText().toString().length() < 6) {
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
                if (cur.moveToFirst()) {
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
                if (cur.moveToFirst()) {
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
    public void AddContact(final String Name, final String MobilePhone, final String Email, final String Address, final String Job, final String Company, final String Note) {

        Task = new AsyncTask<Integer, Integer, Integer>() {
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
                            Toast.makeText(getBaseContext(), "Contact is successfully added", Toast.LENGTH_SHORT).show();
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
        Task.execute();

    }

    @Override
    protected void onDestroy() {
        T.cancel(true);
        if(Task!=null)
            Task.cancel(true);
        super.onDestroy();
    }
}
