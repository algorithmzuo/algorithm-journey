package class160;

// 网络管理，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4175
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 80001;
//const int MAXT = MAXN * 110;
//const int MAXH = 18;
//int n, m, s;
//
//int arr[MAXN];
//int ques[MAXN][3];
//int sorted[MAXN << 1];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int sum[MAXT];
//int cntt;
//
//int deep[MAXN];
//int size[MAXN];
//int dfn[MAXN];
//int stjump[MAXN][MAXH];
//int cntd;
//
//int pos[MAXN];
//int pre[MAXN];
//int cntpos, cntpre;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int kth(int num) {
//    int ls = 1, rs = s, mid;
//    while (ls <= rs) {
//        mid = (ls + rs) / 2;
//        if (sorted[mid] == num) return mid;
//        else if (sorted[mid] < num) ls = mid + 1;
//        else rs = mid - 1;
//    }
//    return -1;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void up(int i) {
//    sum[i] = sum[ls[i]] + sum[rs[i]];
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    if (i == 0) i = ++cntt;
//    if (l == r) sum[i] += jobv;
//    else {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) ls[i] = add(jobi, jobv, l, mid, ls[i]);
//        else rs[i] = add(jobi, jobv, mid + 1, r, rs[i]);
//        up(i);
//    }
//    return i;
//}
//
//void add(int i, int kth, int val) {
//    for (; i <= n; i += lowbit(i)) root[i] = add(kth, val, 1, s, root[i]);
//}
//
//int queryNumber(int jobk, int l, int r) {
//    if (l == r) return l;
//    int mid = (l + r) / 2;
//    int leftsum = 0;
//    for (int i = 1; i <= cntpos; i++) leftsum += sum[ls[pos[i]]];
//    for (int i = 1; i <= cntpre; i++) leftsum -= sum[ls[pre[i]]];
//    if (jobk <= leftsum) {
//        for (int i = 1; i <= cntpos; i++) pos[i] = ls[pos[i]];
//        for (int i = 1; i <= cntpre; i++) pre[i] = ls[pre[i]];
//        return queryNumber(jobk, l, mid);
//    } else {
//        for (int i = 1; i <= cntpos; i++) pos[i] = rs[pos[i]];
//        for (int i = 1; i <= cntpre; i++) pre[i] = rs[pre[i]];
//        return queryNumber(jobk - leftsum, mid + 1, r);
//    }
//}
//
//void dfs(int u, int fa) {
//    deep[u] = deep[fa] + 1;
//    size[u] = 1;
//    dfn[u] = ++cntd;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    for (int e = head[u]; e; e = nxt[e]) {
//        if (to[e] != fa) dfs(to[e], u);
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        if (to[e] != fa) size[u] += size[to[e]];
//    }
//}
//
//int lca(int a, int b) {
//    if (deep[a] < deep[b]) swap(a, b);
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (deep[stjump[a][p]] >= deep[b]) a = stjump[a][p];
//    }
//    if (a == b) return a;
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//void prepare() {
//    s = 0;
//    for (int i = 1; i <= n; i++) sorted[++s] = arr[i];
//    for (int i = 1; i <= m; i++) if (ques[i][0] == 0) sorted[++s] = ques[i][2];
//    sort(sorted + 1, sorted + s + 1);
//    s = unique(sorted + 1, sorted + s + 1) - sorted - 1;
//    for (int i = 1; i <= n; i++) arr[i] = kth(arr[i]);
//    dfs(1, 0);
//    for (int i = 1; i <= n; i++) {
//        add(dfn[i], arr[i], 1);
//        add(dfn[i] + size[i], arr[i], -1);
//    }
//}
//
//void change(int i, int v) {
//    add(dfn[i], arr[i], -1);
//    add(dfn[i] + size[i], arr[i], 1);
//    arr[i] = kth(v);
//    add(dfn[i], arr[i], 1);
//    add(dfn[i] + size[i], arr[i], -1);
//}
//
//int query(int x, int y, int k) {
//    int xylca = lca(x, y);
//    int lcafa = stjump[xylca][0];
//    int num = deep[x] + deep[y] - deep[xylca] - deep[lcafa];
//    if (num < k) return -1;
//    cntpos = cntpre = 0;
//    for (int i = dfn[x]; i; i -= lowbit(i)) pos[++cntpos] = root[i];
//    for (int i = dfn[y]; i; i -= lowbit(i)) pos[++cntpos] = root[i];
//    for (int i = dfn[xylca]; i; i -= lowbit(i)) pre[++cntpre] = root[i];
//    for (int i = dfn[lcafa]; i; i -= lowbit(i)) pre[++cntpre] = root[i];
//    return queryNumber(num - k + 1, 1, s);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(0);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) cin >> arr[i];
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= m; i++) cin >> ques[i][0] >> ques[i][1] >> ques[i][2];
//    prepare();
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 0) {
//            change(ques[i][1], ques[i][2]);
//        } else {
//            int ans = query(ques[i][1], ques[i][2], ques[i][0]);
//            if(ans == -1) {
//            	cout << "invalid request!" << "\n";
//            } else {
//            	cout << sorted[ans] << "\n";
//            }
//        }
//    }
//    return 0;
//}