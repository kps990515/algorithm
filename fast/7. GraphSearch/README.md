## 그래프란
- 정점(Vertex) + 간선(Edge)
- 정점
  - deg(x) : 정점 X의 차수(degree), 정점X에 연결된 간선의 수
  - 모든 정점의 차수의 합 = 간선개수 * 2
- 간선 : 방향성(단방향, 양방향) + 가중치

### 그래프를 저장하는 방법
- 인접행렬 : 행,열에 정점숫자를 넣고, 2차원 배열로 정점이 있으면 1, 없으면 0으로 채움
  - int[][] adj = int new[V][V]
  - 공간 : V제곱만큼 필요(공간필요도 비효율)
  - 이동가능 조회 : O(1)
  - 정점 X에서 갈수있는 정점 조회 : O(V)
- 인접리스트 : 2차원 List생성 -> 정점 List 생성 & List내부에 도달가능한 정점 List넣기
  - ArrayList<ArrayList<Integer>> adj;
  - 공간 : O(E) / E : 간선의 개수
  - A -> B이동 가능 조회 : O(min(deg(A), deg(B)))
  - A에서 갈수있는 정점 조회 : O(deg(A))

### 활용효율성
- A와 B를 있는 간선 존재 여부 조회 : 인접행렬
- A와 연결된 모든 정점 확인 : 인접리스트
- 공간복잡도 : 인접리스트

## 탐색
- 시작점에서 간선을 0개이상 사용해서 갈수 있는 정점들은 무엇인가

### 방법
- 깊이 우선 탐색(DFS) 
    - 활용 : 모든경로 탐색, 사이클 감지, 백트래킹 문제
    - X를 갈 수 있다는 걸 알고 방문한 상태
    - X에서 갈 수 있는 곳들(Y)을 모두 방문
    - 재귀호출을 통해 Y에서 갈 수 있는 곳들도 모두 방문
```java
// x를 갈 수 있다는 걸 알고 방문한 상태
static void dfs(int x){
    // x방문
    visit[x] = true;
    
    // x에서 갈수있는 곳들을 모두 방문
    for(int y : x에서 갈 수 있는 정점들){
        if(visit[y]) continue; // 이미 방문했으면 패스
        // 재귀를 통해 y에서 갈수있는 정점들도 확인
        dfs(y)
    }
}
main(){
    dfs(5);    
}
```

- 너비 우선 탐색(BFS) : 중요한점!! **큐에 넣을때 방문 true 표시 같이해주기**
  - 활용 : 최단경로찾기, 경로길이 구하기, 그래프 연결 확인
  - Queue 활용
    - 방문 가능한 정점이 있을때 큐에 넣어줌
    - Queue에 정점이 남았다 -> 아직 탐색중
    - Queue가 비어있다 -> 탐색 종료
  - start부터 시작해, start는 방문 가능한 점이므로 queue에 넣어준다
  - 큐에서 하나 꺼내서(X), X에서 갈 수 있는 모든 정점 방문 & 큐에 넣어주기
  - 큐가 빌때까지 계속 꺼내서 반복
```java
// start 에서 시작해서 각 노드를 방문하는 BFS 함수 정의
static void bfs(int start) {
    Queue<Integer> que = new LinkedList<>();
    // start 노드를 방문할 정점으로 queue에 넣어준다.
    que.add(start);
    visit[start] = true; // start를 방문 했다고 표시하기 (중요!!!)

    while (!que.isEmpty()) { // 더 방문할 정점이 없다면 종료
        int x = que.poll();

        for (int y : x 에서 갈 수 있는 노드들) {
            if (visit[y]) continue; // x 에서 y를 갈 수는 있지만, 이미 방문했다면 건너뛰기

            // y를 갈 수 있으면 queue에 추가하고, visit 체크 하기!
            que.add(y);
            visit[y] = true;
        }
    }
}
```

### [DFS와 BFS](https://www.acmicpc.net/problem/1260)
- 방문할 수 있는 정점이 여러개인 경우 작은것부터 방문
  - 인접행렬 : 차례로 보면됨
  - 인접리스트 : 내부 리스트 정렬이 필요함

- 인접행렬
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N, M, V;
    static int[][] adj;
    static boolean[] visit;

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        V = scan.nextInt();
        adj = new int[N + 1][N + 1];
        for (int i = 0; i < M; i++) {
            int x = scan.nextInt(), y = scan.nextInt();
            adj[x][y] = adj[y][x] = 1;
        }
    }

    // x 를 갈 수 있다는 걸 알고 방문한 상태
    static void dfs(int x) {
        // x 를 방문했다.
        visit[x] = true;
        sb.append(x).append(' ');

        // x 에서 갈 수 있는 곳들을 작은 번호부터 모두 방문한다.
        for (int y = 1; y <= N; y++) {
            if (adj[x][y] == 0) continue; // 방문할수없는곳 패스

            // y 를 이미 갈 수 있다는 사실을 안다면, 굳이 갈 필요 없다.
            if (visit[y])
                continue;

            // y에서 갈 수 있는 곳들도 확인 해보자
            dfs(y);
        }
    }

    // start 에서 시작해서 갈 수 있는 정점들을 모두 탐색하기
    static void bfs(int start) {
        Queue<Integer> que = new LinkedList<>();

        // start는 방문 가능한 점이므로 que에 넣어준다.
        que.add(start);
        visit[start] = true;  // start를 갈 수 있다고 표시하기 (중요!!!)

        while (!que.isEmpty()) {  // 더 확인할 점이 없다면 정지
            int x = que.poll();

            sb.append(x).append(' ');
            for (int y = 1; y <= N; y++) {
                if (adj[x][y] == 0) continue; // 갈수없는 y 무시
                if (visit[y]) continue;  // x 에서 y 를 갈 수는 있지만, 이미 탐색한 점이면 무시
                
                // y를 갈 수 있으니까 que에 추가하고, visit 처리 하기!
                que.add(y);
                visit[y] = true;
            }
        }
    }

    static void pro() {
        visit = new boolean[N + 1];
        dfs(V);
        sb.append('\n');
        for (int i = 1; i <= N; i++) visit[i] = false;
        bfs(V);
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