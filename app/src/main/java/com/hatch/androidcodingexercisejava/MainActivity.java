package com.hatch.androidcodingexercisejava;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String SORT_BY_FAVORITE_AND_LATEST_MODIFIED = ContactsContract.Contacts.STARRED + " DESC, " + ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP + " DESC";
    private static final int CONTACT_PERMISSION_REQUEST_CODE = 3000;

    private RecyclerView mRecyclerView;
    private TextView mEmptyStateTextView;
    private ContactCursorRecyclerAdapter mContactCursorRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mEmptyStateTextView = findViewById(R.id.emptyStateTextView);
        mContactCursorRecyclerAdapter = new ContactCursorRecyclerAdapter();
        mRecyclerView.setAdapter(mContactCursorRecyclerAdapter);

        checkForContactPermissions();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        switch (requestCode) {
            case CONTACT_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLoader();
                } else {
                    Toast.makeText(this, R.string.contact_permission_explanation, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(final int id, @Nullable final Bundle args) {
        return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, SORT_BY_FAVORITE_AND_LATEST_MODIFIED);
    }

    @Override
    public void onLoadFinished(@NonNull final Loader<Cursor> loader, final Cursor data) {
        int emptyStateVisibility = data!=null ? View.GONE : View.VISIBLE;
        int recyclerViewVisibility = data!=null ?View.VISIBLE : View.GONE;

        mEmptyStateTextView.setVisibility(emptyStateVisibility);
        mRecyclerView.setVisibility(recyclerViewVisibility);

        mContactCursorRecyclerAdapter.updateCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull final Loader<Cursor> loader) {

    }

    private void initLoader() {
        LoaderManager.getInstance(this).initLoader(1, null, this);
    }

    private void checkForContactPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        } else {
            initLoader();
        }
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, R.string.contact_permission_explanation, Toast.LENGTH_LONG).show();
                requestContactPermission();
            } else {
                requestContactPermission();
            }
        } else {
            initLoader();
        }
    }

    private void requestContactPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                CONTACT_PERMISSION_REQUEST_CODE);
    }
}
