package class200;

// 仙人掌删边方案数，C++版
// 给定n个点、m条路径表示无向图，每条路径的相邻两点之间都有边
// 如果图不是仙人掌打印0，如果图是仙人掌，可以在图中删除一些边
// 如果删除后剩下的图仍然连通，就叫有效的删边方案
// 允许一条边也不删，打印有效删边的方案数，答案可能很大，需要高精度
// 1 <= n <= 2 * 10^4
// 1 <= 边总数 <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4129
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 20001;
//const int MAXM = 1000001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int top;
//
//int edgeCnt[MAXN];
//int cntc;
//
//int cycleCnt[MAXN];
//
//string ans;
//
//void multiply(int x) {
//    int carry = 0;
//    for (int i = ans.size() - 1; i >= 0; i--) {
//        int cur = (ans[i] - '0') * x + carry;
//        ans[i] = cur % 10 + '0';
//        carry = cur / 10;
//    }
//    string pre = "";
//    while (carry > 0) {
//        pre += carry % 10 + '0';
//        carry /= 10;
//    }
//    reverse(pre.begin(), pre.end());
//    ans = pre + ans;
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            if (low[v] < dfn[u]) {
//                low[u] = min(low[u], low[v]);
//                cycleCnt[u]++;
//            } else if (low[v] > dfn[u]) {
//                top--;
//            } else {
//                cntc++;
//                edgeCnt[cntc] = 1;
//                int pop;
//                do {
//                    pop = sta[top--];
//                    edgeCnt[cntc]++;
//                } while (pop != v);
//            }
//        } else if (dfn[v] < dfn[u]) {
//            low[u] = min(low[u], dfn[v]);
//            cycleCnt[u]++;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntg = 1;
//    for (int i = 1, k, x, y; i <= m; i++) {
//        cin >> k >> x;
//        for (int j = 2; j <= k; j++) {
//            cin >> y;
//            addEdge(x, y);
//            addEdge(y, x);
//            x = y;
//        }
//    }
//    tarjan(1, 0);
//    bool check = true;
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0 || cycleCnt[i] >= 2) {
//            check = false;
//            break;
//        }
//    }
//    if (check) {
//        ans = "1";
//        for (int i = 1; i <= cntc; i++) {
//            multiply(edgeCnt[i] + 1);
//        }
//        cout << ans << "\n";
//    } else {
//        cout << 0 << "\n";
//    }
//    return 0;
//}