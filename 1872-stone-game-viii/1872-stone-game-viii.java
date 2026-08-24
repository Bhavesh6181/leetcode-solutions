class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        // Prefix sum
        int[] prefix = new int[n];

        prefix[0] = stones[0];

        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }

        // Start with taking all stones
        int ans = prefix[n - 1];

        // Try every possible point where the first move can end
        for (int i = n - 2; i >= 1; i--) {
            ans = Math.max(ans, prefix[i] - ans);
        }

        return ans;
    }
}