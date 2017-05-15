package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.stage.Stage;

import javax.vecmath.*;
import java.util.ArrayList;

public class Main extends Application {
    static Node cameraNode;
    static Vector3d l = new Vector3d(0, 0, -1);
    private double angle = 0.12;

    private long time = 0;
    private byte[] pixels;
    private double[] zbuffer;

    public static void main(String[] args) {
        l.normalize();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        PixelWriter pw = gc.getPixelWriter();


        ArrayList<Node> meshNodes = new ArrayList<>();


        pixels = new byte[800 * 800 * 3];
        zbuffer = new double[800 * 800];

    
       /* Cube cube = new Cube(gc);
        Node cubeNode = new Node();
        cubeNode.mesh = cube;
        cubeNode.computeTransform();
        meshNodes.add(cubeNode);

        Pir pir= new Pir(gc);
        Node pirNode = new Node(cubeNode);
        pirNode.mesh = pir;
        pirNode.computeTransform();
        meshNodes.add(pirNode);*/

        Node cameraNode = new Node();
        cameraNode.camera = new Camera(0.1, 100, 1, Math.PI / 2);
        cameraNode.matrix.setColumn(3, 0, 0, 2.2, 1);
        cameraNode.computeTransform();
        Main.cameraNode = cameraNode;

        Reader readFile = new Reader();
        readFile.readFile();

        Vector3d[] positions = new Vector3d[readFile.positions.length / 3];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = new Vector3d(readFile.positions[i * 3], readFile.positions[i * 3 + 1], readFile.positions[i * 3 + 2]);
        }

        Vector3d[] normals = new Vector3d[readFile.normals.length / 3];
        for (int i = 0; i < normals.length; i++) {
            normals[i] = new Vector3d(readFile.normals[i * 3], readFile.normals[i * 3 + 1], readFile.normals[i * 3 + 2]);
        }

        int[] index = new int[readFile.indices.length];
        for (int i = 0; i < readFile.indices.length; i++) {
            index[i] = readFile.indices[i] & 0xffff;
        }

        TriangleMesh triangleMesh = new TriangleMesh(pixels, zbuffer, gc, positions, index, normals);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                for (int i = 0; i < pixels.length; i++) pixels[i] = (byte) 0;
                for (int i = 0; i < zbuffer.length; i++) zbuffer[i] = -1;
                triangleMesh.matrix.rotY(angle);
                triangleMesh.matrix.setScale(0.01);
                triangleMesh.draw();
                pw.setPixels(0, 0, 800, 800, PixelFormat.getByteRgbInstance(), pixels, 0, 800 * 3);

                angle += 0.01;
            }
        };
        timer.start();
    }

}