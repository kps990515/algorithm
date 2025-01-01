## TwoPointer
- 답을 구하기 위해 봐야할 부분과 / 보지않아도 되는 부분을 구분
- 화살표 두개에 의미를 부여해서 탐색범위를 압축하는 방법
- 유형
  1. 1차원 배열 위에 2개의 포인터를 만드는 경우
     1) 2개 포인터가 모둔 왼쪽에서 시작해서 같은 방향으로 이동
        - 키워드 : 1차원 배열에서의 **연속부분수열**, **순서를 지키며 차례대로**
     2) 2개 포인터가 양 끝에서 서로를 향해 이동
        - 키워드 : **곱의 최소**
  2. 관찰을 통해서 문제에 등장하는 변수2개의 값을 두 포인터로 표현하는 경우

### [부분합](https://www.acmicpc.net/problem/1806)
- 왼쪽 시작 L의 이동 최대횟수는 N번(1~N)
- 부분합을 만족하는 R을 찾기(L+1~N-1)
- L을 R쪽으로 한칸씩 이동해서(L~R) 합을 만족하는 최소 거리 구하기
```java
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader scan = new FastReader();

    static int n, S;
    static int[] a;

    static void input() {
        n = scan.nextInt();
        S = scan.nextInt();
        a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = scan.nextInt();
        }
    }

    static void pro() {
        int R = 0, sum = 0, ans = n + 1;
        for (int L = 1; L <= n; L++) {
            // L - 1 을 구간에서 제외하기
            sum -= a[L - 1];
            
            // R 을 옮길 수 있을 때 까지 옮기기
            while (R + 1 <= n && sum < S)
                sum += a[++R];
            
            // [L ... R] 의 합, 즉 sum이 조건을 만족하면 정답 갱신하기
            if (sum >= S)
                ans = Math.min(ans, R - L + 1);
        }

        // ans 값을 보고 불가능 판단하기
        if (ans == n + 1)
            ans = 0;
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

### [두용액](https://www.acmicpc.net/problem/2470)
- 정렬하기 : 최소, 최대값 구하기 위해
- L : "남아 있는 것들 중" 제일 작은 원소
- R : "남아 있는 것들 중" 제일 큰 원소
- L + R < 0 : 최소 입장에서 0에 가까워 지는 최선을 만난 상태 -> 짝을 찾음(최소 값 더 이상 고려X)
- L + R > 0 : 최대 입장에서 0에 가까워 지는 최선을 만난 상태 -> 짝을 찾음(최대 값 더 이상 고려X)
- L == R : 서로 다른 두 용액을 고를 수 없으므로 종료
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

    static void pro() {
        // 최소, 최대 원소를 빠르게 찾기 위해서 정렬을 미리 해주자.
        Arrays.sort(A, 1, N + 1);

        int best_sum = Integer.MAX_VALUE;
        // L은 제일 작은 원소, R은 제일 큰 원소
        int v1 = 0, v2 = 0, L = 1, R = N; 

        while (L < R){  // L == R 인 상황이면 용액이 한 개 뿐인 것이므로, L < R 일 때까지만 반복한다.
            if (best_sum > Math.abs(A[L] + A[R])) {
                best_sum = Math.abs(A[L] + A[R]);
                v1 = A[L];
                v2 = A[R];
            }
            // L+R > 0 이므로 최대 입장에서 L의 최선을 만난 상태 -> 이제 고려안함
            if (A[L] + A[R] > 0) R--;
            else L++;
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

### [List of Unique Numbers](https://www.acmicpc.net/problem/13144)
- L~R까지 중복된 수가 없다면, 그 사이의 숫자들에도 중복된 것 없으니까 볼 필요 없음 -> R-L 결과에 더해주기
- L을 R-1까지 증가시켜주기
- R을 하나 증가시켜주고 중복된 수가 없는지 파악, 중복되지 않았다면 위 반복
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[] A, cnt;

    static void input() throws IOException{
        N = scan.nextInt();
        A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = scan.nextInt();
        }
        // 숫자가 이미 나왔는지 체크하는 배열
        cnt = new int[100000 + 1];
    }

    static void pro() {
        long ans = 0;

        for (int L=1, R=1; L<=N; L++){  // L 마다 R 을 최대한 옮겨 줄 계획이다.
            // R 을 옮길 수 있는 만큼 옮긴다.
            while (R <= N
                    // 이미 L~R까지 나온 숫자가 아닌경우
                    && cnt[A[R]] == 0){
                cnt[A[R]]++;
                R++;
            }

            // 정답을 갱신한다
            ans += R - L ;

            // L 을 옮겨주면서 A[L] 의 개수를 감소시킨다.
            cnt[A[L]]--;
        }

        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
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

### [좋다](https://www.acmicpc.net/problem/1253)
- 정렬하기
- 타겟 수 결정
- 다른 수 2개를 더해서 타겟의 값이 나오는지 확인
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

    // 3. 다른 수 2개를 더해서 타겟의 값이 나오는지 확인
    static boolean func(int target_idx) {
        int L = 1, R = N;
        int target = A[target_idx];
        while (L < R) {
            if (L == target_idx) L++; // 타겟값이랑 중복되면 안됨
            else if (R == target_idx) R--; //타겟값이랑 중복되면 안됨
            else {
                if (A[L] + A[R] > target) R--; // 타겟값보다 크면 최대값 축소
                else if (A[L] + A[R] == target) return true;
                else L++; // 타겟값보다 작으면 최소값 확대
            }
        }
        return false;
    }

    static void pro() {
        // 1. 정렬하기
        Arrays.sort(A, 1, N + 1);

        int ans = 0;
        for (int i = 1; i <= N; i++) {
            // 2. 타겟값 구하기
            if (func(i)) ans++;
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

### [고냥이](https://www.acmicpc.net/problem/16472)
- cnt : 알파벳별로 사용된 횟수
- Kind : 사용된 알파벳 개수
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, kind;
    static String A;
    static int[] cnt;

    static void input() {
        N = scan.nextInt();
        A = scan.nextLine();
        cnt = new int[26];
    }

    static void add(char x) {  // x 라는 알파벳 추가
        cnt[x - 'a']++;
        if (cnt[x - 'a'] == 1)  // 새롭게 나타난 알파벳이라는 뜻
            kind++;
    }

    static void erase(char x) {  // x 라는 알파벳 제거
        cnt[x - 'a']--;
        if (cnt[x - 'a'] == 0)  // 인식해야 하는 알파벳에서 빠지는 순간
            kind--;
    }

    static void pro() {
        int len = A.length(), ans = 0;
        for (int R = 0, L = 0; R < len; R++) {
            // R 번째 문자를 오른쪽에 추가
            add(A.charAt(R));

            // 불가능하면, 가능할 때까지 L을 이동
            while (kind > N) {
                erase(A.charAt(L++));
            }

            // 정답 갱신
            ans = Math.max(ans, R - L + 1);
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