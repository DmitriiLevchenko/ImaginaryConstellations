package com.example.imaginaryconstellations.model;

import android.content.Context;
import android.util.Log;

import com.example.imaginaryconstellations.GameStarter;
import com.example.imaginaryconstellations.MainActivity;
import com.example.imaginaryconstellations.R;

import java.util.Random;

public class StarShape extends Shape {
    private int radius = 1; // радиус
    public StarShape(Context context) {
        Random random = new Random();
        do
        {
            y=radius*2 + random.nextInt(GameStarter.maxY) - radius*2;
            x =radius*2 + random.nextInt(GameStarter.maxX) - radius*2;
        }while (checkcolision(x,y));
        bitmapId = R.drawable.spinnercompressedtransparent;
        size = radius*2;
        init(context);
    }
    public boolean checkcolision(int x,int y)
    {
        for(int i = 0;i<GameStarter.starShapes.size();i++)
        {
           // return !(((x+Ssize) < starx)||(x > (starx+Ssize))||((y+Ssize) < stary)||(y > (stary+Ssize)));
            int GameStarX = (int) GameStarter.starShapes.get(i).x;
            int GameStarY = (int) GameStarter.starShapes.get(i).y;
            int GameStarR = (int) GameStarter.starShapes.get(i).radius*5;
            int maxX = GameStarX + GameStarR;
            int minX = GameStarX - GameStarR;
            int maxY = GameStarY + GameStarR;
            int minY = GameStarY - GameStarR;
            Log.d(MainActivity.LOG, "GameStarX+GameStarR  = " + (GameStarX+GameStarR) + " GameStarX-GameStarR = " + (GameStarX-GameStarR)
                    + "   GameStarY+GameStarR  = " +(GameStarY+GameStarR) + " GameStarY-GameStarR ="  + (GameStarY-GameStarR) + "Our Koordinates" + "X =" +
                    x + "Y=" + y);
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
