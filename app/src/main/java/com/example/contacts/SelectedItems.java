package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectedItems extends AppCompatActivity implements MyInterface {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    List<Contacts> selectedItems ;


    private static final String TAG = "" ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_items);
        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        selectedItems =b.getParcelableArrayList("checkBoxValue");
        recyclerView = findViewById(R.id.list);

        Log.e( "onCreate:",selectedItems.size()+ "");
        setRecyclerView();

    }
    private void setRecyclerView() {
        if (selectedItems!= null&& selectedItems.size()>0) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Collections.sort(selectedItems, Contacts.ContactsAZComparator);
            adapter = new RecyclerAdapter(inflater, selectedItems, this, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }


    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        //Bundle bundle = new Bundle();
       // bundle.putParcelableArrayList("array", (ArrayList<? extends Parcelable>) selectedItems);
        returnIntent.putParcelableArrayListExtra("result", (ArrayList<? extends Parcelable>) selectedItems);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

}