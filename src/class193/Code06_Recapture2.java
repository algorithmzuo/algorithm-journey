package class193;

// 夺回据点，C++版
// 一共n个据点，m条双向道路，所有据点连通在一起
// 每个据点给定点权，代表夺取这个据点需要的花费
// 当夺取某个据点之后，再夺取相邻的据点，认为没有花费
// 你可以按照任意顺序夺取所有据点，但是过程中必须保证
// 那些还没夺取的据点都在同一个连通区里，不被已经夺取的据点隔开
// 打印最小的总花费
// 1 <= n、m <= 10^5
// 1 <= 点权 <= 10^9
// 测试链接 : https://leetcode.cn/problems/s5kipK/
// 提交以下代码中的Solution类，可以通过所有测试用例

//class Solution {
//public:
//    static const int MAXN = 100001;
//    static const int MAXM = 100001;
//    int n, m;
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
//    int sta[MAXN];
//    int top;
//
//    bool cutVertex[MAXN];
//    vector<vector<int>> vbccArr;
//
//    void prepare() {
//        cntg = cntd = top = 0;
//        for (int i = 1; i <= n; i++) {
//            head[i] = dfn[i] = low[i] = 0;
//            cutVertex[i] = false;
//        }
//        vbccArr.clear();
//    }
//
//    void addEdge(int u, int v) {
//        nxt[++cntg] = head[u];
//        to[cntg] = v;
//        head[u] = cntg;
//    }
//
//    void tarjan(int u, bool root) {
//        dfn[u] = low[u] = ++cntd;
//        sta[++top] = u;
//        int son = 0;
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            if (dfn[v] == 0) {
//                son++;
//                tarjan(v, false);
//                low[u] = min(low[u], low[v]);
//                if (low[v] >= dfn[u]) {
//                    if (!root || son >= 2) {
//                        cutVertex[u] = true;
//                    }
//                    vector<int> list;
//                    list.push_back(u);
//                    int pop;
//                    do {
//                        pop = sta[top--];
//                        list.push_back(pop);
//                    } while (pop != v);
//                    vbccArr.push_back(list);
//                }
//            } else {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//
//    long long minimumCost(vector<int>& cost, vector<vector<int>>& roads) {
//        n = cost.size();
//        m = roads.size();
//        prepare();
//        for (int i = 0; i < m; i++) {
//            int u = roads[i][0] + 1;
//            int v = roads[i][1] + 1;
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//        tarjan(1, true);
//        long long ans = 0;
//        if (vbccArr.size() == 1) {
//            ans = LLONG_MAX;
//            for (int i = 0; i < n; i++) {
//                ans = min(ans, (long long)cost[i]);
//            }
//        } else {
//            int maxVal = INT_MIN;
//            for (auto &vbcc : vbccArr) {
//                int cut = 0;
//                int val = INT_MAX;
//                for (int cur : vbcc) {
//                    if (cutVertex[cur]) {
//                        cut++;
//                    } else {
//                        val = min(val, cost[cur - 1]);
//                    }
//                }
//                if (cut == 1) {
//                    ans += val;
//                    maxVal = max(maxVal, val);
//                }
//            }
//            ans -= maxVal;
//        }
//        return ans;
//    }
//};