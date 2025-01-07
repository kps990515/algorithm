## 트리
- Graph의 특수한 형태
- 그래프 중 다음의 특성중 2개 이상 만족
  - 모두가 연결되어 있는 그래프
  - 사이클이 존재하지 않음
  - 정점 개수 = 간선개수 + 1
- 저장하는 법 : 인접리스트(인접행렬은 공간복잡도가 너무 큼)
- 키워드
  - 모든 두 정점을 잇는 경로 존재 + 사이클이 존재하지 않을 때
  - 마을과 마을 사이를 잇는 N-1개의 길이 있으며, 모든 마을은 연결
- 풀이법
  - DFS를 주로 사용 
  - 정점, 간선에 대해 정확하게 정의
  - 트리의 요소와 문제의 요구사항 매칭

## Rooted Tree
- Node : 정점
- Root : 최상위 정점
- Depth : Root로부터의 거리
- Parent, Child, Ancestor(자신~루트 사이), Sibling(같은 부모를 가진 형제)
- Leaf Node : 맨 아래 노드

### [트리의 부모 찾기](https://www.acmicpc.net/problem/11725)
1. 인접리스트로 저장하기
2. Root말고는 아무것도 정답을 구하지 않은 상태로 시작
3. Root와 연결된 자식들을 구함
4. 차례로 자식들을 구함
```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int n;
    static ArrayList<Integer>[] adj;
    static int[] parent;

    static void input() {
        n = scan.nextInt();
        adj = new ArrayList[n + 1];
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        // 1. 인접 리스트 구성하기
        for (int i = 1; i < n; i++) {
            int x = scan.nextInt(), y = scan.nextInt();
            adj[x].add(y);
            adj[y].add(x);
        }
    }

    // dfs(x, par) := 정점 x 의 부모가 par 였고, x의 children들을 찾아주는 함수
    static void dfs(int x, int par) {
        for (int y : adj[x]) {
            if (y == par) continue; // 자기 자신은 제외
            parent[y] = x;
            dfs(y, x);
        }
    }

    static void pro() {
        // 1 번 정점이 ROOT 이므로, 여기서 시작해서 Tree의 구조를 파악하자.
        dfs(1, -1);

        // 정답 출력하기
        for (int i = 2; i <= n; i++) {
            sb.append(parent[i]).append('\n');
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

### [트리](https://www.acmicpc.net/problem/1068)
- 정점 X가 지워진다 = 정점 X의 부모에서 정점 X로 가는 간선을 삭제 or 무시
- 큰 문제를 작은 문제로 나누기 = 큰문제(전체 트리의 단말노드 개수) -> 작은문제(SubTree의 단말노드 개수)
- leaf(x) = x를 Root로 하는 SubTree에 있는 단말노드의 개수
- leaf(x)를 계산하는 법(X가 루트 Y가 child)
  - X에서 DFS를 하면 Y1,Y2,Y3... 까지 탐색을 하고 돌아옴
  - leaf(x) = leaf(Y1), leaf(Y2), leaf(Y3)... 의 합
```java
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int n, root, erased;
    static ArrayList<Integer>[] child;
    static int[] leaf;

    static void input() {
        n = scan.nextInt();
        child = new ArrayList[n];
        leaf = new int[n];
        for (int i = 0; i < n; i++) child[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int par = scan.nextInt();
            if (par == -1){ // 문제상 -1이면 root임
                root = i;
                continue;
            }
            child[par].add(i);
        }
        erased = scan.nextInt();
    }

    // dfs(x, par) := 정점 x 의 부모가 par 였고, Subtree(x) 의 leaf 개수를 세주는 함수
    static void dfs(int x, int par) {
        // leaf노드라면 +1
        if (child[x].isEmpty())
            leaf[x]++;
        for (int y : child[x]) {
            if (y == par) continue;
            dfs(y, x);
            // SubTree탐색이 끝나면 SubTree Root에 SubTree 단말노드의 합 더해주기
            leaf[x] += leaf[y];
        }
    }

    static void pro() {
        // i의 자식중 지워질 노드가 있다면 부모와의 연결 지우기
        for (int i=0;i<n;i++){
            if (child[i].contains(erased)){
                child[i].remove(child[i].indexOf(erased));
            }
        }

        // erased 가 root 인 예외 처리하기
        if (root != erased) dfs(root, -1);

        // 정답 출력하기
        System.out.println(leaf[root]);
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
