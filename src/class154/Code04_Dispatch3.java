package class154;

// 派遣，dfs用递归实现，C++版
// 一共有n个忍者，每个忍者有工资、领导力、上级编号，三个属性
// 如果某个忍者的上级编号为0，那么他是整棵忍者树的头
// 你一共有m的预算，可以在忍者树上随意选一棵子树，然后在这棵子树上挑选忍者
// 你选择某棵子树之后，不一定要选子树头的忍者，只要不超过m的预算，你就可以随意选择子树上的忍者
// 最终收益 = 雇佣人数 * 子树头忍者的领导力，返回能取得的最大收益是多少
// 输入保证，任何忍者的编号 > 该忍者上级的编号
// 1 <= n <= 10^5           1 <= m <= 10^9
// 1 <= 每个忍者工资 <= m     1 <= 每个忍者领导力 <= 10^9
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
//int fa[MAXN];
//int siz[MAXN];  
//long long sum[MAXN]; 
//
//void prepare() {
//    ans = 0;
//    cnt = 1;
//    dist[0] = -1;
//    for (int i = 1; i <= n; i++) {
//        head[i] = ls[i] = rs[i] = dist[i] = siz[i] = 0;
//        sum[i] = 0;
//        fa[i] = i;
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
//    fa[i] = fa[i] == i ? i : find(fa[i]);
//    return fa[i];
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
//    fa[ls[i]] = fa[rs[i]] = i;
//    return i;
//}
//
//int pop(int i) {
//    fa[ls[i]] = ls[i];
//    fa[rs[i]] = rs[i];
//    fa[i] = merge(ls[i], rs[i]);
//    ls[i] = rs[i] = dist[i] = 0;
//    return fa[i];
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