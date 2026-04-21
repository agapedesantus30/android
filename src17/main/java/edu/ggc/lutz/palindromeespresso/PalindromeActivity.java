package edu.ggc.lutz.palindromeespresso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PalindromeActivity extends AppCompatActivity {

    private static String TAG = "PalindromeApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palindrome);

        final EditText input = findViewById(R.id.etInput);
        final ImageView imageResult = findViewById(R.id.ivResult);
        final TextView textResult = findViewById(R.id.tvResult);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = input.getText().toString();

                if (Palindrome.check(value)) {
                    Log.v(TAG, value + " is a palindrome)");
                    imageResult.setImageResource(R.drawable.check);
                    imageResult.setTag(R.drawable.check);
                    textResult.setText("Is a Palindrome");
                } else {
                    Log.v(TAG, value + " is not a palindrome)");
                    imageResult.setImageResource(R.drawable.x);
                    imageResult.setTag(R.drawable.x);
                    textResult.setText("Is NOT a Palindrome");
                }
            }
        };
        input.addTextChangedListener(watcher);

    }
}
