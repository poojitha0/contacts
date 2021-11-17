package com.example.contacts;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

public class ExampleDialog extends AppCompatDialogFragment {


    private EditText name;
    private EditText phoneNum;
    private ExampleDialogListener listener;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


         View view = inflater.inflate(R.layout.dialog_layout, null);

         builder.setView(view)
                 .setTitle("Add Contacts")
                 .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.dismiss();
                     }
                 })
         .setPositiveButton("ok", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

                 String Name = name.getText().toString();
                 String PhoneNum = phoneNum.getText().toString();

                listener.applyTexts(Name,PhoneNum);

             }
         });

         name = view.findViewById(R.id.name_edt);
         phoneNum = view.findViewById(R.id.phoneNum_edt);
                 return builder.create();
     }
    @Override
     public void onAttach(Context context) {
        super.onAttach(context);


         try {
             listener = (ExampleDialogListener) context;
         } catch (ClassCastException e) {
             throw new ClassCastException(context.toString()+"just implement ExampleDialog Listener");
         }
     }


    public interface ExampleDialogListener{

         void applyTexts(String Name, String PhoneNum);
    }
}
