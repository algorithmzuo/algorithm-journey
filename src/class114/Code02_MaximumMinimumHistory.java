package class114;

// 区间最值和历史最值
// 给定两个长度都为n的数组A和B，一开始两个数组完全一样
// 实现以下五种操作，一共会调用m次
// 操作 1 l r v : A[l..r]范围上每个数加上v
// 操作 2 l r v : A[l..r]范围上每个数A[i]变成min(A[i],v)
// 操作 3 l r   : 返回A[l..r]范围上的累加和
// 操作 4 l r   : 返回A[l..r]范围上的最大值
// 操作 5 l r   : 返回B[l..r]范围上的最大值
// 任何操作做完，都更新B数组，B[i] = max(B[i],A[i])
// 1 <= n、m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P6242
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过
public class Code02_MaximumMinimumHistory {

}
