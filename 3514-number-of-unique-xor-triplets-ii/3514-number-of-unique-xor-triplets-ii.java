class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int val : nums) {
            boolean[][] next = new boolean[4][MAX];

            // Option: take this index 0 times
            for (int k = 0; k <= 3; k++) {
                System.arraycopy(dp[k], 0, next[k], 0, MAX);
            }

            for (int k = 0; k <= 3; k++) {
                for (int xr = 0; xr < MAX; xr++) {
                    if (!dp[k][xr]) continue;

                    // Take once
                    if (k + 1 <= 3) {
                        next[k + 1][xr ^ val] = true;
                    }

                    // Take twice (xor unchanged)
                    if (k + 2 <= 3) {
                        next[k + 2][xr] = true;
                    }

                    // Take thrice
                    if (k + 3 <= 3) {
                        next[k + 3][xr ^ val] = true;
                    }
                }
            }

            dp = next;
        }

        int ans = 0;
        for (int xr = 0; xr < MAX; xr++) {
            if (dp[3][xr]) ans++;
        }

        return ans;
    }
}