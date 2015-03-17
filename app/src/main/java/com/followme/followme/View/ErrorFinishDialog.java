package com.followme.followme.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Robinson on 14/03/15.
 */
public class ErrorFinishDialog {
    private String message;
    private String Button;
    private Activity weakCopy;

    public ErrorFinishDialog(String message, String button, Activity weakCopy) {
        this.message = message;
        Button = button;
        this.weakCopy = weakCopy;
    }

    public void openDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(weakCopy);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        weakCopy.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
