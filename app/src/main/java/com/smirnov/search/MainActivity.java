package com.smirnov.search;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final Set<String> storage = new HashSet<>();

    EditText editTextScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int keysId = R.string.keys;
        int dict1 = R.string.dict1;
        int dict2 = R.string.dict2;
        int dict3 = R.string.dict3;
        int dict4 = R.string.dict4;
        int dict5 = R.string.dict5;
        int dict6 = R.string.dict6;
        int dict7 = R.string.dict7;
        //int valuesId = R.string.values;

        //String keys = getString(keysId);
        //int valuesId = R.string.values;
        //String values = getString(valuesId);
        //String[] keysSplit = keys.split("\\s+");
        //String[] valuesSplit = values.split(";");
        storage.addAll(getDict(dict1));
        storage.addAll(getDict(dict2));
        storage.addAll(getDict(dict3));
        storage.addAll(getDict(dict4));
        storage.addAll(getDict(dict5));
        storage.addAll(getDict(dict6));
        storage.addAll(getDict(dict7));

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

    private List<String> getDict(int dictId){
        String dict = getString(dictId);
        String[] dictSplit = dict.split("\\s+");
        return Arrays.asList(dictSplit);
    }

    private void getToast(String result) {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(getMessage(result));
        biggerText.setSpan(new RelativeSizeSpan(1.35f), 0, getMessage(result).length(), 0);
        Toast toast = Toast.makeText(MainActivity.this, biggerText, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    private String getMessage(String result) {
        String number = getNumbers(result);
        if (storage.contains(getNumbers(result))) {
            playSound(com.google.zxing.client.android.R.raw.zxing_beep);
            return getNumbers(result) + "\nобнаружен: " + number;
        }
        return getNumbers(result) + "\n-";
    }

    private void playSound(int resId){
        MediaPlayer mp = MediaPlayer.create(MainActivity.this, resId);
        mp.setOnCompletionListener(mediaPlayer -> {
            mediaPlayer.reset();
            mediaPlayer.release();
        });
        mp.start();
    }

//    private void getAlert(String result) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("Result");
//        builder.setMessage(getMessage(result));
//        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();
//    }

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