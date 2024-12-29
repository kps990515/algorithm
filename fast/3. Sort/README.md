## Sort
- compareTo : 음수면 내가먼저 / 양수면 상대편이 먼저 (오름차순)
- primitive Sort : 최악 O(N의 2승), In-place Sort
- Object Sort : O(NlogN), Stable Sort
- in-place한가 : 정렬하는 과정에서 무시할만한 개수의 메모리만큼만 사용하는가
- stable한가 : 정렬뒤에도 동등한 순서의 숫자들의 순서가 보장되는가
- 특성
  - 같은 정보는 인접해있다
  - 각 원소마다 가장 가까운 원소는 자신의 양 옆중에 있다

### [국영수](https://www.acmicpc.net/problem/10825)
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static class Elem implements Comparable<Elem> {

        public String name;
        public int korean, english, math;

        @Override
        public int compareTo(Elem other) {
            if (korean != other.korean) return other.korean - korean; // 내림차순
            if (english != other.english) return english - other.english; // 오름차순
            if (math != other.math) return other.math - math; // 내림차순
            return name.compareTo(other.name); // 사전순
        }
    }

    static int N;
    static Elem[] a;

    static void input() {
        N = scan.nextInt();
        a = new Elem[N];
        for (int i = 0; i < N; i++) {
            a[i] = new Elem();
            a[i].name = scan.next();
            a[i].korean = scan.nextInt();
            a[i].english = scan.nextInt();
            a[i].math = scan.nextInt();
        }
    }

    static void pro() {
        Arrays.sort(a);
        for (int i = 0; i < N; i++) {
            sb.append(a[i].name).append('\n');
        }
        System.out.println(sb.toString());
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

### [수열정렬](https://www.acmicpc.net/problem/1015)
- 시간 : O(NlogN)
- 공간 : O(N)
- 풀이법
  - 배열 정렬
  - P 배열 구하기
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static class Elem implements Comparable<Elem> {

        /**
         * @param idx A 배열의 idx 위치를 기억하는 변수
         * @param num A[idx]의 원래 값
         */
        public int num, idx;

        @Override
        // 1. num 의 비내림차순
        // 2. num이 같으면 idx 오름차순(필요없음 TimSort의 경우 Stable Sort로 idx 유지됨
        public int compareTo(Elem other) {
            return num - other.num;
        }
    }

    static int N;
    static int[] P;
    static Elem[] B;

    static void input() {
        N = scan.nextInt();
        B = new Elem[N];
        P = new int[N];
        for (int i = 0; i < N; i++) {
            B[i] = new Elem();
            B[i].num = scan.nextInt();
            B[i].idx = i;
        }
    }

    static void pro() {
        // 1. B배열 정렬하기
        Arrays.sort(B); 
        // 2. B배열의 값을 이용해서 P배열 채우기
        for (int i = 0; i < N; i++) {
            P[B[i].idx] = i;
        }
        // 3. P배열 출력하기
        for (int i = 0; i < N; i++) {
            sb.append(P[i]).append(' ');
        }
        System.out.println(sb.toString());
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

### [카드](https://www.acmicpc.net/problem/11652)
- 유형 : N 중복가능 / M은 순서있음
- 입출력 : -2의 62승 ~ 2의 62승 -> long 사용해야함
- 가장쉬운 풀이법 : HashMap 사용
- 정렬 풀이법 : 같은 숫자는 나의 인접한 곳에 있을 것이다
  - 시간복잡도 : O(NlongN), 공간복잡도: O(N)
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static long[] a;

    static void input() {
        N = scan.nextInt();
        a = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            a[i] = scan.nextLong();
        }
    }

    static void pro() {
        Arrays.sort(a, 1, N + 1);  // Sort
        // mode: 최빈값, modeCnt: 최빈값의 등장 횟수, curCnt: 현재 값(a[1])의 등장 횟수
        long mode = a[1];
        int modeCnt = 1;
        int curCnt = 1;  

        for (int i = 2; i <= N; i++) {
            if (a[i] == a[i - 1]) {  // a[i] 라는 숫자가 계속 등장하고 있다.
                curCnt++;
            } else {
                curCnt = 1;  // a[i] 라는 숫자가 새롭게 등장했다.
            }

            // 현재 숫자의 등장횟수가 이전 최빈값을 넘었다면 갱신
            if (curCnt > modeCnt) {
                modeCnt = curCnt;
                mode = a[i];
            }
        }
        System.out.println(mode);
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

### [화살표 그리기](https://www.acmicpc.net/problem/15970)
- 풀이법
  - 색깔마다 List에 넣어준다
  - List들을 정렬해준다
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static ArrayList<Integer>[] a;

    static void input() {
        N = scan.nextInt();
        a = new ArrayList[N + 1];
        // 색깔마다 ArrayList에 넣어주기
        for (int color = 1; color <= N; color++) {
            a[color] = new ArrayList<Integer>();
        }
        for (int i = 1; i <= N; i++) {
            int coord, color;
            coord = scan.nextInt(); // 점의 좌표
            color = scan.nextInt();
            a[color].add(coord);
        }
    }

    static int toLeft(int color, int idx) {
        if (idx == 0)  // 왼쪽에 더 이상 점이 없는 상태
            return Integer.MAX_VALUE;
        return a[color].get(idx) - a[color].get(idx - 1);
    }

    static int toRight(int color, int idx) {
        if (idx + 1 == a[color].size())  // 오른쪽에 더 이상 점이 없는 상태
            return Integer.MAX_VALUE;
        return a[color].get(idx + 1) - a[color].get(idx);
    }

    static void pro() {
        // 색깔List마다 정렬
        for (int color = 1; color <= N; color++)
            Collections.sort(a[color]);

        int ans = 0;
        // 색깔별, 점별로 가장 가까운 점 찾기
        for (int color = 1; color <= N; color++) { // 색깔별
            for (int i = 0; i < a[color].size(); i++) { // 점별
                int left_distance = toLeft(color, i);
                int right_distance = toRight(color, i);
                ans += Math.min(left_distance, right_distance);
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