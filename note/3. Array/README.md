## 배열
- get(i) 
- change(int i, int val) : i번째 원소를 val로 교체
- append(int val) : 맨마지막에 원소 넣기
- insert(int i, int val) : i번째 원소 앞에 원소 삽입 -> O(N)
- erase(int i) : 해당 인덱스 값 삭제 -> O(N)
- fill(array, '?') : 해당 배열 특정값으로 채우기
- BufferedReader / Writer
  - BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  - br.readLine();
  - BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
  - bw.write() / bw.flush()

### 문제 1 : 모든 행과 열에 경비원이 최소 한명씩 있을때 추가로 필요한 경비원 수
- 경비원이 필요한 행의 숫자, 열의 숫자 구하기
- 행에 경비원을 넣을때 열에 경비원 없으면 한번에 넣어주기(반대로도 가능)
- -> 필요한 행 -1 && 필요한 열 -1 동시에 진행됨
- 행, 열의 숫자 중 큰 값 출력
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        char[][] map = new char[N][M];
        for (int i = 0; i < N; i++)
            map[i] = sc.next().toCharArray();

        boolean[] rowExist = new boolean[N];
        boolean[] colExist = new boolean[M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (map[i][j] == 'X') {
                    rowExist[i] = true;
                    colExist[j] = true;
                }

        int rowNeedCount = N;
        int colNeedCount = M;
        for (int i = 0; i < N; i++)
            if (rowExist[i]) rowNeedCount--; // 필요한 행의 숫자
        for (int i = 0; i < M; i++)
            if (colExist[i]) colNeedCount--; // 필요한 열의 숫자

        System.out.println(Math.max(rowNeedCount, colNeedCount));
    }
} 
```

### 문제2. 입력된 순으로 키 순으로 줄을 설때 학생들이 뒤로 물러난 걸음 수의 총합
```java
import java.io.*;
import java.util.*;

public class Main {
  static StringBuilder sb = new StringBuilder();
  static FastReader sc = new FastReader();

  static int T, N;  // 테스트 케이스 번호와, 불필요한 변수들을 제거하였습니다.
  static int[] dist = new int[20]; // 학생들의 키 저장 배열

  static void input() {
    T = sc.nextInt();  // 테스트 케이스 번호를 올바르게 읽어옵니다.
    for(int i = 0; i < 20; i++) {
      dist[i] = sc.nextInt();  // 학생들의 키 입력
    }
  }

  static void pro() {
    int count = 0;
    // 초기에 line 리스트는 비어 있으며, 학생들을 적절한 위치에 삽입하면서 줄을 세웁니다.
    ArrayList<Integer> line = new ArrayList<>();
    for (int h : dist) {
      int position = 0;
      // 적절한 위치를 찾기 위해 이미 줄 선 학생들을 검사합니다.
      while (position < line.size() && line.get(position) < h) {
        position++; // 오름차순으로 되어있으면 넘어가기
      }
      line.add(position, h);
      // 삽입된 학생 뒤에 있는 모든 학생들이 한 칸씩 뒤로 물러납니다.
      count += line.size() - position - 1;
    }
    System.out.println(T + " " + count);  // 결과 출력
  }

  public static void main(String[] args) {
    int P = sc.nextInt();  // 테스트 케이스 수 입력
    while (P-- > 0) {
      input();
      pro();
    }
  }

  static class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader() {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreElements()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }
}

```

### 문제3. N개의 자연수가 주어질때 오름차순 정렬하기
- 1~10000까지의 숫자를 넣을 수 있는 10000개의 배열 만들기
- 각 배열마다 숫자중복되는 만큼 출력해주기
- Arrays.sort로 하면 시간초과 가능성 있음
```java
import java.io.*;

class Main
{
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] cnt = new int[10001];
        for (int i = 0; i < N; i++) {
            int n = Integer.parseInt(br.readLine());
            cnt[n]++;
        }

        for (int i = 1; i <= 10000; i++)
            while(cnt[i]-- > 0) {
                bw.write(i + "\n");
            }
        bw.flush();
    }
}
```

### 문제4. 서로 다른 양의 정수로 이루어진 수열에서 두 수의 합이 X가 되는 쌍의 개수
- 중복된 쌍을 보지 않아야함
- i<j도 확인도 필수
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 몇개의 수가 들어온건지
        int[] a = new int[N];
        for (int i = 0; i < N; i++)
            a[i] = sc.nextInt(); // 수의 개수만큼 넣어주기
        int X = sc.nextInt(); // 합의 수

        // 1~100000까지 해당 숫자가 있는지 없는지 배열에 넣어주기
        boolean[] cnt = new boolean[1000001]; 
        for (int i = 0; i < N; i++)
            cnt[a[i]] = true;

        long ans = 0;
        // 중복된 쌍을 보지 않도록 앞에거 반만 돌면서 뒤랑 더해주기
        // 1 2 3 4 5 6 7 / X:8 -> (1,7) (2,7) (3,5) -> 1,2,3까지만 봐도됨
        for (int i = 1; i <= (X - 1) / 2; i++)
            if (X - i <= 1000000)
                ans += (cnt[i] && cnt[X-i]) ? 1: 0; // 합에 맞는 쌍이 있으면 플러스
        System.out.println(ans);
    }
}
```