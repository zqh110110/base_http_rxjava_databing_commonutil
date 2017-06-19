package com.zhy.autolayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;


/**
 * Created by ahurwitz on 5/13/17.
 */

public class RippleEffect {

    public static ColorBean addRippleEffect(Context context, AttributeSet attrs , View view) {
        ColorBean xmlAttributes = RippleEffect.getXMLAttributes(context, null);
        if (xmlAttributes.rippleEnabled &&  Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                //Create RippleDrawable
                view.setBackground(getPressedColorRippleDrawable(xmlAttributes.backgroundColor, xmlAttributes.rippleColor));

                //Customize Ripple color
                RippleDrawable rippleDrawable = (RippleDrawable) view.getBackground();
                int[][] states = new int[][]{new int[]{android.R.attr.state_enabled}};
                int[] colors = new int[]{xmlAttributes.rippleColor};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                rippleDrawable.setColor(colorStateList);
                view.setBackground(rippleDrawable);

        } else if (xmlAttributes.rippleEnabled) {

            //Create Selector for pre Lollipop
            ViewCompat.setBackground(view, createStateListDrawable(xmlAttributes.backgroundColor, xmlAttributes.rippleColor));

        } else {

            //Ripple Disabled
            ViewCompat.setBackground(view,new ColorDrawable(xmlAttributes.backgroundColor));
//            view.setBackground(new ColorDrawable(backgroundColor));

        }
        return xmlAttributes;
    }

    private static ColorBean getXMLAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RippleAttr,
                0, 0);
        ColorBean bean = null;
        try {
            bean = new ColorBean(typedArray.getBoolean(
                    R.styleable.RippleAttr_rippleEnabled,
                    true),typedArray.getColor(
                    R.styleable.RippleAttr_backgroundColor,
                    context.getResources().getColor(R.color.background_default)),typedArray.getColor(
                    R.styleable.RippleAttr_rippleColor,
                    context.getResources().getColor(R.color.ripple_default)));
        } finally {
            typedArray.recycle();
        }
        return bean;
    }

    public static class ColorBean {
        private boolean rippleEnabled;
        private int backgroundColor;
        private int rippleColor;

        public ColorBean(boolean rippleEnabled, int backgroundColor, int rippleColor) {
            this.rippleEnabled = rippleEnabled;
            this.backgroundColor = backgroundColor;
            this.rippleColor = rippleColor;
        }
    }

    private static StateListDrawable createStateListDrawable(int backgroundColor, int rippleColor) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[] {android.R.attr.state_pressed}, createDrawable(rippleColor));
        stateListDrawable.addState(StateSet.WILD_CARD, createDrawable(backgroundColor));
        return stateListDrawable;
    }

    private static Drawable createDrawable(int background){
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setColor(background);
        return shapeDrawable;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static Drawable getPressedColorRippleDrawable(int normalColor, int pressedColor) {
        return new RippleDrawable(getPressedColorSelector(normalColor, pressedColor), getColorDrawableFromColor(normalColor), null);
    }

    private static ColorStateList getPressedColorSelector(int normalColor, int pressedColor) {
        return new ColorStateList(
                new int[][]
                        {
                                new int[]{}
                        },
                new int[]
                        {
                                pressedColor
                        }
        );
    }

    private static ColorDrawable getColorDrawableFromColor(int color) {
        return new ColorDrawable(color);
    }
}
