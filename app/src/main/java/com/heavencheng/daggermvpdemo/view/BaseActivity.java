package com.heavencheng.daggermvpdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.heavencheng.daggermvpdemo.R;

/**
 * @author Heaven
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isInvade() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.color.colorPrimary, typedValue, true);
            int[] attribute = new int[]{R.color.colorPrimary};
            TypedArray array = obtainStyledAttributes(typedValue.resourceId, attribute);
            int color = array.getColor(0, getResources().getColor(R.color.colorPrimary));
            array.recycle();
            window.setStatusBarColor(color);
        }
    }

    protected boolean isInvade() {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if ((view instanceof EditText)) {
                EditText editText = (EditText) view;
                int[] leftTop = {0, 0};
                view.getLocationInWindow(leftTop);
                int left = leftTop[0];
                int top = leftTop[1];
                int bottom = top + view.getHeight();
                int right = left + view.getWidth();
                if (!(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    editText.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
