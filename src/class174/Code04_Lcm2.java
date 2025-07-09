package class174;

// 最小公倍数，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3247
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Param {
//    int u, v, a, b, qid;
//};
//
//bool cmpa(Param &x, Param &y) {
//    return x.a < y.a;
//}
//
//bool cmpb(Param &x, Param &y) {
//    return x.b < y.b;
//}
//
//const int MAXN = 50001;
//const int MAXM = 100001;
//const int MAXQ = 50001;
//int n, m, q;
//int blen, bnum;
//
//Param edge[MAXM];
//Param ques[MAXQ];
//
//int arr[MAXQ];
//int cntq;
//
//int fa[MAXN];
//int siz[MAXN];
//int maxa[MAXN];
//int maxb[MAXN];
//
//int rollback[MAXN][5];
//int opsize;
//bool ans[MAXQ];
//
//void build() {
//    for (int i = 1; i <= n; i++) {
//        fa[i] = i;
//        siz[i] = 1;
//        maxa[i] = -1;
//        maxb[i] = -1;
//    }
//}
//
//int find(int x) {
//    while (x != fa[x]) {
//        x = fa[x];
//    }
//    return x;
//}
//
//void Union(int x, int y, int a, int b) {
//    int fx = find(x);
//    int fy = find(y);
//    if (siz[fx] < siz[fy]) {
//        swap(fx, fy);
//    }
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//    rollback[opsize][2] = siz[fx];
//    rollback[opsize][3] = maxa[fx];
//    rollback[opsize][4] = maxb[fx];
//    if (fx != fy) {
//        fa[fy] = fx;
//        siz[fx] += siz[fy];
//    }
//    maxa[fx] = max({maxa[fx], maxa[fy], a});
//    maxb[fx] = max({maxb[fx], maxb[fy], b});
//}
//
//void undo() {
//    for (int fx, fy; opsize > 0; opsize--) {
//        fx = rollback[opsize][0];
//        fy = rollback[opsize][1];
//        fa[fy] = fy;
//        siz[fx] = rollback[opsize][2];
//        maxa[fx] = rollback[opsize][3];
//        maxb[fx] = rollback[opsize][4];
//    }
//}
//
//bool query(int x, int y, int a, int b) {
//    int fx = find(x);
//    int fy = find(y);
//    return fx == fy && maxa[fx] == a && maxb[fx] == b;
//}
//
//void compute(int l, int r) {
//    build();
//    cntq = 0;
//    for (int i = 1; i <= q; i++) {
//        if (edge[l].a <= ques[i].a && (r + 1 > m || ques[i].a < edge[r + 1].a)) {
//            arr[++cntq] = i;
//        }
//    }
//    if (cntq > 0) {
//        sort(edge + 1, edge + l, cmpb);
//        int pos = 1;
//        for (int i = 1; i <= cntq; i++) {
//            for (; pos < l && edge[pos].b <= ques[arr[i]].b; pos++) {
//                Union(edge[pos].u, edge[pos].v, edge[pos].a, edge[pos].b);
//            }
//            opsize = 0;
//            for (int j = l; j <= r; j++) {
//                if (edge[j].a <= ques[arr[i]].a && edge[j].b <= ques[arr[i]].b) {
//                    Union(edge[j].u, edge[j].v, edge[j].a, edge[j].b);
//                }
//            }
//            ans[ques[arr[i]].qid] = query(ques[arr[i]].u, ques[arr[i]].v, ques[arr[i]].a, ques[arr[i]].b);
//            undo();
//        }
//    }
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(m * log2(n)));
//    bnum = (m + blen - 1) / blen;
//    sort(edge + 1, edge + m + 1, cmpa);
//    sort(ques + 1, ques + q + 1, cmpb);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v, a, b; i <= m; i++) {
//        cin >> edge[i].u >> edge[i].v >> edge[i].a >> edge[i].b;
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> ques[i].u >> ques[i].v >> ques[i].a >> ques[i].b;
//        ques[i].qid = i;
//    }
//    prepare();
//    for (int i = 1, l, r; i <= bnum; i++) {
//        l = (i - 1) * blen + 1;
//        r = min(i * blen, m);
//        compute(l, r);
//    }
//    for (int i = 1; i <= q; i++) {
//        cout << (ans[i] ? "Yes" : "No") << '\n';
//    }
//    return 0;
//}