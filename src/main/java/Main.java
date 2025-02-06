import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        // 배열 A의 크기 N과 배열 B의 크기 M을 입력 받음
        int N = sc.nextInt();
        int M = sc.nextInt();

        // 배열 A와 B를 선언하고 입력 값을 받음
        int[] a = new int[N];
        int[] b = new int[M];
        for (int i = 0; i < N; i++)
            a[i] = sc.nextInt(); // 배열 A의 모든 요소를 입력 받음
        for (int i = 0; i < M; i++)
            b[i] = sc.nextInt(); // 배열 B의 모든 요소를 입력 받음

        int[] merged = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).sorted().toArray();

        for(int temp : merged){
            sb.append(temp).append(" ");
        }
        System.out.println(sb);
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