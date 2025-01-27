import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();

    static int T,N, S, M, V, group_cnt, ans, erased, root, Q, max, min, sum, K, start, end, W, H, P;
    static boolean[][] visit;
    static boolean[] used, possible;
    static int[] dist;
    static int[][] dist_hog;
    static int[] A,B,indeg, numbers, operator, cols, Limit, parent, leaf, B_DONE;
    static String[] s;
    static String s1, s2;
    static ArrayList<Integer>[] adj;
    static int[][] dir = {{1,0}, {-1,0}, {0,-1}, {0,1}};
    static TreeMap<Long, Long> cards;

    static char[] chars1;
    static char[] chars2;
    static void input() {
        N = sc.nextInt();
        used = new boolean[1000001];
        for(int i=0; i<N; i++){
            used[sc.nextInt()] = true;
        }
        M = sc.nextInt();
    }

    static void pro(){
        int count = 0;
        for(int i=1; i<=(M-1)/2; i++){
            if(i<= 100000 && M-i <= 100000 && used[i] && used[M-i]){
                count++;
            }

        }
        System.out.println(count);
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