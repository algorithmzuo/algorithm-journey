package class175;

// 给你一棵树，C++版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树
// 树的路径是指，从端点x到端点y的简单路径，k路径是指，路径的节点数正好为k
// 整棵树希望分解成尽量多的k路径，k路径的节点不能复用，所有k路径不要求包含所有点
// 打印k = 1, 2, 3..n时，k路径有最多有几条
// 测试链接 : https://www.luogu.com.cn/problem/CF1039D
// 测试链接 : https://codeforces.com/problemset/problem/1039/D
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//int n, blen;
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//
//int fa[MAXN];
//int dfnOrder[MAXN];
//int cntd = 0;
//
//int len[MAXN];
//int max1[MAXN];
//int max2[MAXN];
//
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int f) {
//    fa[u] = f;
//    dfnOrder[++cntd] = u;
//    for (int e = head[u]; e; e = nxt[e]) {
//        if (to[e] != f) {
//            dfs(to[e], u);
//        }
//    }
//}
//
//int query(int k) {
//    int cnt = 0;
//    for (int i = n, cur, father; i >= 1; i--) {
//        cur = dfnOrder[i];
//        father = fa[cur];
//        if (max1[cur] + max2[cur] + 1 >= k) {
//            cnt++;
//            len[cur] = 0;
//        } else {
//            len[cur] = max1[cur] + 1;
//        }
//        if (len[cur] > max1[father]) {
//            max2[father] = max1[father];
//            max1[father] = len[cur];
//        } else if (len[cur] > max2[father]) {
//            max2[father] = len[cur];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        len[i] = max1[i] = max2[i] = 0;
//    }
//    return cnt;
//}
//
//int jump(int l, int r, int curAns) {
//    int find = l;
//    while (l <= r) {
//        int mid = (l + r) >> 1;
//        int check = query(mid);
//        if (check < curAns) {
//            r = mid - 1;
//        } else if (check > curAns) {
//            l = mid + 1;
//        } else {
//            find = mid;
//            l = mid + 1;
//        }
//    }
//    return find + 1;
//}
//
//void compute() {
//    for (int i = 1; i <= blen; i++) {
//        ans[i] = query(i);
//    }
//    for (int i = blen + 1; i <= n; i = jump(i, n, ans[i])) {
//        ans[i] = query(i);
//    }
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(n * log2(n)));
//    fill(ans + 1, ans + n + 1, -1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    prepare();
//    compute();
//    for (int i = 1; i <= n; i++) {
//        if (ans[i] == -1) {
//            ans[i] = ans[i - 1];
//        }
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}