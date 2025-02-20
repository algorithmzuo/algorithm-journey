package class122;

// 运输计划，C++版，递归不用改迭代
// 有n个节点，给定n-1条边使其连接成一棵树，每条边有正数边权
// 给定很多运输计划，每个运输计划(a,b)表示从a去往b
// 每个运输计划的代价就是沿途边权和，运输计划之间完全互不干扰
// 你只能选择一条边，将其边权变成0
// 你的目的是让所有运输计划代价的最大值尽量小
// 返回所有运输计划代价的最大值最小能是多少
// 测试链接 : https://www.luogu.com.cn/problem/P2680
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//const int MAXM = 300001;
//int n, m;
//int num[MAXN];
//int headEdge[MAXN];
//int edgeNext[MAXN << 1];
//int edgeTo[MAXN << 1];
//int edgeWeight[MAXN << 1];
//int tcnt;
//int headQuery[MAXN];
//int queryNext[MAXM << 1];
//int queryTo[MAXM << 1];
//int queryIndex[MAXM << 1];
//int qcnt;
//bool visited[MAXN];
//int unionfind[MAXN];
//int quesu[MAXM];
//int quesv[MAXM];
//int dist[MAXN];
//int lca[MAXM];
//int cost[MAXM];
//int maxCost;
//int atLeast;
//int beyond;
//
//void build() {
//    tcnt = 1;
//    qcnt = 1;
//    for(int i = 1; i <= n; i++) {
//        headEdge[i] = 0;
//        headQuery[i] = 0;
//        visited[i] = false;
//        unionfind[i] = i;
//    }
//    maxCost = 0;
//}
//
//void addEdge(int u, int v, int w) {
//    edgeNext[tcnt] = headEdge[u];
//    edgeTo[tcnt] = v;
//    edgeWeight[tcnt] = w;
//    headEdge[u] = tcnt++;
//}
//
//void addQuery(int u, int v, int i) {
//    queryNext[qcnt] = headQuery[u];
//    queryTo[qcnt] = v;
//    queryIndex[qcnt] = i;
//    headQuery[u] = qcnt++;
//}
//
//int find(int i) {
//    if(i != unionfind[i]) {
//        unionfind[i] = find(unionfind[i]);
//    }
//    return unionfind[i];
//}
//
//void tarjan(int u, int f, int w) {
//    visited[u] = true;
//    dist[u] = dist[f] + w;
//    for(int e = headEdge[u]; e != 0; e = edgeNext[e]) {
//        int v = edgeTo[e];
//        if(v != f) {
//            tarjan(v, u, edgeWeight[e]);
//        }
//    }
//    for(int e = headQuery[u]; e != 0; e = queryNext[e]) {
//        int v = queryTo[e];
//        if(visited[v]) {
//            int i = queryIndex[e];
//            lca[i] = find(v);
//            cost[i] = dist[u] + dist[v] - 2 * dist[lca[i]];
//            maxCost = max(maxCost, cost[i]);
//        }
//    }
//    unionfind[u] = f;
//}
//
//bool dfs(int u, int f, int w) {
//    for(int e = headEdge[u]; e != 0; e = edgeNext[e]) {
//        int v = edgeTo[e];
//        if(v != f) {
//            if(dfs(v, u, edgeWeight[e])) {
//                return true;
//            }
//        }
//    }
//    for(int e = headEdge[u]; e != 0; e = edgeNext[e]) {
//        int v = edgeTo[e];
//        if(v != f) {
//            num[u] += num[v];
//        }
//    }
//    return (num[u] == beyond && w >= atLeast);
//}
//
//bool f(int limit) {
//    atLeast = maxCost - limit;
//    for(int i = 1; i <= n; i++) {
//        num[i] = 0;
//    }
//    beyond = 0;
//    for(int i = 1; i <= m; i++) {
//        if(cost[i] > limit) {
//            num[quesu[i]]++;
//            num[quesv[i]]++;
//            num[lca[i]] -= 2;
//            beyond++;
//        }
//    }
//    if(beyond == 0) return true;
//    return dfs(1, 0, 0);
//}
//
//int compute() {
//    tarjan(1, 0, 0);
//    int l = 0;
//    int r = maxCost;
//    int ans = 0;
//    while(l <= r) {
//        int mid = (l + r) / 2;
//        if(f(mid)) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//int main(){
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    build();
//    cin >> m;
//    for(int i = 1; i < n; i++){
//        int u,v,w;
//        cin >> u >> v >> w;
//        addEdge(u,v,w);
//        addEdge(v,u,w);
//    }
//    for(int i = 1; i <= m; i++){
//        int u,v;
//        cin >> u >> v;
//        quesu[i] = u;
//        quesv[i] = v;
//        addQuery(u,v,i);
//        addQuery(v,u,i);
//    }
//    cout << compute() << "\n";
//    return 0;
//}