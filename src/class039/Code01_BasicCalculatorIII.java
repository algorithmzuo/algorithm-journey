package class039;

import java.util.Stack;

// 含有嵌套的表达式求值
// 测试链接 : https://leetcode.cn/problems/basic-calculator-iii/
// 01:27 ~ 39:00
public class Code01_BasicCalculatorIII {

    public static int start;

    public static int calculate(String str) {
        start = 0;
        return func1(str.toCharArray(), 0);
//        return func2(str.toCharArray(), 0);// 我改写的😄
    }

    // 12:58
    // str[i....]开始计算，遇到字符串终止 或者 遇到)停止
    // 返回 : 自己负责的这一段，计算的结果
    // 返回之间，更新全局变量start，为了上游函数知道从哪继续！
    // In computer programming, an operand is any object capable of being manipulated.
    // For example, in "1 + 2" the "1" and "2" are the operands and the plus symbol is the operator.
    public static int func1(char[] str, int i) {
        int operand = 0;
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        while (i < str.length && str[i] != ')') {
            if (Character.isDigit(str[i])) {
                operand = operand * 10 + str[i++] - '0';
            } else if (str[i] != '(') {
                push1(operands, operators, operand, str[i++]);
                operand = 0;
            } else {
                operand = func1(str, i + 1);
                i = start + 1;
            }
        }
        push1(operands, operators, operand, '+');
        start = i;
        return compute1(operands, operators);
    }

    public static int func2(char[] str, int i) {
        int operand = 0;
        char operator = '+';
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        push2(operands, operators, operand, operator);
        while (i < str.length && str[i] != ')') {
            if (Character.isDigit(str[i])) {
                operand = operand * 10 + str[i++] - '0';
            } else if (str[i] != '(') {
                push2(operands, operators, operand, operator);
                operand = 0;
                operator = str[i++];
            } else {
                operand = func2(str, i + 1);
                i = start + 1;
            }
        }
        push2(operands, operators, operand, operator);
        start = i;
        return compute2(operands, operators);
    }

    public static void push1(Stack<Integer> operands, Stack<Character> operators, int operand, char operator) {
        int n = operands.size();
        if (n == 0 || operators.peek() == '+' || operators.peek() == '-') {
            operands.push(operand);
            operators.push(operator);
        } else {
            int topOperand = operands.pop();
            char topOperator = operators.pop();
            if (topOperator == '*') {
                operands.push(topOperand * operand);
            } else if (topOperator == '/') {
                operands.push(topOperand / operand);
            }
            operators.push(operator);
        }
    }

    public static void push2(Stack<Integer> operands, Stack<Character> operators, int operand, char operator) {
        if (operator == '+' || operator == '-') {
            operands.push(operand);
            operators.push(operator);
        } else {
            int topOperand = operands.pop();
            if (operator == '*')
                operands.push(topOperand * operand);
            else if (operator == '/')
                operands.push(topOperand / operand);
        }
    }

    public static int compute1(Stack<Integer> operands, Stack<Character> operators) {
        int invalid = operators.pop();
        int ans = operands.pop();
        while (!operands.isEmpty()) {
            char operator = operators.pop();
            if (operator == '+')
                ans = ans + operands.pop();
            else if (operator == '-')
                ans = -ans + operands.pop();
        }
        return ans;
    }

    public static int compute2(Stack<Integer> operands, Stack<Character> operators) {
        int ans = 0;
        while (!operands.isEmpty() && !operators.isEmpty()) {
            char operator = operators.pop();
            if (operator == '+')
                ans += operands.pop();
            else if (operator == '-')
                ans -= operands.pop();
        }
        return ans;
    }

    public static void main(String[] args) {
//        String expression = "3+(2*(9-4))";
        String expression = "33+33";
        int result = calculate(expression);
        System.out.println("Result: " + result);
    }

}
