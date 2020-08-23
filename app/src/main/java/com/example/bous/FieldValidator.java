package com.example.bous;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.ArrayList;

public final class FieldValidator {
    /*  verifier si les champs sont remplis */
    public static FieldValidator fv = null;

    //singleton pattern

    private FieldValidator() {
        super();
    }

    public static synchronized FieldValidator getFieldValidor() {
        if (fv == null) {
            fv = new FieldValidator();
        }
        return fv;
    }

    public boolean estRempli(ArrayList<EditText> editTextArray) {
        boolean allFieldFilled = true;
        for (int i = 0; i < editTextArray.size(); i++) {
            if (TextUtils.isEmpty(editTextArray.get(i).getText())) {

                allFieldFilled = false;
                editTextArray.get(i).setError("Champs obligatoire");

            }
        }
        return allFieldFilled;
    }

    //surchage
    public boolean estRempli(EditText editText) {
        boolean allFieldFilled = true;
        if (TextUtils.isEmpty(editText.getText())) {

            allFieldFilled = false;
            editText.setError("Champs obligatoire");
        }
        return allFieldFilled;
    }


}
