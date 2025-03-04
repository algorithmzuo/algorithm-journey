package class059;

// 拓扑排序模版（牛客）
// 邻接表建图（动态方式）
// 测试链接 : https://www.nowcoder.com/practice/88f7e156ca7d43a1a535f619cd3f495c
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.*;
//todo  栈溢出
public class Code02_TopoSortDynamicNowcoder1 {
    public static int max=20001;
    public static int[][] graph=new int[max][max];
    public static int[] queue=new int[max];
    public static int[] indegree=new int[max];
    public static int l, r;
    public static int m, n;

    public static void main(String[] args) throws java.io.IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            m = (int) in.nval;
            in.nextToken();
            n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                int x = (int) in.nval;
                in.nextToken();
                int y = (int) in.nval;
                graph[x][y] = 1;
                indegree[y]++;
            }
            if (!exits()) {
                out.println(-1);
            } else {
                for (int i = 0; i < r - 1; i++) {
                    out.print(queue[i] + " ");
                }
                out.println(queue[r - 1]);
            }
        }
        out.flush();
        out.close();
        br.close();
    }

    public static boolean exits() {
        int ans = 0;
        l = r = 0;
        for (int i = 1; i <= m; i++) {
            if (indegree[i] == 0) queue[r++] = i;
        }
        while (l < r) {
            int cur = queue[l++];
            ans++;
            for(int i=1;i<=m;i++){
                if(graph[cur][i]>0){
                    if(--indegree[i]==0) queue[r++]=i;
                }
            }
        }
        return ans == m;
    }
}