package com.zhy.autolayout;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Created by ahurwitz on 5/13/17.
 */

public class RippleTextView extends AppCompatTextView {

    public RippleTextView(Context context) {
        super(context);
        //Retrieve attribute values at runtime
        RippleEffect.addRippleEffect(context, null,this);
    }

    public RippleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Retrieve attribute values at runtime
        RippleEffect.addRippleEffect(context, attrs,this);
    }

    public RippleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Retrieve attribute values at runtime
        RippleEffect.addRippleEffect(context, attrs,this);
    }
}
