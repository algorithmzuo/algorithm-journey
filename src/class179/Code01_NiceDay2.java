package class179;

// 美好的每一天，C++版
// 给定一个长度为n的字符串str，其中都是小写字母
// 如果一个子串重新排列字符之后能成为回文串，那么该子串叫做达标子串
// 接下来有m条查询，格式为 l r : 打印str[l..r]范围上有多少达标子串
// 1 <= n、m <= 6 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P3604
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
//const int MAXN = 60002;
//const int MAXV = 1 << 26;
//int n, m;
//int arr[MAXN];
//Query query[MAXN];
//
//int bi[MAXN];
//int cnt[MAXV];
//long long num = 0;
//long long ans[MAXN];
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
//void add(int s) {
//    num += cnt[s];
//    cnt[s]++;
//    for (int i = 0; i < 26; i++) {
//        num += cnt[s ^ (1 << i)];
//    }
//}
//
//void del(int s) {
//    cnt[s]--;
//    num -= cnt[s];
//    for (int i = 0; i < 26; i++) {
//        num -= cnt[s ^ (1 << i)];
//    }
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id = query[i].id;
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
//        ans[id] = num;
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        arr[i] ^= arr[i - 1];
//    }
//    for (int i = n; i >= 0; i--) {
//        arr[i + 1] = arr[i];
//    }
//    n++;
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= m; i++) {
//        query[i].r++;
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    char c;
//    for (int i = 1; i <= n; i++) {
//        cin >> c;
//        arr[i] = 1 << (c - 'a');
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> query[i].l >> query[i].r;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}