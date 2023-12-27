package class057;

import java.util.*;

class Solution {
    int n, m, dirs[] = {0, -1, 0, 1, 0};
    char[][] board;

    public int numIslands(char[][] board) {
        this.n = board.length;
        this.m = board[0].length;
        this.board = board;
        int islands = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] == '1') {
                    islands++;
                    dfs(r, c);
                }
            }
        }
        return islands;
    }

    void dfs(int r, int c) {
        if (r < 0 || r == n || c < 0 || c == m || board[r][c] != '1') return;
        board[r][c] = 0;
        for (int k = 0; k < 4; k++) {
            int newR = r + dirs[k], newC = c + dirs[k + 1];
            dfs(newR, newC);
        }
    }
}