package com.example.david.scorecardpro;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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

        setMinimumHeight(50);

        centerTextPaint = new Paint();
        centerTextPaint.setColor(0xff000000);

        bottomRightTextPaint = new Paint ();
        centerTextPaint.setColor(0xff000000);

        runScoreIndicatorPaint =  new Paint();
        runScoreIndicatorPaint.setColor(0xff000000);
        runScoreIndicatorPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(0xffff0000);
        linePaint.setStrokeWidth(10);

        bgLinePaint = new Paint();
        bgLinePaint.setColor(0xff808080);
        bgLinePaint.setStrokeWidth(6);

        strikeAndBallBoxPaint = new Paint();
        strikeAndBallBoxPaint.setColor(0xff808080);
        strikeAndBallBoxPaint.setStrokeWidth(1);

        centerText = null;
        bottomRightText = null;
        runScoreIndicator = false;
        setBackgroundResource(R.drawable.backgroundboxoutline);
    }

    public void drawBaseLine (int startingBase, int endingBase)
    {
        if (startingBase == 0)
        {
            if (endingBase == 1)
            {
                homeFirst = true;
            }

            else if (endingBase == 2)
            {
                homeFirst = true;
                firstSecond = true;
            }

            else if (endingBase == 3)
            {
                homeFirst = true;
                firstSecond = true;
                secondThird = true;
            }

            else if (endingBase == 0)
            {
                homeFirst = true;
                firstSecond = true;
                secondThird = true;
                thirdHome = true;
            }
        }

        else if (startingBase == 1)
        {
            if (endingBase == 2)
            {
                firstSecond = true;
            }

            else if (endingBase == 3)
            {
                firstSecond = true;
                secondThird = true;
            }

            else if (endingBase == 0)
            {
                firstSecond = true;
                secondThird = true;
                thirdHome = true;
            }
        }

        else if (startingBase == 2)
        {
            if (endingBase == 3)
            {
                secondThird = true;
            }

            else if (endingBase == 0)
            {
                secondThird = true;
                thirdHome = true;
            }
        }

        else if (startingBase == 3)
        {
            thirdHome = true;
        }
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
        float pad = 10;
        float h = canvas.getHeight();
        float w = canvas.getWidth();

        canvas.drawLine(pad, h / 2, w / 2, pad, bgLinePaint);
        canvas.drawLine(w / 2, pad, w - pad, h / 2, bgLinePaint);
        canvas.drawLine(w - pad, h / 2, w / 2, h - pad, bgLinePaint);
        canvas.drawLine(w / 2, h - pad, pad, h / 2, bgLinePaint);

        canvas.drawRect(0, 0, h/6, w/8, strikeAndBallBoxPaint);

        if (centerText != null)
        {
            Rect bounds = new Rect();
            centerTextPaint.getTextBounds(centerText, 0, centerText.length(), bounds);
            float x = (w - bounds.width()) / 2;
            float y = (h - bounds.height()) / 2 + bounds.height();
            canvas.drawText(centerText, x, y, centerTextPaint);
        }

        if (bottomRightText != null)
        {
            canvas.drawText(bottomRightText, canvas.getHeight() - 5, canvas.getWidth() - 5, bottomRightTextPaint);
        }

        if (runScoreIndicator == true)
        {
            canvas.drawCircle(w/2, h/2, w/8, runScoreIndicatorPaint);
        }

        if (homeFirst == true)
        {
            canvas.drawLine(w / 2, pad, w - pad, h / 2, linePaint);
        }

        if (firstSecond == true)
        {
            canvas.drawLine(w - pad, h / 2, w / 2, h - pad, linePaint);
        }

        if (secondThird == true)
        {
            canvas.drawLine(w / 2, h - pad, pad, h / 2, linePaint);
        }

        if (thirdHome == true)
        {
            canvas.drawLine(pad, h / 2, w / 2, pad, linePaint);
        }
    }

    private String centerText;
    private Paint centerTextPaint;

    private String bottomRightText;
    private Paint bottomRightTextPaint;

    private boolean runScoreIndicator;
    private Paint runScoreIndicatorPaint;

    private boolean homeFirst;
    private boolean firstSecond;
    private boolean secondThird;
    private boolean thirdHome;
    private Paint linePaint;

    private Paint bgLinePaint;

    private Paint strikeAndBallBoxPaint;
}
