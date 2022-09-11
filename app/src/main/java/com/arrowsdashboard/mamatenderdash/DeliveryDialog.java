package com.arrowsdashboard.mamatenderdash;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class DeliveryDialog extends Dialog implements Validator.ValidationListener {
    @NotEmpty(message = "يجب ادخال السعر")
    public EditText delivery_input;
    @NotEmpty(message = "يجب ادخال المنطقة")
    public EditText area_input;
    public Button add_btn;
    private final Validator validator;
    private boolean validate =false;
    public DeliveryDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delivery_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        delivery_input =findViewById(R.id.delivery_input);
        area_input = findViewById(R.id.area_input);
        add_btn = findViewById(R.id.add_area);

        validator = new Validator(this);
        validator.setValidationListener(this);
        getWindow().setAttributes(lp);

    }

    public Validator getValidator() {
        return validator;
    }

    public boolean isValidate() {
        return validate;
    }


    @Override
    public void onValidationSucceeded() {
        validate = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
