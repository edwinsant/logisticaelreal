package com.inventario.acreal.floatbutton.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.UI.Activities.ConfirmationActivity;

/**
 * Created by amelara on 06/11/2017.
 */

public class ShowDialog {

    // value 1: boxes
    // value 2: unit


    public static void showAlertDialog(Activity activity, final int value, final TextView control) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.confirm_counts_dialog, null);
        final EditText mCount = (EditText) view.findViewById(R.id.et_count);


            //Confirmar la Cantidad
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String totalUnit2;
                if(notEmpty(mCount.getText().toString())){
                    int count = Integer.parseInt(mCount.getText().toString());
                        control.setText(String.valueOf(count)) ;

                    }

              //  control.setText("123");
                dialog.dismiss();
            }
        });

        //Confirmar
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if(value == 1){
            builder.setTitle(activity.getResources().getString(R.string.change_count_boxes));
        }else{
            builder.setTitle(activity.getResources().getString(R.string.change_count_unit));
        }

        builder.setView(view);
        builder.create();
        final AlertDialog dialog = builder.show();

        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView tv = (TextView) dialog.findViewById(textViewId);
        tv.setTextColor(activity.getResources().getColor(R.color.negro));

        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(dividerId);
        if (divider != null){
            divider.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
        }




    }

    public static boolean notEmpty(String text) {
        return (text != null && text.length() > 0);
    }



}
