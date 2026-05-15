package class199;

// 岛屿，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4381
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//using namespace std;
//using ll = long long;
//
//const int MAXN = 1000001;
//int n;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//bool vis[MAXN];
//bool cycle[MAXN];
//int start;
//
//int arr[MAXN];
//int cnta;
//
//bool flag[MAXN];
//ll dist[MAXN];
//ll diameter;
//
//ll sum[MAXN << 1];
//ll height[MAXN << 1];
//int que[MAXN << 1];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//bool dfs(int u, int preEdge) {
//    if (vis[u] == true) {
//        start = u;
//        cycle[u] = true;
//        arr[++cnta] = u;
//        return true;
//    }
//    vis[u] = true;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (e != (preEdge ^ 1) && dfs(v, e)) {
//            if (u == start) {
//                return false;
//            }
//            cycle[u] = true;
//            arr[++cnta] = u;
//            sum[cnta] = w;
//            return true;
//        }
//    }
//    return false;
//}
//
//void dp(int u) {
//    flag[u] = true;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (!flag[v] && !cycle[v]) {
//            dp(v);
//            diameter = max(diameter, dist[u] + dist[v] + w);
//            dist[u] = max(dist[u], dist[v] + w);
//        }
//    }
//}
//
//ll compute(int root) {
//    cnta = 0;
//    dfs(root, 0);
//    if (cnta == 0) {
//        diameter = 0;
//        dp(root);
//        return diameter;
//    }
//    for (int e = head[arr[1]]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v == arr[cnta]) {
//            sum[cnta + 1] = w;
//            break;
//        }
//    }
//    for (int i = cnta + 2; i <= cnta * 2; i++) {
//        sum[i] = sum[i - cnta];
//    }
//    for (int i = 1; i <= cnta * 2; i++) {
//        sum[i] += sum[i - 1];
//    }
//    ll ans1 = 0;
//    for (int i = 1; i <= cnta; i++) {
//        diameter = 0;
//        dp(arr[i]);
//        ans1 = max(ans1, diameter);
//        height[i] = dist[arr[i]];
//        height[i + cnta] = height[i];
//    }
//    ll ans2 = 0;
//    int ql = 1, qr = 0;
//    for (int i = 1; i <= cnta * 2; i++) {
//        while (ql <= qr && que[ql] <= i - cnta) {
//            ql++;
//        }
//        if (ql <= qr) {
//            ans2 = max(ans2, height[i] + height[que[ql]] + sum[i] - sum[que[ql]]);
//        }
//        while (ql <= qr && height[que[qr]] - sum[que[qr]] <= height[i] - sum[i]) {
//            qr--;
//        }
//        que[++qr] = i;
//    }
//    return max(ans1, ans2);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    cntg = 1;
//    for (int u = 1, v, w; u <= n; u++) {
//        cin >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    ll ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (!flag[i]) {
//            ans += compute(i);
//        }
//    }
//    cout << ans << "\n";
//    return 0;
//}