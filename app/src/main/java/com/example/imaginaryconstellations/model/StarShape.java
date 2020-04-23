package com.example.imaginaryconstellations.model;

import android.content.Context;
import android.util.Log;

import com.example.imaginaryconstellations.GameStarter;
import com.example.imaginaryconstellations.GameViewEvading;
import com.example.imaginaryconstellations.MainActivity;
import com.example.imaginaryconstellations.R;

import java.util.Random;

public class StarShape extends Shape {
    public int radius = 1; // радиус
    public StarShape(Context context) {
        Random random = new Random();
        do
        {

            y= (int) (radius*2 + Math.random()*(GameViewEvading.maxY-radius*5));
            x = (int) (radius*2 + Math.random()*(GameViewEvading.maxX-radius*5));
        }while (checkcolision(x,y));
        bitmapId = R.drawable.spinnerverycompressed;
        size = radius*2;
        init(context);
    }
    public boolean checkcolision(int x,int y)
    {
        for(int i = 0;i<GameViewEvading.starShapes.size();i++)
        {
            int GameStarX = (int) GameViewEvading.starShapes.get(i).x;
            int GameStarY = (int) GameViewEvading.starShapes.get(i).y;
            int GameStarR = (int) GameViewEvading.starShapes.get(i).radius*4;
            int maxX = GameStarX + GameStarR;
            int minX = GameStarX - GameStarR;
            int maxY = GameStarY + GameStarR;
            int minY = GameStarY - GameStarR;
            if(x<maxX&&x>minX&&y<maxY&&y>minY)
            {
                Log.d(MainActivity.LOG,"true");
                return true;
            }
        }
        Log.d(MainActivity.LOG,"false");
        return false;
    }
}
