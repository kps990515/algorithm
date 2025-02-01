import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        int T = sc.nextInt();

        int N = sc.nextInt();
        int[] Ns = new int[N];
        for(int i=0; i<N; i++){
            Ns[i] = sc.nextInt();
        }

        int M = sc.nextInt();
        int[] Ms = new int[M];
        for(int i=0; i<M; i++){
            Ms[i] = sc.nextInt();
        }

        int[] partSumN = getPartSum(Ns);
        int[] partSumM = getPartSum(Ms);
        Arrays.sort(partSumM);

        long ans = 0;
        for(int sum : partSumN){
            int partSum = T - sum;

        }



    }

    static int[] getPartSum(int[] Ns){
        int length = Ns.length;
        int[] partSum = new int[(length*length+1)/2];
        int index = 0;
        for(int i=0; i<length; i++){
            int sum = 0;
            for(int j=i; j<length; j++){
                sum += Ns[j];
                partSum[index++] = sum;
            }
        }
        return partSum;
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