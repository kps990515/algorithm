import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        int N = sc.nextInt();
        int[] houses = new int[N];

        for(int i=0; i<N; i++){
            houses[i] = sc.nextInt();
        }
        Arrays.sort(houses);

        // N일 홀수 일 경우 (N-1)/2이 중앙 값
        // N이 짝수 일 경우 이론적으로 (N-1)/2과 N/2는 결과값이 같음
        // 문제에서는 작은 걸 뽑으라고 했기 때문에 (N-1)/2이 정답
        System.out.println(houses[(N-1)/2]);
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