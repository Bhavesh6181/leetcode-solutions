class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;
        long sum1 = 0, sum2 = 0;
        int cnt1 = 0, cnt2 = 0;

        for (int i = 0; i < half; i++) {
            char c = num.charAt(i);
            if (c == '?') cnt1++;
            else sum1 += c - '0';
        }
        for (int i = half; i < n; i++) {
            char c = num.charAt(i);
            if (c == '?') cnt2++;
            else sum2 += c - '0';
        }

        long diff = sum1 - sum2;
        int q = cnt1 - cnt2;

        if ((q & 1) != 0) return true;          // odd unpaired '?' -> Alice always wins

        return 2 * diff + 9L * q != 0;           // Bob wins only if this exact target is hit
    }
}