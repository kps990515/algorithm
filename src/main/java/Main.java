import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();

    static int N, S, M, V, group_cnt, B, ans;
    static boolean[][] visit;
    static int[][] adj;
    static int[][] blank;
    static String[] s;
    static String str;
    static ArrayList<Integer> group = new ArrayList<>();
    static int[][] dir = {{1,0}, {-1,0}, {0,-1}, {0,1}};

    static void input() {
        N = sc.nextInt();
        M = sc.nextInt();
        visit = new boolean[N+1][M+1];
        blank = new int[N*M+1][2];
        adj = new int[N+1][M+1];
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= M; j++)
                adj[i][j] = sc.nextInt();

    }


    public static void main(String[] args) {
        input();
        pro();
    }

    static void pro(){
        for(int i=1;i<=N;i++){
            for(int j=1;j<=M;j++){
                if(adj[i][j]==0){
                    B++;
                    blank[B][0] = i;
                    blank[B][1] = j;
                }
            }
        }
        dfs(1,0);
        System.out.println(ans);
    }

    static void dfs(int idx, int count){
        if(count == 3){
            bfs();
            return;
        }
        if(idx > B) return;

        adj[blank[idx][0]][blank[idx][1]] = 1;
        dfs(idx+1, count+1);
        adj[blank[idx][0]][blank[idx][1]] = 0;
        dfs(idx+1, count);
    }

    static void bfs(){
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                visit[i][j] = false;
                if(adj[i][j] == 2){
                    queue.add(i);
                    queue.add(j);
                    visit[i][j] = true;
                }
            }
        }

        while(!queue.isEmpty()){
            int x = queue.poll(), y = queue.poll();
            for (int k = 0; k < 4; k++) {
                int nx = x + dir[k][0], ny = y + dir[k][1];
                if (nx < 1 || ny < 1 || nx > N || ny > M) continue;
                if (adj[nx][ny] != 0) continue; // 확산할 수 없으면 패스
                if (visit[nx][ny]) continue; // 이미 퍼졌으면 패스
                visit[nx][ny] = true;
                queue.add(nx);
                queue.add(ny);
            }
        }

        // 탐색이 종료된 시점이니, 안전 영역의 넓이를 계산하고, 정답을 갱신한다.
        int cnt = 0;
        for (int i = 1; i <= N; i++){
            for (int j = 1; j <= M; j++){
                if (adj[i][j] == 0 && !visit[i][j]) cnt++; // 안전영역이므로 cnt ++
            }
        }
        ans = Math.max(ans, cnt);
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

        private void ensureTokenizer(){
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }catch (IOException e){
                    throw new RuntimeException();
                }
            }
        }

        String next(){
            ensureTokenizer();
            return st.nextToken();
        }

        int nextInt(){
            return Integer.parseInt(next());
        }

        long nextLong(){
            return Long.parseLong(next());
        }

        double nextDouble(){
            return Double.parseDouble(next());
        }

        String nextLine(){
            st = null; // reset the tokenizer
            try{
                return br.readLine();
            }catch (Exception e){
                throw new RuntimeException();
            }

        }
    }
}