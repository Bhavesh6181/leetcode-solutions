class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int left = 0;
        int ones = 0;

        String ans = "";
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }

            // Keep exactly k ones in the window
            while (ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            // We have exactly k ones
            if (ones == k) {
                // Remove leading zeros.
                // This gives the shortest substring for this group of k ones.
                while (left <= right && s.charAt(left) == '0') {
                    left++;
                }

                int len = right - left + 1;
                String cur = s.substring(left, right + 1);

                if (len < minLen || (len == minLen && cur.compareTo(ans) < 0)) {
                    minLen = len;
                    ans = cur;
                }
            }
        }

        return ans;
    }
}