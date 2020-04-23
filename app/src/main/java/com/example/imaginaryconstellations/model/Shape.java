package com.example.imaginaryconstellations.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.imaginaryconstellations.GameStarter;
import com.example.imaginaryconstellations.GameViewEvading;

public class Shape {
    public int x; // координаты
    public int y;
    public float size; // размер
    protected int bitmapId; // id картинки
    protected Bitmap bitmap; // картинка

    public void init(Context context) { // сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size * GameViewEvading.unitW), (int)(size * GameViewEvading.unitH), false);
        cBitmap.recycle();
    }
    public void drow(Paint paint, Canvas canvas){ // рисуем картинку
        canvas.drawBitmap(bitmap, x* GameViewEvading.unitW, y* GameViewEvading.unitH, paint);
    }

}
