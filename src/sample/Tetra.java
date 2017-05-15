package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.Vector3d;

/**
 * Created by Тим on 17.04.2017.
 */
public class Tetra extends Mesh {
    Vector3d[] vertex = {new Vector3d(0, 0, 0),
            new Vector3d(1, 0, 0),
            new Vector3d(0.5, Math.sqrt(3) / 2, 0 ),
            new Vector3d(0.5, Math.sqrt(3) / 6, Math.sqrt(2) / Math.sqrt(6))};

    int[] edges = {0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 2, 3};
    private GraphicsContext gc;
    private Vector3d[] vertexTransform = {
            new Vector3d(),
            new Vector3d(),
            new Vector3d(),
            new Vector3d(),
    };
    private Color color, colorbord;

    public Tetra(GraphicsContext gc) {
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
        for (int i = 0; i < 12 ; i+=2) {
            gc.moveTo(vertexTransform[edges[i]].x, vertexTransform[edges[i]].y);
            gc.lineTo(vertexTransform[edges[i+1]].x, vertexTransform[edges[i+1]].y);
        }
        gc.closePath();
        gc.stroke();

    }



}

