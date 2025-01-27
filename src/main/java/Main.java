import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();

    static int T,N, S, M, V, group_cnt, ans, erased, root, Q, max, min, sum, K, start, end;
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
        s1 = sc.nextLine();
        s2 = sc.nextLine();
    }

    static void pro(){
        String[] first = s1.split(":");
        String[] second = s2.split(":");

        int firstTime;
        int secondTime;
        for(int i=0; i<3; i++){

        }
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