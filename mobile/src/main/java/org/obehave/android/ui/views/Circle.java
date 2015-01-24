package org.obehave.android.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import org.obehave.android.R;

public class Circle extends View{

    private Paint circlePaint;
    private int circleColor;


    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs){

        Log.d("init", "init");

        circlePaint = new Paint();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Circle, 0, 0);

        try {
            circleColor = a.getInteger(R.styleable.Circle_color, 0);//0 is default
        } finally {
            a.recycle();
        }
    }

    public int getCircleColor(){
        return circleColor;
    }

    public void setCircleColor(int newColor){
        //update the instance variable
        circleColor=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("init", "drawsss");
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;

        Log.d("viewHeightHalf","" + viewHeightHalf);
        Log.d("viewWidthHalf","" + viewWidthHalf);

        int radius = 0;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf;
        else
            radius=viewWidthHalf;

        Log.d("","" + radius);

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        circlePaint.setColor(circleColor);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);

    }
}