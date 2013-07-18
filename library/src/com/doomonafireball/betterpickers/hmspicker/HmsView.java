package com.doomonafireball.betterpickers.hmspicker;

import com.doomonafireball.betterpickers.R;
import com.doomonafireball.betterpickers.ZeroTopPaddingTextView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class HmsView extends LinearLayout {

    private ZeroTopPaddingTextView mHoursOnes, mHoursTens;
    private ZeroTopPaddingTextView mMinutesOnes, mMinutesTens;
    private final Typeface mAndroidClockMonoThin;
    private Typeface mOriginalHoursTypeface;

    private ColorStateList mTextColor;

    /**
     * Instantiate an HmsView
     *
     * @param context the Context in which to inflate the View
     */
    public HmsView(Context context) {
        this(context, null);
    }

    /**
     * Instantiate an HmsView
     *
     * @param context the Context in which to inflate the View
     * @param attrs attributes that define the title color
     */
    public HmsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mAndroidClockMonoThin =
                Typeface.createFromAsset(context.getAssets(), "fonts/AndroidClockMono-Thin.ttf");

        // Init defaults
        mTextColor = getResources().getColorStateList(R.color.dialog_text_color_holo_dark);
    }

    /**
     * Set a theme and restyle the views. This View will change its text color.
     *
     * @param themeResId the resource ID for theming
     */
    public void setTheme(int themeResId) {
        if (themeResId != -1) {
            TypedArray a = getContext().obtainStyledAttributes(themeResId, R.styleable.BetterPickersDialogFragment);

            mTextColor = a.getColorStateList(R.styleable.BetterPickersDialogFragment_bpTextColor);
        }

        restyleViews();
    }

    private void restyleViews() {
        if (mHoursOnes != null) {
            mHoursOnes.setTextColor(mTextColor);
        }
        if (mHoursTens != null) {
            mHoursTens.setTextColor(mTextColor);
        }
        if (mMinutesOnes != null) {
            mMinutesOnes.setTextColor(mTextColor);
        }
        if (mMinutesTens != null) {
            mMinutesTens.setTextColor(mTextColor);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mHoursOnes = (ZeroTopPaddingTextView) findViewById(R.id.hours_ones);
        mHoursTens = (ZeroTopPaddingTextView) findViewById(R.id.hours_tens);
        mMinutesTens = (ZeroTopPaddingTextView) findViewById(R.id.minutes_tens);
        mMinutesOnes = (ZeroTopPaddingTextView) findViewById(R.id.minutes_ones);
        if (mHoursOnes != null) {
            mOriginalHoursTypeface = mHoursOnes.getTypeface();
        }
    }

    /**
     * Set the time shown
     *
     * @param hoursOnesDigit the ones digit of the hours TextView
     * @param minutesTensDigit the tens digit of the minutes TextView
     * @param minutesOnesDigit the ones digit of the minutes TextView
     */
    public void setTime(int hoursTensDigit, int hoursOnesDigit, int minutesTensDigit, int minutesOnesDigit) {
        if (mHoursTens != null) {
            mHoursTens.setText(String.format("%d", hoursTensDigit));
        }
        if (mHoursOnes != null) {
            mHoursOnes.setText(String.format("%d", hoursOnesDigit));
        }
        if (mMinutesTens != null) {
            mMinutesTens.setText(String.format("%d", minutesTensDigit));
        }
        if (mMinutesOnes != null) {
            mMinutesOnes.setText(String.format("%d", minutesOnesDigit));
        }
    }
}