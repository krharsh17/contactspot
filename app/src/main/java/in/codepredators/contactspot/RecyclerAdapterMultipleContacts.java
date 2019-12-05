package in.codepredators.contactspot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapterMultipleContacts extends RecyclerView.Adapter<RecyclerAdapterMultipleContacts.ViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<DetailedContact> ContactsList;
    private ArrayList<ViewHolder> viewHolders;


    ArrayList<DetailedContact> getContactsList() {
        return ContactsList;
    }

    void setContactsList(ArrayList<DetailedContact> ContactsList) {
        this.ContactsList = ContactsList;
    }

    // data is passed into the constructor
    RecyclerAdapterMultipleContacts(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        viewHolders = new ArrayList<>();
        ContactsList = new ArrayList<>();
        ContactsList.add(new DetailedContact(0));
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.multiple_contacts_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (position >= viewHolders.size()) {
            viewHolders.add(position, holder);
            Log.i("DEBUG", "Viewholder added - " + position);
        }

        Log.i("DEBUG", "Binded - " + position);

        holder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemCount() == 1) {
                    Toast.makeText(context, "Cannot delete the last contact", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.Name.setText("");
                holder.MobilePhone.setText("");
                holder.Email.setText("");
                holder.Address.setText("");
                holder.Job.setText("");
                holder.Company.setText("");
                holder.Note.setText("");
                viewHolders.remove(Integer.parseInt(holder.ID.getText().toString()));
                ContactsList.remove(Integer.parseInt(holder.ID.getText().toString()));
                notifyItemRemoved(Integer.parseInt(holder.ID.getText().toString()));

                for (int i = 0; i < viewHolders.size(); i++) {
                    viewHolders.get(i).ID.setText(String.valueOf(i));
                }
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return ContactsList.size();
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView More;
        EditText Name;
        EditText MobilePhone;
        EditText WorkPhone;
        EditText HomePhone;
        EditText Email;
        EditText Address;
        EditText Job;
        EditText Company;
        EditText Note;

        Button NameCancel;
        Button MobilePhoneCancel;
        Button WorkPhoneCancel;
        Button HomePhoneCancel;
        Button EmailCancel;
        Button AddressCancel;
        Button JobCancel;
        Button CompanyCancel;
        Button NoteCancel;
        Button MobilePhoneAdd;
        Button WorkPhoneAdd;

        TextView ID;

        ImageView Cancel;

        ViewHolder(View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.ItemID);
            Name = itemView.findViewById(R.id.ContactsMultipleName);
            MobilePhone = itemView.findViewById(R.id.ContactsMultiplePhone);
            WorkPhone = itemView.findViewById(R.id.ContactsMultipleWorkNumber);
            HomePhone = itemView.findViewById(R.id.ContactsHomeNumber);
            Email = itemView.findViewById(R.id.ContactsMultipleEmail);
            Address = itemView.findViewById(R.id.ContactsMultipleAddress);
            Job = itemView.findViewById(R.id.ContactsMultipleJobTitle);
            Company = itemView.findViewById(R.id.ContactsMultipleCompany);
            Note = itemView.findViewById(R.id.ContactsMultipleNotes);
            More = itemView.findViewById(R.id.ContactsMultipleMore);
            NameCancel = itemView.findViewById(R.id.CoontactsMultipleNameCancel);
            MobilePhoneCancel = itemView.findViewById(R.id.CoontactsMultiplePhoneCancel);
            WorkPhoneCancel = itemView.findViewById(R.id.ContactsAddNumberCancel);
            HomePhoneCancel = itemView.findViewById(R.id.CoontactsHomeNumberCancel);
            EmailCancel = itemView.findViewById(R.id.CoontactsMultipleEmailCancel);
            AddressCancel = itemView.findViewById(R.id.CoontactsMultipleAddressCancel);
            JobCancel = itemView.findViewById(R.id.CoontactsMultipleJobTitleCancel);
            CompanyCancel = itemView.findViewById(R.id.CoontactsMultipleCompanyCancel);
            NoteCancel = itemView.findViewById(R.id.CoontactsMultipleNotesCancel);
            MobilePhoneAdd = itemView.findViewById(R.id.CoontactsMultiplePhoneAdd);
            WorkPhoneAdd = itemView.findViewById(R.id.coontactsMultipleWorkAdd);

            final RelativeLayout.LayoutParams VisibleFieldParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            final RelativeLayout.LayoutParams GoneFieldParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            VisibleFieldParams.setMargins(0, 10, 0, 0);
            GoneFieldParams.setMargins(0, 0, 0, 0);

            MobilePhoneAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkPhone.setVisibility(View.VISIBLE);
                    WorkPhoneAdd.setVisibility(View.VISIBLE);
                    WorkPhoneCancel.setVisibility(View.VISIBLE);
                    WorkPhone.setLayoutParams(VisibleFieldParams);
                    MobilePhoneAdd.setVisibility(View.GONE);
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

            Cancel = itemView.findViewById(R.id.ContactsMultipleCancel);

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

            WorkPhoneCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (WorkPhone.getText().toString().equals("")) {
                        WorkPhone.setVisibility(View.GONE);
                        WorkPhoneAdd.setVisibility(View.GONE);
                        WorkPhoneCancel.setVisibility(View.GONE);
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
                        HomePhoneCancel.setVisibility(View.GONE);
                        if (WorkPhone.getVisibility() == View.VISIBLE) {
                            WorkPhoneAdd.setVisibility(View.VISIBLE);
                        } else {
                            MobilePhoneAdd.setVisibility(View.VISIBLE);
                        }
                    } else {
                        HomePhone.setText("");
                    }
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


            More.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RtlHardcoded")
                @Override
                public void onClick(View v) {
                    if (!((Dashboard) context).isEmail)
                        if (Email.getVisibility() == View.VISIBLE) {
                            Email.setVisibility(View.GONE);
                            EmailCancel.setVisibility(View.GONE);
                            More.setText("+ More");
                            Email.setLayoutParams(GoneFieldParams);
                            More.setGravity(Gravity.RIGHT);
                        } else {
                            Email.setVisibility(View.VISIBLE);
                            EmailCancel.setVisibility(View.VISIBLE);
                            More.setText("- Less");
                            Email.setLayoutParams(VisibleFieldParams);
                            More.setGravity(Gravity.RIGHT);
                        }

                    if (!((Dashboard) context).isAddress)
                        if (Address.getVisibility() == View.VISIBLE) {
                            Address.setVisibility(View.GONE);
                            AddressCancel.setVisibility(View.GONE);
                            Address.setLayoutParams(GoneFieldParams);
                        } else {
                            Address.setVisibility(View.VISIBLE);
                            AddressCancel.setVisibility(View.VISIBLE);
                            Address.setLayoutParams(VisibleFieldParams);
                        }

                    if (!((Dashboard) context).isJob)
                        if (Job.getVisibility() == View.VISIBLE) {
                            Job.setVisibility(View.GONE);
                            JobCancel.setVisibility(View.GONE);
                            Job.setLayoutParams(GoneFieldParams);
                        } else {
                            Job.setVisibility(View.VISIBLE);
                            JobCancel.setVisibility(View.VISIBLE);
                            Job.setLayoutParams(VisibleFieldParams);
                        }

                    if (!((Dashboard) context).isCompany)
                        if (Company.getVisibility() == View.VISIBLE) {
                            Company.setVisibility(View.GONE);
                            CompanyCancel.setVisibility(View.GONE);
                            Company.setLayoutParams(GoneFieldParams);
                        } else {
                            Company.setVisibility(View.VISIBLE);
                            CompanyCancel.setVisibility(View.VISIBLE);
                            Company.setLayoutParams(VisibleFieldParams);
                        }

                    if (!((Dashboard) context).isNotes)
                        if (Note.getVisibility() == View.VISIBLE) {
                            Note.setVisibility(View.GONE);
                            NoteCancel.setVisibility(View.GONE);
                            Note.setLayoutParams(GoneFieldParams);
                        } else {
                            Note.setVisibility(View.VISIBLE);
                            NoteCancel.setVisibility(View.VISIBLE);
                            Note.setLayoutParams(VisibleFieldParams);
                        }
                    if(More.getText().equals("+More"))
                        More.setText("-Less");
                    else
                        More.setText("+More");
                }
            });
            VisibilityChanger();
        }

        void VisibilityChanger() {
            if (((Dashboard) context).isEmail) {
                Email.setVisibility(View.VISIBLE);
                EmailCancel.setVisibility(View.VISIBLE);
            } else {
                Email.setVisibility(View.GONE);
                EmailCancel.setVisibility(View.GONE);
            }

            if (((Dashboard) context).isAddress) {
                Address.setVisibility(View.VISIBLE);
                AddressCancel.setVisibility(View.VISIBLE);
            } else {
                Address.setVisibility(View.GONE);
                AddressCancel.setVisibility(View.GONE);
            }

            if (((Dashboard) context).isCompany) {
                Company.setVisibility(View.VISIBLE);
                CompanyCancel.setVisibility(View.VISIBLE);
            } else {
                Company.setVisibility(View.GONE);
                CompanyCancel.setVisibility(View.GONE);
            }

            if (((Dashboard) context).isJob) {
                Job.setVisibility(View.VISIBLE);
                JobCancel.setVisibility(View.VISIBLE);
            } else {
                Job.setVisibility(View.GONE);
                JobCancel.setVisibility(View.GONE);
            }

            if (((Dashboard) context).isNotes) {
                Note.setVisibility(View.VISIBLE);
                NoteCancel.setVisibility(View.VISIBLE);
            } else {
                Note.setVisibility(View.GONE);
                NoteCancel.setVisibility(View.GONE);
            }


        }


    }


    ArrayList<ViewHolder> getViewHolders() {
        return viewHolders;
    }

}


