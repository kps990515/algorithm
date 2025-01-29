import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();

    public static void main(String[] args) {
        int N = sc.nextInt();
        int M = sc.nextInt();
        char[][] board = new char[N][M];
        for(int i = 0; i < N; i++) {
            String line = sc.nextLine();
            board[i] = line.toCharArray();
        }
        int max = 64;
        int result;
        for(int i=0; i<=N-8; i++){
            for(int j=0; j<=M-8; j++){
                result = check(i, j, board);
                max = Math.min(max, result);
            }
        }
        System.out.println(max);
    }

    static int check(int w, int h, char[][] board){
        char[][] newBoard = board;
        int cnt = 0;
        for(int i=w; i< w+7; i++){
            for(int j=h; j<h+7; j++){
                if(newBoard[i][j] == newBoard[i+1][j]){
                    cnt++;
                    if(newBoard[i+1][j] == 'W'){
                        newBoard[i+1][j] = 'B';
                    }else{
                        newBoard[i+1][j] = 'W';
                    }
                }
                if(newBoard[i][j] == newBoard[i][j+1]){
                    cnt++;
                    if(newBoard[i][j+1] == 'W'){
                        newBoard[i][j+1] = 'B';
                    }else{
                        newBoard[i][j+1] = 'W';
                    }
                }
            }
        }
        return cnt;
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