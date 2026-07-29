class Solution {

    public String smallestPalindrome(String s, int k) {

        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        int[] half = new int[26];
        StringBuilder mid = new StringBuilder();

        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];

            if ((freq[i] & 1) == 1) {
                mid.append((char) ('a' + i));
            }
        }

        long total = countWays(half, halfLen, (long) k);

        if (total < k)
            return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                long ways = countWays(half, halfLen - pos - 1, (long) k);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        String right = new StringBuilder(left).reverse().toString();

        return left.toString() + mid.toString() + right;
    }

    private long countWays(int[] cnt, int total, long limit) {

        long ans = 1;

        int rem = total;

        for (int i = 0; i < 26; i++) {

            if (cnt[i] == 0)
                continue;

            ans *= nCr(rem, cnt[i], limit);

            if (ans >= limit)
                return limit;

            rem -= cnt[i];
        }

        return ans;
    }

    private long nCr(int n, int r, long limit) {

        if (r > n)
            return 0;

        r = Math.min(r, n - r);

        long ans = 1;

        for (int i = 1; i <= r; i++) {

            ans = ans * (n - r + i) / i;

            if (ans >= limit)
                return limit;
        }

        return ans;
    }
}