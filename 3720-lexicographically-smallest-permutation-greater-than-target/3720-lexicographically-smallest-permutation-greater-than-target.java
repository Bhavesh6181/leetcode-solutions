class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char[] ans = new char[n];

        for (int i = 0; i < n; i++) {
            int t = target.charAt(i) - 'a';

            // Try to match target[i]
            if (freq[t] > 0) {
                ans[i] = target.charAt(i);
                freq[t]--;
                continue;
            }

            // Cannot match target[i].
            // Find smallest character greater than target[i].
            for (int c = t + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    ans[i] = (char) ('a' + c);
                    freq[c]--;

                    return build(ans, i + 1, freq);
                }
            }

            // We cannot continue here.
            // Backtrack to an earlier position.
            for (int j = i - 1; j >= 0; j--) {
                int old = ans[j] - 'a';
                freq[old]++;

                for (int c = old + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        ans[j] = (char) ('a' + c);
                        freq[c]--;

                        return build(ans, j + 1, freq);
                    }
                }

                ans[j] = 0;
            }

            return "";
        }

        // s itself can form exactly target, but we need STRICTLY greater.
        for (int i = n - 1; i >= 0; i--) {
            int old = ans[i] - 'a';
            freq[old]++;

            for (int c = old + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    ans[i] = (char) ('a' + c);
                    freq[c]--;

                    return build(ans, i + 1, freq);
                }
            }
        }

        return "";
    }

    private String build(char[] ans, int start, int[] freq) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < start; i++) {
            sb.append(ans[i]);
        }

        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                sb.append((char) ('a' + c));
                freq[c]--;
            }
        }

        return sb.toString();
    }
}