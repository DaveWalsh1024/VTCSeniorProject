package com.example.david.scorecardpro;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
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
        setBackgroundResource(R.drawable.backgroundbox);
    }
}
