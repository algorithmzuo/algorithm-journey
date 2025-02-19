package class160;

// 线段树套平衡树，C++版
// 给定一个长度为n的数组arr，下标1~n，每条操作都是如下5种类型中的一种，一共进行m次操作
// 操作 1 x y z : 查询数字z在arr[x..y]中的排名
// 操作 2 x y z : 查询arr[x..y]中排第z名的数字
// 操作 3 x y   : arr中x位置的数字改成y
// 操作 4 x y z : 查询数字z在arr[x..y]中的前驱，不存在返回-2147483647
// 操作 5 x y z : 查询数字z在arr[x..y]中的后继，不存在返回+2147483647
// 1 <= n、m <= 5 * 10^4
// 数组中的值永远在[0, 10^8]范围内
// 测试链接 : https://www.luogu.com.cn/problem/P3380
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXT = MAXN * 40;
//const int INF = INT_MAX;
//double ALPHA = 0.7;
//int n, m;
//int arr[MAXN];
//int root[MAXN << 2];
//int key[MAXT];
//int cnts[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int diff[MAXT];
//int cnt;
//int collect[MAXT];
//int ci;
//int top, father, side;
//
//int init(int num) {
//    key[++cnt] = num;
//    ls[cnt] = rs[cnt] = 0;
//    cnts[cnt] = siz[cnt] = diff[cnt] = 1;
//    return cnt;
//}
//
//void up(int i) {
//	siz[i] = siz[ls[i]] + siz[rs[i]] + cnts[i];
//	diff[i] = diff[ls[i]] + diff[rs[i]] + (cnts[i] > 0 ? 1 : 0);
//}
//
//bool balance(int i) {
//    return i == 0 || ALPHA * diff[i] >= max(diff[ls[i]], diff[rs[i]]);
//}
//
//void inorder(int i) {
//    if (i) {
//        inorder(ls[i]);
//        if (cnts[i] > 0) collect[++ci] = i;
//        inorder(rs[i]);
//    }
//}
//
//int innerBuild(int l, int r) {
//    if (l > r) return 0;
//    int mid = (l + r) >> 1;
//    int h = collect[mid];
//    ls[h] = innerBuild(l, mid - 1);
//    rs[h] = innerBuild(mid + 1, r);
//    up(h);
//    return h;
//}
//
//int innerRebuild(int i) {
//    if (top) {
//        ci = 0;
//        inorder(top);
//        if (ci > 0) {
//            if (father == 0) {
//                i = innerBuild(1, ci);
//            } else if (side == 1) {
//            	ls[father] = innerBuild(1, ci);
//            } else {
//            	rs[father] = innerBuild(1, ci);
//            }
//        }
//    }
//    return i;
//}
//
//int innerInsert(int num, int i, int f, int s) {
//    if (!i) {
//        i = init(num);
//    } else {
//        if (key[i] == num) {
//        	cnts[i]++;
//        } else if (key[i] > num) {
//        	ls[i] = innerInsert(num, ls[i], i, 1);
//        } else {
//        	rs[i] = innerInsert(num, rs[i], i, 2);
//        }
//        up(i);
//        if (!balance(i)) {
//            top = i;
//            father = f;
//            side = s;
//        }
//    }
//    return i;
//}
//
//int innerInsert(int num, int i) {
//    top = father = side = 0;
//    i = innerInsert(num, i, 0, 0);
//    i = innerRebuild(i);
//    return i;
//}
//
//int innerSmall(int num, int i) {
//    if (!i) return 0;
//    if (key[i] >= num) return innerSmall(num, ls[i]);
//    return siz[ls[i]] + cnts[i] + innerSmall(num, rs[i]);
//}
//
//int innerIndex(int index, int i) {
//    int leftsize = siz[ls[i]];
//    if (leftsize >= index) {
//        return innerIndex(index, ls[i]);
//    } else if (leftsize + cnts[i] < index) {
//        return innerIndex(index - leftsize - cnts[i], rs[i]);
//    }
//    return key[i];
//}
//
//int innerPre(int num, int i) {
//    int kth = innerSmall(num, i) + 1;
//    if (kth == 1) return -INF;
//    return innerIndex(kth - 1, i);
//}
//
//int innerPost(int num, int i) {
//    int kth = innerSmall(num + 1, i);
//    if (kth == siz[i]) return INF;
//    return innerIndex(kth + 1, i);
//}
//
//void innerRemove(int num, int i, int f, int s) {
//    if (key[i] == num) {
//    	cnts[i]--;
//    } else if (key[i] > num) {
//    	innerRemove(num, ls[i], i, 1);
//    } else {
//    	innerRemove(num, rs[i], i, 2);
//    }
//    up(i);
//    if (!balance(i)) {
//        top = i;
//        father = f;
//        side = s;
//    }
//}
//
//int innerRemove(int num, int i) {
//    if (innerSmall(num, i) != innerSmall(num + 1, i)) {
//        top = father = side = 0;
//        innerRemove(num, i, 0, 0);
//        i = innerRebuild(i);
//    }
//    return i;
//}
//
//void build(int l, int r, int i) {
//    for (int j = l; j <= r; j++) root[i] = innerInsert(arr[j], root[i]);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//    }
//}
//
//void update(int jobi, int jobv, int l, int r, int i) {
//    root[i] = innerRemove(arr[jobi], root[i]);
//    root[i] = innerInsert(jobv, root[i]);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) update(jobi, jobv, l, mid, i << 1);
//        else update(jobi, jobv, mid + 1, r, i << 1 | 1);
//    }
//}
//
//int small(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) return innerSmall(jobv, root[i]);
//    int mid = (l + r) >> 1, ans = 0;
//    if (jobl <= mid) ans += small(jobl, jobr, jobv, l, mid, i << 1);
//    if (jobr > mid) ans += small(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//    return ans;
//}
//
//int number(int jobl, int jobr, int jobk) {
//    int l = 0, r = 100000000, mid, ans = 0;
//    while (l <= r) {
//        mid = (l + r) >> 1;
//        if (small(jobl, jobr, mid + 1, 1, n, 1) >= jobk) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//int pre(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) return innerPre(jobv, root[i]);
//    int mid = (l + r) >> 1, ans = -INF;
//    if (jobl <= mid) ans = max(ans, pre(jobl, jobr, jobv, l, mid, i << 1));
//    if (jobr > mid) ans = max(ans, pre(jobl, jobr, jobv, mid + 1, r, i << 1 | 1));
//    return ans;
//}
//
//int post(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) return innerPost(jobv, root[i]);
//    int mid = (l + r) >> 1, ans = INF;
//    if (jobl <= mid) ans = min(ans, post(jobl, jobr, jobv, l, mid, i << 1));
//    if (jobr > mid) ans = min(ans, post(jobl, jobr, jobv, mid + 1, r, i << 1 | 1));
//    return ans;
//}
//
//int main(){
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for(int i = 1; i <= n; i++) cin >> arr[i];
//    build(1, n, 1);
//    for(int i = 1, op, x, y, z; i <= m; i++) {
//        cin >> op >> x >> y;
//        if(op == 3) {
//            update(x, y, 1, n, 1);
//            arr[x] = y;
//        } else {
//            cin >> z;
//            if(op == 1) cout << small(x, y, z, 1, n, 1) + 1 << "\n";
//            else if(op == 2) cout << number(x, y, z) << "\n";
//            else if(op == 4) cout << pre(x, y, z, 1, n, 1) << "\n";
//            else cout << post(x, y, z, 1, n, 1) << "\n";
//        }
//    }
//    return 0;
//}