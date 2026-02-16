package class191;

// 割边模版题2，C++版
// 给定一张无向图，一共n个点、m条边
// 点的编号0~n-1，保证所有点连通
// 找出图中所有的割边，返回每条割边的两个端点
// 请保证原图即使有重边，答案依然正确
// 1 <= n、m <= 10^5
// 测试链接 : https://leetcode.cn/problems/critical-connections-in-a-network/
// 提交以下代码中的Solution类，可以通过所有测试用例

//class Solution {
//public:
//    static const int MAXN = 100001;
//    static const int MAXM = 100001;
//    int n, m;
//
//    int a[MAXM];
//    int b[MAXM];
//
//    int head[MAXN];
//    int nxt[MAXM << 1];
//    int to[MAXM << 1];
//    int cntg;
//
//    int dfn[MAXN];
//    int low[MAXN];
//    int cntd;
//
//    bool cutEdge[MAXM];
//
//    void prepare() {
//        cntg = 1;
//        cntd = 0;
//        for (int i = 1; i <= n; i++) {
//            head[i] = dfn[i] = low[i] = 0;
//        }
//        for (int i = 1; i <= m; i++) {
//            cutEdge[i] = false;
//        }
//    }
//
//    void addEdge(int u, int v) {
//        nxt[++cntg] = head[u];
//        to[cntg] = v;
//        head[u] = cntg;
//    }
//
//    void tarjan(int u, int preEdge) {
//        dfn[u] = low[u] = ++cntd;
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            if ((e ^ 1) == preEdge) {
//                continue;
//            }
//            int v = to[e];
//            if (dfn[v] == 0) {
//                tarjan(v, e);
//                low[u] = min(low[u], low[v]);
//                if (low[v] > dfn[u]) {
//                    cutEdge[e >> 1] = true;
//                }
//            } else {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//
//    vector<vector<int>> criticalConnections(int nodeCnt, vector<vector<int>>& connections) {
//        n = nodeCnt;
//        m = (int)connections.size();
//        prepare();
//        for (int i = 1; i <= m; i++) {
//            a[i] = connections[i - 1][0] + 1;
//            b[i] = connections[i - 1][1] + 1;
//            addEdge(a[i], b[i]);
//            addEdge(b[i], a[i]);
//        }
//        tarjan(1, 0);
//        vector<vector<int>> ans;
//        for (int i = 1; i <= m; i++) {
//            if (cutEdge[i]) {
//                ans.push_back({a[i] - 1, b[i] - 1});
//            }
//        }
//        return ans;
//    }
//};