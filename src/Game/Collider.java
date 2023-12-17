package Game;

public class Collider {
    private static boolean betweenEdges(int[] edgeA, int[] edgeB, int[] edgeC) {
        return (edgeA[0] <= edgeB[0] && edgeB[0] <= edgeC[0]) && (edgeA[1] <= edgeB[1] && edgeB[1] <= edgeC[1]);
    }

    public static boolean isCollided(Block block1, Block block2) {

        // Absolute Game.Shape = edge + position
        int[][] absoluteShapeA = block1.shape.getAbsoluteShape(block1.position);
        int[][] absoluteShapeB = block2.shape.getAbsoluteShape(block2.position);

        for (int[] edgeA : absoluteShapeA) {
            for (int[] edgeB : absoluteShapeB) {
                // A1 = edgeA[0]; A2 = edgeB[0]
                int[] C1 = {edgeA[0] + 1, edgeA[1] + 1};
                int[] C2 = {edgeB[0] + 1, edgeB[1] + 1};

                return betweenEdges(edgeA, edgeB, C1) || betweenEdges(edgeB, edgeA, C2);
            }
        }

        return false;
    }

    public static boolean withGameBorder(Block block) {
        int[][] edges = new int[2 * (Game.COLS + Game.ROWS)][2];
        for (int i = 0; i < (Game.COLS + Game.ROWS); i++) {
            if (i < Game.COLS) {
                edges[i][0] = i;
                edges[i][1] = -1;
                edges[i + Game.COLS + Game.ROWS][0] = i;
                edges[i + Game.COLS + Game.ROWS][1] = Game.ROWS;
            } else {
                edges[i][0] = -1;
                edges[i][1] = i - Game.COLS;
                edges[i + 2 * Game.ROWS][0] = Game.COLS;
                edges[i + 2 * Game.ROWS][1] = i - Game.COLS;
            }
        }
        Shape gameBorderShape = new Shape(edges);

        Block gameBorder = new Block(gameBorderShape);

        return Collider.isCollided(block, gameBorder);
    }
}
