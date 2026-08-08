class Solution {
    public int[] validSequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        // suf[j] = latest position in word1
        // from which word2[j...] can be matched exactly
        int[] suf = new int[m];

        // n means impossible
        for (int j = 0; j < m; j++) {
            suf[j] = n;
        }

        int i = n - 1;

        // Build suffix array
        for (int j = m - 1; j >= 0; j--) {

            while (i >= 0 && word1.charAt(i) != word2.charAt(j)) {
                i--;
            }

            if (i < 0) {
                break;
            }

            suf[j] = i;
            i--;
        }

        int[] ans = new int[m];

        int j = 0;
        int k = 0;
        boolean mismatchUsed = false;

        // Greedy
        for (i = 0; i < n && j < m; i++) {

            // Characters match
            if (word1.charAt(i) == word2.charAt(j)) {

                ans[k++] = i;
                j++;
            }

            // Characters don't match.
            // We can use our one mismatch only if
            // the remaining suffix can be matched exactly.
            else if (!mismatchUsed &&
                     (j == m - 1 ||
                      (suf[j + 1] < n && suf[j + 1] > i))) {

                ans[k++] = i;
                j++;
                mismatchUsed = true;
            }
        }

        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}