package com.behruz.magmovie.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.behruz.magmovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomErrorView extends FrameLayout {

    @BindView(R.id.error_text)
    TextView errorTextView;
    @BindView(R.id.error_message_text)
    TextView errorMessageTextView;

    public CustomErrorView(Context context) {
        this(context, null);
    }

    public CustomErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.custom_error_view, this);
        ButterKnife.bind(this);
    }

    public void setError(@NonNull Throwable t) {
        errorTextView.setText(getResources().getString(R.string.network_error));
        errorMessageTextView.setText(t.getMessage());
    }
}
