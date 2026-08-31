class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return new int[]{-1, -1};
        }

        int firstIdx = -1;
        int prevIdx = -1;
        int minDistance = Integer.MAX_VALUE;

        ListNode prev = head;
        ListNode curr = head.next;
        int currIdx = 1; // 0-indexed position for head.next

        while (curr.next != null) {
            ListNode next = curr.next;

            // Check if current node is a local maxima or minima
            boolean isMaxima = curr.val > prev.val && curr.val > next.val;
            boolean isMinima = curr.val < prev.val && curr.val < next.val;

            if (isMaxima || isMinima) {
                if (firstIdx == -1) {
                    firstIdx = currIdx;
                } else {
                    minDistance = Math.min(minDistance, currIdx - prevIdx);
                }
                prevIdx = currIdx;
            }

            prev = curr;
            curr = next;
            currIdx++;
        }

        // If fewer than 2 critical points were found
        if (firstIdx == -1 || firstIdx == prevIdx) {
            return new int[]{-1, -1};
        }

        int maxDistance = prevIdx - firstIdx;
        return new int[]{minDistance, maxDistance};
    }
}