package com.arrowsdashboard.mamatenderdash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

public class pin_code extends AppCompatActivity {

    PasscodeView pin_code ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        pin_code = findViewById(R.id.pin_code);

        pin_code.setPasscodeLength(4)
                .setLocalPasscode("1010")
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(pin_code.this, "الرمز خطأ حاول مرة اخري", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                        check_page(prevelant.page);
                        Toast.makeText(pin_code.this, "الرمز صحيح", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void check_page(String page) {
        if (page.equals("user")){
            startActivity(new Intent(this,user_activity.class));
        }
        else if (page.equals("delivery")){
            startActivity(new Intent(this,delivery_page.class));
        }
        else if (page.equals("promo")){
            startActivity(new Intent(this,promo_code_page.class));
        }
        else if (page.equals("point")){
            startActivity(new Intent(this,point_activity.class));
        }
    }
}