class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long lo = 1;
        long hi = (long) coins[0] * k;

        // A tighter upper bound is possible using the smallest coin.
        for (int c : coins) {
            hi = Math.min(hi, (long) c * k);
        }

        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;

            if (count(mid, coins) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    private long count(long x, int[] coins) {
        int n = coins.length;
        long result = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            long lcm = 1;
            int bits = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    long g = gcd(lcm, coins[i]);

                    // lcm / g * coins[i], but stop if it exceeds x.
                    long next = lcm / g;

                    if (next > x / coins[i]) {
                        overflow = true;
                        break;
                    }

                    lcm = next * coins[i];

                    if (lcm > x) {
                        overflow = true;
                        break;
                    }
                }
            }

            if (overflow) {
                continue;
            }

            long multiples = x / lcm;

            if ((bits & 1) == 1) {
                result += multiples;
            } else {
                result -= multiples;
            }
        }

        return result;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}