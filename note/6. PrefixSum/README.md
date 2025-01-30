## 누적합
### 주어진 숫자를 더한 값들에 대한 새로운 배열 만들기
- arr[] : 1,2,3,4,5,6
- acc[] : 1,3,6,10,15,21 <-- 누적합
### XOR 
- 0^0=0 / 1^0,0^1=1 / 1^1=0 
### 표의 누적합
- 누적합 구하고 싶은 곳의 왼쪽 누적합 + 위쪽 누적합 - 중복된구간 누적합(대각선 왼위 누적합) + 자기 자신의 값 
### 표 구간합 범위
- (x2,y2) 누적합 - (x1-1,y2) 누적합 - (x2, y1-1) 누적합 + (x1-1,y1-1) 누적합 <-- 중복으로 빠진 부분
### 변화량 구하기
1. 기본 배열 만들기
   -  | Index 1 | Index 2 | Index 3 | Index 4 | Index 5 | Index 6 | Index 7 | Index 8 | Index 9 | Index 10 |
      |---------|---------|---------|---------|---------|---------|---------|---------|---------|----------|
      | 1       | 2       | 3       | 4       | 5       | -1      | -2      | -3      | -4      | -5       |

2. delta(변화량) 배열 만들기
   - 1 ~ 10까지는 -3, 6 ~ 10까지는 +3
   - | Index 1 | Index 2 | Index 3 | Index 4 | Index 5 | Index 6 | Index 7 | Index 8 | Index 9 | Index 10 |
     |---------|---------|---------|---------|---------|---------|---------|---------|---------|----------|
     | -3      | 0       | 0       | 0       | 0       | 3       | 0       | 0       | 0       | 0        |
   
   - 6 ~ 10까지는 +5 
   - | Index 1 | Index 2 | Index 3 | Index 4 | Index 5 | Index 6 | Index 7 | Index 8 | Index 9 | Index 10 |
     |---------|---------|---------|---------|---------|---------|---------|---------|---------|----------|
     | -3      | 0       | 0       | 0       | 0       | 8       | 0       | 0       | 0       | 0        |
     
   - 2 ~ 10까지는 +2, 8 ~ 10까지는 -2
   - | Index 1 | Index 2 | Index 3 | Index 4 | Index 5 | Index 6 | Index 7 | Index 8 | Index 9 | Index 10 |
     |---------|---------|---------|---------|---------|---------|---------|---------|---------|----------|
     | -3      | 2       | 0       | 0       | 0       | 8       | 0       | -2      | 0       | 0        |
     
3. accDelta(변화량 합) 배열 만들기
   - 1 ~ 10까지는 -3, 6~10까지는 +3
   - | Index 1 | Index 2 | Index 3 | Index 4 | Index 5 | Index 6 | Index 7 | Index 8 | Index 9 | Index 10 |
     |---------|---------|---------|---------|---------|---------|---------|---------|---------|----------|
     | -3      | -3      | -3      | -3      | -3      | 0       | 0       | 0       | 0       | 0        |
   - 6~10까지는 +5
   - | Index 1 | Index 2 | Index 3 | Index 4 | Index 5 | Index 6 | Index 7 | Index 8 | Index 9 | Index 10 |
     |---------|---------|---------|---------|---------|---------|---------|---------|---------|----------|
     | -3      | -3      | -3      | -3      | -3      | 5       | 5       | 5       | 5       | 5        |
   - 2~10까지는 +2, 8~10까지는 -2
   - | Index 1 | Index 2 | Index 3 | Index 4 | Index 5 | Index 6 | Index 7 | Index 8 | Index 9 | Index 10 |
     |---------|---------|---------|---------|---------|---------|---------|---------|---------|----------|
     | -3      | -1      | -1      | -1      | -1      | 7       | 7       | 5       | 5       | 5        |

4. accDelta는 매번할필요없고 마지막에만 하면된다

