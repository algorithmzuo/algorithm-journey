package class182;

// 根节点的概率问题，C++版
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
//int childCnt[MAXN];
//int child[MAXN][2];
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
//	sum[i] = (sum[ls[i]] + sum[rs[i]]) % MOD;
//}
//
//void lazy(int i, long long v) {
//    if (i) {
//    	sum[i] = sum[i] * v % MOD;
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
//int update(int jobi, int jobv, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = ++cntt;
//        mul[rt] = 1;
//    }
//    if (l == r) {
//    	sum[rt] = jobv % MOD;
//    } else {
//        down(rt);
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = update(jobi, jobv, l, mid, ls[rt]);
//        } else {
//            rs[rt] = update(jobi, jobv, mid + 1, r, rs[rt]);
//        }
//        up(rt);
//    }
//    return rt;
//}
//
//int merge(int l, int r, int t1, int t2, long long mul1, long long mul2, long long v) {
//    if (t1 == 0 || t2 == 0) {
//        if (t1) {
//            lazy(t1, mul1);
//        }
//        if (t2) {
//            lazy(t2, mul2);
//        }
//        return t1 + t2;
//    }
//    down(t1);
//    down(t2);
//    int mid = (l + r) >> 1;
//    int ls1 = ls[t1];
//    int rs1 = rs[t1];
//    int ls2 = ls[t2];
//    int rs2 = rs[t2];
//    long long lsum1 = sum[ls1];
//    long long rsum1 = sum[rs1];
//    long long lsum2 = sum[ls2];
//    long long rsum2 = sum[rs2];
//    long long tmp = (1 - v + MOD) % MOD;
//    ls[t1] = merge(l, mid, ls1, ls2, (mul1 + rsum2 * tmp) % MOD, (mul2 + rsum1 * tmp) % MOD, v);
//    rs[t1] = merge(mid + 1, r, rs1, rs2, (mul1 + lsum2 * v) % MOD, (mul2 + lsum1 * v) % MOD, v);
//    up(t1);
//    return t1;
//}
//
//void dfs(int u) {
//    if (childCnt[u] == 0) {
//        root[u] = update(val[u], 1, 1, cntv, root[u]);
//    } else if (childCnt[u] == 1) {
//        dfs(child[u][0]);
//        root[u] = root[child[u][0]];
//    } else {
//        dfs(child[u][0]);
//        dfs(child[u][1]);
//        root[u] = merge(1, cntv, root[child[u][0]], root[child[u][1]], 0, 0, val[u]);
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
//            child[fa[i]][childCnt[fa[i]]++] = i;
//        }
//    }
//    long long inv = power(10000, MOD - 2);
//    for (int i = 1; i <= n; i++) {
//        if (childCnt[i] == 0) {
//        	sorted[++cntv] = val[i];
//        } else {
//            val[i] = (int)(inv * val[i] % MOD);
//        }
//    }
//    sort(sorted + 1, sorted + cntv + 1);
//    int len = 1;
//    for (int i = 2; i <= cntv; i++) {
//        if (sorted[len] != sorted[i]) {
//        	sorted[++len] = sorted[i];
//        }
//    }
//    cntv = len;
//    for (int i = 1; i <= n; i++) {
//        if (childCnt[i] == 0) {
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