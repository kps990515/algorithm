## Dynamic Programming
- 동적 프로그래밍
- 문제의 크기를 변화하면서 정답을 계산하면서
- 작은 문제의 결과를 이용해서 큰 문제의 정답을 빠르게 계산하는 알고리즘

### 시나리오
- 문제가 원하는 정답을 찾기 위한 완전탐색을 시도
- 완전탐색 경우가 지나치게 많음을 확인
- 이럴때, 모든 경우를 빠르게 탐색하는 방법으로 Dynamic Programming 접근 시도 가능 

### 풀이법
1. 풀고 싶은 가짜 문제 정의
   - Dy[i]:= 1~i번 원소에 대해 조건을 만족하는 경우의 수
   - Dy[i][j]:= i~j번 원소에 대해서 조건을 만족하는 최댓값
   - Dy[i][j]:= 수열 A[1...i]와 수열 B[1...j]에 대해 무언가를 계산한 값
2. 가짜 문제를 풀면 진짜 문제를 풀수 있는가?
   - 진짜 문제 : 수열 A[1...N]에서 조건을 만족하는 부분 수열의 개수
   - 가짜 문제 : Dy[i]:= 수열 A[1...i]에서 조건을 만족하는 부분 수열의 개수
   - 가짜문제를 푼다면 진짜문제를 풀 수 있음으로 통과
3. 초기값은 어떻게 되는가 : 가장 작은 문제 해결하기 
4. 점화식 구해내기 : 3번 계산을 기반으로 점점 더 큰 문제를 해결하며 Dy배열을 계산하는 과정
5. 진짜 문제 정답 출력하기

### [1, 2, 3 더하기](https://www.acmicpc.net/problem/9095)
1. 풀고 싶은 가짜 문제 정의
    - Hint : 진짜문제 써보기
    - 진짜문제 := 주어진 N에 대해서 N을 1,2,3의 합으로 표현하는 경우의 수
    - 가짜문제 := Dy[i] = i를 1,2,3의 합으로 표현하는 경우의 수
2. 가짜 문제로 진짜 문제를 풀 수 있는가 : yes
3. 초기값은 어떻게 되는가 : 쪼개지않아도 풀수있는 작은문제에 대한 정답
4. 점화식 구해내기 (마지막 더해진 부분 보는게 빠름)
   - Dy[i] 계산에 필요한 탐색 경우를 공통점끼리 묶어내기(마지막에 더해진 숫자로 Grouping)
   - 묶어낸 부분의 정답을 Dy배열을 이용해 빠르게 계산하기
      - 마지막에 1을 더하는 경우 = 합이 i-1인 경우의 수 = Dy[i-1]
      - 마지막에 2을 더하는 경우 = 합이 i-2인 경우의 수 = Dy[i-2]
      - 마지막에 3을 더하는 경우 = 합이 i-3인 경우의 수 = Dy[i-3]
   - 결국 Dy[i] = Dy[i - 1] + Dy[i - 2] + Dy[i - 3];
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int[] Dy;
    
    static void preprocess(){
        Dy = new int[15];
        // 초기값 구하기
        Dy[1] = 1;
        Dy[2] = 2;
        Dy[3] = 4;

        // 점화식을 토대로 Dy 배열 채우기
        for (int i = 4; i <= 11; i++){
            Dy[i] = Dy[i - 1] + Dy[i - 2] + Dy[i - 3];
        }
    }

    static void pro() {
        int T = scan.nextInt();
        for (int tt = 1; tt <= T; tt++){
            int N = scan.nextInt();
            sb.append(Dy[N]).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
        preprocess();
        pro();
    }


    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(new File(s)));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
```

### [2×n 타일링](https://www.acmicpc.net/problem/11726)
1. 풀고 싶은 가짜 문제 정의
  - Hint : 진짜문제 써보기
  - 진짜문제 := 주어진 N에 대해서 2*N 타일링 경우의 수
  - 가짜문제 := Dy[i] = 2*i 타일링 경우의 수
2. 가짜 문제로 진짜 문제를 풀 수 있는가 : yes
3. 초기값은 어떻게 되는가 : 쪼개지않아도 풀수있는 작은문제에 대한 정답
4. 점화식 구해내기(마지막 더해진 부분 보는게 빠름)
  - Dy[i] 계산에 필요한 탐색 경우를 공통점끼리 묶어내기(마지막에 더해진 타일로 Grouping)
  - 묶어낸 부분의 정답을 Dy배열을 이용해 빠르게 계산하기
    - 마지막에 세로타일을 더하는 경우 = 합이 i-1인 경우의 수 = Dy[i-1]
    - 마지막에 가로타일 2개를 더하는 경우 = 합이 i-2인 경우의 수 = Dy[i-2]
  - 결국 Dy[i] = Dy[i - 1] + Dy[i - 2]
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[] Dy;
    
    static void input(){
        N = scan.nextInt();
    }

    static void pro() {
        Dy = new int[1005];
        // 초기값 구하기
        Dy[1] = 1;
        Dy[2] = 2;

        // 점화식을 토대로 Dy 배열 채우기
        for (int i = 3; i <= N; i++){
            Dy[i] = (Dy[i - 1] + Dy[i - 2]) % 10007;
        }
        System.out.println(Dy[N]);
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
            br = new BufferedReader(new FileReader(new File(s)));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
```