import java.util.*;

class Solution {

    class Node {
        long score;
        List<Integer> list;

        Node(long score, List<Integer> list) {
            this.score = score;
            this.list = list;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        int[][] a = new int[n][4];

        // start, end, weight, original index
        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        // Sort by start
        Arrays.sort(a, (x, y) -> {
            if (x[0] != y[0]) {
                return Integer.compare(x[0], y[0]);
            }
            return Integer.compare(x[3], y[3]);
        });

        // Find next non-overlapping interval
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {

            int low = i + 1;
            int high = n;

            while (low < high) {

                int mid = low + (high - low) / 2;

                if (a[mid][0] > a[i][1]) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            next[i] = low;
        }

        Node[][] dp = new Node[n + 1][5];

        // Initialize ALL states
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new Node(0, new ArrayList<>());
            }
        }

        // DP
        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= 4; k++) {

                // Skip current interval
                Node skip = dp[i + 1][k];

                // Take current interval
                Node nextNode = dp[next[i]][k - 1];

                List<Integer> takeList =
                        new ArrayList<>(nextNode.list);

                takeList.add(a[i][3]);

                Collections.sort(takeList);

                Node take = new Node(
                        a[i][2] + nextNode.score,
                        takeList
                );

                if (take.score > skip.score) {
                    dp[i][k] = take;
                }
                else if (take.score < skip.score) {
                    dp[i][k] = skip;
                }
                else {
                    if (isSmaller(take.list, skip.list)) {
                        dp[i][k] = take;
                    } else {
                        dp[i][k] = skip;
                    }
                }
            }
        }

        List<Integer> ans = dp[0][4].list;

        Collections.sort(ans);

        int[] result = new int[ans.size()];

        for (int i = 0; i < ans.size(); i++) {
            result[i] = ans.get(i);
        }

        return result;
    }

    private boolean isSmaller(List<Integer> a, List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (a.get(i) < b.get(i)) {
                return true;
            }

            if (a.get(i) > b.get(i)) {
                return false;
            }
        }

        return a.size() < b.size();
    }
}