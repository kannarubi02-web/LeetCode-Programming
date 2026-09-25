
class Solution {
    int index = 0 ;
    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);
        return new ArrayList<>(result);
    }
    private Set<String> parse(String s){
        Set<String> result = new TreeSet<>();
        Set<String> current = new TreeSet<>();
        current.add("");
        while(index < s.length() && s.charAt(index) != '}'){
            if(s.charAt(index) == ','){
                result.addAll(current);
                current = new TreeSet<>();
                current.add("");
                index++;
            }
            else if(s.charAt(index) == '{'){
                index++;
                Set<String> inside = parse(s);
                index++;
                current = combine(current, inside);
            }
            else{
                String letter = String.valueOf(s.charAt(index));
                Set<String> single = new TreeSet<>();
                single.add(letter);
                current = combine(current, single);
                index++;
            }
        }
        result.addAll(current);
        return result;
    }
    private Set<String> combine(Set<String> a, Set<String> b ){
        Set<String> result = new TreeSet <>();
        for(String x : a){
            for(String y : b){
                result.add(x + y);
            }
        }
        return result;
    }
}