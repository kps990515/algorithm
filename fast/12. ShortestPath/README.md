## 최단거리
- BFS : 간선의 가중치 1 / 시간복잡도 : O(V+E)
- Dijkstra : 간선의 가중치 >=0 / 시간복잡도 : O(ELogV)

## Dijkstra
- input 
  - 그래프(V,E) + 0이상의 가중치 그래프
  - 시작정점 필요함
- output : 시작점에서 모든점까지의 최단 거리
- 시간복잡도 : O(ELogV)
- 필요한 정보
  - dist[i] : 시작점에서 i번 정점까지의 최단 거리
  - PriorityQueue(v,d) : 시작점에서 v까지 d만에 갈 수 있음을 확인
- 순서도
  - dist배열 초기화 / D에 (S,0)을 추가
  - PriorityQueue에서 가장 작은 d를 갖는 자료 (v,d)를 뽑는다
  - (중요)dist[v] < d라면 필요없는 정보이므로 D에서 d를 제거
  - 새로찾은 정점까지의 거리를 통해 기존 정점의 dist를 변경가능
    - dist[w] : 알고있음(c)
    - dist[v] : 새로알게됨(d)
    - v,w는 연결되어있을때
    - d+c < dist[w] 이면 dist[w] = d+c로 변경해줌 & PriorityQueue에서 (w, d+c)로 변경해줌

### [최소비용 구하기](https://www.acmicpc.net/problem/1916)
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static class Edge {
        public int to, weight;

        public Edge(int _to, int _weight) {
            this.to = _to;
            this.weight = _weight;
        }
    }

    static class Info {
        public int idx, dist;

        public Info() {
        }

        public Info(int _idx, int _dist) {
            this.idx = _idx;
            this.dist = _dist;
        }
    }

    static int N, M, start, end;
    static int[] dist;
    static ArrayList<Edge>[] edges;

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        dist = new int[N + 1];
        edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) edges[i] = new ArrayList<Edge>();
        for (int i = 1; i <= M; i++) {
            int from = scan.nextInt();
            int to = scan.nextInt();
            int weight = scan.nextInt();
            edges[from].add(new Edge(to, weight));
        }
        start = scan.nextInt();
        end = scan.nextInt();
    }

    static void dijkstra(int start) {
        // 1. 모든 정점까지에 대한 거리를 무한대로 초기화 해주기.
        // ※주의사항※
        // 문제의 정답으로 가능한 거리의 최댓값보다 큰 값임을 보장해야 한다.
        for (int i = 1; i <= N; i++) dist[i] = Integer.MAX_VALUE;

        // 2. 최소 힙 생성
        PriorityQueue<Info> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.dist));
        // 다른 방법) PriorityQueue<Info> pq = new PriorityQueue<>((o1, o2) -> o1.dist - o2.dist);

        // 3. 시작점에 대한 정보(Information)을 기록에 추가하고, 거리 배열(dist)에 갱신해준다.
        pq.add(new Info(start, 0));
        dist[start] = 0;

        // 4. 거리 정보들이 모두 소진될 때까지 거리 갱신을 반복한다.
        while (!pq.isEmpty()) {
            Info info = pq.poll();
            
            // 5. 꺼낸 정보가 최신 정보랑 다르면, 의미없이 낡은 정보이므로 폐기한다.
            if (dist[info.idx] != info.dist) continue;

            // 6. 연결된 모든 간선들을 통해서 다른 정점들에 대한 정보를 갱신해준다.
            for (Edge e : edges[info.idx]) {
                if (dist[info.idx] + e.weight >= dist[e.to]) continue;

                // e.to 까지 갈 수 있는 더 짧은 거리를 찾았다면 이에 대한 정보를 갱신하고 PQ에 기록해준다.
                dist[e.to] = dist[info.idx] + e.weight;
                pq.add(new Info(e.to, dist[e.to]));
            }
        }
    }

    static void pro() {
        dijkstra(start);
        System.out.print(dist[end]);
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

### [최단경로](https://www.acmicpc.net/problem/1753)
```java
import java.io.*;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static class Edge {
        public int to, weight;

        public Edge(int _to, int _weight) {
            this.to = _to;
            this.weight = _weight;
        }
    }

    static class Info {
        public int idx, dist;

        public Info() {
        }

        public Info(int _idx, int _dist) {
            this.idx = _idx;
            this.dist = _dist;
        }
    }

    static int N, M, K;
    static int[] dist;
    static ArrayList<Edge>[] edges;

    static void input() {
        N = scan.nextInt();
        M = scan.nextInt();
        K = scan.nextInt();
        dist = new int[N + 1];
        edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) edges[i] = new ArrayList<Edge>();
        for (int i = 1; i <= M; i++) {
            int from = scan.nextInt();
            int to = scan.nextInt();
            int weight = scan.nextInt();
            edges[from].add(new Edge(to, weight));
        }
    }

    static void dijkstra(int start) {
        // 모든 정점까지에 대한 거리를 무한대로 초기화 해주기.
        // ※주의사항※
        // 문제의 정답으로 가능한 거리의 최댓값보다 큰 값임을 보장해야 한다.
        for (int i = 1; i <= N; i++) dist[i] = Integer.MAX_VALUE;

        // 최소 힙 생성
        PriorityQueue<Info> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.dist));
        // 다른 방법) PriorityQueue<Info> pq = new PriorityQueue<>((o1, o2) -> o1.dist - o2.dist);

        // 시작점에 대한 정보(Information)을 기록에 추가하고, 거리 배열(dist)에 갱신해준다.
        pq.add(new Info(start, 0));
        dist[start] = 0;

        // 거리 정보들이 모두 소진될 때까지 거리 갱신을 반복한다.
        while (!pq.isEmpty()) {
            Info info = pq.poll();
            
            // 꺼낸 정보가 최신 정보랑 다르면, 의미없이 낡은 정보이므로 폐기한다.
            if (dist[info.idx] != info.dist) continue;

            // 연결된 모든 간선들을 통해서 다른 정점들에 대한 정보를 갱신해준다.
            for (Edge e : edges[info.idx]) {
                if (dist[info.idx] + e.weight >= dist[e.to]) continue;

                // e.to 까지 갈 수 있는 더 짧은 거리를 찾았다면 이에 대한 정보를 갱신하고 PQ에 기록해준다.
                dist[e.to] = dist[info.idx] + e.weight;
                pq.add(new Info(e.to, dist[e.to]));
            }
        }
    }

    static void pro() {
        dijkstra(K);
        for (int i = 1; i <= N; i++) {
            if (dist[i] == Integer.MAX_VALUE) sb.append("INF\n");
            else sb.append(dist[i]).append('\n');
        }
        System.out.print(sb);
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