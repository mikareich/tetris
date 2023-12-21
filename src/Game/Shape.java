package Game;

public class Shape {
    private final int[] pivot;
    public int[][] edges;

    public Shape(int[][] edges, int[] pivot) {
        this.edges = edges;
        this.pivot = pivot;
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
        return new Shape(edges, new int[]{0, 1});
    }

    public static Shape Square() {
        int[][] edges = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        return new Shape(edges, null);
    }

    public void rotate() {
        if (pivot == null) return;

        for (int i = 0; i < edges.length; i++) {
            int deltaX = edges[i][0] - pivot[0];
            int deltaY = edges[i][1] - pivot[1];
            edges[i][0] = pivot[0] - deltaY;
            edges[i][1] = pivot[1] + deltaX;
        }
    }

    public int[][] getAbsoluteShape(int[] position) {
        int NUMBER_OF_EDGES = edges.length;
        int[][] absoluteEdges = new int[NUMBER_OF_EDGES][2];

        for (int i = 0; i < NUMBER_OF_EDGES; i++) {
            int[] edge = edges[i];
            absoluteEdges[i][0] = edge[0] + position[0];
            absoluteEdges[i][1] = edge[1] + position[1];
        }

        return absoluteEdges;
    }

    public int[][] getAbsoluteOutlines(int[] position) {
        int[][] absoluteEdges = getAbsoluteShape(position);

        int[] topLeft = absoluteEdges[0];
        int[] bottomRight = absoluteEdges[1];

        for (int[] edge : absoluteEdges) {
            if (edge[0] < topLeft[0] && edge[1] < topLeft[1]) {
                topLeft = edge;
            } else if (edge[0] > bottomRight[0] && edge[1] > bottomRight[1]) {
                bottomRight = edge;
            }
        }

        return new int[][]{topLeft, bottomRight};
    }
}
