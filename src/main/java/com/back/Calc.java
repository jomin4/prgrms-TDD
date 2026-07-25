package com.back;

public class Calc {
    public static int run(String expression) {
        // 공백을 모두 제거해 파싱을 단순화한다. "10 + 5 * 2" -> "10+5*2"
        return new Parser(expression.replaceAll("\\s", "")).parseExpr();
    }

    /**
     * 재귀 하강 파서(Recursive Descent Parser).
     * Stack 자료구조 대신 "메서드 호출(=재귀)"로 연산자 우선순위와 괄호를 처리한다.
     *
     * 문법(우선순위 낮음 -> 높음):
     *   expr   := term   ( ('+' | '-') term )*      // 덧셈/뺄셈
     *   term   := factor ( '*' factor )*            // 곱셈 (덧셈보다 먼저 묶임)
     *   factor := '-' factor | '(' expr ')' | number // 단항 마이너스, 괄호, 숫자
     */
    private static class Parser {
        private final String s;
        private int pos = 0; // 현재 읽고 있는 위치

        Parser(String s) {
            this.s = s;
        }

        // expr := term ( ('+'|'-') term )*
        int parseExpr() {
            int result = parseTerm();

            while (pos < s.length() && (peek() == '+' || peek() == '-')) {
                char op = next();
                int rhs = parseTerm();
                result = (op == '+') ? result + rhs : result - rhs;
            }

            return result;
        }

        // term := factor ( '*' factor )*
        int parseTerm() {
            int result = parseFactor();

            while (pos < s.length() && peek() == '*') {
                next(); // '*' 소비
                result *= parseFactor();
            }

            return result;
        }

        // factor := '-' factor | '(' expr ')' | number
        int parseFactor() {
            // 단항 마이너스: -factor
            if (peek() == '-') {
                next(); // '-' 소비
                return -parseFactor();
            }

            // 괄호: '(' expr ')'  -> 안쪽을 최우선으로 계산
            if (peek() == '(') {
                next(); // '(' 소비
                int result = parseExpr();
                next(); // ')' 소비
                return result;
            }

            // 숫자
            return parseNumber();
        }

        int parseNumber() {
            int start = pos;
            while (pos < s.length() && Character.isDigit(peek())) {
                pos++;
            }
            return Integer.parseInt(s.substring(start, pos));
        }

        private char peek() {
            return s.charAt(pos);
        }

        private char next() {
            return s.charAt(pos++);
        }
    }
}
