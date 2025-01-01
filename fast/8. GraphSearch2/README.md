### [단지번호붙이기](https://www.acmicpc.net/problem/2667)
- 격자형 그래프 구성
  - dir[4][2] = {(-1,0), (1,0), (0,-1), (0,1)}
- 단지를 찾을때 마다 해당 단지에 속해있던 집은 단지처리 필요
```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
  static FastReader scan = new FastReader();
  static StringBuilder sb = new StringBuilder();

  static int N, group_cnt;
  static String[] a;
  static boolean[][] visit;
  static int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}}; // 격자형 그래프
  static ArrayList<Integer> group;

  static void input() {
    N = scan.nextInt();
    a = new String[N];
    for (int i = 0; i < N; i++)
      a[i] = scan.nextLine();
    visit = new boolean[N][N];
  }

  // x, y 를 갈 수 있다는 걸 알고 방문한 상태
  static void dfs(int x, int y) {
    group_cnt++;
    visit[x][y] = true;
    for (int k = 0; k < 4; k++) { // 격자형 그래프 탐색
      int nx = x + dir[k][0]; 
      int ny = y + dir[k][1]; 
      if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;  // 지도를 벗어나면 패스
      if (a[nx].charAt(ny) == '0') continue;  // 집이 없으면 패스
      if (visit[nx][ny]) continue;  // 방문했으면 패스
      dfs(nx, ny);
    }
  }

  static void pro() {
    group = new ArrayList<>(); // 단지정보
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (!visit[i][j] && a[i].charAt(j) == '1') {
          // 갈 수 있는 칸인데, 이미 방문처리 된, 즉 새롭게 만난 단지인 경우!
          group_cnt = 0;
          dfs(i, j);
          group.add(group_cnt);
        }
      }
    }

    Collections.sort(group);
    sb.append(group.size()).append('\n');
    for (int cnt: group) sb.append(cnt).append('\n');
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

### [물통](https://www.acmicpc.net/problem/2251)
- 그래프탐색이라는걸 알기 쉽지 않아 어려움
- A,B,C물통의 물의 상태 : (a,b,c) 페어로 표현 가능
- 물을 붓는 행위 : (a,b,c) -> (a',b',c')
- (a,b,c) -> (a',b',c')를 정점과 간선(단방향)으로 추론 가능
```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

// 물통의 현재 상태와 물을 붓는 행위를 관리하는 구조체
class State{
    int[] X;
    State(int[] _X){
        X = new int[3];
        for (int i=0;i<3;i++) X[i] = _X[i];
    }
    
