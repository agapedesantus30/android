package com.zybooks.thebanddatabase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private Dice mDice;

    public static DetailsFragment newInstance(int bandId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("diceId", bandId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the band ID from the intent that started DetailsActivity
        int diceId = 1;
        if (getArguments() != null) {
            diceId = getArguments().getInt("diceId");
        }

        mDice = DiceDatabase.get(getContext()).getDice(diceId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calc, container, false);

        TextView nameTextView = view.findViewById(R.id.diceName);
        nameTextView.setText(mDice.getName());

        TextView calculationTextView = view.findViewById(R.id.bandCalculation);
        calculationTextView.setText(mDice.getCalculation());

        return view;
    }
}
