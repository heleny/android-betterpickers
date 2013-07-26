package com.doomonafireball.betterpickers.hmspicker;

import android.content.Intent;
import com.doomonafireball.betterpickers.R;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Vector;

/**
 * Dialog to set alarm time.
 */
public class HmsPickerDialogFragment extends DialogFragment {

    private static final String REFERENCE_KEY = "HmsPickerDialogFragment_ReferenceKey";
    private static final String THEME_RES_ID_KEY = "HmsPickerDialogFragment_ThemeResIdKey";

    private Button mSet, mCancel;
    private HmsPicker mPicker;

    private int mReference = -1;
    private int mTheme = -1;
    private View mDividerOne, mDividerTwo;
    private int mDividerColor;
    private ColorStateList mTextColor;
    private int mButtonBackgroundResId;
    private int mDialogBackgroundResId;
    private Vector<HmsPickerDialogHandler> mHmsPickerDialogHandlers = new Vector<HmsPickerDialogHandler>();
    private static int[] mTime;

    /**
     * Create an instance of the Picker (used internally)
     *
     * @param reference an (optional) user-defined reference, helpful when tracking multiple Pickers
     * @param themeResId the style resource ID for theming
     * @return a Picker!
     */
    public static HmsPickerDialogFragment newInstance(int reference, int themeResId, int[] time) {
        mTime = time;
        final HmsPickerDialogFragment frag = new HmsPickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(REFERENCE_KEY, reference);
        args.putInt(THEME_RES_ID_KEY, themeResId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(REFERENCE_KEY)) {
            mReference = args.getInt(REFERENCE_KEY);
        }
        if (args != null && args.containsKey(THEME_RES_ID_KEY)) {
            mTheme = args.getInt(THEME_RES_ID_KEY);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        // Init defaults
        mTextColor = getResources().getColorStateList(R.color.dialog_text_color_holo_dark);
        mButtonBackgroundResId = R.drawable.button_background_dark;
        mDividerColor = getResources().getColor(R.color.default_divider_color_dark);
        mDialogBackgroundResId = R.drawable.dialog_full_holo_dark;

        if (mTheme != -1) {
            TypedArray a = getActivity().getApplicationContext()
                    .obtainStyledAttributes(mTheme, R.styleable.BetterPickersDialogFragment);

            mTextColor = a.getColorStateList(R.styleable.BetterPickersDialogFragment_bpTextColor);
            mButtonBackgroundResId = a.getResourceId(R.styleable.BetterPickersDialogFragment_bpButtonBackground,
                    mButtonBackgroundResId);
            mDividerColor = a.getColor(R.styleable.BetterPickersDialogFragment_bpDividerColor, mDividerColor);
            mDialogBackgroundResId = a
                    .getResourceId(R.styleable.BetterPickersDialogFragment_bpDialogBackground, mDialogBackgroundResId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.hms_picker_dialog, null);
        mSet = (Button) v.findViewById(R.id.set_button);
        mCancel = (Button) v.findViewById(R.id.cancel_button);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mPicker = (HmsPicker) v.findViewById(R.id.hms_picker);
        mPicker.setSetButton(mSet);
        mSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (HmsPickerDialogHandler handler : mHmsPickerDialogHandlers) {
                    handler.onDialogHmsSet(mReference, mPicker.getHours(), mPicker.getMinutes());
                }
                final Activity activity = getActivity();
                final Fragment fragment = getTargetFragment();
                if (activity instanceof HmsPickerDialogHandler) {
                    final HmsPickerDialogHandler act =
                            (HmsPickerDialogHandler) activity;
                    act.onDialogHmsSet(mReference, mPicker.getHours(), mPicker.getMinutes());
                } else if (fragment instanceof HmsPickerDialogHandler) {
                    final HmsPickerDialogHandler frag =
                            (HmsPickerDialogHandler) fragment;
                    frag.onDialogHmsSet(mReference, mPicker.getHours(), mPicker.getMinutes());
                }
                dismiss();
            }
        });

        mDividerOne = v.findViewById(R.id.divider_1);
        mDividerTwo = v.findViewById(R.id.divider_2);
        mDividerOne.setBackgroundColor(mDividerColor);
        mDividerTwo.setBackgroundColor(mDividerColor);
        mSet.setTextColor(mTextColor);
        mSet.setBackgroundResource(mButtonBackgroundResId);
        mCancel.setTextColor(mTextColor);
        mCancel.setBackgroundResource(mButtonBackgroundResId);
        mPicker.setTheme(mTheme);
        getDialog().getWindow().setBackgroundDrawableResource(mDialogBackgroundResId);

        if (mTime != null && mTime.length == 4) {
            mPicker.getHmsView().setTime(mTime[0], mTime[1], mTime[2], mTime[3]);

            // figure out number of zeros at the beginning
            int numZeroBeginning = 0;
            for (int i = 0; i < 4; i++) {
                if (mTime[i] == 0) {
                    numZeroBeginning++;
                } else {
                    break;
                }
            }

            for (int i = numZeroBeginning; i < 4; i++) {
                mPicker.addClickedNumber(mTime[i]);
            }

            if (numZeroBeginning < 4) {
                mPicker.enableDeleteButton();
            }
        }
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * This interface allows objects to register for the Picker's set action.
     */
    public interface HmsPickerDialogHandler {

        void onDialogHmsSet(int reference, int hours, int minutes);
    }

    /**
     * Attach a Vector of handlers to be notified in addition to the Fragment's Activity and target Fragment.
     *
     * @param handlers a Vector of handlers
     */
    public void setHmsPickerDialogHandlers(Vector<HmsPickerDialogHandler> handlers) {
        mHmsPickerDialogHandlers = handlers;
    }
}