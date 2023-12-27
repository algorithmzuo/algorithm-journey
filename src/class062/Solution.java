package class062;

import java.util.*;

class Solution {
    public int maxDistance(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[] dirs = {-1, 0, 1, 0, -1};
        pair[] queue = new pair[n * m];
        int top = 0, bottom = 0, max = -1;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (grid[i][j] == 1)
                    queue[top++] = new pair(i, j, 0);

        if (top == 0 || top - bottom == n * m) return -1;

        while (top != bottom) {
            int size = top - bottom;
            for (int i = 0; i < size; i++) {
                pair cell = queue[bottom++];
                int r = cell.r, c = cell.c, cur = cell.d;
                for (int k = 0; k < 4; k++) {
                    int newR = r + dirs[k], newC = c + dirs[k + 1];
                    boolean inArea = newR >= 0 && newR < n && newC >= 0 && newC < m;
                    if (!inArea || grid[newR][newC] != 0) continue;
                    grid[newR][newC] = 1;
                    queue[top++] = new pair(newR, newC, cur + 1);
                }
                max = cur;
            }
        }

        return max;
    }

    class pair {
        int r, c, d;

        pair(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

}
