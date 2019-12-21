package com.hoc.thltm2.bai2;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

public class Eval {
  private static final Map<String, BiFunction<Double, Double, Double>> OPERATORS_FUNCTION;

  static {
    OPERATORS_FUNCTION = new HashMap<>();
    OPERATORS_FUNCTION.put("+", Double::sum);
    OPERATORS_FUNCTION.put("-", (x, y) -> x - y);
    OPERATORS_FUNCTION.put("*", (x, y) -> x * y);
    OPERATORS_FUNCTION.put("/", (x, y) -> x / y);
    OPERATORS_FUNCTION.put("%", (x, y) -> x % y);
    OPERATORS_FUNCTION.put("^", Math::pow);
  }

  public static void main(String[] args) {
    System.out.println(Eval.execute("5+13-(12-4*6) -((3+4)-5)"));
    System.out.println(Eval.execute("1+2+3+4"));
    System.out.println(Eval.execute("1 % 2"));
  }

  /*@NonNull*/
  private static String refine(/*@NonNull*/ String infix) {
    final StringBuilder infixBuilder = new StringBuilder(infix);
    final int parenthesesDifferent = countChar(infixBuilder, '(')
      - countChar(infixBuilder, ')');
    if (parenthesesDifferent > 0) {
      infixBuilder.append(
        String.valueOf(new char[parenthesesDifferent])
          .replace('\0', ')')
      );
    } else if (parenthesesDifferent < 0 || !balancedParentheses(infix)) {
      return "";
    }

    return infixBuilder.toString()
      .replaceAll("\\s+", "")
      .replaceAll("([(*/%^])-(\\d+(\\.(\\d+)?)?)", "$1(0-$2)")
      .replaceAll("([(*/%^])-\\(", "$1(-1)*(")
      .replaceAll("\\)\\(", ")*(")
      .replaceAll("(\\d)\\(", "$1*(")
      .replaceAll("[+\\-*/%^()]", " $0 ")
      .replaceAll("\\d+(\\.(\\d+)?)?", "$0 ")
      .trim();
  }

  private static boolean balancedParentheses(/*@NonNull*/ String s) {
    final Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
      if (c == '(') {
        stack.push(c);
      } else if (c == ')') {
        if (stack.isEmpty()) {
          return false;
        }
        stack.pop();
      }
    }
    return stack.isEmpty();
  }

  private static int countChar(/*@NonNull*/ CharSequence s, char ch) {
    return Math.toIntExact(s.chars().filter(c -> c == ch).count());
  }

  public static double execute(/*@NonNull*/ String infix) {
    final String postfix = infixToPostfix(infix);
    //System.out.println("Postfix: " + postfix);
    return evaluation(postfix);
  }

  private static double evaluation(/*@NonNull*/ String postfix) {
    final Stack<Double> stack = new Stack<>();
    for (String s : postfix.trim().split("\\s+")) {
      if ("+-*/%^".contains(s)) {
        final Double y = stack.pop();
        final Double x = stack.pop();

        final BiFunction<Double, Double, Double> function = OPERATORS_FUNCTION.get(s);
        if (function == null) {
          throw new IllegalStateException("Unknown operator '" + s + "'");
        } else {
          stack.push(function.apply(x, y));
        }
      } else {
        stack.push(Double.parseDouble(s));
      }
    }
    return stack.peek();
  }

  /*@NonNull*/
  private static String infixToPostfix(/*@NonNull*/ String infix) {
    final StringBuilder postfix = new StringBuilder();
    final Stack<String> stack = new Stack<>();

    final String refined = refine(infix);
    // System.out.println("Refined: " + refined);
    for (String elem : refined.split("\\s+")) {
      if ("+-*/%^".contains(elem)) {
        while (!stack.isEmpty()
          && priorityOf(elem) <= priorityOf(stack.peek())) {
          postfix.append(stack.pop())
            .append(' ');
        }
        stack.push(elem);
      } else if ("(".equals(elem)) {
        stack.push(elem);
      } else if (")".equals(elem)) {
        while (!"(".equals(stack.peek())) {
          postfix.append(stack.pop())
            .append(' ');
        }
        stack.pop();
      } else {
        postfix.append(elem).append(' ');
      }
    }

    while (!stack.isEmpty()) {
      postfix.append(stack.pop()).append(' ');
    }

    return postfix.toString();
  }

  private static int priorityOf(/*@NonNull*/ String operator) {
    if ("^".equals(operator)) return 3;
    if ("*/%".contains(operator)) return 2;
    if ("+-".contains(operator)) return 1;
    if ("()".contains(operator)) return 0;
    throw new IllegalStateException("Operator '" + operator + "' not implement");
  }
}
