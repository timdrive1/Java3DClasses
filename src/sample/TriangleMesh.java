package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.Vector3d;


/**
 * Created by Тим on 17.04.2017.
 */
public class TriangleMesh extends Mesh {
    int[] indices;

    Vector3d[] vertices;
    Vector3d[] normals;

    Vector3d[] transformedPositions;
    Vector3d[] transformedNormals;

    Color color = Color.RED;

    double[] zbuffer;
    private Vector3d n = new Vector3d();
    private GraphicsContext gc;

    TriangleMesh(byte[] pixels, double[] zbuffer, GraphicsContext gc, Vector3d[] vertices, int[] indices, Vector3d[] normals) {
        this.zbuffer = zbuffer;
        this.pixels = pixels;
        this.gc = gc;
        this.vertices = vertices;
        this.normals = normals;
        this.indices = indices;
        transformedPositions = new Vector3d[vertices.length];
        transformedNormals = new Vector3d[normals.length];
        for (int i = 0; i < vertices.length; i++) {
            transformedPositions[i] = new Vector3d();
            transformedNormals[i] = new Vector3d();
        }
    }

    public void draw() {
        for (int i = 0; i < vertices.length; i++) {
            transformNormals3d(normals[i], transformedNormals[i]);
        }

        matrix.mul(Main.cameraNode.worldTransform, matrix);
        matrix.mul(Main.cameraNode.camera.matrix, matrix);

        for (int i = 0; i < vertices.length; i++) {
            transform3d(vertices[i], transformedPositions[i]);
            transformedPositions[i].x = (transformedPositions[i].x + 1) * 400;
            transformedPositions[i].y = (transformedPositions[i].y + 1) * 400;
        }

        for (int i = 0; i < indices.length / 3; i++) {
            final int i1 = indices[i * 3];
            final int i2 = indices[i * 3 + 1];
            final int i3 = indices[i * 3 + 2];

            final Vector3d p1 = transformedPositions[i1];
            final Vector3d p2 = transformedPositions[i2];
            final Vector3d p3 = transformedPositions[i3];

            final Vector3d n1 = transformedNormals[i1];
            final Vector3d n2 = transformedNormals[i2];
            final Vector3d n3 = transformedNormals[i3];

            drawTriangle(p1, p2, p3, n1, n2, n3);
        }
    }

    private void drawTriangle(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d n1, Vector3d n2, Vector3d n3) {
        int minX = (int) Math.ceil(Math.max(Math.min(Math.min(v1.x, v2.x), v3.x), 0));
        int maxX = (int) Math.ceil(Math.min(Math.max(Math.max(v1.x, v2.x), v3.x), 800));
        int minY = (int) Math.ceil(Math.max(Math.min(Math.min(v1.y, v2.y), v3.y), 0));
        int maxY = (int) Math.ceil(Math.min(Math.max(Math.max(v1.y, v2.y), v3.y), 800));
        double lyamdiv = (v2.y - v3.y) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.y - v3.y);
        final double borderWidth = 0.02;

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                double lyam1 = ((v2.y - v3.y) * (i - v3.x) + (v3.x - v2.x) * (j - v3.y)) / lyamdiv;
                double lyam2 = ((v3.y - v1.y) * (i - v3.x) + (v1.x - v3.x) * (j - v3.y)) / lyamdiv;
                double lyam3 = 1 - lyam1 - lyam2;
                if ((lyam1 < 0) || (lyam2 < 0) || (lyam3 < 0)) continue;

                n.x = lyam1 * n1.x + lyam2 * n2.x + lyam3 * n3.x;
                n.y = lyam1 * n1.y + lyam2 * n2.y + lyam3 * n3.y;
                n.z = lyam1 * n1.z + lyam2 * n2.z + lyam3 * n3.z;
                n.normalize();

                double light = Math.max(0.0, n.dot(Main.l));

                double z = lyam1 * v1.z + lyam2 * v2.z + lyam3 * v3.z;

             /*   if ((lyam1 < borderWidth) || (lyam2 < borderWidth) || (lyam3 < borderWidth)) {
                    if (zbuffer[800 * j + i] < z) {
                      *//*  pixels[(800 * j + i) * 3] = (byte) (colorbord.getRed() * 255);
                        pixels[(800 * j + i) * 3 + 1] = (byte) (colorbord.getGreen() * 255);
                        pixels[(800 * j + i) * 3 + 2] = (byte) (colorbord.getBlue() * 255);
                        zbuffer[800 * j + i] = z;*//*
                    }*/
                if (zbuffer[800 * j + i] < z) {
                    pixels[(800 * j + i) * 3] = (byte) (255 * light);
                    pixels[(800 * j + i) * 3 + 1] = (byte) (255 * light);
                    pixels[(800 * j + i) * 3 + 2] = (byte) (255 * light);
                    zbuffer[800 * j + i] = z;
                }
            }
        }
    }
}
