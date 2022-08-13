package com.example.appmonkeykeeping.custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.remote.ImageSet;

public class CustomDialogPreview extends AppCompatDialogFragment {
    private Uri uriImage;
    private ImageSet listener;
    ImageView imgCheck;

    public void setUriImage(Uri uriImage) {
        this.uriImage = uriImage;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_image_check,null);
        builder.setView(view).setTitle("Bet ?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Oui !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BitmapDrawable drawable = (BitmapDrawable) imgCheck.getDrawable();
                        listener.imageAfterCheck(drawable);
                    }
                });
        imgCheck = view.findViewById(R.id.img_preview);
        imgCheck.setImageURI(uriImage);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ImageSet) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }
}
