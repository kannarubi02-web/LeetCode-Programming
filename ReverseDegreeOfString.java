class Solution {
    public int reverseDegree(String s) {
        int answer = 0 ;
        for(int i = 1; i <= s.length();i++){
            char ch = s.charAt(i - 1);
            int reverseValue = 'z' - ch + 1;
            answer += reverseValue * i;
        }
        return answer ;
    }
}