## 문제1. 수 N개가 있을때, M번의 (i,j) 질문에 대해 i번째부터 j번째 수까지 합을 구하기
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        // 기본 배열 만들기
        int[] arr = new int[N + 1];
        for (int i = 1; i <= N; i++)
            arr[i] = sc.nextInt();
        // 누적합 배열 만들기
        int[] acc = new int[N + 1];
        for (int i = 1; i <= N; i++)
            acc[i] = acc[i - 1] + arr[i];
        // j까지의 누적합 - i-1까지의 누적합 = i~j까지의 합
        while (M-- > 0) {
            int i = sc.nextInt();
            int j = sc.nextInt();
            System.out.println(acc[j] - acc[i - 1]);
        }
    }
}
```

### 문제2. 길이 N인 수열이 있을때, Q개의 쿼리(s,e)에 대해 S부터 e까지의 XOR값을 구하라
- XOR : 0^0=0 / 1^0,0^1=1 / 1^1=0
```java
class Main
{
  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int Q = sc.nextInt();
    int[] arr = new int[N + 1];
    // 기본 숫자 배열 구하기
    for (int i = 1; i <= N; i++)
      arr[i] = sc.nextInt();

    int[] acc = new int[N + 1];
    // 누적 XOR배열 구하기(acc[0] = 0)
    for (int i = 1; i <= N; i++)
      acc[i] = acc[i - 1] ^ arr[i];

    int ans = 0;
    while (Q-- > 0) {
      // s~e까지의 XOR 구하기
      int s = sc.nextInt();
      int e = sc.nextInt();
      // XOR값들 XOR해주기
      ans ^= acc[e] ^ acc[s - 1];
    }
    System.out.println(ans);
  }
} 
```

### 문제3. N*N표가 있을때 M개의 쿼리(x1,y1,x2,y2)에 대해 (x1,y1) ~ (x2,y2)까지의 합을 구하라
- 표의 누적합 
  - 누적합 구하고 싶은 곳의 왼쪽 누적합 + 위쪽 누적합 - 중복된구간 누적합(대각선 왼위 누적합) + 자기 자신의 값
- 표 구간합 범위
  - (x2,y2) 누적합 - (x1-1,y2) 누적합 - (x2, y1-1) 누적합 + (x1-1,y1-1) 누적합 <-- 중복으로 빠진 부분
```java
class Main
{
    public static void main (String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] arr = new int[N + 1][N + 1];
        // 1. 기본 표 그려주기
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= N; j++)
                arr[i][j] = sc.nextInt();

        // 2. 누적합 표 그려주기
        int[][] acc = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= N; j++)
                // 누적합 구하고 싶은 곳의 왼쪽 누적합 + 위쪽 누적합 - 대각선 왼위 누적합 + 자기 자신의 값
                acc[i][j] = acc[i - 1][j] + acc[i][j - 1] - acc[i - 1][j - 1] + arr[i][j];

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while (M-- > 0) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            // 3. 구간의 누적합 구해주기
            // (x2,y2) 누적합 - (x1-1,y2)의 누적합 - (x2,y1-1)의 누적합 + 중복제거된 부분 x1,y1 대간선왼위
            bw.write(acc[x2][y2] - acc[x1 - 1][y2] - acc[x2][y1 - 1] + acc[x1 - 1][y1 - 1] + "\n");
        }
        bw.flush();
    }
}
```

### 문제4. N칸의 길이 있을때 M번의 지시에 따라 [a:b]칸의 높이를 k만큼 바꿀때, 연병장 각 칸의 높이
1. 기본 배열 만들기
  -  1  2  3  4  5 -1 -2 -3 -4 -5
2. delta(변화량) 배열 만들기
  - -3  0  0  0  0  3  0  0  0  0 (1~10까지는 -3, 6~10까지는 +3)
  - -3  0  0  0  0  8  0  0  0  0 (6~10까지는 +5)
  - -3  2  0  0  0  8  0 -2  0  0 (2~10까지는 +2, 8~10까지는 -2)
3. accDelta(변화량 합) 배열 만들기
  - -3 -3 -3 -3 -3  0  0  0  0  0 (1~10까지는 -3, 6~10까지는 +3)
  - -3 -3 -3 -3 -3  5  5  5  5  5 (6~10까지는 +5)
  - -3 -1 -1 -1 -1  7  7  5  5  5 (2~10까지는 +2, 8~10까지는 -2)
4. accDelta는 매번할필요없고 마지막에만 하면된다
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] origin = new int[N + 1];
        // 1. N 길이의 연병장
        for (int i = 1; i <= N; i++)
            origin[i] = sc.nextInt();

        // 2. delta 변화량
        int[] delta = new int[N + 2];
        while (M-- > 0) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int k = sc.nextInt();
            delta[a] += k; //a부터는 변화량 입력
            delta[b + 1] -= k; // b이후부터는 -변화량 입력
        }

        // 3. accDelta 구하기
        int[] accDelta = new int[N + 1];
        for (int i = 1; i <= N; i++)
            accDelta[i] = accDelta[i - 1] + delta[i];

        for (int i = 1; i <= N; i++)
            System.out.print(origin[i] + accDelta[i] + " ");
        System.out.println();
    }
}
```

