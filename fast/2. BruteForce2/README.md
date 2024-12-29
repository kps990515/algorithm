## BruteForcce2(완전탐색)
- 문제를 해결하기 위해 모든 경우를 전부 탐색하는 방법
- 백트래킹이 가능한 경우 확인해서 적용하기
- 유형
  - N개중 중복을 허용하기 / 허용하지 않기
  - M개를 순서있게 나열하기 / 고르기

### [연산자끼워넣기](https://www.acmicpc.net/problem/14888)
- 문제 : N개의 숫자와 N-1개의 연산자가 주어짐, 구할수 있는 최대/최소값 구하기
- 유형 : 숫자 중복불가 / 연산자 순서있게 나열하기
- 시간복잡도 : O(N의M승)
- 공간복잡도 : M

```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static void input() {
        N = scan.nextInt();
        nums = new int[N + 1];
        operators = new int[5];
        for (int i = 1; i <= N; i++) nums[i] = scan.nextInt();
        for (int i = 1; i <= 4; i++) operators[i] = scan.nextInt();

        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
    }

    static int N, max, min;
    static int[] nums, operators;

    // 피연산자 2개와 연산자가 주어졌을 때 계산해주는 함수
    static int calculator(int operand1, int operator, int operand2){
        // value, order[i], num[i+1]
        if (operator == 1) // +
            return operand1 + operand2;
        else if (operator == 2) // -
            return operand1 - operand2;
        else if (operator == 3) // *
            return operand1 * operand2;
        else // /
            return operand1 / operand2;
    }


    // order[1...N-1] 에 연산자들이 순서대로 저장될 것이다.
    // value : k-1번째 연산자까지 계산한 결과
    static void rec_func(int k, int value) {
        if (k == N) {
            // 완성된 식에 맞게 계산을 해서 정답에 갱신하는 작업
            max = Math.max(max, value);
            min = Math.min(min, value);
        } else {
            // k 번째 연산자는 무엇을 선택할 것인가?
            for (int cand = 1; cand <= 4; cand++){
                if (operators[cand] >= 1){
                    operators[cand]--;
                    rec_func(k + 1, calculator(value, cand, nums[k + 1]));
                    operators[cand]++; // 백트래킹
                }
            }
        }
    }

    public static void main(String[] args) {
        input();
        // 1 번째 원소부터 M 번째 원소를 조건에 맞게 고르는 모든 방법을 탐색해줘
        rec_func(1, nums[1]);
        sb.append(max).append('\n').append(min);
        System.out.println(sb.toString());
    }


    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(s));
        }

        private void ensureTokenizer() throws IOException {
            if (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
        }

        String next() throws IOException {
            ensureTokenizer();
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        String nextLine() throws IOException {
            st = null; // reset the tokenizer
            return br.readLine();
        }
    }
}
```

### [N Queen](https://www.acmicpc.net/problem/9663)
- 문제 : N*N 체스판에 N개의 퀸을 서로 공격하지 못하게 놓을 수 있는 경우의 수
- 유형 : N개의 행에 중복해서 놓을 수 있고 / N개를 순서대로 나열
- 시간복잡도 : O(N의M승)
- 공간복잡도 : M
- 중복없이 해결 : used배열 만들어서 각 숫자를 사용했으면 1로 표기
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static void input() {
        N = scan.nextInt();
        col = new int[N + 1];
    }

    static int N, ans;
    static int[] col; // col[i] : i번째 행의 퀸이 놓여진 열의 위치

    static boolean attackable(int r1, int c1, int r2, int c2) {
        if (c1 == c2) return true; // 같은열에 있는 공격 가능
        // 대각선에 있으면 공격 가능
        if (r1 - c1 == r2 - c2) return true; // 행-열의 값이 같으면 같은 대각선
        if (r1 + c1 == r2 + c2) return true; // 행+열의 값이 같으면 같은 대각선
        return false;
    }
    // row번 ~ N번행에 대해 가능한 퀸을 놓는 경우의 수
    static void rec_func(int row) {
        // BaseCase : N번까지 행의 다 놓았을때
        if (row == N + 1) {
            ans++;
        } else {
            // Recursive Case : 1~N번의 행까지 퀸 놓기
            for (int c = 1; c <= N; c++) {
                boolean possible = true;
                // 이전 행까지 놓았던 퀸들에게 공격을 안받는지 확인
                for (int i=1;i<=row-1;i++){
                    // (i, col[i])
                    if (attackable(row, c, i, col[i])){
                        possible = false;
                        break;
                    }
                }
                if (possible) {
                    col[row] = c;
                    rec_func(row + 1);
                    col[row] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        input();
        // 1 번째 원소부터 M 번째 원소를 조건에 맞게 고르는 모든 방법을 탐색해줘
        rec_func(1);
        System.out.println(ans);
    }
    
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(s));
        }

        private void ensureTokenizer() throws IOException {
            if (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
        }

        String next() throws IOException {
            ensureTokenizer();
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        String nextLine() throws IOException {
            st = null; // reset the tokenizer
            return br.readLine();
        }
    }
} 
```

### 문제 : N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.
- 유형 : N 중복가능 / M은 순서있음
- 시간복잡도 : O(N의M승)
- 공간복잡도 : M
- 비내림차순 : 이전숫자인 같거나 start보다 큰 숫자로 범위제한 
  - for (int cand = start; cand <= N; cand++) 
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static void input() {
        N = scan.nextInt();
        S = scan.nextInt();
        nums = new int[N + 1];
        for (int i = 1; i <= N; i++) nums[i] = scan.nextInt();
    }

    static int N, S, ans;
    static int[] nums;
    // k번째 원소를 포함시킬 지 정하는 함수
    // value:= k-1 번째 원소까지 골라진 원소들의 합
    static void rec_func(int k, int value) {
        if (k == N + 1) {  // 부분 수열을 하나 완성 시킨 상태
            // value 가 S 랑 같은 지 확인하기
            if (value == S) ans++;
        } else {
            // k 번째 원소를 포함시킬 지 결정하고 재귀호출해주기
            // Include
            rec_func(k + 1, value + nums[k]);
            // Not Include
            rec_func(k + 1, value);
        }
    }

    public static void main(String[] args) {
        input();
        // 1 번째 원소부터 M 번째 원소를 조건에 맞게 고르는 모든 방법을 탐색해줘
        rec_func(1, 0);
        // S의 기본값이 0 일때의 경우의수는 제외
        if (S == 0){
            ans--;
        }
        System.out.println(ans);
    }


    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(s));
        }

        private void ensureTokenizer() throws IOException {
            if (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
        }

        String next() throws IOException {
            ensureTokenizer();
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        String nextLine() throws IOException {
            st = null; // reset the tokenizer
            return br.readLine();
        }
    }
} 
```

