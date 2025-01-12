### [파일 합치기](https://www.acmicpc.net/problem/11066)
1. 풀고 싶은 가짜 문제 정의
   - 진짜문제 := N번째 까지의 파일을 합칠떄 필요한 최소비용
   - 가짜문제 
     - Dy[i][j] (i<=j) : i번~j번 파일을 하나로 합치는 최소 비용
     - Sum[i][j] := i번~j번 파일의 총 크기
2. 가짜 문제로 진짜 문제를 풀 수 있는가 : Dy[1][K]
3. 초기값은 어떻게 되는가 : 쪼개지않아도 풀수있는 작은문제에 대한 정답
4. 점화식 구해내기(마지막 더해진 부분 보는게 빠름)
   - Dy[i] 계산에 필요한 탐색 경우를 공통점끼리 묶어내기(마지막에 더한 파일들이 무엇인지)
   - Dy[i][j] = min{Dy[i][k] + Dy[k+1][j] + (i~j파일총량), i<=k<=j}
   - i~j파일 총량 = Sum[i][j] = Sum[i][j-1] + A[j]
   - Dy배열을 채워나가는 순서 : 짧은 구간부터 계산

```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
   static FastReader scan = new FastReader();
   static StringBuilder sb = new StringBuilder();

   static int K, Q;
   static int[] num;
   static int[][] Dy, sum;

   static void input(){
      K = scan.nextInt();
      num = new int[K + 1];
      sum = new int[K + 1][K + 1];
      for (int i = 1; i <= K; i++){
         num[i] = scan.nextInt();
      }
   }

   static void preprocess(){
      // i~j파일 총량 
      for (int i = 1; i <= K; i++){
         for (int j = i; j <= K; j++){
            sum[i][j] = sum[i][j - 1] + num[j];
         }
      }
   }

   static void pro() {
      preprocess();
      Dy = new int[K + 1][K + 1];
      // len = 짧은 구간 부터 계산  
      for (int len = 2; len <= K; len ++){
         for (int i = 1; i <= K - len + 1; i++){
            int j = i + len - 1;
            Dy[i][j] = Integer.MAX_VALUE;
            for (int k = i; k < j; k++){
               Dy[i][j] = Math.min(Dy[i][j], Dy[i][k] + Dy[k + 1][j] + sum[i][j]);
            }
         }
      }

      System.out.println(Dy[1][K]);
   }

   public static void main(String[] args) {
      Q = scan.nextInt();
      for (int rep = 1; rep<=Q;rep++) {
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

### [트리와 쿼리](https://www.acmicpc.net/problem/15681)
1. 풀고 싶은 가짜 문제 정의
   - Dy[i] = i를 root로 하는 subtree의 정점 개수
2. 가짜 문제로 진짜 문제를 풀 수 있는가 
3. 초기값은 어떻게 되는가 : leaf node가 가장 작은 문제
4. 점화식 구해내기(마지막 더해진 부분 보는게 빠름)
   - Dy[부모노드] = Dy[자식노드]들의 합 + 자기자신

```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
   static FastReader scan = new FastReader();
   static StringBuilder sb = new StringBuilder();

   static int N, R, Q;
   static ArrayList<Integer>[] con;
   static int[] Dy;

   static void input(){
      N = scan.nextInt();
      R = scan.nextInt();
      Q = scan.nextInt();
      con = new ArrayList[N + 1];
      for (int i = 1; i <= N; i++){
         con[i] = new ArrayList<>();
      }
      for (int i = 1; i < N; i++){
         int x = scan.nextInt(), y = scan.nextInt();
         con[x].add(y);
         con[y].add(x);
      }
   }

   // Dy[x] 를 계산하는 함수
   static void dfs(int x, int prev){
      Dy[x] = 1; // 자기자신 더해주기
      for (int y: con[x]){
         if (y == prev) continue;
         dfs(y, x);
         Dy[x] += Dy[y];
      }
   }

   static void pro() {
      Dy = new int[N + 1];

      dfs(R, -1);

      for (int i = 1; i <= Q; i++){
         int q = scan.nextInt();
         sb.append(Dy[q]).append('\n');
      }
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

### [우수 마을](https://www.acmicpc.net/problem/1949)
1. 풀고 싶은 가짜 문제 정의
   - 가짜문제
      - Dy[i][0] : i를 root로 하는 subtree에서 root를 **선택하지 않고** 가능한 최대 주민수
      - Dy[i][1] : i를 root로 하는 subtree에서 root를 **선택하고** 가능한 최대 주민수
      - 이렇게 나눈 이유 : subtree의 root를 선택해버리면 자식은 고를 수 없기 때문에 
2. 가짜 문제로 진짜 문제를 풀 수 있는가 : Max(Dy[1][0], Dy[1][1])
3. 초기값은 어떻게 되는가 : leaf node
4. 점화식 구해내기(마지막 더해진 부분 보는게 빠름)
   - Dy[Root][1] = Dy[child][0]들의 합 + num[Root]
   - Dy[Root][0] = MAX(Dy[child][0], Dy[child][1])들의 합

```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[] num;
    static ArrayList<Integer>[] con;
    static int[][] Dy;

    static void input(){
        N = scan.nextInt();
        num = new int[N + 1];
        con = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++){
            num[i] = scan.nextInt();
            con[i] = new ArrayList<>();
        }
        for (int i = 1; i < N; i++){
            int x = scan.nextInt(), y = scan.nextInt();
            con[x].add(y);
            con[y].add(x);
        }
    }

    static void dfs(int x, int prev){
        Dy[x][1] = num[x];
        for (int y: con[x]){
            if (y == prev) continue;
            dfs(y, x);
            Dy[x][0] += Math.max(Dy[y][0], Dy[y][1]);
            Dy[x][1] += Dy[y][0];
        }
    }

    static void pro() {
        Dy = new int[N + 1][2];

        dfs(1, -1);

        System.out.println(Math.max(Dy[1][0], Dy[1][1]));
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