## 문제5. N*M 바둑판에서 1시간마다 각 칸을 중심으로 주위 (2K+1)*(2K+1) 생명에 따라 아래 상황일때 T시간 뒤 생명현황
1. 생존 : 만약 현재 칸에 생명이 있고, 주위에 a개 이상 b개 이하의 생명이 있다면 현재 칸의 생명은 다음 단계에 살아남는다.
2. 고독 : 만약 현재 칸에 생명이 있고, 주위에 a개 미만의 생명이 있다면 현재 칸의 생명은 외로워서 죽는다.
3. 과밀 : 만약 현재 칸에 생명이 있고, 주위에 b개 초과의 생명이 있다면 현재 칸의 생명은 과밀로 죽는다.
4. 탄생 : 만약 현재 칸에 생명이 없고, 주위에 a개 초과 b개 이하의 생명이 있다면 다음 단계에서 현재 칸에 생명이 생긴다.

- 풀이법 : 매 시간마다 현재 생명에 대한 누적합 계산 필요
```java
class Main
{
    // 누적합 표 구하기
    static int[][] getPrefixSum(char[][] map) {
        int[][] acc = new int[map.length][map[0].length];
        for (int i = 1; i < map.length; i++)
            for (int j = 1; j < map[0].length; j++) {
                int alive = (map[i][j] == '*' ? 1 : 0);
                // 왼쪽 + 위쪽 - 대각선왼위 + 자기자신
                acc[i][j] = acc[i - 1][j] + acc[i][j - 1] - acc[i - 1][j - 1] + alive;
            }
        return acc;
    }

    // 표의 구간합 구하기
    static int getRangeSum(int[][] acc, int r, int c, int K) {
        int r1 = Math.max(r - K, 1);
        int c1 = Math.max(c - K, 1);
        int r2 = Math.min(r + K, acc.length - 1);
        int c2 = Math.min(c + K, acc[0].length - 1);
        // (r2,c2) 누적합 - (r1-1,c2)의 누적합 - (r2,c1-1)의 누적합 + 중복제거된 부분 r1,c1 대각선왼위
        return acc[r2][c2] - acc[r1 - 1][c2] - acc[r2][c1 - 1] + acc[r1 - 1][c1 - 1];
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int T = sc.nextInt();
        int K = sc.nextInt();
        int A = sc.nextInt();
        int B = sc.nextInt();

        // 1. 기본 표 만들기
        char[][] map = new char[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            String rowMap = sc.next(); // 한줄 다 가져오기
            for (int j = 1; j <= M; j++)
                map[i][j] = rowMap.charAt(j - 1); // 줄에 있는 문자들 하나씩 넣기
        }

        while (T-- > 0) {
            // 2. 누적합 구하기
            int[][] acc = getPrefixSum(map);
            for (int i = 1; i <= N; i++)
                for (int j = 1; j <= M; j++) {
                    // 3. 주변 생명숫자 구하기(표의 구간합)
                    int nearAlive = getRangeSum(acc, i, j, K);
                    // 자기자신에게 생명있으면 제외시키기
                    if (map[i][j] == '*') {
                        nearAlive--;
                        // 고독, 과밀로 죽기
                        if (nearAlive < A || B < nearAlive)
                            map[i][j] = '.';
                        // 생존
                    }
                    // 탄생
                    else if (A < nearAlive && nearAlive <= B)
                        map[i][j] = '*';
                }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++)
                System.out.print(map[i][j]);
            System.out.println();
        }
    }
}
```
