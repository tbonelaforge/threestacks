package com.tford.threestacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tford on 3/9/17.
 */
public class StackOverflowActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_overflow);
        Intent intent = getIntent();
        String stackId = intent.getStringExtra(MainActivity.EXTRA_STACK_ID);
        TextView textView = (TextView) findViewById(R.id.stack_overflow_message);
        textView.setText("Cannot push anymore onto stack " + stackId);
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        super.finish();
    }
}
