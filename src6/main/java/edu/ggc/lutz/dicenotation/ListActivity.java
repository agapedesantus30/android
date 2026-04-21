package edu.ggc.lutz.dicenotation;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.ggc.lutz.dicenotation.dummy.DummyContent;

public class ListActivity extends AppCompatActivity
        implements DialogAdd.AddNotationDialogListener,
        DialogRemove.RemoveNotationDialogListener {

    private boolean mTwoPane;

    private DetailFragment fragment;
    private SimpleItemRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment != null) {
                    fragment.newRoll();
                } else {
                    Toast.makeText(ListActivity.this, "Please pick a notation first!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        } else {
            fab.hide();
        }

        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_master_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }

        if (id == R.id.action_add) {
            FragmentManager manager = getFragmentManager();
            DialogAdd dialog = new DialogAdd();
            dialog.show(manager, "addNotationDialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishAddNotationDialog(String id) {
        DummyContent.addItem(new DummyContent.DummyItem(id,"",id));
        adapter.notifyItemInserted(adapter.getItemCount()-1);
    }

    @Override
    public void onFinishRemoveNotationDialog(String id, final int position) {
        DummyContent.removeItem(DummyContent.items.get(position));
        adapter.notifyItemRemoved(position);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleItemRecyclerViewAdapter(this, DummyContent.items, mTwoPane);
        recyclerView.setAdapter(adapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(ListActivity parent, List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(DetailFragment.ARG_ITEM_ID, item.id);
                        fragment = new DetailFragment();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(DetailFragment.ARG_ITEM_ID, item.id);
                        context.startActivity(intent);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Bundle arguments = new Bundle();
                    final DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                    arguments.putString(DetailFragment.ARG_ITEM_ID, item.id);
                    arguments.putInt("position", position);
                    DialogRemove yesNo;
                    yesNo = new DialogRemove();
                    yesNo.setArguments(arguments);
                    yesNo.show(mParentActivity.getFragmentManager(), "removeNotationDialog");
                    return true;
                }
            });
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
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }
}
