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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClipboardManager.OnPrimaryClipChangedListener {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private LinearLayout lMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clipboard_history);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        lMain = (LinearLayout) findViewById(R.id.lMain);
        myClipboard.addPrimaryClipChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView b = (TextView)v;
        String text = b.getText().toString();
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "Text Copied",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrimaryClipChanged() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        int btnGravity = Gravity.LEFT;
        ClipData cp = myClipboard.getPrimaryClip();
        ClipData.Item item = cp.getItemAt(0);
        String text = item.getText().toString();
        layoutParams.gravity = btnGravity;
        TextView tvNew = new TextView(this);
        tvNew.setClickable(true);
        tvNew.setOnClickListener(this);
        tvNew.setText(text);
        lMain.addView(tvNew, layoutParams);
    }
}
