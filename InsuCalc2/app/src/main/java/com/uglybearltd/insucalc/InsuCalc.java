package com.uglybearltd.insucalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class InsuCalc extends AppCompatActivity {

    public EditText etDIA, etCBS, etTBS, etICR, etCV;
    public String sDIA, sCBS, sTBS, sICR, sCV;
    public TextView insuDose;
    public float fDIA, fCBS, fTBS, fICR, fCV;
    public int[] etIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu_calc);

        // EditText assignments and limitFilters
        etDIA = findViewById(R.id.DIA);
        InputFilterMinMax limitFilterDIA = new InputFilterMinMax((float) 1.00, (float) 100.00);
        etDIA.setFilters(new InputFilter[]{ limitFilterDIA}); //fine

        etCBS = findViewById(R.id.CBS);
        InputFilterMinMax limitFilterCBS = new InputFilterMinMax((float) 1.00, (float) 23.00);
        etCBS.setFilters(new InputFilter[]{ limitFilterCBS}); //fine

        etTBS = findViewById(R.id.TBS);
        InputFilterMinMax limitFilterTBS = new InputFilterMinMax((float) 5.00, (float) 8.00);
        etTBS.setFilters(new InputFilter[]{ limitFilterTBS}); // Upper limit not working

        etICR = findViewById(R.id.ICR);
        InputFilterMinMax limitFilterICR = new InputFilterMinMax((float) 1.00, (float) 20.00);
        etICR.setFilters(new InputFilter[]{ limitFilterICR}); //fine

        etCV = findViewById(R.id.CV);
        InputFilterMinMax limitFilterCV = new InputFilterMinMax((float) 1.00, (float) 200.00);
        etCV.setFilters(new InputFilter[]{ limitFilterCV}); //fine

        insuDose =  findViewById(R.id.insuDose);

        etIds = new int[]
                {
                        R.id.DIA,
                        R.id.CBS,
                        R.id.TBS,
                        R.id.ICR,
                        R.id.CV
                };
    }

    public void calcDose(View view) {

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

            float BID = fCV/fICR;
            float ISF = 100/fDIA;

            float fInsuDose = Math.round((BID + ((fCBS - fTBS)/ISF)) * 2) / 2;
            insuDose.setText(Float.toString(fInsuDose));

        } catch (NumberFormatException e) {
            validateEditText(etIds);
        }
    }

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
        }
    }
    /*
    public float roundToHalf(float number) {
        return Math.round(number * 2) / 2;
    } */
}
