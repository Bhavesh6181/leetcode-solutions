import java.util.*;

class Solution {

    static class State {
        int r, c, energy, mask;

        State(int r, int c, int energy, int mask) {
            this.r = r;
            this.c = c;
            this.energy = energy;
            this.mask = mask;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();   // FIXED

        int sr = -1, sc = -1;
        int litterCount = 0;

        // Store ID of every litter cell
        int[][] litterId = new int[m][n];

        for (int i = 0; i < m; i++) {
            Arrays.fill(litterId[i], -1);

            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        // If there are k litter cells:
        // allMask = 111...111 (k bits)
        int allMask = (1 << litterCount) - 1;

        /*
         * visited[row][col][energy][mask]
         *
         * row, col   -> current position
         * energy     -> remaining energy
         * mask       -> collected litter
         */
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        Queue<State> queue = new ArrayDeque<>();

        // Initial state
        queue.offer(new State(sr, sc, energy, 0));
        visited[sr][sc][energy][0] = true;

        int moves = 0;

        int[][] directions = {
            {1, 0},    // down
            {-1, 0},   // up
            {0, 1},    // right
            {0, -1}    // left
        };

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process all states having same number of moves
            while (size-- > 0) {

                State cur = queue.poll();

                // All litter collected
                if (cur.mask == allMask) {
                    return moves;
                }

                for (int[] dir : directions) {

                    int nr = cur.r + dir[0];
                    int nc = cur.c + dir[1];

                    // Outside grid
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    // Obstacle
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // No energy -> cannot make another move
                    if (cur.energy == 0) {
                        continue;
                    }

                    // Moving costs 1 energy
                    int newEnergy = cur.energy - 1;

                    int newMask = cur.mask;

                    char cell = classroom[nr].charAt(nc);

                    // Collect litter
                    if (cell == 'L') {
                        int id = litterId[nr][nc];

                        newMask |= (1 << id);
                    }

                    // Reset energy at R
                    if (cell == 'R') {
                        newEnergy = energy;
                    }

                    // Already visited this exact state
                    if (visited[nr][nc][newEnergy][newMask]) {
                        continue;
                    }

                    visited[nr][nc][newEnergy][newMask] = true;

                    queue.offer(
                        new State(nr, nc, newEnergy, newMask)
                    );
                }
            }

            // One BFS level = one move
            moves++;
        }

        // Impossible to collect all litter
        return -1;
    }
}