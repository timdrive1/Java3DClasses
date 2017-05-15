package sample;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector2d;

/**
 * Created by Тим on 20.03.2017.
 */
abstract class Shape implements Drawable {
    byte[] pixels;
    Matrix3d matrix = new Matrix3d();

    public Shape() {
        matrix.setIdentity();
    }

    public void transform2d(Vector2d source, Vector2d target){
        target.x = source.x * matrix.getM00() + source.y * matrix.getM01() + matrix.getM02();
        target.y = source.x * matrix.getM10() + source.y * matrix.getM11() + matrix.getM12();
    }

    public Vector2d transform2d(Vector2d source){
        Vector2d target = new Vector2d();
        target.x = source.x * matrix.getM00() + source.y * matrix.getM01() + matrix.getM02();
        target.y = source.x * matrix.getM10() + source.y * matrix.getM11() + matrix.getM12();
        return target;
    }
}