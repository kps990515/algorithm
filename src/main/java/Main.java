import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        int S = sc.nextInt();
        int P = sc.nextInt();
        String dnaString = sc.nextLine();
        // ACGT 필요 최수개수
        int[] minReq = new int[4];
        for(int i=0; i<4; i++){
            minReq[i] = sc.nextInt();
        }

        // ACGT 현재개수
        int[] dnaCount = new int[4];

        // 초기 윈도우 설정(문자열의 처음P개 문자)
        for(int i=0; i<P; i++){
            char dna = dnaString.charAt(i);
            dnaCount[dnaToIndex(dna)]++;
        }

        int answer = 0;

        if(isValid(dnaCount, minReq)){
            answer++;
        }

        // 슬라이딩 윈도우: 윈도우를 한 칸씩 이동하면서 조건 만족 여부 확인
        for (int i = P; i < S; i++) {
            // 새로 들어오는 문자 추가
            char newDna = dnaString.charAt(i);
            dnaCount[dnaToIndex(newDna)]++;

            // 맨 첫 문자 제거
            char firstDna = dnaString.charAt(i-P);
            dnaCount[dnaToIndex(firstDna)]--;

            if(isValid(dnaCount, minReq)){
                answer++;
            }
        }
        System.out.println(answer);
    }

    static int dnaToIndex(char dna){
        switch(dna) {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3;
            default:  return -1;
        }
    }

    static boolean isValid(int[] dnaCount, int[] minReq){
        for(int i=0; i<4; i++){
            if(dnaCount[i] < minReq[i]){
                return false;
            }
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