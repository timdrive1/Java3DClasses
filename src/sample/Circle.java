package sample;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import javax.vecmath.Vector2d;
import java.util.ArrayList;

/**
 * Created by Тим on 02.03.2017.
 */
class Circle extends Shape {

    private int radius;
    private Vector2d center;
    private Color color;
    private Color colorborder;

    private Vector2d tcenter = new Vector2d();

    Circle(byte[] pixels, int radius, Vector2d center, Color color, Color colorborder) {
        this.pixels = pixels;

        this.radius = radius;
        this.center = center;
        this.color = color;
        this.colorborder = colorborder;
    }

    public void draw() {
        transform2d(center, tcenter);
        int x = (int) tcenter.x;
        int y = (int) tcenter.y;
        final double R = Math.pow(radius, 2);
        final double borderWidth = 500;
        for (int i = x - radius; i < x + radius; i++) {
            for (int j = y - radius; j < y + radius; j++) {
                final double r = Math.pow((i - x), 2) + Math.pow((j - y), 2);
                if (r <= R && r >= R - borderWidth) {
                    pixels[(800 * j + i) * 3] = (byte)(colorborder.getRed() * 255.0);
                    pixels[(800 * j + i) * 3 + 1] = (byte)(colorborder.getGreen() * 255.0);
                    pixels[(800 * j + i) * 3 + 2] = (byte)(colorborder.getBlue() * 255.0);

                } else if (r <= R - borderWidth) {
                    pixels[(800 * j + i) * 3] = (byte)(color.getRed() * 255.0);
                    pixels[(800 * j + i) * 3 + 1] = (byte)(color.getGreen() * 255.0);
                    pixels[(800 * j + i) * 3 + 2] = (byte)(color.getBlue() * 255.0);

                }
            }
        }

    }
}
