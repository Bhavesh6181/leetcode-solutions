class Solution {
    public int maxSubarrayLength(int[] nums, int k) {

        HashMap<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < nums.length; right++) {

            // Add nums[right] to the window
            freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);

            // If frequency becomes greater than k,
            // shrink the window from the left
            while (freq.get(nums[right]) > k) {

                freq.put(nums[left], freq.get(nums[left]) - 1);

                left++;
            }

            // Current window is valid
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}