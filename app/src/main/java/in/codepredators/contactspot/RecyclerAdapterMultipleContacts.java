package in.codepredators.contactspot;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Address;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.MainThread;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapterMultipleContacts extends RecyclerView.Adapter<RecyclerAdapterMultipleContacts.ViewHolder> {

    private ArrayList<DetailedContact> mData;
    private LayoutInflater mInflater;
    private Context context;
    private EditText[] Names;
    private EditText[] Phones;
    private EditText[] Emails;
    private EditText[] Addresses;
    private EditText[] Jobs;
    private EditText[] Company;
    private EditText[] Notes;

    private ViewHolder[] viewHolders;


    public EditText[] getNames() {
        return Names;
    }

    public EditText[] getPhones() {
        return Phones;
    }

    public EditText[] getEmails() {
        return Emails;
    }

    public EditText[] getAddresses() {
        return Addresses;
    }

    public EditText[] getJobs() {
        return Jobs;
    }

    public EditText[] getCompany() {
        return Company;
    }

    public EditText[] getNotes() {
        return Notes;
    }


    // data is passed into the constructor
    RecyclerAdapterMultipleContacts(Context context, ArrayList<DetailedContact> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        Names = new EditText[50];
        Phones = new EditText[50];
        Emails = new EditText[50];
        Addresses = new EditText[50];
        Jobs = new EditText[50];
        Company = new EditText[50];
        Notes = new EditText[50];
        viewHolders = new ViewHolder[50];

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.multiple_contacts_recycler, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String name = mData.get(position).getName();
//        holder.Name.setText(name);
        viewHolders[position] = holder;
        Names[position] = holder.Name;
        Phones[position] = holder.Phone;
        Emails[position] = holder.Email;
        Addresses[position] = holder.Address;
        Jobs[position] = holder.Job;
        Company[position] = holder.Company;
        Notes[position] = holder.Note;

        holder.Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DetailedContact newmData = mData.get(position);
                newmData.setName(s.toString());
                mData.set(position, newmData);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DetailedContact newmData = mData.get(position);
                newmData.setPhone(s.toString());
                mData.set(position, newmData);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DetailedContact newmData = mData.get(position);
                newmData.setEmail(s.toString());
                mData.set(position, newmData);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.Address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DetailedContact newmData = mData.get(position);
                newmData.setAddress(s.toString());
                mData.set(position, newmData);
                Log.i("Phone", mData.get(position).getPhone());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.Job.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DetailedContact newmData = mData.get(position);
                newmData.setJob(s.toString());
                mData.set(position, newmData);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.Company.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DetailedContact newmData = mData.get(position);
                newmData.setCompany(s.toString());
                mData.set(position, newmData);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.Note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DetailedContact newmData = mData.get(position);
                newmData.setNotes(s.toString());
                mData.set(position, newmData);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Toast.makeText(context, "Cannot delete the last contact", Toast.LENGTH_SHORT).show();
                    return;
                }
                mData.remove(position);
                notifyDataSetChanged();
                holder.Name.setText("");
                holder.Phone.setText("");
                holder.Email.setText("");
                holder.Address.setText("");
                holder.Job.setText("");
                holder.Company.setText("");
                holder.Note.setText("");
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView More;
        EditText Name;
        EditText Phone;
        EditText Email;
        EditText Address;
        EditText Job;
        EditText Company;
        EditText Note;

        Button NameCancel;
        Button PhoneCancel;
        Button EmailCancel;
        Button AddressCancel;
        Button JobCancel;
        Button CompanyCancel;
        Button NoteCancel;

        ImageView Cancel;

        ViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.ContactsMultipleName);
            Phone = itemView.findViewById(R.id.ContactsMultiplePhone);
            Email = itemView.findViewById(R.id.ContactsMultipleEmail);
            Address = itemView.findViewById(R.id.ContactsMultipleAddress);
            Job = itemView.findViewById(R.id.ContactsMultipleJobTitle);
            Company = itemView.findViewById(R.id.ContactsMultipleCompany);
            Note = itemView.findViewById(R.id.ContactsMultipleNotes);
            More = itemView.findViewById(R.id.ContactsMultipleMore);
            NameCancel = itemView.findViewById(R.id.CoontactsMultipleNameCancel);
            PhoneCancel = itemView.findViewById(R.id.CoontactsMultiplePhoneCancel);
            EmailCancel = itemView.findViewById(R.id.CoontactsMultipleEmailCancel);
            AddressCancel = itemView.findViewById(R.id.CoontactsMultipleAddressCancel);
            JobCancel = itemView.findViewById(R.id.CoontactsMultipleJobTitleCancel);
            CompanyCancel = itemView.findViewById(R.id.CoontactsMultipleCompanyCancel);
            NoteCancel = itemView.findViewById(R.id.CoontactsMultipleNotesCancel);

            Cancel = itemView.findViewById(R.id.ContactsMultipleCancel);

            NameCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Name.setText("");
                }
            });

            PhoneCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Phone.setText("");
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

            Note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note.setText("");
                }
            });

