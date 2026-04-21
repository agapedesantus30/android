package edu.ggc.lutz.listpersistsolution;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import edu.ggc.lutz.listpersistsolution.dummy.DummyContent;

import static edu.ggc.lutz.listpersistsolution.NotationPersistenceContract.NotationEntry.COLUMN_NAME_CONTENT;
import static edu.ggc.lutz.listpersistsolution.NotationPersistenceContract.NotationEntry.COLUMN_NAME_DETAILS;
import static edu.ggc.lutz.listpersistsolution.NotationPersistenceContract.NotationEntry.COLUMN_NAME_ID;
import static edu.ggc.lutz.listpersistsolution.NotationPersistenceContract.NotationEntry.TABLE_NAME;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private boolean isSharedPrefsPersist = true;
    private SimpleItemRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            int rand = new Random().nextInt(100);
            String content = "Item " + rand;
            String details = "Item " + rand;
            DummyContent.addItem(
                    new DummyContent.DummyItem(Integer.toString(rand), content, details));
            adapter.notifyItemInserted(adapter.getItemCount()-1);
            return true;
        }

        if (id == R.id.action_delete_last) {
            int loc = DummyContent.removeLast();
            adapter.notifyItemRemoved(loc);
            return true;
        }

        if (id == R.id.action_clear) {
            DummyContent.clear();
            adapter.notifyDataSetChanged();
            return true;
        }

        if (id == R.id.action_switch_persistence) {
            int resource = (isSharedPrefsPersist) ?
                    R.string.switch_to_sharedprefs:
                    R.string.switch_to_sql;
            String title = getResources().getString(resource);
            item.setTitle(title);
            isSharedPrefsPersist = !isSharedPrefsPersist;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (isSharedPrefsPersist) {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            StringBuffer sb = new StringBuffer();
            for (DummyContent.DummyItem item : DummyContent.ITEMS) {
                if (sb.length() > 0) sb.append(",");
                sb.append(item.id); sb.append(",");
                sb.append(item.content); sb.append(",");
                sb.append(item.details);
            }
            editor.putString(TABLE_NAME, sb.toString());
            editor.commit();
        } else {
            NotationPersistenceDbHelper dbHelper =
                    new NotationPersistenceDbHelper(this.getApplicationContext());
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = COLUMN_NAME_ID + " LIKE ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { "*" };
            // Issue SQL statement.
            int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            for (DummyContent.DummyItem item : DummyContent.ITEMS) {
                values.put(COLUMN_NAME_ID, item.id);
                values.put(COLUMN_NAME_CONTENT, item.content);
                values.put(COLUMN_NAME_DETAILS, item.details);
                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(TABLE_NAME, null, values);
            }

            Cursor mCount= db.rawQuery("select count(*) from " + TABLE_NAME, null);
            mCount.moveToFirst();
            int count= mCount.getInt(0);
            Log.v("ListPersist", "written = " + count);
            mCount.close();
        }

    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isSharedPrefsPersist) {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            String s = prefs.getString("notations", "");
            Log.v("ListPersist", "resume ... " + s);
            String[] values = s.split(",");
            if (!s.equals("")) {
                DummyContent.ITEMS.clear(); // scorch the earth!
                DummyContent.ITEM_MAP.clear();
            }
            for (int i = 0; i <= values.length-3; i+=3) {
                DummyContent.addItem(
                        new DummyContent.DummyItem(values[i], values[i+1], values[i+2]));
            }
        } else {
            NotationPersistenceDbHelper dbHelper =
                    new NotationPersistenceDbHelper(ItemListActivity.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("select * from notations",null);
            if (c.getCount() > 0) {
                DummyContent.ITEMS.clear(); // scorch the earth!
                DummyContent.ITEM_MAP.clear();
            }
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(COLUMN_NAME_ID));
                String content = c.getString(c.getColumnIndex(COLUMN_NAME_CONTENT));
                String details = c.getString(c.getColumnIndex(COLUMN_NAME_DETAILS));
                DummyContent.ITEMS.add(new DummyContent.DummyItem(id, content, details));
            }

            Log.i("ListPersist", "read in and added: " + c.getCount());
        }
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane);
        recyclerView.setAdapter(adapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
