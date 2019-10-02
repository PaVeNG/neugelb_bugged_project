package com.bugged.themoviedb.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.bugged.themoviedb.injection.ApplicationContext;
import com.bugged.themoviedb.R;

import javax.inject.Inject;


//for showing alert dialog
public class AlertDialogManager {
    private Context context;

    @Inject
    public AlertDialogManager(@ApplicationContext Context context) {
        this.context = context;
    }

    //Showing alert if location is not enabled
    public void showAlertDialog(final Context context,String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                alertDialog.dismiss();
                //get gps
            }
        });

        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    public ProgressDialog showProgressBar(Context context){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        return progressDialog;
    }
}