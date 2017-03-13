package com.tford.threestacks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //private ThreeStacks threeStacks = new ThreeStacks();
    private ThreeStacks2 threeStacks = new ThreeStacks2();
    private ThreeStacksProcessor cellFiller;
    private Drawable emptyCellBackground;
    private Drawable fullCellBackground;

    private static String cellIdTemplate = "stack%d_cell%d";
    private final static int STACK_OVERFLOW_REQUEST = 1;
    public final static String EXTRA_STACK_ID = "com.tford.threestacks.STACK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cellFiller = new ThreeStacksProcessor() {
            public void process(int i, int j, int value) {
                String cellId = String.format(cellIdTemplate, i, j);
                int resID = getResources().getIdentifier(cellId, "id", getPackageName());
                TextView cell = (TextView) findViewById(resID);
                cell.setText(String.valueOf(value));
                cell.setBackground(fullCellBackground);
            }
        };
        emptyCellBackground = getResources().getDrawable(R.drawable.empty_cell_shape, null);
        fullCellBackground = getResources().getDrawable(R.drawable.cell_shape, null);
    }

    public void pushStack1(View view) {
        pushStack(0);
    }

    public void popStack1(View view) {
        popStack(0);
    }

    public void pushStack2(View view) {
        pushStack(1);
    }

    public void popStack2(View view) {
        popStack(1);
    }

    public void pushStack3(View view) {
        pushStack(2);
    }

    public void popStack3(View view) {
        popStack(2);
    }

    private void pushStack(int i) {
        Integer stackTop = threeStacks.getStackTop(i);
        int newValue = 1;
        if (stackTop != null) {
            newValue = stackTop + 1;
        }
        try {
            threeStacks.push(i, newValue);
            updateStackView();
        } catch (StackOverflowError e) {
            handleStackOverflow(String.valueOf(i + 1));
        }
    }

    private void popStack(int i) {
        Integer popped = threeStacks.pop(i);
        if (popped != null) {
            updateStackView();
        }
    }

    private void handleStackOverflow(String stackId) {
        Intent intent = new Intent(this, StackOverflowActivity.class);
        intent.putExtra(EXTRA_STACK_ID, stackId);
        startActivityForResult(intent, STACK_OVERFLOW_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == STACK_OVERFLOW_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                System.out.println(
                        "Inside MainActivity.onActivityResult, " +
                        "realized the Stack Overflow dialog was canceled."
                );
            }
        }
    }



    private void updateStackView() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clearStackCell(i, j);
            }
        }
        threeStacks.iterate(cellFiller);
    }

    private void clearStackCell(int i, int j) {
        String cellId = String.format(cellIdTemplate, i, j);
        int resID = getResources().getIdentifier(cellId, "id", getPackageName());
        TextView textView = (TextView) findViewById(resID);
        textView.setText("");
        textView.setBackground(emptyCellBackground);
    }
}
