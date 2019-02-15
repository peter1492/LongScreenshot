package com.ezydev.bigscreenshot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BigScreenshot {
    private List<Bitmap> bitmaps = new ArrayList<>();
    private boolean isScreenShots = false;

    private ProcessScreenshot processScreenshot;
    private ViewGroup viewGroup;
    private ViewGroup container;

    private ViewGroup getViewGroup() {
        return this.viewGroup;
    }

    private ViewGroup getContainer() {
        return this.container;
    }

    public BigScreenshot(ProcessScreenshot processScreenshot, ViewGroup viewGroup , ViewGroup container) {
        this.processScreenshot = processScreenshot;
        this.viewGroup = viewGroup;
        this.container = container;

    }

    public void startScreenshot(){

        bitmaps.clear();
        isScreenShots = true;
        autoScroll();

    }

    public void stopScreenshot(){
        isScreenShots = false;
    }

    private void autoScroll() {
        final int delay = 16;
        final int step = 10;
        final MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis()
                , SystemClock.uptimeMillis()
                , MotionEvent.ACTION_DOWN
                , getViewGroup().getWidth() / 2
                , getViewGroup().getHeight() / 2
                , 0);
        getViewGroup().dispatchTouchEvent(motionEvent);

        motionEvent.setAction(MotionEvent.ACTION_MOVE);
        motionEvent.setLocation(motionEvent.getX(), motionEvent.getY() - (ViewConfiguration.get(getViewGroup().getContext()).getScaledTouchSlop()));
        getViewGroup().dispatchTouchEvent(motionEvent);

        final int startScrollY = (int) motionEvent.getY();






        getViewGroup().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isScreenShots) {
                    drawRemainAndAssemble(startScrollY, (int) motionEvent.getY());
                    return;
                }

                drawIfNeeded(startScrollY, (int) motionEvent.getY());

                motionEvent.setAction(MotionEvent.ACTION_MOVE);

                int nextStep;
                int gap = (startScrollY - (int) motionEvent.getY() + step) % getContainer().getHeight();
                if (gap > 0 && gap < step) {
                    nextStep = step - gap;
                } else {
                    nextStep = step;
                }

                motionEvent.setLocation((int) motionEvent.getX(), (int) motionEvent.getY() - nextStep);
                getViewGroup().dispatchTouchEvent(motionEvent);

                getViewGroup().postDelayed(this, delay);
            }
        }, delay);
    }

    private void drawRemainAndAssemble(int startScrollY, int curScrollY) {
        if ((curScrollY - startScrollY) % getContainer().getHeight() != 0) {
            Bitmap film = Bitmap.createBitmap(getContainer().getWidth(), getContainer().getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas();
            canvas.setBitmap(film);
            getContainer().draw(canvas);

            int part = (startScrollY - curScrollY) / getContainer().getHeight();
            int remainHeight = startScrollY - curScrollY - getContainer().getHeight() * part;
            Bitmap remainBmp = Bitmap.createBitmap(film, 0, getContainer().getHeight() - remainHeight, getContainer().getWidth(), remainHeight);
            bitmaps.add(remainBmp);
        }

        assembleBmp();

    }

    private void assembleBmp() {
        int h = 0;
        for (Bitmap bitmap : bitmaps) {
            h += bitmap.getHeight();
        }
        Bitmap bitmap = Bitmap.createBitmap(getContainer().getWidth(), h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        for (Bitmap b : bitmaps) {
            canvas.drawBitmap(b, 0, 0, null);
            canvas.translate(0, b.getHeight());

        }

        processScreenshot.getScreenshot(bitmap);
    }

    private void drawIfNeeded(int startScrollY, int curScrollY) {
        if ((curScrollY - startScrollY) % getContainer().getHeight() == 0) {
            Bitmap film = Bitmap.createBitmap(getContainer().getWidth(), getContainer().getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas();
            canvas.setBitmap(film);
            getContainer().draw(canvas);
            bitmaps.add(film);
        }
    }



    public interface ProcessScreenshot{
        public void getScreenshot(Bitmap bitmap);


    }

}
