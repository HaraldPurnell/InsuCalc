package com.uglybearltd.insucalc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class InsuCalc extends AppCompatActivity {

    public EditText etDIA, etCBS, etTBS, etICR, etCV;
    public String sDIA, sCBS, sTBS, sICR, sCV;
    public TextView insuDose;
    public double dDIA, dCBS, dTBS, dICR, dCV, point1, point2, point3, point4, roPoint1, roPoint2, roPoint3, roPoint4;
    public int[] etIds;
    GraphView graph;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu_calc);

        // EditText assignments
        etDIA = findViewById(R.id.DIA);
        etCBS = findViewById(R.id.CBS);
        etTBS = findViewById(R.id.TBS);
        etICR = findViewById(R.id.ICR);
        etCV = findViewById(R.id.CV);

        insuDose =  findViewById(R.id.insuDose);
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


        Button buttonCalc = findViewById(R.id.CalcInsDos);
        Button buttonDiscl = findViewById(R.id.disclaimer);
        Button simulate = findViewById(R.id.simulate);

        buttonCalc.setOnClickListener(mListener);
        buttonDiscl.setOnClickListener(mListener);
        simulate.setOnClickListener(mListener);
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

                            double BID = dCV / dICR;
                            double ISF = 100 / dDIA;

                            double tempDose = (BID + ((dCBS - dTBS) / ISF));
                            double dInsuDose = roundToHalf(tempDose);

                            if (((dDIA > 100.0) || (dDIA < 10.0)) && ((dCBS > 22.0) || (dCBS < 2.0)) && ((dTBS > 8.0)
                                    || (dTBS < 5.0)) && ((dICR > 20.0) || (dICR < 1.0)) && ((dCV > 200.0) || (dCV < 1.0))) {
                                validateEditText(etIds);
                            } else if ((dDIA > 100.0) || (dDIA < 10.0)) {
                                etDIA.setError("Out of range!");
                            } else if ((dCBS > 22.0) || (dCBS < 2.0)) {
                                etCBS.setError("Out of range!");
                            } else if ((dTBS > 8.0) || (dTBS < 5.0)) {
                                etTBS.setError("Out of range!");
                            } else if ((dICR > 20.0) || (dICR < 1.0)) {
                                etICR.setError("Out of range!");
                            } else if ((dCV > 200.0) || (dCV < 1.0)) {
                                etCV.setError("Out of range!");
                            }
                            else {
                                clearErrorMessages(etIds);
                                insuDose.setError(null);
                                insuDose.setText(Double.toString(dInsuDose));
                            }
                        } catch (NumberFormatException e) {
                            validateEditText(etIds);
                        }
                        break;

                    case R.id.disclaimer:

                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.disclaimer);
                        dialog.setTitle("Disclaimer");

                        Button disclaimerButton = dialog.findViewById(R.id.quitDisclaimer);
                        disclaimerButton.setOnClickListener(new View.OnClickListener()
                                {
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog.show();

                    case R.id.simulate:
                        try {

                            graph.removeAllSeries();



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

                            String insuDosecheck = insuDose.getText().toString();

                            if (insuDosecheck.equals("00.0")) {
                                insuDose.requestFocus();
                                insuDose.setError("Press button: Calculate Insulin Dose");
                            }
                            else {
                                if (dCBS > dTBS){

                                    point1 = dCBS/0.775;
                                    point2 = dCBS/0.945;
                                    point3 = dCBS/1.069;
                                    point4 = dCBS/1.216875;

                                    roPoint1 = round(point1, 1);
                                    roPoint2 = round(point2, 1);
                                    roPoint3 = round(point3, 1);
                                    roPoint4 = round(point4, 1);

                                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                            new DataPoint(0, dCBS),
                                            new DataPoint(1, point1),
                                            new DataPoint(2, point2),
                                            new DataPoint(3, point3),
                                            new DataPoint(4, point4)
                                    });
                                    graph.addSeries(series);
                                }
                                else if (dCBS < dTBS) {

                                    point1 = dCBS/0.49;
                                    point2 = dCBS/0.56;
                                    point3 = dCBS/0.606;
                                    point4 = dCBS/0.6715;

                                    roPoint1 = round(point1, 1);
                                    roPoint2 = round(point2, 1);
                                    roPoint3 = round(point3, 1);
                                    roPoint4 = round(point4, 1);


                                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                            new DataPoint(0, dCBS),
                                            new DataPoint(1, point1),
                                            new DataPoint(2, point2),
                                            new DataPoint(3, point3),
                                            new DataPoint(4, point4)
                                    });
                                    graph.addSeries(series);
                                } else {

                                    point1 = dCBS/0.7252;
                                    point2 = dCBS/0.8238;
                                    point3 = dCBS/0.9612;
                                    point4 = dCBS/1.04575;
                                    roPoint1 = round(point1, 1);
                                    roPoint2 = round(point2, 1);
                                    roPoint3 = round(point3, 1);
                                    roPoint4 = round(point4, 1);

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
                            validateEditText(etIds);
                        }
                        break;
                }
        }
    };

    // Checks if strings length is empty and if so, returns true
    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    // For all editTexts in integer list, set error message
    public void validateEditText(int[] ids)
    {
        for(int id: ids)
        {
            EditText editText = findViewById(id);

            if (isEmpty(editText)) {
                editText.setError("You must Input a number!");
            }
            else {
                editText.setError("Out of range!");
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
}
