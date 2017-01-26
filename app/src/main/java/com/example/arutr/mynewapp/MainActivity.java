package com.example.arutr.mynewapp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClipboardManager.OnPrimaryClipChangedListener {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private ListView lMain;
    ArrayList<String> clipboardList = new ArrayList<String>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clipboard_history);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        lMain = (ListView) findViewById(R.id.lMain);
        myClipboard.addPrimaryClipChangedListener(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, clipboardList);
        lMain.setAdapter(adapter);
        lMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView b = (TextView) view;
                String text = b.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });

        lMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        TextView b = (TextView) v;
        String text = b.getText().toString();
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "Text Copied",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrimaryClipChanged() {
        Boolean existenceOfSimilarWordInArray = false;
        ClipData cp = myClipboard.getPrimaryClip();
        ClipData.Item item = cp.getItemAt(0);
        String text = item.getText().toString();
        for (String string : clipboardList) {
            if (string.equals(text)) {
                existenceOfSimilarWordInArray = true;
                break;
            }
        }
        if (!existenceOfSimilarWordInArray) {
            clipboardList.add(text);
            adapter.notifyDataSetChanged();
        }
    }
}
