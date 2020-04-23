package com.example.imaginaryconstellations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.imaginaryconstellations.model.StarShape;
import com.example.imaginaryconstellations.util.ActFinisher;
import com.example.imaginaryconstellations.util.Line;
import com.example.imaginaryconstellations.util.Pointer;

import java.util.ArrayList;

public class GameStarter extends AppCompatActivity implements View.OnTouchListener, ActFinisher {
    public  float x = 0;
    public static float rotate = 0;
    public  float y = 0;
    public Pointer startPointer = null;
    public Pointer finishPointer = null;
    public static ArrayList<Line> LArray = new ArrayList<>();
    public  boolean previousPointischeked = false;
    private StarShape StartstarShape = null,finishstarShape =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_starter);

        InizializateComponents();
    }

    private void InizializateComponents()
    {
        GameViewEvading.starShapes.clear();
        GameViewEvading gameView = new GameViewEvading(this,this);
        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        gameLayout.setOnTouchListener(this);
        gameLayout.addView(gameView); // и добавляем в него gameView
        GameStarter.LArray.clear();
        GameViewEvading.usesstarShapes.clear();
        GameViewEvading.starShapes.clear();
    }

    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                Log.d(MainActivity.LOG,"ACTION_DOWN" + x + " " + y);
                if(checkStarsSituation(x,y))
                {
                    if(previousPointischeked && startPointer !=null)
                    {
                        if(StartstarShape.x != finishstarShape.x && StartstarShape.y != finishstarShape.y)
                        {

                            finishPointer = new Pointer(x,y);
                            //Log.d(MainActivity.LOG,"LArray Addddd");
                            LArray.add(new Line(startPointer, finishPointer));
                            for(int i = 0;i<GameViewEvading.usesstarShapes.size();i++)
                            {
                                if(GameViewEvading.usesstarShapes.get(i) == StartstarShape)
                                {
                                    StartstarShape=null;
                                }
                                if(GameViewEvading.usesstarShapes.get(i) == finishstarShape)
                                {
                                    finishstarShape=null;
                                }
                            }
                            if(StartstarShape!=null)
                            {
                                GameViewEvading.usesstarShapes.add(StartstarShape);
                            }
                            if(finishstarShape!=null)
                            {
                                GameViewEvading.usesstarShapes.add(finishstarShape);
                            }
                            Log.d(MainActivity.LOG,"starShapes = " + GameViewEvading.starShapes.size() + "usesstarShapes = " +   GameViewEvading.usesstarShapes.size());

                        }
                        previousPointischeked = false;
                        StartstarShape = null;
                        finishstarShape = null;
                        startPointer = null;
                        finishPointer = null;
                    }
                    else
                    {
                        startPointer = new Pointer(x,y);
                        previousPointischeked = true;
                    }
                    Log.d(MainActivity.LOG,"POPALLLLLLLLL" + x + " " + y);
                }
                else
                {
                    previousPointischeked = false;
                }
                break;
        }
        return true;
    }
    private boolean checkStarsSituation(float x,float y)
    {
        for(int i = 0;i<GameViewEvading.starShapes.size();i++)
        {
            int GameStarX = (int) ( GameViewEvading.starShapes.get(i).x*GameViewEvading.unitW);
            int GameStarY = (int) ( GameViewEvading.starShapes.get(i).y*GameViewEvading.unitW);
            int GameStarR = (int) ( GameViewEvading.starShapes.get(i).radius*GameViewEvading.unitW*2);
            int maxX = GameStarX + GameStarR;
            int minX = GameStarX - GameStarR;
            int maxY = GameStarY + GameStarR;
            int minY = GameStarY - GameStarR;
            if(x<maxX&&x>minX&&y<maxY&&y>minY)
            {
                if(!previousPointischeked)
                {
                    StartstarShape=GameViewEvading.starShapes.get(i);
                }
                else
                {
                    finishstarShape=GameViewEvading.starShapes.get(i);
                }
                this.y = GameStarY;
                this.x = GameStarX+GameStarR/2;
                return true;
            }
        }
        return  false;
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
//    private  void  InizializateComponents()
//    {
//
//    }


//
//
//
//    class  DrawGameView extends View
//    {
//
//        public DrawGameView(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            canvas.drawColor(Color.GREEN);
//            //
//            unitW = canvas.getWidth() / maxX;
//            unitH = canvas.getHeight() / maxY;
//            //
//            Bitmap cBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_0000_2);
//            bitmap = Bitmap.createScaledBitmap(
//                        cBitmap, canvas.getWidth(), canvas.getHeight(), false);
//            canvas.drawBitmap(bitmap, 0, 0, null);
//            paint = new Paint();
//            GenerateStars(canvas);
//            paint.setColor(Color.BLUE);
//            paint.setStrokeWidth(10);
//            canvas.drawLine(0, 0, 480, 650, paint);
//            canvas.drawLine(480, 0, 0, 650, paint);
//        }
//        private void GenerateStars(Canvas canvas)
//        {
//            double starsquantaty = 7 + Math.random()*8;
//            for(int i= 0;i< starsquantaty;i++)
//            {
//                StarShape starShape = new StarShape(GameStarter.this);
//                starShape.drow(paint,canvas);
//                starShapes.add(starShape);
//            }
//        }
//    }
//}
