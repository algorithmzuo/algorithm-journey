package class154;

// 派遣，dfs用递归实现，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1552
// 逻辑和java的递归版完全一样，java必须改迭代版才能通过，C++的递归版就能通过
// 这是洛谷没有考虑其他语言导致的，提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//long long ans;
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cnt;
//long long cost[MAXN];
//long long lead[MAXN];
//int ls[MAXN];  
//int rs[MAXN];  
//int dist[MAXN];
//int father[MAXN];
//int siz[MAXN];  
//long long sum[MAXN]; 
//
//void prepare() {
//    ans = 0;
//    cnt = 1;
//    for (int i = 1; i <= n; i++) {
//        head[i] = ls[i] = rs[i] = dist[i] = siz[i] = 0;
//        sum[i] = 0;
//        father[i] = i;
//    }
//}
//
//void addEdge(int u, int v) {
//    nxt[cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt++;
//}
//
//int find(int i) {
//    if (father[i] != i) {
//        father[i] = find(father[i]);
//    }
//    return father[i];
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    if (cost[i] < cost[j]) {
//        int tmp = i;
//        i = j;
//        j = tmp;
//    }
//    rs[i] = merge(rs[i], j);
//    if (dist[ls[i]] < dist[rs[i]]) {
//        int tmp = ls[i];
//        ls[i] = rs[i];
//        rs[i] = tmp;
//    }
//    dist[i] = dist[rs[i]] + 1;
//    father[i] = father[ls[i]] = father[rs[i]] = i;
//    return i;
//}
//
//int pop(int i) {
//    father[ls[i]] = ls[i];
//    father[rs[i]] = rs[i];
//    father[i] = merge(ls[i], rs[i]);
//    ls[i] = rs[i] = 0;
//    return father[i];
//}
//
//void dfs(int u) {
//    for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
//        dfs(to[ei]);
//    }
//    int usize = 1;
//    long long usum = cost[u];
//    for (int ei = head[u], v, l, r; ei > 0; ei = nxt[ei]) {
//        v = to[ei];
//        l = find(u);
//        r = find(v);
//        usize += siz[r];
//        usum += sum[r];
//        merge(l, r);
//    }
//    int i;
//    while (usum > m) {
//        i = find(u);
//        usize--;
//        usum -= cost[i];
//        pop(i);
//    }
//    i = find(u);
//    siz[i] = usize;
//    sum[i] = usum;
//    ans = max(ans, (long long)usize * lead[u]);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    prepare();
//    int root = 0;
//    for (int i = 1; i <= n; i++) {
//        int f;
//        cin >> f >> cost[i] >> lead[i];
//        if (f == 0) {
//            root = i;
//        } else {
//            addEdge(f, i);
//        }
//    }
//    dfs(root);
//    cout << ans << endl;
//    return 0;
//}