package com.example.imaginaryconstellations.util;

public class Line {
    private Pointer kor1;
    private Pointer kor2;

    public Line(Pointer kor1, Pointer kor2) {
        this.kor1 = kor1;
        this.kor2 = kor2;
    }

    public Pointer getKor1() {
        return kor1;
    }

    public void setKor1(Pointer kor1) {
        this.kor1 = kor1;
    }

    public Pointer getKor2() {
        return kor2;
    }

    public void setKor2(Pointer kor2) {
        this.kor2 = kor2;
    }
}
