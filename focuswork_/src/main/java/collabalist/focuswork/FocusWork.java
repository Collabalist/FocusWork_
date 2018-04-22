package collabalist.focuswork;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Deepak on 4/22/2018.
 */

public class FocusWork {
    private Context mContext;
    EditText[] ets;
    EditText mCurrentlyFocusedEditText;

    private FocusWork(Context context) {
        this.mContext = context;
    }

    public static FocusWork with(Context context) {
        return new FocusWork(context);
    }

    public void createOTPfocusFor(EditText... otpETs) {
        this.ets = otpETs;
        if (this.ets.length > 1) {
            setForewordBackwardFocus();
            setFocusListener();
            setTextChangeListener();
            setOnKeyListener();
        } else {
            ets[0].setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
    }


    void setForewordBackwardFocus() {
        if (ets.length > 1) {
            for (int i = 0; i < ets.length; i++) {
                if (i != (ets.length - 1)) {
                    ets[i].setNextFocusRightId(ets[i + 1].getId());
                    ets[i].setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                } else {
                    ets[i].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                }
                if (i != 0) {
                    ets[i].setNextFocusLeftId(ets[i - 1].getId());
                }
            }
        }
    }

    void setFocusListener() {
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mCurrentlyFocusedEditText = (EditText) v;
                mCurrentlyFocusedEditText.setSelection(mCurrentlyFocusedEditText.getText().length());
            }
        };
        for (int i = 0; i < ets.length; i++)
            ets[i].setOnFocusChangeListener(onFocusChangeListener);
    }

    TextWatcher textWatcher;

    void setTextChangeListener() {
        textWatcher = new TextWatcher() {
            String nextChar = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCurrentlyFocusedEditText.getText().length() >= 1
                        && mCurrentlyFocusedEditText != ets[ets.length - 1]) {
                    if (mCurrentlyFocusedEditText.getText().length() > 1) {
                        //first remove the next char from current
                        mCurrentlyFocusedEditText.removeTextChangedListener(textWatcher);
                        nextChar = mCurrentlyFocusedEditText.getText().toString().substring(1, 2);
                        mCurrentlyFocusedEditText.setText(mCurrentlyFocusedEditText.getText().toString().substring(0, 1));
                        mCurrentlyFocusedEditText.addTextChangedListener(textWatcher);

                        mCurrentlyFocusedEditText.focusSearch(View.FOCUS_RIGHT).requestFocus();
                        //now set the nextChar to Next
                        mCurrentlyFocusedEditText.removeTextChangedListener(textWatcher);
                        mCurrentlyFocusedEditText.setText(nextChar);
                        mCurrentlyFocusedEditText.setSelection(1);
                        mCurrentlyFocusedEditText.addTextChangedListener(textWatcher);
                    } else
                        mCurrentlyFocusedEditText.focusSearch(View.FOCUS_RIGHT).requestFocus();


                } else if (mCurrentlyFocusedEditText.getText().length() >= 1
                        && mCurrentlyFocusedEditText == ets[ets.length - 1]) {
                    InputMethodManager imm =
                            (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(mCurrentlyFocusedEditText.getWindowToken(), 0);
                    }
                } else {
                    String currentValue = mCurrentlyFocusedEditText.getText().toString();
                    if (currentValue.length() <= 0 && mCurrentlyFocusedEditText.getSelectionStart() <= 0) {
                        if (mCurrentlyFocusedEditText.focusSearch(View.FOCUS_LEFT) != null)
                            mCurrentlyFocusedEditText.focusSearch(View.FOCUS_LEFT).requestFocus();
                    }
                }
            }
        };
        for (int i = 0; i < ets.length; i++)
            ets[i].addTextChangedListener(textWatcher);
    }

    void setOnKeyListener() {
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    if (mCurrentlyFocusedEditText.getText().length() == 0) {
                        if (mCurrentlyFocusedEditText.focusSearch(View.FOCUS_LEFT) != null)
                            mCurrentlyFocusedEditText.focusSearch(View.FOCUS_LEFT).requestFocus();
                    }
                }
                return false;
            }
        };
        for (int i = 0; i < ets.length; i++)
            ets[i].setOnKeyListener(keyListener);
    }
}
