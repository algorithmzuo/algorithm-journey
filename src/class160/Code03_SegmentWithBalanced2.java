package class160;

// 线段树套平衡树，C++版
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
//void inorder(int i) {
//    if (i) {
//        inorder(ls[i]);
//        if (cnts[i] > 0) collect[++ci] = i;
//        inorder(rs[i]);
//    }
//}
//
//int build(int l, int r) {
//    if (l > r) return 0;
//    int mid = (l + r) >> 1;
//    int h = collect[mid];
//    ls[h] = build(l, mid - 1);
//    rs[h] = build(mid + 1, r);
//    up(h);
//    return h;
//}
//
//int rebuild(int i) {
//    if (top) {
//        ci = 0;
//        inorder(top);
//        if (ci > 0) {
//            if (father == 0) {
//                i = build(1, ci);
//            } else if (side == 1) {
//            	ls[father] = build(1, ci);
//            } else {
//            	rs[father] = build(1, ci);
//            }
//        }
//    }
//    return i;
//}
//
//bool balance(int i) {
//    return i == 0 || ALPHA * diff[i] >= max(diff[ls[i]], diff[rs[i]]);
//}
//
//int insertNumber(int num, int i, int f, int s) {
//    if (!i) {
//        i = init(num);
//    } else {
//        if (key[i] == num) {
//        	cnts[i]++;
//        } else if (key[i] > num) {
//        	ls[i] = insertNumber(num, ls[i], i, 1);
//        } else {
//        	rs[i] = insertNumber(num, rs[i], i, 2);
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
//int insertNumber(int num, int i) {
//    top = father = side = 0;
//    i = insertNumber(num, i, 0, 0);
//    i = rebuild(i);
//    return i;
//}
//
//int querySmall(int num, int i) {
//    if (!i) return 0;
//    if (key[i] >= num) return querySmall(num, ls[i]);
//    return siz[ls[i]] + cnts[i] + querySmall(num, rs[i]);
//}
//
//int queryIndex(int index, int i) {
//    int leftsize = siz[ls[i]];
//    if (leftsize >= index) {
//        return queryIndex(index, ls[i]);
//    } else if (leftsize + cnts[i] < index) {
//        return queryIndex(index - leftsize - cnts[i], rs[i]);
//    }
//    return key[i];
//}
//
//int queryPre(int num, int i) {
//    int kth = querySmall(num, i) + 1;
//    if (kth == 1) return -INF;
//    return queryIndex(kth - 1, i);
//}
//
//int queryPost(int num, int i) {
//    int kth = querySmall(num + 1, i);
//    if (kth == siz[i]) return INF;
//    return queryIndex(kth + 1, i);
//}
//
//void removeNumber(int num, int i, int f, int s) {
//    if (key[i] == num) {
//    	cnts[i]--;
//    } else if (key[i] > num) {
//    	removeNumber(num, ls[i], i, 1);
//    } else {
//    	removeNumber(num, rs[i], i, 2);
//    }
//    up(i);
//    if (!balance(i)) {
//        top = i;
//        father = f;
//        side = s;
//    }
//}
//
//int removeNumber(int num, int i) {
//    if (querySmall(num, i) != querySmall(num + 1, i)) {
//        top = father = side = 0;
//        removeNumber(num, i, 0, 0);
//        i = rebuild(i);
//    }
//    return i;
//}
//
//void build(int l, int r, int i) {
//    for (int j = l; j <= r; j++) root[i] = insertNumber(arr[j], root[i]);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//    }
//}
//
//void update(int jobi, int jobv, int l, int r, int i) {
//    root[i] = removeNumber(arr[jobi], root[i]);
//    root[i] = insertNumber(jobv, root[i]);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) update(jobi, jobv, l, mid, i << 1);
//        else update(jobi, jobv, mid + 1, r, i << 1 | 1);
//    }
//}
//
//int small(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) return querySmall(jobv, root[i]);
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
//    if (jobl <= l && r <= jobr) return queryPre(jobv, root[i]);
//    int mid = (l + r) >> 1, ans = -INF;
//    if (jobl <= mid) ans = max(ans, pre(jobl, jobr, jobv, l, mid, i << 1));
//    if (jobr > mid) ans = max(ans, pre(jobl, jobr, jobv, mid + 1, r, i << 1 | 1));
//    return ans;
//}
//
//int post(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) return queryPost(jobv, root[i]);
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
//    for(int i=1;i<=n;i++) cin >> arr[i];
//    build(1,n,1);
//    for(int i=1,op,x,y,z;i<=m;i++){
//        cin >> op >> x >> y;
//        if(op==3){
//            update(x,y,1,n,1);
//            arr[x]=y;
//        } else {
//            cin >> z;
//            if(op==1) cout << small(x,y,z,1,n,1)+1 << "\n";
//            else if(op==2) cout << number(x,y,z) << "\n";
//            else if(op==4) cout << pre(x,y,z,1,n,1) << "\n";
//            else cout << post(x,y,z,1,n,1) << "\n";
//        }
//    }
//    return 0;
//}