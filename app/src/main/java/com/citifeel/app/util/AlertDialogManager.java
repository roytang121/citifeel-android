package com.citifeel.app.util;

/**
 * Created by ywng on 11/5/14.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {

    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialogBuilder.setTitle(title);

        // Setting Dialog Message
        alertDialogBuilder.setMessage(message);

        // Setting OK Button
        alertDialogBuilder.setPositiveButton("確定",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alertDialog=alertDialogBuilder.create();

        if(status != null) {
            // Setting alert dialog icon
            // alertDialog.setIcon((status) ? com.citifeel.app.R.drawable.success : com.citifeel.app.R.drawable.fail);
        }

        // Showing Alert Message
        alertDialog.show();
    }

    public ProgressDialog showProgress(Context context, String title, String msg, boolean intermediate, boolean cancelable) {
        //TODO: customize the progress dialog style down here
        return ProgressDialog.show(context, title, msg, intermediate, cancelable);
    }

}
