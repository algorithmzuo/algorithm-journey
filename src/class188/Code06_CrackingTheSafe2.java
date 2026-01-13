package class188;

// 破解保险箱，C++版
// 给定正数n，表示密码有n位，给定正数k，表示每一位可能的数字是[0..k-1]
// 密码有(k^n)个可能性，构造一个字符串，其中的连续子串包含所有可能的密码
// 先保证字符串的长度最短，作为加强要求，保证字典序尽量的小，返回字符串
// 1 <= n <= 4    1 <= k <= 10
// 测试链接 : https://leetcode.cn/problems/cracking-the-safe/
// 提交以下代码中的Solution类，可以通过所有测试用例

//class Solution {
//public:
//    static const int MAXN = 5001;
//    int n, k, m;
//    int cur[MAXN];
//    int path[MAXN];
//    int cntp;
//
//    int sta[MAXN][2];
//    int u, e;
//    int stacksize;
//
//    void push(int u, int e) {
//        sta[stacksize][0] = u;
//        sta[stacksize][1] = e;
//        stacksize++;
//    }
//
//    void pop() {
//        stacksize--;
//        u = sta[stacksize][0];
//        e = sta[stacksize][1];
//    }
//
//    void prepare(int len, int num) {
//        n = len;
//        k = num;
//        m = 1;
//        for (int i = 1; i <= n - 1; i++) {
//            m *= k;
//        }
//        for (int i = 0; i < m; i++) {
//            cur[i] = 0;
//        }
//        cntp = 0;
//    }
//
//    void euler1(int u, int e) {
//        while (cur[u] < k) {
//            int ne = cur[u]++;
//            euler1((u * k + ne) % m, ne);
//        }
//        path[++cntp] = e;
//    }
//
//    void euler2(int node, int edge) {
//        stacksize = 0;
//        push(node, edge);
//        while (stacksize > 0) {
//            pop();
//            if (cur[u] < k) {
//                int ne = cur[u]++;
//                push(u, e);
//                push((u * k + ne) % m, ne);
//            } else {
//                path[++cntp] = e;
//            }
//        }
//    }
//
//    string crackSafe(int n, int k) {
//        prepare(n, k);
//        euler1(0, 0);
//        // euler2(0, 0);
//        string ans;
//        ans.reserve((n - 1) + (cntp - 1));
//        for (int i = 1; i <= n - 1; i++) {
//            ans.push_back('0');
//        }
//        for (int i = cntp - 1; i >= 1; i--) {
//            ans.push_back(char('0' + path[i]));
//        }
//        return ans;
//    }
//};