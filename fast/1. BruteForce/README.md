## BruteForcce(완전탐색)
- 문제를 해결하기 위해 모든 경우를 전부 탐색하는 방법
- 백트래킹이 가능한 경우 확인해서 적용하기
- 유형
  - N개중 중복을 허용하기 / 허용하지 않기
  - M개를 순서있게 나열하기 / 고르기

### [N과 M(3)](https://www.acmicpc.net/problem/15651)
- 문제 : 자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
- 유형 : N개중 중복을 허용하기 / M개를 순서있게 나열하기
- 시간복잡도 : O(N의M승)
- 공간복잡도 : M

```java
public class Main {
    static StringBuilder sb = new StringBuilder();

    static void input() {
        FastReader scan = new FastReader();
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M + 1];
    }

    static int N, M;
    static int[] selected; // 선택한 수열 저장소
    
    // Recurrence Function (재귀 함수)
    // 만약 M 개를 전부 고름   => 조건에 맞는 탐색을 한 가지 성공한 것!
    // 아직 M 개를 고르지 않음 => k 번째부터 M번째 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    static void rec_func(int k) {
        // BaseCase : M개를 다 고른 경우
        if (k == M + 1) { 
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            // Recursive Case : M개를 고르지 않은 경우(k가 M+1될때까지 반복)
            // k번째 자리수를 1~N까지 채워주는 함수
            for (int cand = 1; cand <= N; cand++) {
                // k번째 자리에 숫자 채워주기
                selected[k] = cand;

                // k+1번째 자리수를 1~N까지 채워주는 함수
                rec_func(k + 1);
                // 백트래킹을 위해 초기화
                selected[k] = 0;
            }
        }
    }

    public static void main(String[] args) {
        input();

        // 1 번째 원소부터 M 번째 원소를 조건에 맞는 모든 방법을 찾아줘
        rec_func(1);
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

### [N과 M(1)](https://www.acmicpc.net/problem/15649)
- 문제 : 자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
- 유형 : N개중 중복없이 / M개를 순서있게 나열하기
- 시간복잡도 : O(N!/(N-M)!)
- 공간복잡도 : M
- 중복없이 해결 : used배열 만들어서 각 숫자를 사용했으면 1로 표기
```java
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();

    static void input() {
        FastReader scan = new FastReader();
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M + 1];
        used = new int[N + 1];
    }

    static int N, M;
    static int[] selected, used;
    // Recurrence Function (재귀 함수)
    // 만약 M 개를 전부 고름   => 조건에 맞는 탐색을 한 가지 성공한 것!
    // 아직 M 개를 고르지 않음 => k 번째부터 M번째 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    static void rec_func(int k) {
        if (k == M + 1) { // 1 ~ M 번째를 전부 다 골랐다!
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            for (int cand = 1; cand <= N; cand++) {
                // 이미 쓰인 숫자라면 넘어가기
                if (used[cand] == 1) continue;
                // k 번째에 cand 가 올 수 있으면
                selected[k] = cand;    used[cand] = 1;

                rec_func(k + 1);

                selected[k] = 0;       used[cand] = 0;
            }
        }
    }

    public static void main(String[] args) {
        input();

        // 1 번째 원소부터 M 번째 원소를 조건에 맞는 모든 방법을 찾아줘
        rec_func(1);
        System.out.println(sb.toString());

    }
    
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            BufferedReader br = new BufferedReader(new FileReader(s));
        }

        public void ensureTokenizer() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }

        public String next() {
            ensureTokenizer();
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            st = null;
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
} 
```

### [N과 M(4)](https://www.acmicpc.net/problem/15649)
- 문제 : 자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
- 유형 : N개중 중복가능 / M은 비내림차순
- 시간복잡도 : O(N의M승)
- 공간복잡도 : M
- 비내림차순 : 이전숫자인 같거나 start보다 큰 숫자로 범위제한 
  - for (int cand = start; cand <= N; cand++) 
```java
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();

    static void input() {
        FastReader scan = new FastReader();
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M + 1];
    }

    static int N, M;
    static int[] selected;
    // Recurrence Function (재귀 함수)
    // 만약 M 개를 전부 고름   => 조건에 맞는 탐색을 한 가지 성공한 것!
    // 아직 M 개를 고르지 않음 => k 번째부터 M번째 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    static void rec_func(int k) {
        if (k == M + 1) { // 1 ~ M 번째를 전부 다 골랐다!
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            int start = selected[k-1];
            if (start == 0) start = 1;
            // 비내림차순을 위해 이전수보다 큰 숫자로만 계산
            for (int cand = start; cand <= N; cand++) {
                // k 번째에 cand 가 올 수 있으면
                selected[k] = cand;
                rec_func(k + 1);
                selected[k] = 0;
            }
        }
    }

    public static void main(String[] args) {
        input();

        // 1 번째 원소부터 M 번째 원소를 조건에 맞는 모든 방법을 찾아줘
        rec_func(1);
        System.out.println(sb.toString());

    }


    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            BufferedReader br = new BufferedReader(new FileReader(s));
        }

        public void ensureTokenizer() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }

        public String next() {
            ensureTokenizer();
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            st = null;
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
} 
```

### N과 M(2)
- 문제 : 자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
- 유형 : N개중 중복없이 / M은 오름차순
- 시간복잡도 : O(N의M승)
- 공간복잡도 : M
- 비내림차순 : 이전숫자인 start보다 큰 숫자로 범위제한
    - for (int cand = selected[k-1] + 1; cand <= N; cand++)
```java
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();

    static void input() {
        FastReader scan = new FastReader();
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M + 1];
    }

    static int N, M;
    static int[] selected;

    // Recurrence Function (재귀 함수)
    // 만약 M 개를 전부 고름   => 조건에 맞는 탐색을 한 가지 성공한 것!
    // 아직 M 개를 고르지 않음 => k 번째부터 M번째 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    static void rec_func(int k) {
        if (k == M + 1) {
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            for (int cand = selected[k - 1] + 1; cand <= N; cand++) {
                // k 번째에 cand 가 올 수 있으면
                selected[k] = cand;
                rec_func(k + 1);
                selected[k] = 0;
            }
        }
    }

    public static void main(String[] args) {
        input();

        // 1 번째 원소부터 M 번째 원소를 조건에 맞는 모든 방법을 찾아줘
        rec_func(1);
        System.out.println(sb.toString());

    }


        static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            BufferedReader br = new BufferedReader(new FileReader(s));
        }

        public void ensureTokenizer() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }

        public String next() {
            ensureTokenizer();
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            st = null;
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}
```