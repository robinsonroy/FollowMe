package com.followme.followme.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by Robinson on 14/03/15.
 */
public class ErrorDialog {
    private String message;
    private String button;
    private Activity weakCopy;

    public ErrorDialog(String message, String button, Activity weakCopy) {
        this.message = message;
        this.button = button;
        this.weakCopy = weakCopy;
    }

    public void openDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(weakCopy);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(button,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