    State move(int from,int to,int[] Limit){
        // from 물통에서 to 물통으로 물을 옮긴다.
        int[] nX = new int[]{X[0], X[1], X[2]};
        if (X[from] + X[to] <= Limit[to]){  // 만약 from 을 전부 부을 수 있다면
            nX[to] = nX[from] + nX[to];
            nX[from] = 0;
        }else{  // from 의 일부만 옮길 수 있는 경우
            nX[from] -= Limit[to] - nX[to];
            nX[to] = Limit[to];
        }
        return new State(nX);
    }
}

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int[] Limit;
    static boolean[] possible;
    static boolean[][][] visit;

    static void input() {
        Limit = new int[3];
        for (int i=0;i<3;i++) Limit[i] = scan.nextInt();
        visit = new boolean[205][205][205];
        possible = new boolean[205];
    }

    // 물통 탐색 시작~
    static void bfs(int x1, int x2, int x3) {
        Queue<State> Q = new LinkedList<>();
        visit[x1][x2][x3] = true;
        Q.add(new State(new int[]{x1, x2, x3}));

        // BFS 과정 시작
        while (!Q.isEmpty()) {
            State st = Q.poll();
            // 답이 원하는 A의 물통이 비어있기에 C의 물양을 정답에 추가
            if (st.X[0] == 0) possible[st.X[2]] = true;
            for (int from = 0; from < 3; from++) {
                for (int to = 0; to < 3; to++) {
                    if (from == to) continue; // 같은 물통끼리는 물 옮기기 불가
                    // i 번 물통에서 j 번 물통으로 물을 옮긴다.
                    State nxt = st.move(from, to, Limit);

                    // 만약 바뀐 상태를 탐색한 적이 없다면
                    if (!visit[nxt.X[0]][nxt.X[1]][nxt.X[2]]) {
                        visit[nxt.X[0]][nxt.X[1]][nxt.X[2]] = true;
                        Q.add(nxt);
                    }
                }
            }
        }
    }

    static void pro() {
        bfs(0, 0, Limit[2]); // 첫 상태
        for (int i=0;i<=Limit[2];i++){
            if (possible[i]) sb.append(i).append(' ');
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

### [연구소](https://www.acmicpc.net/problem/14502)
- 시작점(바이러스)이 여러개인 탐색(MultiSource BFS)
  - 모든 시작점을 전부 Queue에 넣은 상태로 BFS 시작
  - Queue : {(1,1), (2,6), (5,2), (6,6)}
```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, M, B, ans;
    static int[][] A, blank;
    static boolean[][] visit;
    static int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        A = new int[N + 1][M + 1];
        blank = new int[N * M + 1][2];
        visit = new boolean[N + 1][M + 1];
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= M; j++)
                A[i][j] = scan.nextInt();
    }

    // 바이러스 퍼뜨리기!!
    static void bfs() {
        Queue<Integer> Q = new LinkedList<>();

        // 모든 바이러스가 시작점으로 가능하니까, 전부 큐에 넣어준다.
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                visit[i][j] = false;
                if (A[i][j] == 2) {
                    Q.add(i); // 행 값 넣기
                    Q.add(j); // 열 값 넣기
                    visit[i][j] = true;
                }
            }
        }

        // BFS 과정
        while (!Q.isEmpty()) {
            int x = Q.poll(), y = Q.poll(); //행, 열을 한번에 빼기
            // 격자배열 경로 확인
            for (int k = 0; k < 4; k++) {
                int nx = x + dir[k][0], ny = y + dir[k][1];
                if (nx < 1 || ny < 1 || nx > N || ny > M) continue;
                if (A[nx][ny] != 0) continue; // 확산할 수 없으면 패스
                if (visit[nx][ny]) continue; // 이미 퍼졌으면 패스
                visit[nx][ny] = true;
                Q.add(nx);
                Q.add(ny);
            }
        }

        // 탐색이 종료된 시점이니, 안전 영역의 넓이를 계산하고, 정답을 갱신한다.
        int cnt = 0;
        for (int i = 1; i <= N; i++){
            for (int j = 1; j <= M; j++){
                if (A[i][j] == 0 && !visit[i][j]) cnt++; // 안전영역이므로 cnt ++
            }
        }
        ans = Math.max(ans, cnt);
    }

    // 2. idx 번째 빈 칸에 벽을 세울 지 말 지 결정해야 하고, 이 전까지 selected_cnt 개의 벽을 세웠다.
    static void dfs(int idx, int selected_cnt) {
        if (selected_cnt == 3) {  // 3 개의 벽을 모두 세운 상태
            bfs(); // 바이러스 퍼트리면서 확인하기
            return;
        }
        if (idx > B) return;  // 더 이상 세울 수 있는 벽이 없는 상태

        A[blank[idx][0]][blank[idx][1]] = 1; // 해당위치에 벽 설치
        dfs(idx + 1, selected_cnt + 1); // 다음 벽 설치

        A[blank[idx][0]][blank[idx][1]] = 0; // 세웠던 벽 삭제
        dfs(idx + 1, selected_cnt); // 재귀호출을 통해 다시 벽 세우기
    }

    static void pro() {
        // 1. 벽을 설치할 수 있는 위치를 먼저 파악
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (A[i][j] == 0) {
                    B++; // 벽 개수 증가
                    blank[B][0] = i; // 벽의 행 번호
                    blank[B][1] = j; // 벽의 열 번호
                    // blank[1] = (i,j) -> 첫번째 벽의 위치 
                }
            }
        }

        // 벽을 3개 세우는 모든 방법을 확인해보자!
        dfs(1, 0);
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