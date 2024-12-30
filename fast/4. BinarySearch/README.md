## BinarySearch
- 정렬되어있는 배열에서 X를 찾기 위해 범위를 이분하면서 탐색하는 방법
- 시간복잡도 : log(N)

### [먹을 것인가 먹힐 것인가](https://www.acmicpc.net/problem/7795)
- B에서 A[i]이하 원소들 찾기(이분탐색)
- B배열 정렬 : O(MlogN)
- 모든 A원소마다 B배열에 대한 이분탐색 : O(NlogM)
- 총 시간복잡도 O(N+M log M)
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, M;
    static int[] A, B;

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        A = new int[N + 1];
        B = new int[M + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = scan.nextInt();
        }
        for (int i = 1; i <= M; i++) {
            B[i] = scan.nextInt();
        }
    }

    static int lower_bound(int[] A, int L, int R, int X) {
        // A[L...R] 에서 X 미만의 수 중 제일 오른쪽 인덱스를 return 하는 함수
        // A배열은 정렬되어있기 때문에 제일 오른쪽 인덱스 왼쪽부분들은 다 답에 포함됨
        // 그런 게 없다면 L - 1 을 return 한다

        int res = L - 1;  // 만약 A[L...R] 중 X 이하의 수가 없다면 L - 1 을 return 한다.
        while (L <= R) {
            int mid = (L + R) / 2;
            if (A[mid] < X) {
                res = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return res;
    }

    static void pro() {
        // B 배열 정렬
        Arrays.sort(B, 1, M + 1); // i를 1부터 M+1까지 넣어서 이런 함수가 됨
        
        int ans = 0;
        for (int i = 1; i <= N; i++) {
            // A[i] 를 선택했을 때, B 에서는 A[i]보다 작은 게 몇 개나 있는 지 count하기
            ans += lower_bound(B, 1, M, A[i]);
        }
        System.out.println(ans);
    }

    public static void main(String[] args) {
        int TT;
        TT = scan.nextInt(); // 총 테스트케이스
        for (int tt = 1; tt <= TT; tt++) {
            input();
            pro();
        }
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

### [두 용액](https://www.acmicpc.net/problem/2470)
- 시간 : O(NlogN)
- 공간 : O(N)
- 풀이법
  - 배열 정렬
  - A[left]를 골랐을때 -A[left]와 가까워야함
  - Result : A[left+1...N]에서 X=-A[left]이상의 원소중 가장 왼쪽 위치 / 없다면 N+1
  - A[Result-1]과 A[Result]중 X랑 가장 가까운 원소가 있음
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[] A;

    static void input() {
        N = scan.nextInt();
        A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = scan.nextInt();
        }
    }

    static int lower_bound(int[] A, int L, int R, int X) {
        // A[L...R] 에서 X 이상의 수 중 제일 왼쪽 인덱스를 return 하는 함수
        // 그런 게 없다면 R + 1 을 return 한다

        int res = R + 1;  // 만약 A[L...R] 중
        while (L <= R) {
            int mid = (L + R) / 2;
            if (A[mid] >= X) {
                res = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return res;
    }

    static void pro() {
        // A 에 대해 이분 탐색을 할 예정이니까, 정렬을 미리 해주자.
        Arrays.sort(A, 1, N + 1);

        int best_sum = Integer.MAX_VALUE;
        int v1 = 0, v2 = 0;
        // N-1까지인 이유는 마지막 Right가 N이여야해서
        for (int left = 1; left <= N - 1; left++) {
            // A[left] 용액을 쓸 것이다. 고로 -A[left] 와 가장 가까운 용액을 자신의 오른쪽 구간에서 찾자.
            int candidate = lower_bound(A, left + 1, N, -A[left]);

            // A[candidate - 1] 와 A[candidate] 중에 A[left] 와 섞었을 때의 정보를 정답에 갱신시킨다.

            // 1. A[left] + A[candidate - 1]
            if (left < candidate - 1 && Math.abs(A[left] + A[candidate - 1]) < best_sum) {
                best_sum = Math.abs(A[left] + A[candidate - 1]);
                v1 = A[left];
                v2 = A[candidate - 1];
            }

            // 2. A[left] + A[candidate]
            if (candidate <= N && Math.abs(A[left] + A[candidate]) < best_sum) {
                best_sum = Math.abs(A[left] + A[candidate]);
                v1 = A[left];
                v2 = A[candidate];
            }
            
        }
        sb.append(v1).append(' ').append(v2);
        System.out.println(sb);
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
