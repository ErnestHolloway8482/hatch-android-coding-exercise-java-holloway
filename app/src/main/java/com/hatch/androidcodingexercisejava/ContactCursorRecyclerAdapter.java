package com.hatch.androidcodingexercisejava;

import android.database.Cursor;
import android.view.View;

import androidx.annotation.NonNull;

public class ContactCursorRecyclerAdapter extends BaseRecyclerViewCursorAdapter<ContactViewHolder, Contact>{

    @Override
    protected int getItemLayoutResId() {
        return R.layout.contact_item;
    }

    @Override
    protected void bindData(final Contact data, @NonNull final ContactViewHolder holder) {
        holder.bindDataModel(data);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected ContactViewHolder createViewHolder(final View itemView) {
        return new ContactViewHolder(itemView);
    }

    @Override
    protected Contact createData(final Cursor cursor) {
        return new Contact(cursor);
    }
}
