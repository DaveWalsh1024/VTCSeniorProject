package com.example.david.scorecardpro;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by david on 2/2/2018.
 */

public class ScorecardBox extends View
{
    public ScorecardBox (Context cdx)
    {
        super (cdx);



        centerTextPaint = new Paint();
        centerTextPaint.setColor(0xffffff);

        bottomRightTextPaint = new Paint ();
        centerTextPaint.setColor(0xffffff);

        centerText = null;
        bottomRightText = null;
        runScoreIndicator = false;
        setBackgroundResource(R.drawable.backgroundbox);
    }

    public void drawLine ()
    {

    }

    //draw dot
    public void markBaseWithDot ()
    {

    }

public void centerText (String argument)
    {
        centerText = argument;
    }


    //bottom right text
    public void bottomRightText (String argument)
    {
        bottomRightText = argument;
    }

    //fill diamond
    public void runScoreIndicator (boolean argument)
    {
        runScoreIndicator = true;
    }

    public void onDraw (Canvas canvas)
    {
        super.onDraw(canvas);
        if (centerText != null) {
            canvas.drawText(centerText, canvas.getHeight() / 2, canvas.getWidth() / 2, centerTextPaint);
        }
        canvas.drawText(bottomRightText, canvas.getHeight() - 5, canvas.getWidth() - 5, bottomRightTextPaint);
    }

    private String centerText;
    private Paint centerTextPaint;

    private String bottomRightText;
    private Paint bottomRightTextPaint;

    private boolean runScoreIndicator;
    private Paint runScoreIndicatorPaint;
}
