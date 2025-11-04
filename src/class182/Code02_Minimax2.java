package class182;

// 根节点的取值，C++版
// 一共有n个节点，1号节点是整棵树的头，所有节点组成一棵树
// 给定一个长度为n的数组arr，如果节点x是叶，那么arr[x]表示点权，所有叶节点的点权都不同
// 如果节点x不是叶，那么它最多有两个孩子，此时arr[x]代表概率，节点x按照以下规则取得点权
// 以arr[x]的概率是所有儿子的点权最大值，以1 - arr[x]的概率是所有儿子的点权最小值
// 表示概率时，arr[x]的范围是[1, 9999]，表示概率 0.0001 ~ 0.9999
// 假设1号结点的权值有m种可能性，第i小的权值是Vi，取得概率为Di
// 计算 i = 1..m 时，每一项 (i * Vi * Di * Di) 的累加和，答案对 998244353 取模
// 1 <= n <= 3 * 10^5    1 <= 叶节点权值 <= 10^9    1 <= 概率 <= 9999
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
//int arr[MAXN];
//int sorted[MAXN];
//int cntv;
//
//int sonCnt[MAXN];
//int son[MAXN][2];
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//long long sum[MAXT];
//long long mulLazy[MAXT];
//int cntt;
//
//long long D[MAXN];
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
//        mulLazy[i] = mulLazy[i] * v % MOD;
//    }
//}
//
//void down(int i) {
//    if (mulLazy[i] != 1) {
//        lazy(ls[i], mulLazy[i]);
//        lazy(rs[i], mulLazy[i]);
//        mulLazy[i] = 1;
//    }
//}
//
//int insert(int jobi, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = ++cntt;
//        mulLazy[rt] = 1;
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
//        long long lsum1 = (sum1 + sum[rs[t1]] * (1 - p + MOD)) % MOD;
//        long long lsum2 = (sum2 + sum[rs[t2]] * (1 - p + MOD)) % MOD;
//        long long rsum1 = (sum1 + sum[ls[t1]] * p) % MOD;
//        long long rsum2 = (sum2 + sum[ls[t2]] * p) % MOD;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2], p, lsum1, lsum2);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2], p, rsum1, rsum2);
//        up(t1);
//    }
//    return t1;
//}
//
//void dp(int u) {
//    if (sonCnt[u] == 0) {
//        root[u] = insert(arr[u], 1, cntv, root[u]);
//    } else if (sonCnt[u] == 1) {
//        dp(son[u][0]);
//        root[u] = root[son[u][0]];
//    } else {
//        dp(son[u][0]);
//        dp(son[u][1]);
//        root[u] = merge(1, cntv, root[son[u][0]], root[son[u][1]], arr[u], 0, 0);
//    }
//}
//
//void getd(int l, int r, int i) {
//    if (i == 0) {
//        return;
//    }
//    if (l == r) {
//        D[l] = sum[i] % MOD;
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
//            sorted[++cntv] = arr[i];
//        } else {
//            arr[i] = (int)(inv * arr[i] % MOD);
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
//            arr[i] = kth(arr[i]);
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
//        cin >> arr[i];
//    }
//    prepare();
//    dp(1);
//    getd(1, cntv, root[1]);
//    long long ans = 0;
//    for (int i = 1; i <= cntv; i++) {
//        ans = (ans + (1LL * i * sorted[i]) % MOD * D[i] % MOD * D[i] % MOD) % MOD;
//    }
//    cout << ans << '\n';
//    return 0;
//}