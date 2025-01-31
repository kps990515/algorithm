import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] numbers = new int[N];
        for(int i=0; i<N; i++){
            numbers[i] = sc.nextInt();
        }

        int L=1, R=N*100000, answer = -1;
        while(L<=R){
            int mid = (L+R) / 2;
            if(determine(numbers, mid, M)){
                answer = mid;
                R = mid -1;
            }else{
                L = mid +1;
            }
        }
        System.out.println(answer);
    }

    static boolean determine(int[] numbers, int mid, int M){
        int sum = 0;
        int count = 1;
        for(int i=0; i<numbers.length; i++){
            if(mid<numbers[i]){
                return false;
            }
            if(sum+numbers[i] > mid){
                count++;
                if(count > M){
                    return false;
                }
                sum = 0;
            }
            sum += numbers[i];
        }
        return true;
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