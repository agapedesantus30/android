package edu.ggc.lutz.dicenotation;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class DialogAdd extends DialogFragment {

    public interface AddNotationDialogListener {
        void onFinishAddNotationDialog(String inputText);
    }

    private AddNotationDialogListener mListener;
    private EditText mEditText;
    private AlertDialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_notation, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.enter_new_notation);
        builder.setView(view);
        mEditText = view.findViewById(R.id.etInput);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                boolean valid = DiceNotation.isValid(s.toString());
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(valid);
            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                AddNotationDialogListener activity = (AddNotationDialogListener) getActivity();
                activity.onFinishAddNotationDialog(mEditText.getText().toString());
                DialogAdd.this.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                DialogAdd.this.dismiss();
            }
        });
        dialog = builder.create();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AddNotationDialogListener) getActivity();
    }

}
