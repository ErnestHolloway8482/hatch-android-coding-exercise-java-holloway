package com.hatch.androidcodingexercisejava;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private Contact mModel;

    private ProgressBar mProgressBar;
    private TextView mContactName;
    private TextView mLastModifiedDate;
    private ImageView mFavoriteIcon;
    private Button mSendTextMessageButton;


    public ContactViewHolder(final View view) {
        super(view);
        initView();
    }

    public void bindDataModel(final Contact model) {
        mModel = model;

        clearViews();

        mProgressBar.setVisibility(View.GONE);
        mContactName.setText(mModel.name);

        updateLastModifiedTime(mModel);

        updateFavoriteIcon(mModel);
    }

    private void initView() {
        mProgressBar = itemView.findViewById(R.id.progressBar);
        mContactName = itemView.findViewById(R.id.contactName);
        mLastModifiedDate = itemView.findViewById(R.id.lastModifiedDate);
        mFavoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        mSendTextMessageButton = itemView.findViewById(R.id.sendMessageButton);

        mSendTextMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                displaySendMessageAlertDialog();
            }
        });
    }

    private void clearViews() {
        mProgressBar.setVisibility(View.VISIBLE);
        mContactName.setText(null);
        mLastModifiedDate.setText(null);
        mFavoriteIcon.setVisibility(View.GONE);
    }

    private void updateFavoriteIcon(final Contact model) {
        int favoriteDrawable = model.isFavorite ? R.drawable.vector_icon_favorite : R.drawable.vector_icon_unfavorite;
        mFavoriteIcon.setImageResource(favoriteDrawable);
        mFavoriteIcon.setVisibility(View.VISIBLE);
    }

    private void updateLastModifiedTime(final Contact model) {
        String formattedTime = getFormattedDateTime(model.lastModifiedDate);
        mLastModifiedDate.setVisibility(TextUtils.isEmpty(formattedTime) ? View.GONE : View.VISIBLE);
        mLastModifiedDate.setText(formattedTime);
    }

    private void sendMessage() {
        Context context = itemView.getContext();

        //We will only start the SMS intent message if the contact actually has a phone numnber.
        if (context instanceof Activity && mModel.hasPhoneNumber) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", mModel.mobilePhoneNumber, null)));
        }
    }

    private void displaySendMessageAlertDialog() {
        Context context = itemView.getContext();

        if (context instanceof Activity) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.dialog_title)
                    .setMessage(R.string.dialog_message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, final int i) {
                            sendMessage();
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialogInterface, final int i) {

                }
            }).create().show();
        }
    }

    private String getFormattedDateTime(final String timeStamp) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
            Calendar calender = Calendar.getInstance();
            TimeZone currentTimeZone = calender.getTimeZone();
            timeFormat.setTimeZone(currentTimeZone);
            Date date = new Date(Long.parseLong(timeStamp));
            return timeFormat.format(date);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
