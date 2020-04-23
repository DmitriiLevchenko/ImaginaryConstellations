package com.example.imaginaryconstellations;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.imaginaryconstellations.model.StarShape;
import com.example.imaginaryconstellations.util.ActFinisher;
import com.example.imaginaryconstellations.util.Pointer;

import java.util.ArrayList;
import java.util.logging.Handler;


public class GameViewEvading extends SurfaceView implements Runnable, ActFinisher {
    public static int maxX = 32;
    public static int maxY = 20;
    public static float unitW = 0;
    public static float unitH = 0;
    private boolean firstTime = true;
    private boolean gameRunning = true;
    private Thread gameThread = null;
    private Paint paint;
    private  Paint lpaint = new Paint();
    private Canvas canvas;
    private Context context;
    private SurfaceHolder surfaceHolder;
    private Bitmap bitmap;
    private Boolean cheker = false;
    public Boolean Gameisprogress;
    public static ArrayList<StarShape> starShapes = new ArrayList<StarShape>();
    public static ArrayList<StarShape> usesstarShapes = new ArrayList<>();
    ActFinisher finisher;
    Handler h;


    public GameViewEvading(Context context, ActFinisher finisher) {
        super(context);
        lpaint.setColor(Color.BLUE);
        lpaint.setStrokeWidth(10);
        Gameisprogress = true;
        //инициализируем обьекты для рисования
        this.finisher = finisher;
        surfaceHolder = getHolder();
        paint = new Paint();
        this.context = context;
        // инициализируем поток
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (Gameisprogress) {
            draw();
            CheckGameStatus();
        }
    }


    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            if (firstTime) {
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width() / maxX;
                unitH = surfaceHolder.getSurfaceFrame().height() / maxY;
                GenerateStars();
            }
            canvas = surfaceHolder.lockCanvas();

            Bitmap cBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_0000_2);
            if (!cheker) {
                cheker = true;
                bitmap = Bitmap.createScaledBitmap(
                        cBitmap, surfaceHolder.getSurfaceFrame().width(), surfaceHolder.getSurfaceFrame().height(), false);
            }
            canvas.drawBitmap(bitmap, 0, 0, null);//ф

            for (int i = 0; i < starShapes.size(); i++) {
                starShapes.get(i).drow(paint, canvas);
            }

            for (int i = 0; i < GameStarter.LArray.size(); i++) {
                canvas.drawLine(GameStarter.LArray.get(i).getKor1().getX(), GameStarter.LArray.get(i).getKor1().getY(), GameStarter.LArray.get(i).getKor2().getX(), GameStarter.LArray.get(i).getKor2().getY(), lpaint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void GenerateStars() {
        double starsquantaty = 7 + Math.random() * 8;
        for (int i = 0; i < starsquantaty; i++) {
            StarShape starShape = new StarShape(context);
            //starShape.drow(paint,canvas);
            starShapes.add(starShape);
        }
    }

    private void CheckGameStatus()
    {
        for (int i = 0; i < GameStarter.LArray.size(); i++)
        {
            for (int y = 0; y < GameStarter.LArray.size(); y++) {
                if (i != y) {
                    Pointer p1 = new Pointer(GameStarter.LArray.get(i).getKor1().getX(), GameStarter.LArray.get(i).getKor1().getY());
                    Pointer p2 = new Pointer(GameStarter.LArray.get(i).getKor2().getX(), GameStarter.LArray.get(i).getKor2().getY());
                    Pointer p3 = new Pointer(GameStarter.LArray.get(y).getKor1().getX(), GameStarter.LArray.get(y).getKor1().getY());
                    Pointer p4 = new Pointer(GameStarter.LArray.get(y).getKor2().getX(), GameStarter.LArray.get(y).getKor2().getY());
                    if (checkIntersectionOfTwoLineSegments(p1, p2, p3, p4)) {
                        finishActivity();
                    }

                }

            }
        }
        if (GameViewEvading.starShapes.size() == GameViewEvading.usesstarShapes.size() && GameViewEvading.starShapes.size()!=0) {
            victory();
        }
    }

    private boolean checkIntersectionOfTwoLineSegments(Pointer p1, Pointer p2, Pointer p3, Pointer p4) {
        if (p2.getX() < p1.getX()) {

            Pointer tmp = p1;
            p1 = p2;
            p2 = tmp;
        }
        if (p4.getX() < p3.getX()) {

            Pointer tmp = p3;
            p3 = p4;
            p4 = tmp;
        }
        if (p2.getX() < p3.getX()) {
            return false;
        }

        if ((p1.getX() - p2.getX() == 0) && (p3.getX() - p4.getX() == 0)) {

            //если они лежат на одном X
            if (p1.getX() == p3.getX()) {


                if (!((Math.max(p1.getY(), p2.getY()) < Math.min(p3.getY(), p4.getY())) ||
                        (Math.min(p1.getY(), p2.getY()) > Math.max(p3.getY(), p4.getY())))) {

                    return true;
                }
            }

            return false;
        }
        if (p1.getX() - p2.getX() == 0) {


            double Xa = p1.getX();
            double A2 = (p3.getY() - p4.getY()) / (p3.getY() - p4.getY());
            double b2 = p3.getY() - A2 * p3.getX();
            double Ya = A2 * Xa + b2;

            if (p3.getX() <= Xa && p4.getX() >= Xa && Math.min(p1.getY(), p2.getY()) <= Ya &&
                    Math.max(p1.getY(), p2.getY()) >= Ya) {

                return true;
            }

            return false;
        }
        if (p1.getX() - p2.getX() == 0) {

            double Xa = p1.getX();
            double A2 = (p3.getY() - p4.getY()) / (p3.getX() - p4.getX());
            double b2 = p3.getY() - A2 * p3.getX();
            double Ya = A2 * Xa + b2;

            if (p3.getX() <= Xa && p4.getX() >= Xa && Math.min(p1.getY(), p2.getY()) <= Ya &&
                    Math.max(p1.getY(), p2.getY()) >= Ya) {

                return true;
            }

            return false;
        }
        double A1 = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double A2 = (p3.getY() - p4.getY()) / (p3.getY() - p4.getY());
        double b1 = p1.getY() - A1 * p1.getY();
        double b2 = p3.getY() - A2 * p3.getX();

        if (A1 == A2) {
            return false;
        }


        double Xa = (b2 - b1) / (A1 - A2);

        if ((Xa < Math.max(p1.getX(), p3.getX())) || (Xa > Math.min(p2.getX(), p4.getX()))) {
            return false; //точка Xa находится вне пересечения проекций отрезков на ось X
        } else {
            return true;
        }
    }

    @Override
    public void finishActivity() {
          Gameisprogress = false;
          this.finisher.finishActivity();
    }

    private void victory() {
        Gameisprogress = false;
        Log.d(MainActivity.LOG, "VICTORY");
        Log.d(MainActivity.LOG, "VICTORY");
        Log.d(MainActivity.LOG, "VICTORY");
    }
}
