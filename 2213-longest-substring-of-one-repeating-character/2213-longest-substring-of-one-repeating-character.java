class Solution {

    class Node {
        char leftChar;
        char rightChar;

        int len;
        int prefix;
        int suffix;
        int best;

        Node(char ch) {
            leftChar = ch;
            rightChar = ch;
            len = 1;
            prefix = 1;
            suffix = 1;
            best = 1;
        }
    }

    Node[] tree;
    char[] arr;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {

        int n = s.length();
        int k = queryIndices.length;

        arr = s.toCharArray();
        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, index, ch);

            ans[i] = tree[1].best;
        }

        return ans;
    }

    private void build(int node, int l, int r) {

        if (l == r) {
            tree[node] = new Node(arr[l]);
            return;
        }

        int mid = l + (r - l) / 2;

        build(2 * node, l, mid);
        build(2 * node + 1, mid + 1, r);

        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    private void update(int node, int l, int r, int index, char ch) {

        if (l == r) {
            tree[node] = new Node(ch);
            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(2 * node, l, mid, index, ch);
        } else {
            update(2 * node + 1, mid + 1, r, index, ch);
        }

        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    private Node merge(Node left, Node right) {

        Node res = new Node(left.leftChar);

        res.len = left.len + right.len;

        res.leftChar = left.leftChar;
        res.rightChar = right.rightChar;

        // Prefix
        res.prefix = left.prefix;

        if (left.prefix == left.len &&
            left.rightChar == right.leftChar) {

            res.prefix = left.len + right.prefix;
        }

        // Suffix
        res.suffix = right.suffix;

        if (right.suffix == right.len &&
            left.rightChar == right.leftChar) {

            res.suffix = right.len + left.suffix;
        }

        // Best inside left/right
        res.best = Math.max(left.best, right.best);

        // Best substring crossing the middle
        if (left.rightChar == right.leftChar) {

            res.best = Math.max(
                res.best,
                left.suffix + right.prefix
            );
        }

        return res;
    }
}