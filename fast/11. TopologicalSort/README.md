## 위상정렬
- 차수(degree) : 정점에 연결된 간선의 개수
- Indegree : 자기로 들어오는 간선
- OutDegree : 자기에서 나가는 간선
- 위상 : 정점들의 순서
  - 그래프의 각 정점이 방향성을 가진 간선에 의해 서로 연결되어 있을 때, 이 간선의 방향을 유지하면서 모든 정점을 나열하는 것
- 풀이법
  - 하나씩 정렬하기(Queue)
    - 제일 먼저 올 수 있는 정점은? -> 들어오는 간선이 없는 정점들 담기 & 나가는 간선 삭제
    - 나가는 간선이 삭제되면서 들어오는 간선이 없는 정점이 생김
    - 해당 정점들을 담아주고 반복
  - 코딩으로 나타내면
    - 정점들의 Indegree 계산
    - Indeg[i] == 0 정점들을 찾아서 자료구조 D에 넣기
    - D가 빌때까지
      - D에서 원소 X를 꺼내서 **정렬**시키기
      - Graph에서 정점 X **제거**하기
      - **새롭게 정렬 가능한 점**을 찾아서 D에 넣기
## DAG(Directed Acyclic Graph)
- 간선에 방향성이 있는 / Cyclic이 아닌 / 그래프

### [줄 세우기](https://www.acmicpc.net/problem/2252)
- Graph 정의해보기
  - 정점 : i번 학생
  - 간선 : x번 학생이 y번 학생보다 먼저 서야 한다(x -> y)
```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, M;
    static int[] indeg;
    static ArrayList<Integer>[] adj; // 인접리스트

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        adj = new ArrayList[N + 1];
        indeg = new int[N + 1];
        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int x = scan.nextInt(), y = scan.nextInt();
            adj[x].add(y);
            // x보다 y가 뒤에 있기에 indeg[y]++ 해주기
            indeg[y]++;
        }
    }

    static void pro() {
        Deque<Integer> queue = new LinkedList<>();
        // 제일 앞에 "정렬될 수 있는" 정점 찾기
        for (int i = 1; i <= N; i++)
            if (indeg[i] == 0)
                queue.add(i);
        
        // 자료구조가 빌때까지
        while (!queue.isEmpty()) {
            // Graph에서 x 제거
            int x = queue.poll();
            sb.append(x).append(' ');
            // x에서 나가는 간선 제거됨에 따라 연결된 정점의 간선개수 줄여주기
            for (int y : adj[x]) {
                indeg[y]--;
                // 정점담기 반복
                if (indeg[y] == 0) queue.add(y);
            }
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

### [ACM Craft](https://www.acmicpc.net/problem/1005)
- T_done[X] = X 건물이 완성되는 시간 = max(T_done[X의 선행작업]) + T[X]
- T_done[X] 계산을 위해서는 X의 선행작업들이 먼저 계산되어야함
- T_done 배열을 위상 정렬 순서로 계산
- TestCase가 여러개 존재 함으로 **배열 초기화**에 유의
```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, M;
    static int[] indeg, T_done, T;
    static ArrayList<Integer>[] adj;

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        adj = new ArrayList[N + 1];
        indeg = new int[N + 1];
        T = new int[N + 1];
        T_done = new int[N + 1]; 
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
            T[i] = scan.nextInt();
        }
        for (int i = 0; i < M; i++) {
            int x = scan.nextInt(), y = scan.nextInt();
            adj[x].add(y);
            // indegree 계산하기
            indeg[y]++;
        }
    }

    static void pro() {
        Deque<Integer> queue = new LinkedList<>();
        // 제일 앞에 "정렬될 수 있는" 정점 찾기
        for (int i = 1; i <= N; i++)
            if (indeg[i] == 0) {
                queue.add(i);
                T_done[i] = T[i];
            }

        // 위상 정렬 순서대로 T_done 계산을 함께 해주기
        while (!queue.isEmpty()) {
            int x = queue.poll();
            for (int y : adj[x]) {
                indeg[y]--;
                if (indeg[y] == 0) queue.add(y);
                // T_done[y] 계산을 위해서는 y의 선행작업들(x)이 먼저 계산되어야함
                T_done[y] = Math.max(T_done[y], T_done[x] + T[y]);
            }
        }
        int W = scan.nextInt();
        System.out.println(T_done[W]);
    }

    public static void main(String[] args) {
        int Q = scan.nextInt();
        while (Q > 0) {
            Q--;
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
