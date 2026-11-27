package class210;

// 航空路线，C++版
// 一共c个城市，城市名依次给出，城市按照从西到东分布
// 一共f条航线，给定两端城市名，两座城市可以相互直飞
// 从最西城市出发，去程每次只能向东飞，直到最东端城市
// 从最东城市返程，每次只能向西飞，直到飞回最西端城市
// 整个行程中，除了最西城市之外，每座城市最多访问一次
// 如果有解，先输出最多能经过多少城市，再按旅行顺序输出城市名
// 如果多解，输出任意一种即可，如果无解，输出 No Solution!
// 1 <= c < 100
// 1 <= f <= c * (c - 1) / 2
// 测试链接 : https://www.luogu.com.cn/problem/P2770
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 201;
//const int MAXM = 5001;
//int INF = 1000000001;
//int n;
//
//string name[MAXN];
//unordered_map<string, int> id;
//int ans[MAXN];
//int siz;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int weight[MAXM << 1];
//int cost[MAXM << 1];
//int cntg;
//
//int flow[MAXN];
//int pre[MAXN];
//
//queue<int> que;
//int h[MAXN];
//int update[MAXN];
//bool enter[MAXN];
//
//struct HeapNode {
//    int dist;
//    int u;
//
//    bool operator<(const HeapNode &other) const {
//        return dist > other.dist;
//    }
//};
//
//priority_queue<HeapNode> heap;
//int dist[MAXN];
//bool vis[MAXN];
//
//int maxflow, mincost;
//
//void addEdge(int u, int v, int w, int c) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    cost[cntg] = c;
//    head[u] = cntg;
//}
//
//bool spfa(int s) {
//    for (int i = 1; i <= n; i++) {
//        h[i] = INF;
//        update[i] = 0;
//        enter[i] = false;
//    }
//    h[s] = 0;
//    update[s] = 1;
//    enter[s] = true;
//    que = queue<int>();
//    que.push(s);
//    while (!que.empty()) {
//        int u = que.front();
//        que.pop();
//        enter[u] = false;
//        for (int e = head[u], v, w, c; e > 0; e = nxt[e]) {
//            v = to[e];
//            w = weight[e];
//            c = cost[e];
//            if (w > 0 && h[v] > h[u] + c) {
//                h[v] = h[u] + c;
//                if (!enter[v]) {
//                    if (++update[v] > n) {
//                        return true;
//                    }
//                    que.push(v);
//                    enter[v] = true;
//                }
//            }
//        }
//    }
//    return false;
//}
//
//void dijkstra(int s) {
//    for (int i = 1; i <= n; i++) {
//        dist[i] = INF;
//        flow[i] = -1;
//        vis[i] = false;
//    }
//    flow[s] = INF;
//    dist[s] = 0;
//    heap = priority_queue<HeapNode>();
//    heap.push({0, s});
//    while (!heap.empty()) {
//        HeapNode cur = heap.top();
//        heap.pop();
//        int d = cur.dist;
//        int u = cur.u;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head[u], v, w, c; e > 0; e = nxt[e]) {
//                v = to[e];
//                w = weight[e];
//                c = cost[e] + h[u] - h[v];
//                if (w > 0 && !vis[v] && dist[v] > d + c) {
//                    dist[v] = dist[u] + c;
//                    flow[v] = min(w, flow[u]);
//                    pre[v] = e;
//                    heap.push({dist[v], v});
//                }
//            }
//        }
//    }
//}
//
//bool ssp(int s, int t) {
//    bool negCycle = spfa(s);
//    if (negCycle) {
//        return true;
//    }
//    maxflow = mincost = 0;
//    dijkstra(s);
//    while (flow[t] != -1) {
//        maxflow += flow[t];
//        for (int u = t; u != s; u = to[pre[u] ^ 1]) {
//            weight[pre[u]] -= flow[t];
//            weight[pre[u] ^ 1] += flow[t];
//            mincost += flow[t] * cost[pre[u]];
//        }
//        for (int i = 1; i <= n; i++) {
//            if (dist[i] != INF) {
//                h[i] += dist[i];
//            }
//        }
//        dijkstra(s);
//    }
//    return false;
//}
//
//void getRoute(int cities) {
//    siz = 0;
//    int len1 = 0;
//    for (int trip = 1; trip <= 2; trip++) {
//        int u = 1;
//        while (u != cities) {
//            ans[++siz] = u;
//            for (int e = head[cities + u]; e > 0; e = nxt[e]) {
//                if (cost[e] == -1 && weight[e ^ 1] > 0) {
//                    weight[e ^ 1]--;
//                    weight[e]++;
//                    u = to[e];
//                    break;
//                }
//            }
//        }
//        if (trip == 1) {
//            ans[++siz] = cities;
//            len1 = siz;
//        }
//    }
//    for (int l = len1 + 1, r = siz; l < r; l++, r--) {
//        swap(ans[l], ans[r]);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int cities, flights;
//    cin >> cities >> flights;
//    n = cities << 1;
//    int s = 1;
//    int t = n;
//    cntg = 1;
//    for (int i = 1; i <= cities; i++) {
//        cin >> name[i];
//        id[name[i]] = i;
//        if (i == 1 || i == cities) {
//            addEdge(i, cities + i, 2, 0);
//            addEdge(cities + i, i, 0, 0);
//        } else {
//            addEdge(i, cities + i, 1, 0);
//            addEdge(cities + i, i, 0, 0);
//        }
//    }
//    string s1, s2;
//    int a, b, u, v;
//    for (int i = 1; i <= flights; i++) {
//        cin >> s1 >> s2;
//        a = id[s1];
//        b = id[s2];
//        u = min(a, b);
//        v = max(a, b);
//        addEdge(u + cities, v, 2, -1);
//        addEdge(v, u + cities, 0, 1);
//    }
//    ssp(s, t);
//    if (maxflow != 2) {
//        cout << "No Solution!" << '\n';
//    } else {
//        cout << -mincost << '\n';
//        getRoute(cities);
//        for (int i = 1; i <= siz; i++) {
//            cout << name[ans[i]] << '\n';
//        }
//    }
//    return 0;
//}