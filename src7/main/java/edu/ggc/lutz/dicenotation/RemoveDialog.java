package edu.ggc.lutz.dicenotation;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class RemoveDialog extends DialogFragment {

    private String itemId;
    private int position;

    public interface RemoveNotationDialogListener {
        void onFinishRemoveNotationDialog(String id, int position);
    }

    private RemoveNotationDialogListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getArguments().getString(DetailFragment.ARG_ITEM_ID);
        position = getArguments().getInt("position");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove " + itemId);
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                RemoveNotationDialogListener activity = (RemoveNotationDialogListener) getActivity();
                activity.onFinishRemoveNotationDialog(itemId, position);
                dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (RemoveNotationDialogListener) getActivity();
    }
}
