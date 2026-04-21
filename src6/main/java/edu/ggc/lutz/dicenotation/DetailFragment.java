package edu.ggc.lutz.dicenotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ggc.lutz.dicenotation.dummy.DummyContent;

public class DetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private TextView result;
    private TextView formula;

    private DiceNotation notation;

    private DummyContent.DummyItem mItem;

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.itemMap.get(getArguments().getString(ARG_ITEM_ID));
            notation = new DiceNotation(mItem.details);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail, container, false);

        if (mItem != null) {
            notation = new DiceNotation(mItem.details);
            TextView label = rootView.findViewById(R.id.item_detail);
            label.setText(mItem.details);
            result = rootView.findViewById(R.id.tvResult);
            result.setText("###");
            formula = rootView.findViewById(R.id.tvFormula);
            formula.setText("");


        }
        return rootView;
    }

    public void newRoll() {
        result.setText(String.format("%d", notation.roll()));
        formula.setText(notation.getLastFormula());
    }
}
