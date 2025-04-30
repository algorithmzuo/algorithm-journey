package class063;

import java.util.HashSet;
import java.util.List;

class Code01_WordLadder1 {
    public static HashSet<String> dict;
    public static HashSet<String> curlevel=new HashSet();
    public static HashSet<String> nextlevel=new HashSet();
    public static void build(List wordList){
        dict=new HashSet(wordList);
        curlevel.clear();
        nextlevel.clear();
    }
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        build(wordList);
        if(!dict.contains(endWord)){
            return 0;
        }
        return bfs(beginWord,endWord);
    }
    public static int bfs(String beginWord,String endWord){
        curlevel.add(beginWord);
        int ans=1;
        while(!curlevel.isEmpty()){
            dict.removeAll(curlevel);
            ans++;
            for(String word:curlevel){
                char[] w=word.toCharArray();
                for(int i=0;i<w.length;i++){
                    char old=w[i];
                    for(char ch='a';ch<='z';ch++){
                        w[i]=ch;
                        String str=String.valueOf(w);
                        if(str.equals(endWord)) return ans;
                        if(dict.contains(str)){
                            nextlevel.add(str);
                        }
                    }
                    w[i]=old;
                }
            }
            HashSet<String> tmp=curlevel;
            curlevel=nextlevel;
            nextlevel=tmp;
            nextlevel.clear();
        }
        return 0;
    }
}