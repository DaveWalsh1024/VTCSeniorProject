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
    public ScorecardBox (Context cdx, int inning, int battingOrder)
    {
        super (cdx);

        setMinimumHeight(50);

        centerTextPaint = new Paint();
        centerTextPaint.setTextSize(32);
        centerTextPaint.setColor(0xff000000);

        bottomRightTextPaint = new Paint ();
        bottomRightTextPaint.setTextSize(24);
        bottomRightTextPaint.setColor(0xff000000);

        runScoreIndicatorPaint =  new Paint();
        runScoreIndicatorPaint.setColor(0xffff0000);
        runScoreIndicatorPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(0xffff0000);
        linePaint.setStrokeWidth(10);

        linePaintOut = new Paint();
        linePaintOut.setColor(0xff000000);
        linePaintOut.setStrokeWidth(10);

        bgLinePaint = new Paint();
        bgLinePaint.setColor(0xff808080);
        bgLinePaint.setStrokeWidth(6);

        strikeBoxPaint1 = new Paint();
        strikeBoxPaint1.setColor(0xff000000);
        strikeBoxPaint1.setStrokeWidth(3);
        strikeBoxPaint1.setStyle(Paint.Style.STROKE);

        strikeBoxPaint2 = new Paint();
        strikeBoxPaint2.setColor(0xff000000);
        strikeBoxPaint2.setStrokeWidth(3);
        strikeBoxPaint2.setStyle(Paint.Style.STROKE);

        ballBoxPaint1 = new Paint();
        ballBoxPaint1.setColor(0xff000000);
        ballBoxPaint1.setStrokeWidth(3);
        ballBoxPaint1.setStyle(Paint.Style.STROKE);

        ballBoxPaint2 = new Paint();
        ballBoxPaint2.setColor(0xff000000);
        ballBoxPaint2.setStrokeWidth(3);
        ballBoxPaint2.setStyle(Paint.Style.STROKE);

        ballBoxPaint3 = new Paint();
        ballBoxPaint3.setColor(0xff000000);
        ballBoxPaint3.setStrokeWidth(3);
        ballBoxPaint3.setStyle(Paint.Style.STROKE);


        centerText = null;
        bottomRightText = null;
        runScoreIndicator = false;
        strike1 = false;
        strike2 =false;
        ball1 = false;
        ball2 = false;
        ball3 = false;

        this.inning = inning;
        this.battingOrder = battingOrder;

        setBackgroundResource(R.drawable.backgroundboxoutline);
    }

    public String toString ()
    {
        return "Box for " + inning + " , " + battingOrder;
    }

    public void setBall (int n)
    {
        if (n == 1)
        {
            setBall1();
        }

        if (n == 2)
        {
            setBall1();
            setBall2();
        }

        if (n == 3)
        {
            setBall1();
            setBall2();
            setBall3();
        }
    }

    public int getInning ()
    {
        return inning;
    }

    public int getBattingOrder ()
    {
        return battingOrder;
    }

    public void setStrike (int n)
    {
        if (n == 1)
        {
            setStrike1();
        }

        if (n == 2)
        {
            setStrike1();
            setStrike2();
        }

        if (n == 3)
        {
            setStrike1();
            setStrike2();
        }
    }

    public void drawBaseLine (int startingBase, int endingBase, boolean out)
    {
        if (out == false)
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

        else if (out)
        {
            if (startingBase == 0)
            {
                if (endingBase == 1)
                {
                    homeFirstOut = true;
                }

                else if (endingBase == 2)
                {
                    homeFirstOut = true;
                    firstSecondOut  = true;
                }

                else if (endingBase == 3)
                {
                    homeFirstOut  = true;
                    firstSecondOut  = true;
                    secondThirdOut  = true;
                }

                else if (endingBase == 0)
                {
                    homeFirstOut  = true;
                    firstSecondOut  = true;
                    secondThirdOut  = true;
                    thirdHomeOut  = true;
                }
            }

            else if (startingBase == 1)
            {
                if (endingBase == 2)
                {
                    firstSecondOut  = true;
                }

                else if (endingBase == 3)
                {
                    firstSecondOut  = true;
                    secondThirdOut  = true;
                }

                else if (endingBase == 0)
                {
                    firstSecondOut  = true;
                    secondThirdOut  = true;
                    thirdHomeOut  = true;
                }
            }

            else if (startingBase == 2)
            {
                if (endingBase == 3)
                {
                    secondThirdOut  = true;
                }

                else if (endingBase == 0)
                {
                    secondThirdOut  = true;
                    thirdHomeOut  = true;
                }
            }

            else if (startingBase == 3)
            {
                thirdHomeOut  = true;
            }
        }
    }

    public void centerText (String argument)
    {
        centerText = argument;
    }

    public void bottomRightText (String argument)
    {
        bottomRightText = argument;
    }

    public void setBall1 () { ball1 = true; }
    public void setBall2 () { ball2 = true; }
    public void setBall3 () { ball3 = true; }
    public void setStrike1 () { strike1 = true; }
    public void setStrike2 () {strike2 = true; }

    //fill diamond
    public void setRunScoreIndicatorTrue ()
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

        canvas.drawRect(0, 0, h/6, w/8, ballBoxPaint1); //ball 1
        canvas.drawRect(h/6, 0, h/4 + 8, w/8, ballBoxPaint2); //ball 2
        canvas.drawRect(h/4 + 8, 0, h/3 + 15, w/8, ballBoxPaint3); //ball 3

        canvas.drawRect(w - 40, 0, w-20, w/8, strikeBoxPaint1); //strike 1
        canvas.drawRect(w - 20, 0, w, w/8, strikeBoxPaint2); //strike 2

        if (centerText != null)
        {
            System.out.println("We've reached center text");
            Rect bounds = new Rect();
            centerTextPaint.getTextBounds(centerText, 0, centerText.length(), bounds);
            float x = (w - bounds.width()) / 2;
            float y = (h - bounds.height()) / 2 + bounds.height();
            canvas.drawText(centerText, x, y, centerTextPaint);
        }

        if (bottomRightText != null)
        {
            System.out.println("We've reached bottom right text");
            Rect bounds = new Rect();
            bottomRightTextPaint.getTextBounds(bottomRightText, 0, bottomRightText.length(), bounds);
            float x = (w - bounds.width()) - 15;
            float y = (h - bounds.height()) + bounds.height() - 10;
            canvas.drawText(bottomRightText, x, y , bottomRightTextPaint);
        }

        if (runScoreIndicator)
        {
            canvas.drawCircle(w/2, h/2, w/4, runScoreIndicatorPaint);
            canvas.drawCircle(w/2 + 45, h/2, w/10, runScoreIndicatorPaint);
            canvas.drawCircle(w/2 - 45, h/2, w/10, runScoreIndicatorPaint);
        }

        if (homeFirst)
        {
            canvas.drawLine(w - pad, h / 2, w / 2, h - pad, linePaint);
        }

        if (firstSecond)
        {
            canvas.drawLine(w / 2, pad, w - pad, h / 2, linePaint);
        }

        if (secondThird)
        {
            canvas.drawLine(w / 2, pad, pad, h / 2, linePaint);
        }

        if (thirdHome)
        {
            canvas.drawLine(pad, h / 2, w / 2, h - pad, linePaint);
        }

        if (homeFirstOut)
        {
            canvas.drawLine(w - pad, h / 2, w / 2, h - pad, linePaintOut);
        }

        if (firstSecondOut)
        {
            canvas.drawLine(w / 2, pad, w - pad, h / 2, linePaintOut);
        }

        if (secondThirdOut)
        {
            canvas.drawLine(w / 2, pad, pad, h / 2, linePaintOut);
        }

        if (thirdHomeOut)
        {
            canvas.drawLine(pad, h / 2, w / 2, h - pad, linePaintOut);
        }

        if (ball1)
        {
            ballBoxPaint1.setStyle(Paint.Style.FILL);
        }

        if (ball2)
        {
            ballBoxPaint2.setStyle(Paint.Style.FILL);
        }

        if (ball3)
        {
            ballBoxPaint3.setStyle(Paint.Style.FILL);
        }

        if (strike1)
        {
            strikeBoxPaint1.setStyle(Paint.Style.FILL);
        }

        if (strike2)
        {
            strikeBoxPaint2.setStyle(Paint.Style.FILL);
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

    private boolean homeFirstOut;
    private boolean firstSecondOut;
    private boolean secondThirdOut;
    private boolean thirdHomeOut;
    private Paint linePaintOut;

    private Paint bgLinePaint;

    private boolean ball1;
    private boolean ball2;
    private boolean ball3;
    private boolean strike1;
    private boolean strike2;
    private Paint strikeBoxPaint1;
    private Paint strikeBoxPaint2;
    private Paint ballBoxPaint1;
    private Paint ballBoxPaint2;
    private Paint ballBoxPaint3;

    private final int inning;
    private final int battingOrder;
}
