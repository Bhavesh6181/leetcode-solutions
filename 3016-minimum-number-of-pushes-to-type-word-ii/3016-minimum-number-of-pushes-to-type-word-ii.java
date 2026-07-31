import java.util.*;

class Solution {
    public int minimumPushes(String word) {

        int[] freq = new int[26];

        // Count frequency
        for (char ch : word.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Sort in ascending order
        Arrays.sort(freq);

        int ans = 0;
        int index = 0;

        // Traverse from largest frequency to smallest
        for (int i = 25; i >= 0; i--) {

            if (freq[i] == 0)
                break;

            int cost = (index / 8) + 1;
            ans += freq[i] * cost;
            index++;
        }

        return ans;
    }
}