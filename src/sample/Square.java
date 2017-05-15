package sample;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;

/**
 * Created by Тим on 06.03.2017.
 */
class Square extends Shape {

    private Color color, colorbord;
    private Vector2d s1, s2, s3, s4;

    private Vector2d v1 = new Vector2d();
    private Vector2d v2 = new Vector2d();
    private Vector2d v3 = new Vector2d();
    private Vector2d v4 = new Vector2d();


    Square(byte[] pixels,  Vector2d v1, Vector2d v2, Vector2d v3, Vector2d v4, Color color, Color colorbord) {
        this.pixels = pixels;

        this.s1 = v1;
        this.s2 = v2;
        this.s3 = v3;
        this.s4 = v4;
        this.color = color;
        this.colorbord = colorbord;

    }

    public void draw() {
        transform2d(this.s1, v1);
        transform2d(this.s2, v2);
        transform2d(this.s3, v3);
        transform2d(this.s4, v4);


        int minX = (int) Math.round(Math.min(Math.min(v1.x, v2.x), v3.x));
        int maxX = (int) Math.round(Math.max(Math.max(v1.x, v2.x), v3.x));
        int minY = (int) Math.round(Math.min(Math.min(v1.y, v2.y), v3.y));
        int maxY = (int) Math.round(Math.max(Math.max(v1.y, v2.y), v3.y));
        double lyamdiv = (v2.y - v3.y) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.y - v3.y);
        final double borderWidth = 0.005;
        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                double lyam1 = ((v2.y - v3.y) * (i - v3.x) + (v3.x - v2.x) * (j - v3.y)) / lyamdiv;
                double lyam2 = ((v3.y - v1.y) * (i - v3.x) + (v1.x - v3.x) * (j - v3.y)) / lyamdiv;
                double lyam3 = 1 - lyam1 - lyam2;
                if ((lyam1 < 0) || (lyam2 < 0) || (lyam3 < 0)) continue;
                if ((lyam2 < borderWidth) || (lyam3 < borderWidth)) {
                    pixels[(800 * j + i) * 3] = (byte)(colorbord.getRed() * 255);
                    pixels[(800 * j + i) * 3 + 1] = (byte)(colorbord.getGreen() * 255);
                    pixels[(800 * j + i) * 3 + 2] = (byte)(colorbord.getBlue() * 255);
                } else {
                    pixels[(800 * j + i) * 3] = (byte)(color.getRed() * 255);
                    pixels[(800 * j + i) * 3 + 1] = (byte)(color.getGreen() * 255);
                    pixels[(800 * j + i) * 3 + 2] = (byte)(color.getBlue() * 255);

                }
            }
        }
        minX = (int) Math.min(Math.min(v2.x, v3.x), v4.x);
        maxX = (int) Math.max(Math.max(v2.x, v3.x), v4.x);
        minY = (int) Math.min(Math.min(v2.y, v3.y), v4.y);
        maxY = (int) Math.max(Math.max(v2.y, v3.y), v4.y);
        lyamdiv = (v3.y - v4.y) * (v2.x - v4.x) + (v4.x - v3.x) * (v2.y - v4.y);

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                double lyam1 = ((v3.y - v4.y) * (i - v4.x) + (v4.x - v3.x) * (j - v4.y)) / lyamdiv;
                double lyam2 = ((v4.y - v2.y) * (i - v4.x) + (v2.x - v4.x) * (j - v4.y)) / lyamdiv;
                double lyam3 = 1 - lyam1 - lyam2;
                if ((lyam1 < 0) || (lyam2 < 0) || (lyam3 < 0)) continue;
                if ((lyam1 < borderWidth) || (lyam2 < borderWidth)) {
                    pixels[(800 * j + i) * 3] = (byte) ((byte)(colorbord.getRed() * 255) & 0xff);
                    pixels[(800 * j + i) * 3 + 1] = (byte)(colorbord.getGreen() * 255);
                    pixels[(800 * j + i) * 3 + 2] = (byte)(colorbord.getBlue() * 255);
                } else {
                    pixels[(800 * j + i) * 3] = (byte) ((byte)(color.getRed() * 255.0) & 0xff);
                    pixels[(800 * j + i) * 3 + 1] = (byte)(color.getGreen() * 255);
                    pixels[(800 * j + i) * 3 + 2] = (byte)(color.getBlue() * 255);

                }

            }
        }
    }
}
