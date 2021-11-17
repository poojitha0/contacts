package com.example.contacts;

import static android.content.ContentValues.TAG;
import static java.util.Locale.filter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener, MyInterface {


    private ArrayList<Contacts> selectUsers = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    Cursor phones;
    private FloatingActionButton floatingActionButton;
    private SearchView searchView;
    private CheckBox checkBox;
    private Contacts contacts;
    private SwipeRefreshLayout refresh;


    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contacts_list);
        refresh = findViewById(R.id.refresh);
        showContacts();
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

            public void openDialog() {
                ExampleDialog exampleDialog = new ExampleDialog();
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }


        });


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadContact().execute();
                refresh.setRefreshing(false);
                //Toast.makeText(MainActivity.this, "refreshing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void applyTexts(String Name, String PhoneNum) {

        Log.e("TAG", "applyTexts: ");
        Contacts contacts = new Contacts();
        contacts.setName(Name);
        contacts.setPhone(PhoneNum);
        selectUsers.add(contacts);
        adapter.notifyDataSetChanged();
    }

    public void onItemClick(int position) {

        Toast.makeText(MainActivity.this, "selectUsers.get(position)", Toast.LENGTH_SHORT).show();


    }


   public void onLongItemClick(int position) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete contact")
                .setMessage("do you want to delete this item")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectUsers.remove(position);
                        adapter.notifyDataSetChanged();
                        // setRecyclerView();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();

    }


    private void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {

            phones = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            new LoadContact().execute();

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                showContacts();
            } else {

                Toast.makeText(MainActivity.this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT);

            }
        }
    }


    class LoadContact extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {

                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    @SuppressLint("Range") String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    @SuppressLint("Range") String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Contacts selectUser = new Contacts();
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUsers.add(selectUser);
                    Log.e("data", name + phoneNumber);

                }

            } else {
                Log.e("Cursor close 1", "----------------");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setRecyclerView();

        }

        protected void onPreExecute() {
            super.onPreExecute();
        }


    }

    private void setRecyclerView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter = new RecyclerAdapter(inflater, selectUsers,this,false);
        Collections.sort(selectUsers, Contacts.ContactsAZComparator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        MenuItem close = menu.findItem(R.id.close);
        SubMenu sub = delete.getSubMenu();
        SubMenu sub1 = close.getSubMenu();

        //sub.add(0,1 , 0, "Item 1").setShortcut('3', 'c');

        MenuItem SearchViewItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(SearchViewItem);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {

                Context context = getApplicationContext();
                CharSequence start = "Start";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, start, duration);
                toast.show();
                return false;

            }

            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return true;
            }

            private void filter(String newText) {
                List<Contacts> filteredList = new ArrayList<>();
                for (Contacts item : selectUsers) {
                    if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                adapter.filterList(filteredList);
            }

        });

        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete contact")
                        .setMessage("Are you sure want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {

                                deleteSelectedItems();

                                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();

                            }

                            private void deleteSelectedItems() {


                                for (int i = 0; i < selectUsers.size(); i++) {
                                    if (selectUsers.get(i).isChecked()) {
                                        Log.d("testingTAG", String.valueOf(i));
                                        selectUsers.remove(i);
                                       // adapter.notifyItemRemoved(i);
                                        //adapter.notifyItemRangeChanged(i, selectUsers.size());
                                        i--;
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }


                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                break;

            case R.id.close:

                for (int i = 0; i < selectUsers.size(); i++) {

                    selectUsers.get(i).setChecked(false);
                }

                adapter.notifyDataSetChanged();
                break;

            case R.id.share:
                List<Contacts> selectedContacts = new ArrayList<>();
                for (int i = 0; i < selectUsers.size(); i++) {

                    if (selectUsers.get(i).isChecked() == true) {
                        selectedContacts.add(selectUsers.get(i));
                    }

                }
                    Intent intent = new Intent(this, SelectedItems.class);
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("checkBoxValue", (ArrayList<? extends Parcelable>) selectedContacts);
                    intent.putExtras(b);
                   // startActivity(intent);
                    startActivityForResult(intent, 1);


                    break;

            default:
                break;
                }

                return true;
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
               // String SelectedItems =data.getStringExtra("items");
                Bundle b = data.getExtras();
                final ArrayList<Contacts> newlist = b.getParcelableArrayList("result");
                Toast.makeText(this, "selected items "+newlist.size(), Toast.LENGTH_LONG).show();
                /*selectUsers = newlist;
                adapter.notifyDataSetChanged();*/

            }  if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            }
        }

    }
}



