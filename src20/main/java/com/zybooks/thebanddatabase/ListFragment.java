package com.zybooks.thebanddatabase;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListFragment extends Fragment {

    // For the activity to implement
    public interface OnDiceSelectedListener {
        void onDiceSelected(int bandId);
    }

    // Reference to the activity
    private OnDiceSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.dice_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Send bands to recycler view
        DiceAdapter adapter = new DiceAdapter(DiceDatabase.get(getContext()).getDices());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class DiceHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Dice mDice;

        private TextView mNameTextView;

        public DiceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_dice, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.diceName);
        }

        public void bind(Dice dice) {
            mDice = dice;
            mNameTextView.setText(mDice.getName());
        }

        @Override
        public void onClick(View view) {
            // Tell ListActivity what band was clicked
            mListener.onDiceSelected(mDice.getId());
        }
    }

    private class DiceAdapter extends RecyclerView.Adapter<DiceHolder> {

        private List<Dice> mDices;

        public DiceAdapter(List<Dice> dice) {
            mDices = dice;
        }

        @Override
        public DiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DiceHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DiceHolder holder, int position) {
            Dice dice = mDices.get(position);
            holder.bind(dice);
        }

        @Override
        public int getItemCount() {
            return mDices.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDiceSelectedListener) {
            mListener = (OnDiceSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDiceSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Notify activity of band selection
            String bandId = (String) view.getTag();
            mListener.onDiceSelected(Integer.parseInt(bandId));
        }
    };
}
