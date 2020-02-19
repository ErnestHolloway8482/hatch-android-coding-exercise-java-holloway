package com.hatch.androidcodingexercisejava;

import android.database.Cursor;
import android.provider.ContactsContract;

public class Contact {
    public boolean isFavorite;
    public String name;
    public boolean hasPhoneNumber;
    public String mobilePhoneNumber;
    public String lastModifiedDate;

    /**/

    /**
     * CTOR
     *
     * @param cursor is the ContactsProvider cursor based on the SQL Query.
     *               <p>
     *               Note that the assumption here is that we are referring to a phone contact only. We are not using e-mail account types, twitter account types, etc.
     */
    public Contact(final Cursor cursor) {
        if (cursor == null) {
            return;
        }

        isFavorite = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.STARRED)) == 1;
        name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) == 1;
        mobilePhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        lastModifiedDate = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP));
    }
}
