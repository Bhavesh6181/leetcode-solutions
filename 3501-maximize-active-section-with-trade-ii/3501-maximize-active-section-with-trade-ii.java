import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        for (char c : s.toCharArray()) if (c == '1') totalOnes++;
        
        // Build runs
        List<Integer> runLen = new ArrayList<>();
        List<Boolean> isZero = new ArrayList<>();
        List<Integer> runSt = new ArrayList<>();
        int i = 0;
        while (i < n) {
            int j = i;
            char ch = s.charAt(i);
            while (j < n && s.charAt(j) == ch) j++;
            runLen.add(j - i);
            isZero.add(ch == '0');
            runSt.add(i);
            i = j;
        }
        int m = runLen.size();
        
        // Full gains for 1-runs with full adjacent 0s
        List<Integer> oneGains = new ArrayList<>();
        List<Integer> covL = new ArrayList<>();
        List<Integer> covR = new ArrayList<>();
        for (int k = 1; k < m - 1; k++) {
            if (!isZero.get(k) && isZero.get(k - 1) && isZero.get(k + 1)) {
                int g = runLen.get(k - 1) + runLen.get(k + 1);
                oneGains.add(g);
                covL.add(runSt.get(k - 1));
                covR.add(runSt.get(k + 1) + runLen.get(k + 1) - 1);
            }
        }
        int p = oneGains.size();
        
        // Sparse table for max gain
        int[][] sparse = null;
        int[] logTable = null;
        if (p > 0) {
            int maxLog = 0;
            while ((1 << maxLog) <= p) maxLog++;
            sparse = new int[maxLog][p];
            for (int j = 0; j < p; j++) {
                sparse[0][j] = oneGains.get(j);
            }
            for (int lv = 1; lv < maxLog; lv++) {
                for (int j = 0; j + (1 << lv) <= p; j++) {
                    sparse[lv][j] = Math.max(sparse[lv - 1][j], sparse[lv - 1][j + (1 << (lv - 1))]);
                }
            }
            logTable = new int[p + 1];
            for (int j = 2; j <= p; j++) {
                logTable[j] = logTable[j / 2] + 1;
            }
        }
        
        List<Integer> ans = new ArrayList<>();
        for (int[] q : queries) {
            int l = q[0], r = q[1];
            int maxG = 0;
            // full
            if (p > 0) {
                // lower bound for covL >= l
                int leftK = lowerBound(covL, l);
                // upper for covR <= r
                int rightK = upperBound(covR, r) - 1;
                if (leftK <= rightK) {
                    int len = rightK - leftK + 1;
                    int lg = logTable[len];
                    maxG = Math.max(sparse[lg][leftK], sparse[lg][rightK - (1 << lg) + 1]);
                }
            }
            // left boundary
            maxG = Math.max(maxG, getLeftGain(l, r, runSt, isZero, runLen));
            // right boundary
            maxG = Math.max(maxG, getRightGain(l, r, runSt, isZero, runLen));
            ans.add(totalOnes + maxG);
        }
        return ans;
    }
    
    private int lowerBound(List<Integer> arr, int val) {
        int low = 0, high = arr.size();
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr.get(mid) >= val) high = mid;
            else low = mid + 1;
        }
        return low;
    }
    
    private int upperBound(List<Integer> arr, int val) {
        int low = 0, high = arr.size();
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr.get(mid) > val) high = mid;
            else low = mid + 1;
        }
        return low;
    }
    
    private int getRunId(int pos, List<Integer> runSt) {
        int low = 0, high = runSt.size() - 1;
        while (low < high) {
            int mid = (low + high + 1) / 2;
            if (runSt.get(mid) <= pos) low = mid;
            else high = mid - 1;
        }
        return low;
    }
    
    private int getLeftGain(int l, int r, List<Integer> runSt, List<Boolean> isZero, List<Integer> runLen) {
        if (l > r) return 0;
        int rid = getRunId(l, runSt);
        int lead = 0;
        if (isZero.get(rid)) {
            int st = runSt.get(rid);
            int offset = l - st;
            lead = runLen.get(rid) - offset;
            lead = Math.min(lead, r - l + 1);
        }
        if (lead == 0) return 0;
        int onePos = l + lead;
        if (onePos > r) return 0;
        int oneRid = getRunId(onePos, runSt);
        if (isZero.get(oneRid) || runSt.get(oneRid) != onePos) return 0;
        int rightPos = runSt.get(oneRid) + runLen.get(oneRid);
        if (rightPos > r) return 0;
        int rightRid = getRunId(rightPos, runSt);
        if (!isZero.get(rightRid)) return 0;
        int rightL = Math.min(runLen.get(rightRid), r - rightPos + 1);
        return lead + rightL;
    }
    
    private int getRightGain(int l, int r, List<Integer> runSt, List<Boolean> isZero, List<Integer> runLen) {
        if (l > r) return 0;
        int rid = getRunId(r, runSt);
        int trail = 0;
        if (isZero.get(rid)) {
            int st = runSt.get(rid);
            trail = r - Math.max(l, st) + 1;
        }
        if (trail == 0) return 0;
        int oneEnd = r - trail;
        if (oneEnd < l) return 0;
        int oneRid = getRunId(oneEnd, runSt);
        if (isZero.get(oneRid) || runSt.get(oneRid) + runLen.get(oneRid) - 1 != oneEnd) return 0;
        int left0End = runSt.get(oneRid) - 1;
        if (left0End < l) return 0;
        int left0Rid = getRunId(left0End, runSt);
        if (!isZero.get(left0Rid)) return 0;
        int leftSt = runSt.get(left0Rid);
        int leftL = left0End - Math.max(l, leftSt) + 1;
        return leftL + trail;
    }
}