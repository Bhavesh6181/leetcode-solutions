class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode temp = head;
        int count = 0;

        // Count total nodes
        while (temp != null) {
            count++;
            temp = temp.next;
        }

        // Special case: delete head
        if (n == count) {
            return head.next;
        }

        // Move to node BEFORE the node we want to delete
        temp = head;

        for (int i = 1; i < count - n; i++) {
            temp = temp.next;
        }

        // Delete next node
        temp.next = temp.next.next;

        return head;
    }
}