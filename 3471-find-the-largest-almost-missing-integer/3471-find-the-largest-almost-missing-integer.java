class Solution {
    public int largestInteger(int[] nums, int k) {

        int n = nums.length;

        // count[x] = number of size-k subarrays
        // in which x appears
        int[] count = new int[51];

        // Generate every subarray of size k
        for (int start = 0; start <= n - k; start++) {

            boolean[] seen = new boolean[51];

            for (int j = start; j < start + k; j++) {
                seen[nums[j]] = true;
            }

            // Count this window only once for each number
            for (int x = 0; x <= 50; x++) {
                if (seen[x]) {
                    count[x]++;
                }
            }
        }

        // Find largest number appearing in exactly one window
        for (int x = 50; x >= 0; x--) {
            if (count[x] == 1) {
                return x;
            }
        }

        return -1;
    }
}