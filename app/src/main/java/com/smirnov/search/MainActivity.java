package com.smirnov.search;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final Map<String, String> storage = new HashMap<>();

    EditText editTextScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int keysId = R.string.keys;
        int valuesId = R.string.values;

        String keys = getString(keysId);
        String values = getString(valuesId);
        String[] keysSplit = keys.split("\\s+");
        String[] valuesSplit = values.split(";");
        for (int i = 0; i < keysSplit.length; i++) {
            storage.put(keysSplit[i], valuesSplit[i]);
        }

        setContentView(R.layout.activity_main);
        editTextScan = findViewById(R.id.editTextText);
        editTextScan.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                String result = editTextScan.getText().toString();
                getToast(result);
                //getAlert(result);
                editTextScan.getText().clear();
                editTextScan.requestFocus();
                editTextScan.setSelection(0);
            }

            return false;
        });
    }

    private void getToast(String result) {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(getMessage(result));
        biggerText.setSpan(new RelativeSizeSpan(1.35f), 0, getMessage(result).length(), 0);
        Toast toast = Toast.makeText(MainActivity.this, biggerText, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    private String getMessage(String result) {
        if (storage.containsKey(getNumbers(result))) {
            return getNumbers(result) + "\nзадержка: " + storage.get(getNumbers(result));
        }
        return getNumbers(result) + "\nНе найдена";
    }

    private void getAlert(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Result");
        builder.setMessage(getMessage(result));
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();
    }

    private static String getNumbers(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char ch : chars) {
            if (Character.isDigit(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}