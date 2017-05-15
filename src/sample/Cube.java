package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javax.vecmath.Vector3d;

/**
 * Created by Тим on 06.04.2017.
 */
public class Cube extends Mesh {
    Vector3d[] vertex = {
            new Vector3d(-1, -1, -1),
            new Vector3d(1, -1, -1),
            new Vector3d(1, 1, -1),
            new Vector3d(-1, 1, -1),
            new Vector3d(-1, -1, 1),
            new Vector3d(1, -1, 1),
            new Vector3d(1, 1, 1),
            new Vector3d(-1, 1, 1)};

    int[] edges = {0, 1, 1, 2, 2, 3, 3, 0, 0, 4, 1, 5, 5, 4, 3, 7, 7, 6, 6, 5, 4, 7, 2, 6};
    private GraphicsContext gc;
    private Vector3d[] vertexTransform = {new Vector3d(),
            new Vector3d(),
            new Vector3d(),
            new Vector3d(),
            new Vector3d(),
            new Vector3d(),
            new Vector3d(),
            new Vector3d()};
    private Color color, colorbord;

    public Cube(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void draw() {
        matrix.mul(Main.cameraNode.worldTransform, matrix);
        matrix.mul(Main.cameraNode.camera.matrix, matrix);
        for (int i = 0; i < vertex.length; i++) {
            transform3d(vertex[i], vertexTransform[i]);
            vertexTransform[i].x = 400 * vertexTransform[i].x + 400;
            vertexTransform[i].y = 400 * vertexTransform[i].y + 400;
            System.out.println(vertexTransform[i].x+" "+vertexTransform[i].y);
        }


        gc.setStroke(Color.BLACK);

        gc.beginPath();
        for (int i = 0; i < 24 ; i+=2) {
            gc.moveTo(vertexTransform[edges[i]].x, vertexTransform[edges[i]].y);
            gc.lineTo(vertexTransform[edges[i+1]].x, vertexTransform[edges[i+1]].y);
        }
        gc.closePath();
        gc.stroke();

    }



    }

