package com.example.contacts;

import static android.content.ContentValues.TAG;
import static android.media.CamcorderProfile.get;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {


    private LayoutInflater layoutInflater;
    public List<Contacts> cont;
    Contacts list;
    //Context context;
    private ArrayList<Contacts> arraylist;
    View vv;
    private List<Contacts> contactList;
    private List<Contacts> contactListFiltered;
    //private RecyclerAdapterListener listener;
    private MyInterface myInterface;
    boolean isCheckBoxEnable = false;
    boolean isFromSelectedItems = false;

    public RecyclerAdapter(LayoutInflater inflater, List<Contacts> items, MyInterface myInterface, boolean isFromSelectedItems) {
        this.layoutInflater = inflater;
        this.cont = items;
        this.arraylist = new ArrayList<Contacts>();
        this.arraylist.addAll(cont);
        this.myInterface = myInterface;
        //this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.isFromSelectedItems = isFromSelectedItems;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.contactlist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        list = cont.get(position);
        String name = (list.getName());
        holder.title.setText(name);
        holder.phone.setText(list.getPhone());

        if (!isFromSelectedItems) {
            if (cont.get(position).isChecked) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }

            if (isCheckBoxEnable) {
                holder.checkBox.setVisibility(View.VISIBLE);
            } else {
                holder.checkBox.setVisibility(View.GONE);
            }
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    cont.get(position).setChecked(b);
                    //notifyDataSetChanged();
                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    myInterface.onItemClick(position);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (!isCheckBoxEnable) {
                        isCheckBoxEnable = true;
                        notifyDataSetChanged();
                    }

                    return false;
                }

            });

        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cont.size();
    }

    public void filterList(List<Contacts> filteredList) {
        cont = filteredList;
        notifyDataSetChanged();
    }

}


class ViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView phone;
    public LinearLayout contact_select_layout;
    public CheckBox checkBox;
    View view;

    public ViewHolder(View itemView) {
        super(itemView);
        this.setIsRecyclable(false);
        title = (TextView) itemView.findViewById(R.id.name);
        phone = (TextView) itemView.findViewById(R.id.no);
        contact_select_layout = (LinearLayout) itemView.findViewById(R.id.contact_select_layout);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        view = itemView;

    }

}








