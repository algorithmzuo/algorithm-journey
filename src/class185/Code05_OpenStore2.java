package class185;

// 开店，C++版
// 一共n个人，每个人有年龄，给定n-1条路，每条路有距离，路把人连成一棵树
// 一共m条查询，格式 u l r : 查询年龄范围[l, r]的所有人，到第u号人的距离总和
// 1 <= n <= 1.5 * 10^5    1 <= m <= 2 * 10^5
// 0 <= 年龄值 <= 10^9      1 <= 距离值 <= 1000
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P3241
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int age;
//    long long sum;
//};
//
//bool NodeCmp(Node x, Node y) {
//    return x.age < y.age;
//}
//
//const int MAXN = 200001;
//const int MAXK = 4000001;
//int n, m, A;
//int age[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dist[MAXN];
//
//bool vis[MAXN];
//int centfa[MAXN];
//
//int curl[MAXN];
//int curr[MAXN];
//Node curArr[MAXK];
//int cntc;
//
//int fal[MAXN];
//int far[MAXN];
//Node faArr[MAXK];
//int cntf;
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f, int dis) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    dist[u] = dis;
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != f) {
//            dfs1(v, u, dis + w);
//        }
//    }
//    for (int ei = head[u], v; ei; ei = nxt[ei]) {
//        v = to[ei];
//        if (v != f) {
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    while (top[a] != top[b]) {
//        if (dep[top[a]] <= dep[top[b]]) {
//            b = fa[top[b]];
//        } else {
//            a = fa[top[a]];
//        }
//    }
//    return dep[a] <= dep[b] ? a : b;
//}
//
//int getDist(int x, int y) {
//    return dist[x] + dist[y] - (dist[getLca(x, y)] << 1);
//}
//
//void getSize(int u, int f) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != f && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int f) {
//    getSize(u, f);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != f && !vis[v] && siz[v] > half) {
//                f = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void getList(int u, int f, int sum, int rt) {
//    curArr[++cntc] = { age[u], sum };
//    if (centfa[rt] > 0) {
//        faArr[++cntf] = { age[u], getDist(u, centfa[rt]) };
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != f && !vis[v]) {
//            getList(v, u, sum + w, rt);
//        }
//    }
//}
//
//void centroidTree(int u, int f) {
//    centfa[u] = f;
//    vis[u] = true;
//    curl[u] = cntc + 1;
//    fal[u] = cntf + 1;
//    getList(u, 0, 0, u);
//    curr[u] = cntc;
//    far[u] = cntf;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            centroidTree(getCentroid(v, u), u);
//        }
//    }
//}
//
//int kth(Node arr[], int l, int r, int x) {
//    int ans = r + 1;
//    while (l <= r) {
//        int mid = (l + r) >> 1;
//        if (arr[mid].age >= x) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//long long nodeCnt, pathSum;
//
//void query(Node arr[], int l, int r, int agel, int ager) {
//    nodeCnt = pathSum = 0;
//    if (l <= r) {
//        int a = kth(arr, l, r, agel);
//        int b = kth(arr, l, r, ager + 1) - 1;
//        if (a <= b) {
//            nodeCnt = b - a + 1;
//            pathSum = arr[b].sum - (a == l ? 0 : arr[a - 1].sum);
//        }
//    }
//}
//
//long long compute(int u, int agel, int ager) {
//    query(curArr, curl[u], curr[u], agel, ager);
//    long long ans = pathSum;
//    long long cnt1, sum1, cnt2, sum2;
//    for (int cur = u, f = centfa[cur]; f > 0; cur = f, f = centfa[cur]) {
//        query(curArr, curl[f], curr[f], agel, ager);
//        cnt1 = nodeCnt;
//        sum1 = pathSum;
//        query(faArr, fal[cur], far[cur], agel, ager);
//        cnt2 = nodeCnt;
//        sum2 = pathSum;
//        ans += sum1;
//        ans -= sum2;
//        ans += (cnt1 - cnt2) * getDist(u, f);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> A;
//    for (int i = 1; i <= n; i++) {
//        cin >> age[i];
//    }
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    dfs1(1, 0, 0);
//    dfs2(1, 1);
//    centroidTree(getCentroid(1, 0), 0);
//    for (int i = 1; i <= n; i++) {
//        sort(curArr + curl[i], curArr + curr[i] + 1, NodeCmp);
//        for (int j = curl[i] + 1; j <= curr[i]; j++) {
//            curArr[j].sum += curArr[j - 1].sum;
//        }
//        sort(faArr + fal[i], faArr + far[i] + 1, NodeCmp);
//        for (int j = fal[i] + 1; j <= far[i]; j++) {
//            faArr[j].sum += faArr[j - 1].sum;
//        }
//    }
//    long long lastAns = 0;
//    for (int i = 1, u, l, r; i <= m; i++) {
//        cin >> u >> l >> r;
//        l = (int)((lastAns + l) % A);
//        r = (int)((lastAns + r) % A);
//        if (l > r) {
//            swap(l, r);
//        }
//        lastAns = compute(u, l, r);
//        cout << lastAns << '\n';
//    }
//    return 0;
//}