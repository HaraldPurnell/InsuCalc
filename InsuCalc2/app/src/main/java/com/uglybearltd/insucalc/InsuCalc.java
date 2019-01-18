package com.uglybearltd.insucalc;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InsuCalc extends AppCompatActivity {

    public EditText etDIA, etCBS, etTBS, etICR, etCV;
    public String sDIA, sCBS, sTBS, sICR, sCV;
    public TextView insuDose;
    public float fDIA, fCBS, fTBS, fICR, fCV;
    public int[] etIds;
    public Button buttonCalc, buttonDiscl;
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

        etIds = new int[]
                {
                        R.id.DIA,
                        R.id.CBS,
                        R.id.TBS,
                        R.id.ICR,
                        R.id.CV
                };

        Button buttonCalc = findViewById(R.id.CalcInsDos);
        Button buttonDiscl = findViewById(R.id.disclaimer);

        buttonCalc.setOnClickListener(mListener);
        buttonDiscl.setOnClickListener(mListener);
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

                            float fDIA = Float.valueOf(sDIA);
                            float fCBS = Float.valueOf(sCBS);
                            float fTBS = Float.valueOf(sTBS);
                            float fICR = Float.valueOf(sICR);
                            float fCV = Float.valueOf(sCV);

                            float BID = fCV / fICR;
                            float ISF = 100 / fDIA;

                            float fInsuDose = Math.round((BID + ((fCBS - fTBS) / ISF)) * 2) / 2;

                            if ((fDIA > 100.0) && (fDIA < 10.0) && (fCBS > 24.0) && (fCBS < 2.0) && (fTBS > 8.0)
                                    && (fTBS < 5.0) && (fICR > 20.0) || (fICR < 1.0) && (fCV > 200.0) && (fCV < 1.0)) {
                                validateEditText(etIds);
                            } else if ((fDIA > 100.0) || (fDIA < 10.0)) {
                                validateEditText(etIds);
                            } else if ((fCBS > 24.0) || (fCBS < 2.0)) {
                                validateEditText(etIds);
                            } else if ((fTBS > 8.0) || (fTBS < 5.0)) {
                                validateEditText(etIds);
                            } else if ((fICR > 20.0) || (fICR < 1.0)) {
                                validateEditText(etIds);
                            } else if ((fCV > 200.0) || (fCV < 1.0)) {
                                validateEditText(etIds);
                            } else {
                                insuDose.setText(Float.toString(fInsuDose));
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

                }
        }
    };

    // Checks if strings length is empty and if so, returns true
    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public void validateEditText(int[] ids)
    {
        for(int id: ids)
        {
            EditText editText = findViewById(id);

            if (isEmpty(editText)) {
                editText.setError("Incorrect Input");
            }
            else {
                editText.setError("Out of range");
            }
        }
    }

}