//            Resources r = context.getResources();
//            int px = (int) TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP,
//                    1,
//                    r.getDisplayMetrics()
//            );
//            LinearLayout.LayoutParams VisibleFieldParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//            LinearLayout.LayoutParams GoneFieldParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//            LinearLayout.LayoutParams VisibleButtonParams = new LinearLayout.LayoutParams(2*px, 2*px);
//            LinearLayout.LayoutParams GoneButtonParams = new LinearLayout.LayoutParams(2*px, 2*px);
//
//
//            VisibleFieldParams.setMargins(0,0,10,10);


            More.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Email.getVisibility() == View.VISIBLE) {
                        Email.setVisibility(View.GONE);
                        EmailCancel.setVisibility(View.GONE);
                        More.setText("+ More");
                    } else {
                        Email.setVisibility(View.VISIBLE);
                        EmailCancel.setVisibility(View.VISIBLE);
                        More.setText("- Less");
                    }

                    if (Address.getVisibility() == View.VISIBLE) {
                        Address.setVisibility(View.GONE);
                        AddressCancel.setVisibility(View.GONE);
                    } else {
                        Address.setVisibility(View.VISIBLE);
                        AddressCancel.setVisibility(View.VISIBLE);
                    }

                    if (Job.getVisibility() == View.VISIBLE) {
                        Job.setVisibility(View.GONE);
                        JobCancel.setVisibility(View.GONE);
                    } else {
                        Job.setVisibility(View.VISIBLE);
                        JobCancel.setVisibility(View.VISIBLE);
                    }

                    if (Company.getVisibility() == View.VISIBLE) {
                        Company.setVisibility(View.GONE);
                        CompanyCancel.setVisibility(View.GONE);
                    } else {
                        Company.setVisibility(View.VISIBLE);
                        CompanyCancel.setVisibility(View.VISIBLE);
                    }

                    if (Note.getVisibility() == View.VISIBLE) {
                        Note.setVisibility(View.GONE);
                        NoteCancel.setVisibility(View.GONE);
                    } else {
                        Note.setVisibility(View.VISIBLE);
                        NoteCancel.setVisibility(View.VISIBLE);
                    }
                }
            });

        }


    }


    public ArrayList<DetailedContact> getmData() {
        return mData;
    }

    @SuppressLint("StaticFieldLeak")
    public void AddContacts() {

        AsyncTask<Integer, Integer, Integer> Task = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                Looper.prepare();
                for (int i = 0; i < getItemCount(); i++) {
                    Log.i("Adapter", "1");
                    if(Names[i].getText().toString().equals("")||Phones[i].getText().toString().equals("")){
                        Log.i("Adapter", "7");
                        continue;
                    }

                    Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(Phones[i].getText().toString()));
                    String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
                    Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
                    try {
                        if (cur.moveToFirst()) {
                            Toast.makeText(context, Phones[i].getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                            Log.i("Adapter", "10");
                            continue;
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (cur != null) {
                            cur.close();
                        }
                    }
                    Log.i("Adapter", "2");
                    ArrayList<ContentProviderOperation> ops =
                            new ArrayList<>();

                    int rawContactID = ops.size();

                    // Adding insert operation to operations list
                    // to insert a new raw contact in the table ContactsContract.RawContacts
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .build());

                    Log.i("Adapter", "3");
                    // Adding insert operation to operations list
                    // to insert display name in the table ContactsContract.Data
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, Names[i].getText().toString())
                            .build());

                    // Adding insert operation to operations list
                    // to insert Mobile Number in the table ContactsContract.Data
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, Phones[i].getText().toString())
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());

                    if(!Emails[i].getText().toString().equals("")) {
                        // Adding insert operation to operations list
                        // to insert Email in the table ContactsContract.Data
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, Emails[i].getText().toString())
                                .build());
                    }

                    if(!Addresses[i].getText().toString().equals("")) {
                        // Adding insert operation to operations list
                        // to insert Address in the table ContactsContract.Data
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, Addresses[i].getText().toString())
                                .build());
                    }

                    if(!Jobs[i].getText().toString().equals("")) {
                        // Adding insert operation to operations list
                        // to insert Company & Job in the table ContactsContract.Data
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, Company)
                                .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, Jobs[i].getText().toString())
                                .build());
                    }

                    if(!Notes[i].getText().toString().equals("")) {
                        // Adding insert operation to operations list
                        // to insert Notes in the table ContactsContract.Data
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Note.NOTE, Notes[i].getText().toString())
                                .build());
                    }
                    try {
                        // Executing all the insert operations as a single database transaction
                        context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                        Toast.makeText(context, "Contact successfully added", Toast.LENGTH_SHORT).show();

//                        Names[i].setText("");
//                        Phones[i].setText("");
//                        Emails[i].setText("");
//                        Addresses[i].setText("");
//                        Jobs[i].setText("");
//                        Company[i].setText("");
//                        Notes[i].setText("");

                        viewHolders[i].NameCancel.callOnClick();
                        viewHolders[i].PhoneCancel.callOnClick();
                        viewHolders[i].AddressCancel.callOnClick();
                        viewHolders[i].EmailCancel.callOnClick();
                        viewHolders[i].CompanyCancel.callOnClick();
                        viewHolders[i].JobCancel.callOnClick();
                        viewHolders[i].NoteCancel.callOnClick();

                        viewHolders[i].Cancel.callOnClick();

                        Log.i("Adapter", "4");
                        mData.remove(i);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (OperationApplicationException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        Task.execute();

        Log.i("Adapter", "5");
    }

}


