class Solution {
    public List<String> maxNumOfSubstrings(String s) {
       int n = s.length();
       int[] first = new int[26];
       int[] last = new int[26];
       Arrays.fill(first, n);
       Arrays.fill(last, -1);
       for(int i = 0; i < n ; i++){
        int index = s.charAt(i) - 'a' ;
        first[index]=Math.min(first[index], i);
        last[index] = i;
       } 
       List<int[]> intervals = new ArrayList<>();
       for(int c = 0; c< 26;c++){
        if(last[c] == -1){
            continue;
        }
        int left = first[c];
        int right = last[c];
        boolean valid = true;
        for(int i = left; i <= right; i++){
            int index = s.charAt(i) - 'a';
            if(first[index] < left){
                valid = false;
                break;
            }
            right = Math.max(right, last[index]);

        }
        if(valid){
            intervals.add(new int[] {left, right});
        }
       }
       intervals.sort((a,b)  -> a[1] - b[1]);
       List<String> answer = new ArrayList<>();
       int end = -1;
       for(int[] interval : intervals){
        if(interval[0] > end){
            answer.add(s.substring(interval[0], interval[1] + 1));
            end = interval[1];
        }
       }
       return answer;
    }
}