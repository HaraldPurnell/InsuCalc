package com.uglybearltd.insucalc;

/*
  * © 2018-2019 Harald Purnell Some Rights Reserved.
*/

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class InsuCalc extends AppCompatActivity {

    public EditText etDIA, etCBS, etTBS, etICR, etCV, output;
    public String sDIA, sCBS, sTBS, sICR, sCV;
    public TextView insuDose3, insuDose4, insuDose5, insuDose6, insuDose7;
    public double dDIA, dCBS, dTBS, dICR, dCV, point1, point2, point3, point4, roPoint1, roPoint2, roPoint3, roPoint4;
    public int[] etIds;
    GraphView graph;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu_calc);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Assignment of EditTexts
        etDIA = findViewById(R.id.DIA);
        etCBS = findViewById(R.id.CBS);
        etTBS = findViewById(R.id.TBS);
        etICR = findViewById(R.id.ICR);
        etCV = findViewById(R.id.CV);
        output = findViewById(R.id.output);

        output.setInputType(InputType.TYPE_NULL);
        output.setText("00.0");
        
        // Assignment of textviews used for testing purposes
        insuDose3 =  findViewById(R.id.insuDose3);
        insuDose4 =  findViewById(R.id.insuDose4);
        insuDose5 =  findViewById(R.id.insuDose5);
        insuDose6 =  findViewById(R.id.insuDose6);
        insuDose7 =  findViewById(R.id.insuDose7);
     
        // Assignment of graph
        graph = findViewById(R.id.graph);

        etIds = new int[]
                {
                        R.id.DIA,
                        R.id.CBS,
                        R.id.TBS,
                        R.id.ICR,
                        R.id.CV
                };

        graph.setTitle("Blood Sugar Simulated Change");

        // Assignment of buttons and their event handlers 
        Button buttonCalc = findViewById(R.id.CalcInsDos);
        Button buttonDiscl = findViewById(R.id.disclSim);
        Button buttonInstruct = findViewById(R.id.instrSim);
        Button simulate = findViewById(R.id.simulate);

        buttonCalc.setOnClickListener(mListener);
        buttonDiscl.setOnClickListener(mListener);
        buttonInstruct.setOnClickListener(mListener);
        simulate.setOnClickListener(mListener);

        // Hide Keyboard when interface is clicked outside of editText
        etDIA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etCBS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etTBS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etICR.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        etCV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.CalcInsDos:
                        try {

                            sDIA = etDIA.getText().toString();
                            sCBS = etCBS.getText().toString();
                            sTBS = etTBS.getText().toString();
                            sICR = etICR.getText().toString();
                            sCV = etCV.getText().toString();

                            dDIA = Double.valueOf(sDIA);
                            dCBS = Double.valueOf(sCBS);
                            dTBS = Double.valueOf(sTBS);
                            dICR = Double.valueOf(sICR);
                            dCV = Double.valueOf(sCV);

                            if (checkRange()) {
                                double dInsuDose = calcDose(dDIA, dCBS, dTBS, dICR, dCV);
                                clearErrorMessages(etIds);
                                output.setError(null);
                                output.setText(Double.toString(dInsuDose));
                            }

                        } catch (NumberFormatException e) {
                            checkEmpty(etIds);
                        }
                        break;

                    // Opens additional windows without closing the current one and display relevant text
                    case R.id.disclSim:
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.disclaimer);
                        dialog.setTitle("Disclaimer");
                        dialog.show();
                        break;

                    case R.id.instrSim:
                        final Dialog dialog1 = new Dialog(context);
                        dialog1.setContentView(R.layout.instructions);
                        dialog1.setTitle("Instructions");
                        dialog1.show();
                        break;

                    case R.id.simulate:
                        try {

                            graph.removeAllSeries();
                            
                            // Convert values gathered from edit text to usable data types
                            sDIA = etDIA.getText().toString();
                            sCBS = etCBS.getText().toString();
                            sTBS = etTBS.getText().toString();
                            sICR = etICR.getText().toString();
                            sCV = etCV.getText().toString();

                            dDIA = Double.valueOf(sDIA);
                            dCBS = Double.valueOf(sCBS);
                            dTBS = Double.valueOf(sTBS);
                            dICR = Double.valueOf(sICR);
                            dCV = Double.valueOf(sCV);

                            String insuDosecheck = output.getText().toString();

                            if (insuDosecheck.equals("00.0")) {
                                output.setError("Something went wrong (See instructions for help)!");
                            }
                            else {
                                if (dCBS > dTBS){
                                    // Calculate each point on the graph
                                    point1 = dCBS/0.775;
                                    point2 = dCBS/0.945;
                                    point3 = dCBS/1.069;
                                    point4 = dCBS/1.216875;

                                    roPoint1 = round(point1, 1);
                                    roPoint2 = round(point2, 1);
                                    roPoint3 = round(point3, 1);
                                    roPoint4 = round(point4, 1);

                                    insuDose3.setText(Double.toString(dCBS));
                                    insuDose4.setText(Double.toString(roPoint1));
                                    insuDose5.setText(Double.toString(roPoint2));
                                    insuDose6.setText(Double.toString(roPoint3));
                                    insuDose7.setText(Double.toString(roPoint4));
                                    
                                    // Create graph using the points calculated above
                                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                            new DataPoint(0, dCBS),
                                            new DataPoint(1, roPoint1),
                                            new DataPoint(2, roPoint2),
                                            new DataPoint(3, roPoint3),
                                            new DataPoint(4, roPoint4)
                                    });
                                    graph.addSeries(series);
                                }
                                else if (dCBS < dTBS) {
                                    // Calculate each point on the graph
                                    point1 = dCBS/0.49;
                                    point2 = dCBS/0.56;
                                    point3 = dCBS/0.606;
                                    point4 = dCBS/0.6715;

                                    roPoint1 = round(point1, 1);
                                    roPoint2 = round(point2, 1);
                                    roPoint3 = round(point3, 1);
                                    roPoint4 = round(point4, 1);

                                    insuDose3.setText(Double.toString(dCBS));
                                    insuDose4.setText(Double.toString(roPoint1));
                                    insuDose5.setText(Double.toString(roPoint2));
                                    insuDose6.setText(Double.toString(roPoint3));
                                    insuDose7.setText(Double.toString(roPoint4));
                                    
                                    // Create graph using the points calculated above
                                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                            new DataPoint(0, dCBS),
                                            new DataPoint(1, roPoint1),
                                            new DataPoint(2, roPoint2),
                                            new DataPoint(3, roPoint3),
                                            new DataPoint(4, roPoint4)
                                    });
                                    graph.addSeries(series);
                                } else {
                                    // Calculate each point on the graph
                                    point1 = dCBS/0.7252;
                                    point2 = dCBS/0.8238;
                                    point3 = dCBS/0.9612;
                                    point4 = dCBS/1.04575;

                                    roPoint1 = round(point1, 1);
                                    roPoint2 = round(point2, 1);
                                    roPoint3 = round(point3, 1);
                                    roPoint4 = round(point4, 1);

                                    insuDose3.setText(Double.toString(dCBS));
                                    insuDose4.setText(Double.toString(roPoint1));
                                    insuDose5.setText(Double.toString(roPoint2));
                                    insuDose6.setText(Double.toString(roPoint3));
                                    insuDose7.setText(Double.toString(roPoint4));
                                    
                                    // Create graph using the points calculated above
                                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                            new DataPoint(0, dCBS),
                                            new DataPoint(1, roPoint1),
                                            new DataPoint(2, roPoint2),
                                            new DataPoint(3, roPoint3),
                                            new DataPoint(4, roPoint4)
                                    });
                                    graph.addSeries(series);
                                }
                            }
                        } catch (NumberFormatException e) {
                            output.setError("Something went wrong (See instructions for help)!");
                        }
                        break;
                }

        }
    };

    // Calculate Insulin Dose using formula specified in design document
    public double calcDose(double DIA, double CBS, double TBS, double ICR, double CV) {
        double BID = CV / ICR;
        double ISF = 100 / DIA;

        double tempDose = (BID + ((CBS - TBS) / ISF));
        return roundToHalf(tempDose);
    }

    // Checks if strings length is empty and if so, returns true
    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    // For all editTexts in integer list, set error message if empty
    public void validateEditText(int[] ids)
    {
        for(int id: ids)
        {
            EditText editText = findViewById(id);

            if (isEmpty(editText)) {
                editText.setError("You must Input a number!");
            }
        }
    }

    // Check if any edit text value is out of range and set error if possible
    public boolean checkRange() {
        if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0)
                || (dTBS < 5.0)) && ((dICR > 20.0) || (dICR < 1.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }

        // 4's
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0)
                || (dTBS < 5.0)) && ((dICR > 20.0) || (dICR < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0)
                || (dTBS < 5.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0)) &&
                ((dICR > 20.0) || (dICR < 1.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dTBS > 8.0) || (dTBS < 5.0))
                && ((dICR > 20.0) || (dICR < 1.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0) || (dTBS < 5.0))
                && ((dICR > 20.0) || (dICR < 1.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }

        // 3's
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0))
                && ((dTBS > 8.0) || (dTBS < 5.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0))
                && ((dICR > 20.0) || (dICR < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0))
                && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dTBS > 8.0) || (dTBS < 5.0))
                && ((dICR > 20.0) || (dICR < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dTBS > 8.0) || (dTBS < 5.0))
                && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dICR > 20.0) || (dICR < 1.0))
                && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0) || (dTBS < 5.0))
                && ((dICR > 20.0) || (dICR < 1.0))) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            return false;
        }
        else if (((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0) || (dTBS < 5.0))
                && ((dCV > 200.0) || (dCV < 1.0))) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dCBS > 22.0) || (dCBS < 2.0)) && ((dICR > 20.0) || (dICR < 1.0))
                && ((dCV > 200.0) || (dCV < 1.0))) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dTBS > 8.0) || (dTBS < 5.0)) && ((dICR > 20.0) || (dICR < 1.0))
                && ((dCV > 200.0) || (dCV < 1.0))) {
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }

        // 2's
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dTBS > 8.0) || (dTBS < 5.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dICR > 20.0) || (dICR < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            return false;
        }
        else if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0) || (dTBS < 5.0))) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            return false;
        }
        else if (((dCBS > 22.0) || (dCBS < 2.0)) && ((dICR > 20.0) || (dICR < 1.0))) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            return false;
        }
        else if (((dCBS > 22.0) || (dCBS < 2.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dTBS > 8.0) || (dTBS < 5.0)) && ((dICR > 20.0) || (dICR < 1.0))) {
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etICR.setError("Please input a number between 1.0 and 20.0!");
            return false;
        }
        else if (((dTBS > 8.0) || (dTBS < 5.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        else if (((dICR > 20.0) || (dICR < 1.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
            etICR.setError("Please input a number between 1.0 and 20.0!");
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }

        // 1's
        else if ((dDIA > 100.0) || (dDIA < 10.0)) {
            etDIA.setError("Please input a number between 10.0 and 100.0!");
            return false;
        }
        else if ((dCBS > 22.0) || (dCBS < 2.0)) {
            etCBS.setError("Please input a number between 2.0 and 22.0!");
            return false;
        }
        else if ((dTBS > 8.0) || (dTBS < 5.0)) {
            etTBS.setError("Please input a number between 5.0 and 8.0!");
            return false;
        }
        else if ((dICR > 20.0) || (dICR < 1.0)) {
            etICR.setError("Please input a number between 1.0 and 20.0!");
        }
        else if ((dCV > 200.0) || (dCV < 1.0)) {
            etCV.setError("Please input a number between 1.0 and 200.0!");
            return false;
        }
        return true;
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

    // For all editTexts in integer list, clear error messages
    public void clearErrorMessages(int[] ids)
    {
        for(int id: ids)
        {
            EditText editText = findViewById(id);
            editText.setError(null);
        }
    }

    // Rounds double value to nearest .5
    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    // Rounds double value to specified decimal places
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    // Hides soft keyboard
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void launchSimulator(View view) {
        Intent intent = new Intent(this, Simulator.class);
        startActivity(intent);
    }
}
