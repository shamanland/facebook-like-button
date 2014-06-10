package com.shamanland.facebook.likebutton.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class OpenUrlDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle state) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.open_browser)
                .setMessage(getArguments().getString("url"))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getArguments().getString("url")));
                        getActivity().startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create();
    }
}
