package Game;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Collider {
    public static boolean removeFullRows(Block[] blocks) {
        // 1. go through each block
        // 2. add absolute edge to dedicated row
        // 3. check if row isn't completed
        // 4. delete all edges in row
        boolean rowRemoved = false;
        int[][] rows = new int[Game.ROWS][Game.COLS];

        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            int[][] edges = block.shape.getAbsoluteShape(block.position);
            for (int[] edge : edges) {
                rows[edge[1]][edge[0]] = i + 1;
            }
        }

        // remove all edges of the completed rows
        for (int i = 0; i < rows.length; i++) {
            int[] row = rows[i];
            if (ArrayUtils.contains(row, 0)) continue;

            rowRemoved = true;
            int y = i;
            for (int j = 0; j < row.length; j++) {
                int blockIndex = row[j] - 1;
                Block block = blocks[blockIndex];
                block.shape.edges = Arrays.stream(block.shape.edges).filter(edge -> (edge[1] + block.position[1]) != y).toArray(int[][]::new);
            }
        }
        return rowRemoved;
    }

    private static boolean betweenEdges(int[] edgeA, int[] edgeB, int[] edgeC) {
        return (edgeA[0] <= edgeB[0] && edgeB[0] < edgeC[0]) && (edgeA[1] <= edgeB[1] && edgeB[1] < edgeC[1]);
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

                if (betweenEdges(edgeA, edgeB, C1) || betweenEdges(edgeB, edgeA, C2)) return true;
            }
        }

        return false;
    }

    public static boolean withGameBorder(Block block) {
        int[][] outline = block.shape.getAbsoluteOutlines(block.position);
        int[] topLeft = outline[0];
        int[] bottomRight = outline[1];

        return topLeft[0] < 0 || topLeft[1] < 0 || bottomRight[0] >= Game.COLS || bottomRight[1] >= Game.ROWS;
    }
}
