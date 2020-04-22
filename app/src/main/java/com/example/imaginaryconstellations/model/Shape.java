package com.example.imaginaryconstellations.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.imaginaryconstellations.GameStarter;

public class Shape {
    protected int x; // координаты
    protected int y;
    protected float size; // размер
    protected int bitmapId; // id картинки
    protected Bitmap bitmap; // картинка

    public void init(Context context) { // сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size * GameStarter.unitW), (int)(size * GameStarter.unitH), false);
        cBitmap.recycle();
    }
    public void drow(Paint paint, Canvas canvas){ // рисуем картинку
        canvas.drawBitmap(bitmap, x* GameStarter.unitW, y* GameStarter.unitH, paint);
    }

}
