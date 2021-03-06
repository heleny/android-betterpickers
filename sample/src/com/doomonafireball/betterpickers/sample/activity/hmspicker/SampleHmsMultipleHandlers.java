package com.doomonafireball.betterpickers.sample.activity.hmspicker;

import com.doomonafireball.betterpickers.hmspicker.HmsPickerBuilder;
import com.doomonafireball.betterpickers.hmspicker.HmsPickerDialogFragment;
import com.doomonafireball.betterpickers.sample.R;
import com.doomonafireball.betterpickers.sample.activity.BaseSampleActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * User: derek Date: 3/17/13 Time: 3:59 PM
 */
public class SampleHmsMultipleHandlers extends BaseSampleActivity
        implements HmsPickerDialogFragment.HmsPickerDialogHandler {

    private TextView text;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_and_button);

        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);

        text.setText("--");
        button.setText("Set Hms");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HmsPickerBuilder hpb = new HmsPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment)
                        .addHmsPickerDialogHandler(new MyCustomHandler());
                hpb.show();
            }
        });
    }

    class MyCustomHandler implements HmsPickerDialogFragment.HmsPickerDialogHandler {

        @Override
        public void onDialogHmsSet(int reference, int hours, int minutes) {
            Toast.makeText(SampleHmsMultipleHandlers.this, "MyCustomHandler onDialogHmsSet!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onDialogHmsSet(int reference, int hours, int minutes) {
        text.setText("" + hours + ":" + minutes);
    }
}
