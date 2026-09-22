class Solution {

    static class Node {
        int product;
        int[] pref;

        Node(int k) {
            pref = new int[k];
        }
    }

    int n, k;
    int[] nums;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.nums = nums;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Update nums[index]
            nums[index] = value;
            update(1, 0, n - 1, index, value);

            // Query [start ... n-1]
            Node ans = query(1, 0, n - 1, start, n - 1);

            result[q] = ans.pref[x];
        }

        return result;
    }

    // Build segment tree
    void build(int node, int left, int right) {

        if (left == right) {
            tree[node] = new Node(k);

            int rem = nums[left] % k;

            tree[node].product = rem;
            tree[node].pref[rem] = 1;

            return;
        }

        int mid = (left + right) / 2;

        build(node * 2, left, mid);
        build(node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Merge two nodes
    Node merge(Node a, Node b) {

        Node res = new Node(k);

        res.product = (a.product * b.product) % k;

        // Prefixes completely inside left part
        for (int r = 0; r < k; r++) {
            res.pref[r] += a.pref[r];
        }

        // Prefixes containing whole left + prefix of right
        for (int r = 0; r < k; r++) {

            int newRemainder = (a.product * r) % k;

            res.pref[newRemainder] += b.pref[r];
        }

        return res;
    }

    // Point update
    void update(int node, int left, int right, int index, int value) {

        if (left == right) {

            tree[node] = new Node(k);

            int rem = value % k;

            tree[node].product = rem;
            tree[node].pref[rem] = 1;

            return;
        }

        int mid = (left + right) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Range query
    Node query(int node, int left, int right, int ql, int qr) {

        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = (left + right) / 2;

        if (qr <= mid) {
            return query(node * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, right, ql, qr);
        }

        Node a = query(node * 2, left, mid, ql, qr);
        Node b = query(node * 2 + 1, mid + 1, right, ql, qr);

        return merge(a, b);
    }
}