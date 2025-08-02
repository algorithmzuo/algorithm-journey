package class175;

// 给你一棵树，C++版
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
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//
//int fa[MAXN];
//int seg[MAXN];
//int cntd = 0;
//
//int len[MAXN];
//int max1[MAXN];
//int max2[MAXN];
//
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//	nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int f) {
//    fa[u] = f;
//    seg[++cntd] = u;
//    for (int e = head[u]; e; e = nxt[e]) {
//    	if (to[e] != f) {
//    	    dfs(to[e], u);
//    	}
//    }
//}
//
//int getCnt(int limit) {
//    int pathCnt = 0;
//    for (int dfn = n, cur, father; dfn >= 1; dfn--) {
//        cur = seg[dfn];
//        father = fa[cur];
//        if (max1[cur] + max2[cur] + 1 >= limit) {
//            pathCnt++;
//            len[cur] = 0;
//        } else {
//            len[cur] = max1[cur] + 1;
//        }
//        if (len[cur] > max1[father]) {
//        	max2[father] = max1[father];
//            max1[father] = len[cur];
//        } else if (len[cur] > max2[father]) {
//        	max2[father] = len[cur];
//        }
//        max1[cur] = max2[cur] = 0;
//    }
//    return pathCnt;
//}
//
//int findLimit(int l, int r, int pathCnt) {
//    int limit = 1;
//    while (l <= r) {
//        int mid = (l + r) >> 1;
//        if (getCnt(mid) <= pathCnt) {
//            limit = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return limit;
//}
//
//void compute() {
//    for (int k = 1; k <= blen; k++) {
//        ans[k] = getCnt(k);
//    }
//    int l = 1, r = n;
//    for (int pathCnt = 0; pathCnt * blen <= n; pathCnt++) {
//        int limit = findLimit(l, r, pathCnt);
//        if (getCnt(limit) == pathCnt) {
//            ans[limit] = pathCnt;
//            r = limit;
//        }
//    }
//}
//
//void prepare() {
//    dfs(1, 0);
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