## ParametricSearch(매개변수 탐색)
- 배열이 0,1 만 존재하며 오름차순이지만, 전체 배열은 모른다
- 특정 인덱스 값을 O(T)에 계산 가능할때, 0과 1의 경계를 찾는것
- 핵심
  - 정답을 매개변수로 만들고 Yes/No 문제로 바꿔보기
  - 모든 값에 대해 YES/NO를 채웠다고 했을때, 정렬된 상태인가
  - YES/NO 결정하는 문제 풀기
  - 키워드 : ~~의 최댓값을 구하시오 / ~~의 최솟값을 구하시오

### [나무 자르기](https://www.acmicpc.net/problem/2805)
- 원하는 길이 M 만큼을 얻을 수 있는 최대 높이는 얼마인가
- 거꾸로하면 : 어떤 높이(H)로 잘랐을때 원하는 길이 M만큼을 얻을 수 있는가(YES/NO)
- 매개변수 : H
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, M;
    static int[] A;

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = scan.nextInt();
        }
    }

    static boolean determination(int H) {
        // H 높이로 나무들을 잘랐을 때, M 만큼을 얻을 수 있으면 true, 없으면 false를 return하는 함수
        long sum = 0;
        for (int i = 1; i <= N; i++) {
            if (A[i] > H) {
                sum += A[i] - H;
            }
        }
        return sum >= M;
    }

    static void pro() {
        long L = 0, R = 2000000000, ans = 0;
        // [L ... R] 범위 안에 정답이 존재한다!
        // 이분 탐색과 determination 문제를 이용해서 answer를 빠르게 구하자!
        while (L <= R) {
            int mid = (int) ((L + R) / 2);
            if (determination(mid)) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        System.out.println(ans);
    }

    public static void main(String[] args) {
        input();
        pro();
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

### [공유기 설치](https://www.acmicpc.net/problem/2110)
- C개의 공유기를 설치할떄, 최대인접거리(D)는 얼마인가
- 거꾸로 : 어떤 거리(D)만큼 거리둘때 공유기 C를 설치할 수 있는가(YES/NO)
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, C;
    static int[] A;

    static void input() {
        N = scan.nextInt();
        C = scan.nextInt();
        A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = scan.nextInt();
        }
    }

    static boolean determination(int D) {
        // D 만큼의 거리 차이를 둔다면 C 개 만큼의 공유기를 설치할 수 있는가?

        // 제일 왼쪽 집부터 가능한 많이 설치해보자!
        // D 만큼의 거리를 두면서 최대로 설치한 개수와 C 를 비교하자.
        int cnt = 1, last = A[1];
        for (int i = 2; i <= N; i++) {
            if (A[i] - last < D) continue;
            last = A[i];
            cnt++;
        }
        return cnt >= C;
    }

    static void pro() {
        // determination을 빠르게 하기 위해서 정렬해주자.
        Arrays.sort(A, 1, N + 1);

        int L = 1, R = 1000000000, ans = 0;
        // [L ... R] 범위 안에 정답이 존재한다!
        // 이분 탐색과 determination 문제를 이용해서 answer를 빠르게 구하자!
        while (L <= R) {
            int mid = (L + R) / 2;
            if (determination(mid)) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        System.out.println(ans);
    }

    public static void main(String[] args) {
        input();
        pro();
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
