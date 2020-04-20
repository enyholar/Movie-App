package com.behruz.magmovie.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.behruz.magmovie.R;


public class ExpandableTextView extends TextView {

    private static final int DEFAULT_TRIM_LENGTH = 120;
    private static  String ELLIPSIS = "...ReadMore" ;

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;
    private Context mContext;

    public ExpandableTextView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();
        init();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trim = !trim;
                setText();
            }
        });
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
       //     ELLIPSIS = mContext.getResources().getString(R.string.status_error);
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
        } else {
            return originalText;
        }
    }

    public static void setColorForPath(Spannable spannable, String[] paths, int color) {
        for (int i = 0; i < paths.length; i++) {
            int indexOfPath = spannable.toString().indexOf(paths[i]);
            if (indexOfPath == -1) {
                return;
            }
            spannable.setSpan(new ForegroundColorSpan(color), indexOfPath,
                    indexOfPath + paths[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface
                    .createFromAsset(getContext().getAssets(), "fonts/Raleway-Regular.ttf");
            setTypeface(tf);
        }
    }

    public int getTrimLength() {
        return trimLength;
    }
}
