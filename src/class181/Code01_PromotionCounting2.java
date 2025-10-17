package class181;

// 晋升者计数，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3605
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = MAXN * 40;
//int n;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int arr[MAXN];
//int sorted[MAXN];
//int cntv;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int cntt;
//
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int kth(int num) {
//    int left = 1, right = cntv, ret = 0;
//    while (left <= right) {
//        int mid = (left + right) >> 1;
//        if (sorted[mid] <= num) {
//            ret = mid;
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return ret;
//}
//
//void up(int i) {
//    siz[i] = siz[ls[i]] + siz[rs[i]];
//}
//
//int add(int jobi, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = ++cntt;
//    }
//    if (l == r) {
//        siz[rt]++;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = add(jobi, l, mid, ls[rt]);
//        } else {
//            rs[rt] = add(jobi, mid + 1, r, rs[rt]);
//        }
//        up(rt);
//    }
//    return rt;
//}
//
//int merge(int l, int r, int t1, int t2) {
//    if (t1 == 0 || t2 == 0) {
//        return t1 + t2;
//    }
//    if (l == r) {
//        siz[t1] += siz[t2];
//    } else {
//        int mid = (l + r) >> 1;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2]);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
//        up(t1);
//    }
//    return t1;
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl > jobr || i == 0) {
//        return 0;
//    }
//    if (jobl <= l && r <= jobr) {
//        return siz[i];
//    }
//    int mid = (l + r) >> 1;
//    int ret = 0;
//    if (jobl <= mid) {
//        ret += query(jobl, jobr, l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//        ret += query(jobl, jobr, mid + 1, r, rs[i]);
//    }
//    return ret;
//}
//
//void dfs(int u, int fa) {
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            root[u] = merge(1, cntv, root[u], root[v]);
//        }
//    }
//    ans[u] = query(arr[u] + 1, cntv, 1, cntv, root[u]);
//}
//
//void compute() {
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    cntv = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[cntv] != sorted[i]) {
//            sorted[++cntv] = sorted[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        root[i] = add(arr[i], 1, cntv, root[i]);
//    }
//    dfs(1, 0);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 2, fa; i <= n; i++) {
//        cin >> fa;
//        addEdge(fa, i);
//        addEdge(i, fa);
//    }
//    compute();
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}