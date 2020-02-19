package com.hatch.androidcodingexercisejava;

import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Utility class to allow for easy creation of {RecyclerView.Adapter}s which need to read data from a {@link Cursor}.
 * This object handles moving the cursor position asynchronously, then calling the data binding back on the main thread.
 * <p>
 * It is assumed only one {RecyclerView.ViewHolder} type and only one corresponding application data model (<D>) will be in use.
 *
 * @param <VH> The type of {@link RecyclerView.ViewHolder} that will be used
 * @param <D>  The application data model object which will be bound to the {@link RecyclerView.ViewHolder}
 */
public abstract class BaseRecyclerViewCursorAdapter<VH extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<VH> {
    private Cursor mCursor;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(getItemLayoutResId(), parent, false);
        return createViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CursorAdapterAsyncTask cursorAdapterAsyncTask = new CursorAdapterAsyncTask(position, holder);
        cursorAdapterAsyncTask.execute();
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    /**
     * Implement this method to specify the layout to be used by this {@link RecyclerView}
     *
     * @return the layout id for the corresponding layout resource.
     */
    protected abstract int getItemLayoutResId();

    /**
     * Implement this method to create the {@link RecyclerView.ViewHolder} to be used by this {@link RecyclerView}
     *
     * @param itemView is the {@link View} object used by this recyclerview.
     * @return
     */
    protected abstract VH createViewHolder(View itemView);

    /**
     * Create the data model from a {@link Cursor} which has already been moved to the correct position
     *
     * @param cursor A cursor which has been moved to the correct position to read from
     * @return A data model representing the data at the current {@link Cursor} position
     */
    protected abstract D createData(Cursor cursor);

    /**
     * Bind the data (which can be null) to the given view holder
     *
     * @param data   The data to bind
     * @param holder The view holder to bind data to
     */
    protected abstract void bindData(D data, @NonNull VH holder);

    public void updateCursor(final Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    /**
     * This class is used to handle converting the cursor position based on the available size of the Cursor query
     * into a data model. Since the requirements call for not using any 3rd party libraries the AsyncTask is being
     * used in lieu of RxJava.
     */
    public class CursorAdapterAsyncTask extends AsyncTask<Cursor, D, D> {
        private final int mPosition;
        private final VH mViewHolder;

        public CursorAdapterAsyncTask(final int position, final VH viewHolder) {
            mPosition = position;
            mViewHolder = viewHolder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final D data) {
            bindData(data, mViewHolder);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected D doInBackground(final Cursor... cursor) {
            D data = null;

            if (mCursor != null && mCursor.moveToPosition(mPosition)) {
                data = createData(mCursor);
            }
            
            return data;
        }
    }
}