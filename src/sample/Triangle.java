package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javax.vecmath.*;

/**
 * Created by Тим on 06.03.2017.
 */
class Triangle extends Mesh {
    private GraphicsContext gc;

    Vector3d[] vertex = {new Vector3d(), new Vector3d(), new Vector3d()};
    int[] edges = {0, 1, 1, 2, 2, 0};

    Triangle(Vector3d[] vertex, GraphicsContext gc) {
        this.vertex[0].set(vertex[0]);
        this.vertex[1].set(vertex[1]);
        this.vertex[2].set(vertex[2]);
        this.gc = gc;
    }

    Vector3d[] targetVectors = {new Vector3d(), new Vector3d(), new Vector3d()};

    @Override
    public void draw() {
        matrix.mul(Main.cameraNode.worldTransform, matrix);
        matrix.mul(Main.cameraNode.camera.matrix, matrix);

        for (int i = 0; i < vertex.length; i++) {
            transform3d(vertex[i], targetVectors[i]);
            targetVectors[i].x = (targetVectors[i].x + 1) * 400;
            targetVectors[i].y = (targetVectors[i].y + 1) * 400;
        }

        gc.beginPath();
        for (int i = 0; i < edges.length; i += 2) {
            gc.moveTo(targetVectors[edges[i]].x, targetVectors[edges[i]].y);
            gc.lineTo(targetVectors[edges[i + 1]].x, targetVectors[edges[i + 1]].y);
        }
        gc.closePath();
        gc.stroke();
    }
}



