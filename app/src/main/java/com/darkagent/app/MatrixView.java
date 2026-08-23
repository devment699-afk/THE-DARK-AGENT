package com.darkagent.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class MatrixView extends View {

    private final Paint paint = new Paint();
    private final Random rnd = new Random();
    private int[] drops;
    private int cols, size = 34;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private static final String CHARS = "アイウエオカキクケコサシスセソ0123456789ABCDEFXYZ$#@";

    private final Runnable tick = new Runnable() {
        @Override public void run() {
            step();
            invalidate();
            handler.postDelayed(this, 60);
        }
    };

    public MatrixView(Context c) { this(c, null); }
    public MatrixView(Context c, AttributeSet a) { super(c, a); }

    @Override protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        cols = w / size;
        if (cols < 1) cols = 1;
        drops = new int[cols];
        for (int i = 0; i < cols; i++) drops[i] = rnd.nextInt(h / size);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(tick);
    }

    @Override protected void onDetachedFromWindow() {
        handler.removeCallbacks(tick);
        super.onDetachedFromWindow();
    }

    private void step() {
        if (drops == null) return;
        for (int i = 0; i < cols; i++) {
            drops[i] += 1;
            if (drops[i] * size > getHeight() && rnd.nextInt(100) > 96) drops[i] = 0;
        }
    }

    @Override protected void onDraw(Canvas c) {
        c.drawColor(Color.argb(240, 5, 10, 6));
        if (drops == null) return;
        paint.setTextSize(size);
        for (int i = 0; i < cols; i++) {
            char ch = CHARS.charAt(rnd.nextInt(CHARS.length()));
            int x = i * size, y = drops[i] * size;

            paint.setColor(Color.argb(255, 0, 255, 70));
            c.drawText(String.valueOf(ch), x, y, paint);

            paint.setColor(Color.argb(120, 0, 160, 50));
            c.drawText(String.valueOf(CHARS.charAt(rnd.nextInt(CHARS.length()))), x, y - size, paint);
            c.drawText(String.valueOf(CHARS.charAt(rnd.nextInt(CHARS.length()))), x, y - size * 2, paint);
        }
    }
}
