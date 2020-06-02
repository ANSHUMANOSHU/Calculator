package com.media.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity{

    private TextView result, realtime;
    private String equation = "";
    private String tempResult;
    private String operators = "-+*/%";

    private ImageButton backSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        realtime = findViewById(R.id.realTime);

        backSpace = findViewById(R.id.backspace);

    }

    public void equalsResult(View view) {
        equation = realtime.getText().toString();
        tempResult = equation;
        result.setText(equation);
        realtime.setText("");
    }

    private void evaluate() {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
            String solution = engine.eval(equation).toString();
            realtime.setText(solution);
        } catch (Exception ignored) {
            realtime.setText("");
        }
    }

    public void clear(View view) {
        result.setText("0");
        equation = "";
        realtime.setText("0");
    }

    public void backspace() {
        if (!equation.equals("")) {
            equation = equation.substring(0, equation.length() - 1);
            result.setText(equation);
            evaluate();
        } else {
            result.setText("0");
            realtime.setText("0");
        }
    }

    public void equation(View view) {
        if (equation.equals(tempResult) && !operators.contains(view.getTag().toString())) {
            equation = "";
            realtime.setText("");
            result.setText("");
        }

        if (equation.length() == 40) {
            Toast.makeText(this, "maximum length 40 exceeded", Toast.LENGTH_SHORT).show();
            return;
        }
        equation = equation + view.getTag();
        result.setText(equation);
        evaluate();
    }


}
