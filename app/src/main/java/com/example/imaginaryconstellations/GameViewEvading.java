package com.example.imaginaryconstellations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;


import com.example.imaginaryconstellations.activitys.GameStarter;
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
    private Paint lpaint = new Paint();
    private Canvas canvas;
    private Context context;
    private SurfaceHolder surfaceHolder;
    private Bitmap bitmap;
    private Boolean cheker = false;
    static boolean  interrupted;// Tests whether the current thread has been interrupted.
    private TextView textView;
    public static ArrayList<StarShape> starShapes = new ArrayList<StarShape>();
    public static ArrayList<StarShape> usesstarShapes = new ArrayList<>();
    ActFinisher finisher;
    Handler h;


    public GameViewEvading(Context context, ActFinisher finisher) {
        super(context);
        textView = findViewById(R.id.textmassege);
        lpaint.setColor(Color.YELLOW);
        lpaint.setStrokeWidth(8);
        interrupted = true;
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
        while (interrupted) {
            if(interrupted)
            {
                draw();
            }
            if(interrupted)
            {
                CheckGameStatus();
            }
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
            if(interrupted)
            {
                canvas = surfaceHolder.lockCanvas();

                Bitmap cBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_0000_2);
                if (!cheker) {
                    cheker = true;
                    bitmap = Bitmap.createScaledBitmap(
                            cBitmap, surfaceHolder.getSurfaceFrame().width(), surfaceHolder.getSurfaceFrame().height(), false);
                }
                if(bitmap != null) {
                    canvas.drawBitmap(bitmap, 0, 0, null);//ф
                }

                for (int i = 0; i < starShapes.size(); i++) {
                    starShapes.get(i).drow(paint, canvas);
                }

                for (int i = 0; i < GameStarter.LArray.size(); i++) {
                    canvas.drawLine(GameStarter.LArray.get(i).getKor1().getX(), GameStarter.LArray.get(i).getKor1().getY(), GameStarter.LArray.get(i).getKor2().getX(), GameStarter.LArray.get(i).getKor2().getY(), lpaint);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void GenerateStars() {
        double starsquantaty = 7 + Math.random() * 4;
        for (int i = 0; i < starsquantaty; i++) {
            StarShape starShape = new StarShape(context);
            //starShape.drow(paint,canvas);
            starShapes.add(starShape);
        }
    }

    private void CheckGameStatus() {
        for (int i = 0; i < GameStarter.LArray.size(); i++) {
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
        if (GameViewEvading.starShapes.size() == GameViewEvading.usesstarShapes.size() && GameViewEvading.starShapes.size() != 0) {
            victory();
        }
    }
    private boolean checkIntersectionOfTwoLineSegments(Pointer p1, Pointer p2, Pointer p3, Pointer p4) {
        if (p2.x < p1.x) {

            Pointer tmp = p1;
            p1 = p2;
            p2 = tmp;
        }

        if (p4.x < p3.x) {

            Pointer tmp = p3;
            p3 = p4;
            p4 = tmp;
        }
        if (p2.x < p3.x) {
            return false;
        }
//если оба отрезка вертикальные
        if((p1.x - p2.x == 0) && (p3.x - p4.x == 0)) {

            //если они лежат на одном X
            if(p1.x == p3.x) {

                //проверим пересекаются ли они, т.е. есть ли у них общий Y
                //для этого возьмём отрицание от случая, когда они НЕ пересекаются
                if (!((Math.max(p1.y, p2.y) < Math.min(p3.y, p4.y)) ||
                        (Math.min(p1.y, p2.y) > Math.max(p3.y, p4.y)))) {

                    return true;
                }
            }

            return false;
        }
        //
        if (p1.x - p2.x == 0) {

            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = p1.x;
            double A2 = (p3.y - p4.y) / (p3.x - p4.x);
            double b2 = p3.y - A2 * p3.x;
            double Ya = A2 * Xa + b2;

            if (p3.x <= Xa && p4.x >= Xa && Math.min(p1.y, p2.y) <= Ya &&
                    Math.max(p1.y, p2.y) >= Ya) {

                return true;
            }

            return false;
        }
        //
        //если второй отрезок вертикальный
        if (p3.x - p4.x == 0) {

            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = p3.x;
            double A1 = (p1.y - p2.y) / (p1.x - p2.x);
            double b1 = p1.y - A1 * p1.x;
            double Ya = A1 * Xa + b1;

            if (p1.x <= Xa && p2.x >= Xa && Math.min(p3.y, p4.y) <= Ya &&
                    Math.max(p3.y, p4.y) >= Ya) {

                return true;
            }

            return false;
        }
        //оба отрезка невертикальные
        double A1 = (p1.y - p2.y) / (p1.x - p2.x);
        double A2 = (p3.y - p4.y) / (p3.x - p4.x);
        double b1 = p1.y - A1 * p1.x;
        double b2 = p3.y - A2 * p3.x;

        if (A1 == A2) {
            return false; //отрезки параллельны
        }

//Xa - абсцисса точки пересечения двух прямых
        double Xa = (b2 - b1) / (A1 - A2);

        if ((Xa < Math.max(p1.x, p3.x)) || (Xa > Math.min( p2.x, p4.x))) {
            return false; //точка Xa находится вне пересечения проекций отрезков на ось X
        }
        else {
            return true;
        }
    }

    
    @Override
    public void finishActivity() {
        interrupted = false;
        GameStarter.restart = true;
        GameStarter.Smassege = ("FINISH,Game Restart");
        //textView.setText("Tap to continue");
    }

    private void victory() {
        GameStarter.Smassege = ("VICTORY,Game restart");
        GameStarter.restart = true;
        interrupted = false;
        //textView.setText("Tap to continue");
        //this.finisher.finishActivity();
    }
}
