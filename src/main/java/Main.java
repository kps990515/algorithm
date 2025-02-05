import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        runTest();
    }

    public static List<int[]> mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new ArrayList<>();
        }

        // 1. 시작값 기준으로 정렬
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        int[] current = intervals[0]; // 첫 번째 구간을 초기값으로 설정
        merged.add(current);

        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            // 2. 현재 구간이 이전 구간과 겹치는 경우 병합
            if (start <= current[1]) {
                current[1] = Math.max(current[1], end); // 끝값 업데이트
            } else {
                // 겹치지 않으면 새 구간 추가
                current = new int[]{start, end};
                merged.add(current);
            }
        }

        return merged;
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