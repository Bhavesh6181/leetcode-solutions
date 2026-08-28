class Solution {
    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // Count characters in s
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Check whether palindrome is possible
        int oddCount = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                oddCount++;
                middle = i;
            }
        }

        if (oddCount > 1) {
            return "";
        }

        int halfLen = n / 2;

        // Frequency for left half
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        String targetHalf = target.substring(0, halfLen);

        // --------------------------------------------------
        // STEP 1:
        // Try to make left half exactly equal to target half
        // --------------------------------------------------

        int[] remaining = halfFreq.clone();
        boolean possible = true;

        for (int i = 0; i < halfLen; i++) {

            int c = targetHalf.charAt(i) - 'a';

            if (remaining[c] == 0) {
                possible = false;
                break;
            }

            remaining[c]--;
        }

        if (possible) {

            // Left half is exactly equal to target's left half.

            if (n % 2 == 1) {

                int targetMiddle = target.charAt(halfLen) - 'a';

                // Try smallest middle character greater than target middle
                if (middle > targetMiddle) {
                    return buildPalindrome(
                        targetHalf.toCharArray(),
                        middle,
                        n
                    );
                }

                // If middle is equal, compare the complete palindrome
                if (middle == targetMiddle) {

                    String candidate = buildPalindrome(
                        targetHalf.toCharArray(),
                        middle,
                        n
                    );

                    if (candidate.compareTo(target) > 0) {
                        return candidate;
                    }
                }

            } else {

                // Even length:
                // once left half is fixed, entire palindrome is fixed.

                String candidate = buildPalindrome(
                    targetHalf.toCharArray(),
                    -1,
                    n
                );

                if (candidate.compareTo(target) > 0) {
                    return candidate;
                }
            }
        }

        // --------------------------------------------------
        // STEP 2:
        // Find the smallest left half strictly greater
        // than targetHalf.
        // --------------------------------------------------

        for (int i = halfLen - 1; i >= 0; i--) {

            // Check whether targetHalf[0 ... i-1]
            // can be formed.
            remaining = halfFreq.clone();

            boolean prefixPossible = true;

            for (int j = 0; j < i; j++) {

                int c = targetHalf.charAt(j) - 'a';

                if (remaining[c] == 0) {
                    prefixPossible = false;
                    break;
                }

                remaining[c]--;
            }

            if (!prefixPossible) {
                continue;
            }

            int current = targetHalf.charAt(i) - 'a';

            // Find smallest character greater than target[i]
            for (int c = current + 1; c < 26; c++) {

                if (remaining[c] > 0) {

                    remaining[c]--;

                    StringBuilder left = new StringBuilder();

                    // Equal prefix
                    for (int j = 0; j < i; j++) {
                        left.append(targetHalf.charAt(j));
                    }

                    // Greater character
                    left.append((char) ('a' + c));

                    // Fill remaining positions minimally
                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            left.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    // Since left half is strictly greater,
                    // choose the smallest possible middle.
                    return buildPalindrome(
                        left.toString().toCharArray(),
                        middle,
                        n
                    );
                }
            }
        }

        return "";
    }

    private String buildPalindrome(
        char[] left,
        int middle,
        int n
    ) {

        StringBuilder result = new StringBuilder();

        // Left half
        result.append(left);

        // Middle
        if (n % 2 == 1) {
            result.append((char) ('a' + middle));
        }

        // Right half = reverse(left)
        for (int i = left.length - 1; i >= 0; i--) {
            result.append(left[i]);
        }

        return result.toString();
    }
}