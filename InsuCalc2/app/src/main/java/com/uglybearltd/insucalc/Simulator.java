package com.uglybearltd.insucalc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class Simulator extends AppCompatActivity {

    public EditText bs1h, bs2h, bs3h, bs4h, cbsVal;
    public Button addData;
    public int[] etIds;
    GraphView graphV2;
    Context context = this;
    public List<Double> listBs1H, listBs2H, listBs3H, listBs4H;
    public ArrayAdapter<Double> adapter1H, adapter2H, adapter3H, adapter4H;
    public String strCBS, str1H, str2H, str3H, str4H;
    public double doCBS, do1H, do2H, do3H, do4H, point1, point2, point3, point4, roPoint1, roPoint2, roPoint3, roPoint4;
    public Integer i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bs1h = findViewById(R.id.bs1hVal);
        bs2h = findViewById(R.id.bs2hVal);
        bs3h = findViewById(R.id.bs3hVal);
        bs4h = findViewById(R.id.bs4hVal);
        cbsVal = findViewById(R.id.cbsConsVal);

        addData = findViewById(R.id.addData);
        Button createGraph = findViewById(R.id.createGraph);
        Button disclSim = findViewById(R.id.disclSim);
        Button instrSim = findViewById(R.id.instrSim);

        createGraph.setOnClickListener(mListener);
        disclSim.setOnClickListener(mListener);
        instrSim.setOnClickListener(mListener);

        graphV2 = findViewById(R.id.graphV2);

        listBs1H = new ArrayList<Double>();
        adapter1H = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_1, listBs1H);

        listBs2H = new ArrayList<Double>();
        adapter2H = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_1, listBs2H);

        listBs3H = new ArrayList<Double>();
        adapter3H = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_1, listBs3H);

        listBs4H = new ArrayList<Double>();
        adapter4H = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_1, listBs4H);

        etIds = new int[]
                {
                        R.id.bs1hVal,
                        R.id.bs2hVal,
                        R.id.bs3hVal,
                        R.id.bs4hVal
                };


        bs1h.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        bs2h.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        bs3h.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        bs4h.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        cbsVal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (checkRange(etIds)) {
                        str1H = bs1h.getText().toString();
                        do1H = Double.valueOf(str1H);
                        listBs1H.add(do1H);
                        //adapter1H.notifyDataSetChanged();

                        str2H = bs2h.getText().toString();
                        do2H = Double.valueOf(str2H);
                        listBs2H.add(do2H);
                        //adapter2H.notifyDataSetChanged();

                        str3H = bs3h.getText().toString();
                        do3H = Double.valueOf(str3H);
                        listBs3H.add(do3H);
                        //adapter3H.notifyDataSetChanged();

                        str4H = bs4h.getText().toString();
                        do4H = Double.valueOf(str4H);
                        listBs4H.add(do4H);
                        //adapter4H.notifyDataSetChanged();

                        bs1h.setText(null);
                        bs2h.setText(null);
                        bs3h.setText(null);
                        bs4h.setText(null);

                    }
                }
                catch (NumberFormatException e) {
                    checkEmpty(etIds);
                }
            }
        });

    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.disclSim:
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.disclaimer_simulator);
                    dialog.setTitle("Disclaimer");
                    dialog.show();
                    break;

                case R.id.instrSim:
                    final Dialog dialog1 = new Dialog(context);
                    dialog1.setContentView(R.layout.instruct_sim);
                    dialog1.setTitle("Instructions");
                    dialog1.show();
                    break;

                case R.id.createGraph:

                    try {
                        double sumArray1h = 0, sumArray2h = 0, sumArray3h = 0, sumArray4h = 0;
                        graphV2.removeAllSeries();

                        if ((listBs1H.size() == 0)) {
                            cbsVal.setError("The Array is Empty!");
                        } else {
                            if (isEmpty(cbsVal)) {
                                cbsVal.setError("No value has been entered!");
                            } else {
                                if (checkRange(cbsVal)) {

                                    strCBS = cbsVal.getText().toString();
                                    doCBS = Double.valueOf(strCBS);

                                    for (i = 0; i < listBs1H.size(); i++) {
                                        sumArray1h += listBs1H.get(i);
                                    }

                                    double factor1 = ((sumArray1h / listBs1H.size()) / doCBS);
                                    point1 = (factor1 * doCBS);
                                    roPoint1 = round(point1, 2);

                                    for (i = 0; i < listBs2H.size(); i++) {
                                        sumArray2h += listBs2H.get(i);
                                    }

                                    double factor2 = ((sumArray2h / listBs2H.size()) / doCBS);
                                    point2 = (factor2 * doCBS);
                                    roPoint2 = round(point2, 2);

                                    for (i = 0; i < listBs3H.size(); i++) {
                                        sumArray3h += listBs3H.get(i);
                                    }

                                    double factor3 = ((sumArray3h / listBs3H.size()) / doCBS);
                                    point3 = (factor3 * doCBS);
                                    roPoint3 = round(point3, 2);

                                    for (i = 0; i < listBs4H.size(); i++) {
                                        sumArray4h += listBs4H.get(i);
                                    }

                                    double factor4 = ((sumArray4h / listBs4H.size()) / doCBS);
                                    point4 = (factor4 * doCBS);
                                    roPoint4 = round(point4, 2);

                                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                                            new DataPoint(0, doCBS),
                                            new DataPoint(1, roPoint1),
                                            new DataPoint(2, roPoint2),
                                            new DataPoint(3, roPoint3),
                                            new DataPoint(4, roPoint4)
                                    });
                                    graphV2.addSeries(series);
                                    break;
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        cbsVal.setError("No value has been entered!");
                    }


            }
        }
    };

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public void checkEmpty(int[] ids) {
        for(int id: ids)
        {
            EditText editText = findViewById(id);

            if (isEmpty(editText)) {
                editText.setError("You must Input a number!");
            }
        }
    }

    public boolean checkRange(int[] ids) {
        for(int id: ids)
        {
            EditText editText = findViewById(id);
            String stringET = editText.getText().toString();
            Double doubleET = Double.valueOf(stringET);

            if ((doubleET < 2.0) || (doubleET > 22.0)) {
                editText.setError("You must enter a value within the valid range!");
                return false;
            }
            else {
                checkEmpty(ids);
            }
        }
        return true;
    }

    public boolean checkRange(EditText editText) {

        String stringET = editText.getText().toString();
        Double doubleET = Double.valueOf(stringET);

        if ((doubleET < 2.0) || (doubleET > 22.0)) {
            editText.setError("You must enter a value within the valid range!");
            return false;
        }
        return true;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void launchInsuCalc(View view) {
        Intent intent = new Intent(this, InsuCalc.class);
        startActivity(intent);
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

}
