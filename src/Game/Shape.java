package Game;

import java.util.Random;

public class Shape {
    public final int[][] edges;

    public Shape(int[][] edges) {
        this.edges = edges;
    }

    public  int[][] getAbsoluteShape( int[] position) {
        int NUMBER_OF_EDGES = edges.length;
        int[][] absoluteEdges = new int[NUMBER_OF_EDGES][2];

        for (int i = 0; i < NUMBER_OF_EDGES; i++) {
            int[] edge = edges[i];
            absoluteEdges[i][0] = edge[0] + position[0];
            absoluteEdges[i][1] = edge[1] + position[1];
        }

        return absoluteEdges;
    }

    public static Shape Random() {
        int shapeIndex = (int) (Math.random() * 2);

        return switch (shapeIndex) {
            case 0 -> Square();
            default -> L();
        };

    }

    public static Shape L() {
        int[][] edges = {{0, 0}, {0, 1}, {0, 2}, {1, 2}};
        return new Shape(edges);
    }

    public static Shape Square() {
        int[][] edges = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        return new Shape(edges);
    }
}
