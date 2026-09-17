import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        int[] dp = new int [n + 1];
        int INF = 100000000;
        dp[0] = INF;
        int sum = 0;
        int answer = INF;
        for(int i = 1; i <= n ; i++){
            sum += arr[i- 1];
            dp [i] = dp[i - 1];
            if(map.containsKey(sum - target)){
                int j = map.get(sum - target);
                int length = i - j;
                dp[i] = Math.min(dp[i], length);
                answer = Math.min(answer, dp[j] + length);
            }
            map.put(sum , i);
        }
        if(answer == INF){
            return -1;
        }
        return answer;
    }
}