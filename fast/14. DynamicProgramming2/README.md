### [계단 오르기](https://www.acmicpc.net/problem/2579)
- 실패
1. 풀고 싶은 가짜 문제 정의
   - Hint : 진짜문제 써보기
   - 진짜문제 := N번째 계단에 도착하며 얻는 최대 점수
   - 가짜문제 := Dy[i] = i번째 계단에 도착하며 얻는 최대 점수
2. 가짜 문제로 진짜 문제를 풀 수 있는가 : yes
3. 초기값은 어떻게 되는가 : 쪼개지않아도 풀수있는 작은문제에 대한 정답
4. 점화식 구해내기(마지막 더해진 부분 보는게 빠름)
   - Dy[i] 계산에 필요한 탐색 경우를 공통점끼리 묶어내기(마지막에 몇칸을 올랐는지)
   - 묶어낸 부분의 정답을 Dy배열을 이용해 빠르게 계산하기
       - 마지막에 1칸 올라가는 경우 = Dy[i] = Dy[i-1] + A[i]
       - 마지막에 2칸 올라가는 경우 = Dy[i] = Dy[i-2] + A[i]
   - **3번연속 계단을 오르면 안된다고 했기에 i-1번째 계단 직전에 i-2번째 계단도 밟았는지에 대한 정보 부족**

- 다시 시도
1. 풀고 싶은 가짜 문제 정의 + 모자란 정보를 문제에 추가 
    - 진짜문제 := N번째 계단에 도착하며 얻는 최대 점수
    - 가짜문제 
      - Dy[i][0] = i-1번째 계단을 밟지 않고, i번째 계단 도착하며 얻는 최대점수
      - Dy[i][1] = i-1번째 계단을 밟고, i번째 계단 도착하며 얻는 최대점수
2. 가짜 문제로 진짜 문제를 풀 수 있는가 : max(Dy[N][0], Dy[N][1])
3. 초기값은 어떻게 되는가 : 쪼개지않아도 풀수있는 작은문제에 대한 정답
4. 점화식 구해내기(마지막 더해진 부분 보는게 빠름)
    - Dy[i] 계산에 필요한 탐색 경우를 공통점끼리 묶어내기
      - Dy[i][0]의 경우 i-2만 밟은 경우 / i-2,i-3 두 계단 모두 밟은 경우 
      - Dy[i][1]의 경우 i-1, i-3만 밟고 올 수 있음
     - 묶어낸 부분의 정답을 Dy배열을 이용해 빠르게 계산하기
        - Dy[i][0] = Max((Dy[i-2][0] + A[i]),(Dy[i-2][1] + A[i])) 
        - Dy[i][1] = Dy[i-1][0] + A[i]
5. 심화(백트래킹: 뭘 밟고 왔는지)
   - Dy[i][0] -> Come[i][0] = (i-2,0) or (i-2,1)
   - Dy[i][1] -> Come[i][1] = (i-1,0)
```java
import java.io.*;
import java.util.*;
import java.lang.Math;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[][] Dy;
    static int[] A;
    
    static void input(){
        N = scan.nextInt();
        A = new int[N + 1];
        Dy = new int[N + 1][2];
        for (int i = 1; i <= N; i++){
            A[i] = scan.nextInt();
        }
    }

    static void pro() {
        // 초기값 구하기
        Dy[1][0] = 0;
        Dy[1][1] = A[1];
        
        if (N >= 2){
            Dy[2][0] = A[2];
            Dy[2][1] = A[1] + A[2];
        }

        // 점화식을 토대로 Dy 배열 채우기
        for (int i = 3; i <= N; i++){
            Dy[i][0] = Math.max(Dy[i - 2][0] + A[i], Dy[i - 2][1] + A[i]);
            Dy[i][1] = Dy[i - 1][0] + A[i];
        }
        System.out.println(Math.max(Dy[N][0], Dy[N][1]));
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

### [오르막 수](https://www.acmicpc.net/problem/11057)
- 실패
1. 풀고 싶은 가짜 문제 정의
    - Hint : 진짜문제 써보기
    - 진짜문제 := 길이가 N인 오르막 수의 개수
    - 가짜문제 := Dy[i] = 길이가 i인 오르막 수의 개수
    - **길이가 i이며 last로 끝나는 오르막 수의 개수에 대한 정보 부족**

- 다시 시도
1. 풀고 싶은 가짜 문제 정의 + 모자란 정보를 문제에 추가
    - 진짜문제 := 길이가 N인 오르막 수의 개수
    - 가짜문제 := Dy[i][last] = 길이가 i이며 last로 끝나는 오르막 수의 개수
2. 가짜 문제로 진짜 문제를 풀 수 있는가 : max(Dy[N][0], Dy[N][1])
3. 초기값은 어떻게 되는가 : 쪼개지않아도 풀수있는 작은문제에 대한 정답
4. 점화식 구해내기(마지막 더해진 부분 보는게 빠름)
    - Dy[i] 계산에 필요한 탐색 경우를 공통점끼리 묶어내기
        - Dy[i][last-1]의 숫자가 무엇인지
    - 묶어낸 부분의 정답을 Dy배열을 이용해 빠르게 계산하기
        - Dy[i][j] = Dy[i-1][0]+....Dy[i-1][j]

```java
import java.io.*;
import java.util.*;
import java.lang.Math;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[][] Dy;
    static int[] A;

    static void input() {
        N = scan.nextInt();
        A = new int[N + 1];
        Dy = new int[N + 1][10];
    }

    static void pro() {
        // 초기값 구하기
        for (int num = 0; num <= 9; num++) {
            Dy[1][num] = 1;
        }

        // 점화식을 토대로 Dy 배열 채우기
        for (int len = 2; len <= N; len++) {
            for (int num = 0; num <= 9; num++) {
                // 길이가 len이고 num으로 끝나는 개수를 계산하자 == Dy[len][num] 을 계산하자.
                for (int prev = 0; prev <= num; prev++) {
                    Dy[len][num] += Dy[len - 1][prev];
                    Dy[len][num] %= 10007;
                }
            }
        }

        // Dy배열로 정답 계산하기
        int ans = 0;
        for (int num = 0; num <= 9; num++) {
            ans += Dy[N][num];
            ans %= 10007;
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