package com.example.imaginaryconstellations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.imaginaryconstellations.model.StarShape;
import com.example.imaginaryconstellations.util.GravityEnum;

import java.util.ArrayList;
import java.util.Random;

public class GameStarter extends AppCompatActivity implements View.OnTouchListener {
    public static  float x = 0;
    public static  float y = 0;
    public final static int maxX = 32;
    public final static int maxY = 20;
    public static float unitW = 0;
    public static float unitH = 0;
    public static ArrayList<StarShape> starShapes = new ArrayList<StarShape>();
    private Paint paint;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InizializateComponents();
        starShapes.clear();
        setContentView(new DrawGameView(this));


    }
    private  void  InizializateComponents()
    {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        GameStarter.x = event.getX();
        GameStarter.y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                break;
        }
        return true;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class  DrawGameView extends View
    {

        public DrawGameView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.GREEN);
            //
            unitW = canvas.getWidth() / maxX;
            unitH = canvas.getHeight() / maxY;
            //
            Bitmap cBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_0000_2);
            bitmap = Bitmap.createScaledBitmap(
                        cBitmap, canvas.getWidth(), canvas.getHeight(), false);
            canvas.drawBitmap(bitmap, 0, 0, null);
            paint = new Paint();
            GenerateStars(canvas);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            canvas.drawLine(0, 0, 480, 650, paint);
            canvas.drawLine(480, 0, 0, 650, paint);
        }
        private void GenerateStars(Canvas canvas)
        {
            double starsquantaty = 7 + Math.random()*8;
            for(int i= 0;i< starsquantaty;i++)
            {
                StarShape starShape = new StarShape(GameStarter.this);
                starShape.drow(paint,canvas);
                starShapes.add(starShape);
            }
        }
    }

}
