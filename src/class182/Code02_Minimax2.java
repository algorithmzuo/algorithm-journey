package class182;

// 根节点的概率，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5298
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//const int MAXT = MAXN * 40;
//const int MOD = 998244353;
//int n;
//
//int fa[MAXN];
//int val[MAXN];
//int sorted[MAXN];
//int cntv;
//
//int sonCnt[MAXN];
//int son[MAXN][2];
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int cntt;
//
//long long sum[MAXT];
//long long mul[MAXT];
//
//long long d[MAXN];
//
//long long power(long long x, int p) {
//    long long ans = 1;
//    while (p) {
//        if (p & 1) {
//            ans = ans * x % MOD;
//        }
//        x = x * x % MOD;
//        p >>= 1;
//    }
//    return ans;
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
//    sum[i] = (sum[ls[i]] + sum[rs[i]]) % MOD;
//}
//
//void lazy(int i, long long v) {
//    if (i) {
//        sum[i] = sum[i] * v % MOD;
//        mul[i] = mul[i] * v % MOD;
//    }
//}
//
//void down(int i) {
//    if (mul[i] != 1) {
//        lazy(ls[i], mul[i]);
//        lazy(rs[i], mul[i]);
//        mul[i] = 1;
//    }
//}
//
//int insert(int jobi, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = ++cntt;
//        mul[rt] = 1;
//    }
//    if (l == r) {
//        sum[rt] = 1;
//    } else {
//        down(rt);
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = insert(jobi, l, mid, ls[rt]);
//        } else {
//            rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
//        }
//        up(rt);
//    }
//    return rt;
//}
//
//int merge(int l, int r, int t1, int t2, int p, long long sum1, long long sum2) {
//    if (t1 == 0 || t2 == 0) {
//        if (t1) {
//            lazy(t1, sum2);
//        }
//        if (t2) {
//            lazy(t2, sum1);
//        }
//        return t1 + t2;
//    }
//    if (l == r) {
//        sum[t1] = (sum[t1] * sum2 % MOD + sum[t2] * sum1 % MOD) % MOD;
//    } else {
//        down(t1);
//        down(t2);
//        int mid = (l + r) >> 1;
//        long long l1 = (sum1 + sum[rs[t1]] * (1 - p + MOD)) % MOD;
//        long long l2 = (sum2 + sum[rs[t2]] * (1 - p + MOD)) % MOD;
//        long long r1 = (sum1 + sum[ls[t1]] * p) % MOD;
//        long long r2 = (sum2 + sum[ls[t2]] * p) % MOD;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2], p, l1, l2);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2], p, r1, r2);
//        up(t1);
//    }
//    return t1;
//}
//
//void dfs(int u) {
//    if (sonCnt[u] == 0) {
//        root[u] = insert(val[u], 1, cntv, root[u]);
//    } else if (sonCnt[u] == 1) {
//        dfs(son[u][0]);
//        root[u] = root[son[u][0]];
//    } else {
//        dfs(son[u][0]);
//        dfs(son[u][1]);
//        root[u] = merge(1, cntv, root[son[u][0]], root[son[u][1]], val[u], 0, 0);
//    }
//}
//
//void getd(int l, int r, int i) {
//    if (i == 0) {
//        return;
//    }
//    if (l == r) {
//        d[l] = sum[i] % MOD;
//    } else {
//        down(i);
//        int mid = (l + r) >> 1;
//        getd(l, mid, ls[i]);
//        getd(mid + 1, r, rs[i]);
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        if (fa[i] != 0) {
//            son[fa[i]][sonCnt[fa[i]]++] = i;
//        }
//    }
//    long long inv = power(10000, MOD - 2);
//    for (int i = 1; i <= n; i++) {
//        if (sonCnt[i] == 0) {
//            sorted[++cntv] = val[i];
//        } else {
//            val[i] = (int)(inv * val[i] % MOD);
//        }
//    }
//    sort(sorted + 1, sorted + cntv + 1);
//    int len = 1;
//    for (int i = 2; i <= cntv; i++) {
//        if (sorted[len] != sorted[i]) {
//            sorted[++len] = sorted[i];
//        }
//    }
//    cntv = len;
//    for (int i = 1; i <= n; i++) {
//        if (sonCnt[i] == 0) {
//            val[i] = kth(val[i]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> fa[i];
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> val[i];
//    }
//    prepare();
//    dfs(1);
//    getd(1, cntv, root[1]);
//    long long ans = 0;
//    for (int i = 1; i <= cntv; i++) {
//        ans = (ans + (1LL * i * sorted[i]) % MOD * d[i] % MOD * d[i] % MOD) % MOD;
//    }
//    cout << ans << '\n';
//    return 0;
//}