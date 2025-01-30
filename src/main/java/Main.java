import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        int N = sc.nextInt();
        int M = sc.nextInt();
        int T = sc.nextInt();
        int K = sc.nextInt();
        int A = sc.nextInt();
        int B = sc.nextInt();

        char[][] matrix = new char[N+1][M+1];
        for(int i=1; i<N+1; i++){
            String s = sc.nextLine();
            for(int j=1; j<M+1; j++){
                matrix[i][j] = s.charAt(j-1);
            }
        }

        while(T-->0){
            int[][] acc = getPrefixSum(matrix);
            for(int i=1; i<N+1; i++){
                for(int j=1; j<M+1; j++){
                    int nearAlive = getRangesum(acc, i, j, K);
                    if(matrix[i][j] == '*'){
                        nearAlive--;
                        if(nearAlive < A || nearAlive > B){
                            matrix[i][j] = '.';
                        }
                    }else{
                        if(A< nearAlive && nearAlive <=B){
                            matrix[i][j] = '*';
                        }
                    }
                }
            }
        }
        for(int i=1; i<N+1; i++){
            for(int j=1; j<M+1; j++){
                sb.append(matrix[i][j]);
            }
            sb.append("\n");
        }

        System.out.println(sb);



    }
    static int getRangesum(int[][] acc, int i, int j, int K){
        int x1 = Math.max(i-K, 1);
        int y1 = Math.max(j-K, 1);
        int x2 = Math.min(i+K, acc.length-1);
        int y2 = Math.min(j+K, acc[0].length-1);
        return acc[x2][y2] - acc[x1-1][y2] - acc[x2][y1-1] + acc[x1-1][y1-1];
    }

    static int[][] getPrefixSum(char[][] matrix){
        int[][] acc = new int[matrix.length][matrix[0].length];
        for(int i=1; i<matrix.length; i++){
            for(int j=1; j<matrix[0].length; j++){
                int alive = (matrix[i][j] == '*' ? 1:0);
                acc[i][j] = acc[i-1][j] + acc[i][j-1] - acc[i-1][j-1] + alive;
            }
        }
        return acc;
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