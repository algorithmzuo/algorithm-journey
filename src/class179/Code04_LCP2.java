package class179;

// 区间lcp达标对，C++版
// 给定一个长度为n的字符串str，还有一个参数k
// 位置a开头的字符串 和 位置b开头的字符串，如果最长公共前缀 >= k，则(a, b)构成达标对
// 构成达标对的必须是不同的两个位置，并且(a, b)和(b, a)只算一个达标对，不要重复统计
// 接下来有m条查询，格式为 l r : str[l..r]范围上，可以任选开头位置，打印达标对的数量
// 1 <= n、k <= 3 * 10^6       1 <= m <= 10^5
// 1 <= n * n * m <= 10^15    字符集为 f z o u t s y
// 测试链接 : https://www.luogu.com.cn/problem/P5112
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, id;
//};
//
//const int MAXN = 3000001;
//const int MAXM = 100001;
//int n, m, k;
//int len, cntq, cntv;
//char str[MAXN];
//Query query[MAXM];
//
//int base = 499;
//long long basePower[MAXN];
//long long hashValue[MAXN];
//
//long long val[MAXN];
//long long sorted[MAXN];
//int arr[MAXN];
//int bi[MAXN];
//
//long long cnt[MAXN];
//long long curAns;
//long long ans[MAXM];
//
//int kth(long long num) {
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
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    if (bi[a.l] & 1) {
//        return a.r < b.r;
//    } else {
//        return a.r > b.r;
//    }
//}
//
//void add(int x) {
//    curAns -= cnt[x] * cnt[x];
//    cnt[x]++;
//    curAns += cnt[x] * cnt[x];
//}
//
//void del(int x) {
//    curAns -= cnt[x] * cnt[x];
//    cnt[x]--;
//    curAns += cnt[x] * cnt[x];
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= cntq; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id   = query[i].id;
//        while (winl > jobl) {
//            add(arr[--winl]);
//        }
//        while (winr < jobr) {
//            add(arr[++winr]);
//        }
//        while (winl < jobl) {
//            del(arr[winl++]);
//        }
//        while (winr > jobr) {
//            del(arr[winr--]);
//        }
//        ans[id] = (curAns - (jobr - jobl + 1)) / 2;
//    }
//}
//
//void prepare() {
//	basePower[0] = 1;
//    for (int i = 1; i <= n; i++) {
//    	basePower[i] = basePower[i - 1] * base;
//    	hashValue[i] = hashValue[i - 1] * base + (str[i] - 'a' + 1);
//    }
//    for (int l = 1, r = k; r <= n; l++, r++) {
//        val[l] = hashValue[r] - hashValue[l - 1] * basePower[r - l + 1];
//    }
//    for (int i = 1; i <= len; i++) {
//    	sorted[i] = val[i];
//    }
//    sort(sorted + 1, sorted + len + 1);
//    cntv = 1;
//    for (int i = 2; i <= len; i++) {
//        if (sorted[cntv] != sorted[i]) {
//        	sorted[++cntv] = sorted[i];
//        }
//    }
//    for (int i = 1; i <= len; i++) {
//        arr[i] = kth(val[i]);
//    }
//    int blen = max(1, (int)((double)len / sqrt((double)cntq)));
//    for (int i = 1; i <= len; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + cntq + 1, QueryCmp);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> k;
//    string s;
//    s.reserve(n);
//    cin >> s;
//    for (int i = 1; i <= n; i++) {
//    	str[i] = s[i - 1];
//    }
//    len = n - k + 1;
//    cntq = 0;
//    for (int i = 1, l, r; i <= m; i++) {
//        cin >> l >> r;
//        if (l <= len) {
//            query[++cntq].l = l;
//            query[cntq].r = min(r, len);
//            query[cntq].id = i;
//        }
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}