class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;

        // For n = 1 or 2
        if (n <= 2) {
            return n;
        }

        // Smallest power of 2 greater than n
        return 1 << (32 - Integer.numberOfLeadingZeros(n));
    }
}