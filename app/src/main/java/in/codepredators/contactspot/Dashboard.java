package in.codepredators.contactspot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    EditText SingleName;
    EditText SinglePhone;

    Button Save;
    Button SaveAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        Save = findViewById(R.id.button);
//        SinglePhone = findViewById(R.id.editText);
        //TODO findviewbyids here

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckIfNumberExistsAlready(SinglePhone.getText().toString())){

                    addContact("ABCDEDFGH", SinglePhone.getText().toString());
                }
            }
        });
    }

    public boolean CheckEntriesSingle() {
        if (SingleName.getText().toString().length() == 0) {
            Toast.makeText(Dashboard.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (SinglePhone.getText().toString().length() == 0) {
            Toast.makeText(Dashboard.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (SinglePhone.getText().toString().length() < 6) {
            Toast.makeText(Dashboard.this, "The number seems invalid", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public boolean CheckIfNumberExistsAlready(String number){
        if (number != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
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

    public boolean CheckIfNameExistsAlready(String name){
        if (name != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(name));
            String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
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

    private void addContact(String name, String phone) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
//        values.put(Contacts.People.LABEL, name);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        Uri dataUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        Uri updateUri = Uri.withAppendedPath(dataUri, Contacts.People.Phones.CONTENT_DIRECTORY);
        values.clear();
        values.put(Contacts.People.Phones.TYPE, Contacts.People.TYPE_MOBILE);
        values.put(Contacts.People.NUMBER, phone);
        updateUri = getContentResolver().insert(updateUri, values);
    }


}
