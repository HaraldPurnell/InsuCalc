package com.uglybearltd.insucalc;


import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private float min;
    private float max;

    InputFilterMinMax(float min, float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            String stringToMatch = dest.toString() + source.toString();
            float input = Float.parseFloat(stringToMatch);

            if (isInRange(min, max, input)) {
                return null;
            }
        } catch (NumberFormatException e) {
        }
        return "";
    }

    private boolean isInRange(float min, float max, float input) {
        return (input >= min && input <= max);
    }
}