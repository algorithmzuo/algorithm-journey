package class184;

// 最大中位数路径，C++版
// 一共有n个节点，给定n-1条边，每条边给定边权，所有节点组成一棵树
// 一条简单路径上，收集所有边权组成序列，其中的 下中位数 作为路径的权
// 边数在[limitl, limitr]范围的所有路径中，找到最大权的路径
// 如果有多条路径，找到其中一个方案即可，打印两个端点
// 1 <= n <= 10^5    0 <= 边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF150E
// 测试链接 : https://codeforces.com/problemset/problem/150/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Input {
//    int u, v, w;
//};
//
//bool InputCmp(Input a, Input b) {
//    return a.w < b.w;
//}
//
//struct Edge {
//    int eid, size;
//};
//
//bool EdgeCmp(Edge a, Edge b) {
//    return a.size < b.size;
//}
//
//const int MAXN = 100001;
//const int INF = 1000000001;
//int n, limitl, limitr, cntw;
//
//Input arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//Edge edgeArr[MAXN];
//int cnte;
//
//int preVal[MAXN];
//int preNode[MAXN];
//int preLen;
//
//int curVal[MAXN];
//int curNode[MAXN];
//int curLen;
//
//int que[MAXN];
//
//int ans, ansu, ansv;
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int fa) {
//    getSize(u, fa);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void dfs(int u, int fa, int edge, int sum, int limit) {
//    curLen = max(curLen, edge);
//    if (sum > curVal[edge]) {
//        curVal[edge] = sum;
//        curNode[edge] = u;
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, edge + 1, sum + (w >= limit ? 1 : -1), limit);
//        }
//    }
//}
//
//bool check(int u, int limit) {
//    preVal[0] = 0;
//    preNode[0] = u;
//    preLen = 0;
//    for (int k = 1; k <= cnte; k++) {
//        int v = to[edgeArr[k].eid];
//        int w = weight[edgeArr[k].eid];
//        for (int i = 1; i <= siz[v]; i++) {
//            curVal[i] = -INF;
//        }
//        curLen = 0;
//        dfs(v, u, 1, w >= limit ? 1 : -1, limit);
//        int ql = 1, qr = 0;
//        for (int i = min(preLen, limitr); i >= limitl; i--) {
//            while (ql <= qr && preVal[que[qr]] <= preVal[i]) {
//                qr--;
//            }
//            que[++qr] = i;
//        }
//        int down = limitr, up = limitl;
//        for (int i = 1; i <= curLen; i++) {
//            up--;
//            if (up >= 0 && up <= preLen) {
//                while (ql <= qr && preVal[que[qr]] <= preVal[up]) {
//                    qr--;
//                }
//                que[++qr] = up;
//            }
//            if (ql <= qr && que[ql] == down) {
//                ql++;
//            }
//            down--;
//            if (ql <= qr && preVal[que[ql]] + curVal[i] >= 0) {
//                if (limit > ans) {
//                    ans = limit;
//                    ansu = curNode[i];
//                    ansv = preNode[que[ql]];
//                }
//                return true;
//            }
//        }
//        for (int i = 1; i <= curLen; i++) {
//            if (i > preLen || curVal[i] > preVal[i]) {
//                preVal[i] = curVal[i];
//                preNode[i] = curNode[i];
//            }
//        }
//        preLen = max(preLen, curLen);
//    }
//    return false;
//}
//
//void calc(int u) {
//    getSize(u, 0);
//    cnte = 0;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            edgeArr[++cnte] = { e, siz[v] };
//        }
//    }
//    sort(edgeArr + 1, edgeArr + cnte + 1, EdgeCmp);
//    int l = 1, r = cntw, mid;
//    while (l <= r) {
//        mid = (l + r) >> 1;
//        if (check(u, mid)) {
//            l = mid + 1;
//        } else {
//            r = mid - 1;
//        }
//        if (r <= ans) break;
//    }
//}
//
//void solve(int u) {
//    vis[u] = true;
//    calc(u);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            solve(getCentroid(v, u));
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> limitl >> limitr;
//    for (int i = 1; i < n; i++) {
//        cin >> arr[i].u >> arr[i].v >> arr[i].w;
//    }
//    sort(arr + 1, arr + n, InputCmp);
//    cntw = 0;
//    for (int i = 1; i < n; i++) {
//        if (i == 1 || arr[i - 1].w != arr[i].w) {
//            cntw++;
//        }
//        addEdge(arr[i].u, arr[i].v, cntw);
//        addEdge(arr[i].v, arr[i].u, cntw);
//    }
//    solve(getCentroid(1, 0));
//    cout << ansu << " " << ansv << '\n';
//    return 0;
//}