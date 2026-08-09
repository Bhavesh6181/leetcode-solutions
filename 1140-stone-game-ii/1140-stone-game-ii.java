class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;

        // suffix[i] = total stones from i to n-1
        int[] suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        // dp[i][m] = maximum stones current player can get
        // starting from index i with current M = m
        int[][] dp = new int[n + 1][n + 1];

        // Fill from the end towards the beginning
        for (int i = n - 1; i >= 0; i--) {

            for (int m = 1; m <= n; m++) {

                // If we can take all remaining piles,
                // take them all.
                if (i + 2 * m >= n) {
                    dp[i][m] = suffix[i];
                    continue;
                }

                int best = 0;

                // Try taking X piles, where 1 <= X <= 2M
                for (int x = 1; x <= 2 * m && i + x <= n; x++) {

                    // After taking x piles:
                    // opponent starts from i+x
                    // with M = max(m, x)
                    int opponent = dp[i + x][Math.max(m, x)];

                    // Total remaining stones are suffix[i].
                    // We take x piles, opponent gets his optimal amount.
                    // So we get:
                    int current = suffix[i] - opponent;

                    best = Math.max(best, current);
                }

                dp[i][m] = best;
            }
        }

        return dp[0][1];
    }
}