package sample;

import javax.vecmath.Matrix4d;

/**
 * Created by Тим on 13.04.2017.
 */
public class Camera {
    double near;
    double far;
    double aspectRatio;
    double fov;
    Matrix4d matrix = new Matrix4d();

    public Camera(double near, double far, double aspectRatio, double fov) {
        double t = near * Math.tan(fov / 2);
        double r = aspectRatio * t;
        matrix.setM00(near / r);
        matrix.setM11(near / t);
        matrix.setM22((far + near) / (near - far));
        matrix.setM32(-1);
        matrix.setM23(2 * far * near / (near - far));
        this.near = near;
        this.far = far;
        this.aspectRatio = aspectRatio;
        this.fov = fov;
    }


}