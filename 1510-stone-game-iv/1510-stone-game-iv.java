class Solution {
    public boolean winnerSquareGame(int n) {

        boolean[] dp = new boolean[n + 1];

        // dp[0] = false
        // No stones -> player cannot make a move -> loses

        for (int i = 1; i <= n; i++) {

            // Try every square number
            for (int j = 1; j * j <= i; j++) {

                int remaining = i - j * j;

                // If opponent reaches a losing state,
                // current player wins
                if (dp[remaining] == false) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}