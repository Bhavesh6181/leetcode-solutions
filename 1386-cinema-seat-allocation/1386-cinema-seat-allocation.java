import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> map = new HashMap<>();

        // Store reserved seats as a bitmask for each affected row.
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            map.put(row, map.getOrDefault(row, 0) | (1 << col));
        }

        // Rows without reservations can always fit 2 families.
        long ans = (long) (n - map.size()) * 2;

        for (int mask : map.values()) {
            boolean left = canFit(mask, 2, 5);
            boolean middle = canFit(mask, 4, 7);
            boolean right = canFit(mask, 6, 9);

            if (left && right) {
                ans += 2;
            } else if (left || middle || right) {
                ans += 1;
            }
        }

        return (int) ans;
    }

    private boolean canFit(int mask, int start, int end) {
        for (int seat = start; seat <= end; seat++) {
            if ((mask & (1 << seat)) != 0) {
                return false;
            }
        }
        return true;
    }
}