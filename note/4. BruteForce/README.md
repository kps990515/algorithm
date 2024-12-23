## Brute Force

### 문제1. 9명 난쟁이의 키가 주어질때, 키의 합이 100이되는 7명 구하고 정렬하기
- 9명의 키 합(sum) 구하고 두명씩 빼보기
```java
import java.util.Arrays;
import java.util.Scanner;

class Main
{
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[i]) {
                    int cur = arr[i];
                    for (int k = i; k > j; k--)
                        arr[k] = arr[k - 1];
                    arr[j] = cur;
                    break;
                }
            }
        }
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] h = new int[9];
        for (int i = 0; i < 9; i++)
            h[i] = sc.nextInt();

        // Arrays.sort(h);
        sort(h);

        int sum = Arrays.stream(h).sum();
        boolean find = false;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++)
                if (sum - h[i] - h[j] == 100) {
                    h[i] = h[j] = -1;
                    find = true;
                    break;
                }
            if (find) break;
        }

        for (int i = 0; i < 9; i++)
            if (h[i] > 0)
                System.out.println(h[i]);
    }
}
```

### 문제2. K가 주어질때 3개의 삼각수의 합으로 구해질수있는가
```java
class Main {
    static boolean[] isEurekaNumber = new boolean[1001];

    public static void preprocess() {
        int[] triangleNumbers = new int[50];
        int triangleNumberCount = 0;
        for (int i = 1; ; i++) {
            int triangleNumber = i * (i + 1) / 2; // 삼각수 공식
            if (triangleNumber > 1000) break; // 삼각수 3개의 합 최대 제한
            // 삼각수 합의 개수만큼 저장
            triangleNumbers[triangleNumberCount++] = triangleNumber; 
        }

        for (int i = 0; i < triangleNumberCount; i++)
            for (int j = i; j < triangleNumberCount; j++)
                for (int k = j; k < triangleNumberCount; k++) {
                    // 삼각수 3개의 합 모두 구하기
                    int eurekaNumber = triangleNumbers[i] + triangleNumbers[j] + triangleNumbers[k];
                    if (eurekaNumber > 1000) break;
                    // 삼각수 3개의 합의 숫자는 true로 표시
                    isEurekaNumber[eurekaNumber] = true;
                }
    }

    public static void main (String[] args) {
        preprocess();

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int K = sc.nextInt();
            // 삼각수 합 배열에 해당 숫자가 있는가
            System.out.println(isEurekaNumber[K] ? "1" : "0");
        }
    }
}
```

### 문제3. 진법변환
```java
class Main {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int B = sc.nextInt();

        String ans = "";
        while (N > 0) {
            int digit = N % B; // 1자리수부터 구하기
            if (digit < 10) ans += digit; //10 미만일때는 숫자로 표현
            else ans += (char)('A' + digit - 10); // 10이상일때는 알파벳으로 표현
            N /= B; // 더 이상 안나눠질때까지 계속
        }

        System.out.println(new StringBuilder(ans).reverse());
    }
}
```

### 문제4. 주어진 숫자가 2~64 진법으로 표현했을때 회문(왼쪽, 오른쪽으로 읽어도 같은 수)이 될 수 있는가
```java
class Main {
    // 1. 가능한 모든 진법에 대해 진법 변환
    public static boolean isPalindromeInBase(int x, int base) {
        int[] digit = new int[20];
        int len = 0;
        while (x > 0) {
            digit[len++] = x % base;
            x /= base;
        }
        // 2. 회문인지 확인
        for (int i = 0; i < len / 2; i++)
            if (digit[i] != digit[len - i - 1])
                return false;
        return true;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int x = sc.nextInt();
            boolean ans = false;
            for (int i = 2; i <= 64; i++) {
                if (isPalindromeInBase(x, i)) {
                    ans = true;
                    break;
                }
            }
            System.out.println(ans ? 1 : 0);
        }
    }
}
```

### 문제 5. 색이 다른 사탕이 인접한 두 칸으로 골라 서로 교환할때, 같은색으로 이루어진 긴 연속부분 행/열의 최댓값
1. 가능한 모든 쌍의 사탕으로 서로 교환(4방향 모두면 중복이 발생하기에, 오른쪽/아래쪽만 확인)
2. 교환한 상태에서 가장 긴 연속 부분 행/열을 찾는다
3. 교환한 사탕을 원복
```java
class Main
{
    public static int calcScore(char[][] map) {
        int N = map.length;
        int maxScore = 0;
        // 가장 긴 연속 column 길이 찾기
        for (int r = 0; r < N; r++) {
            int scr = 1;
            for (int c = 1; c < N; c++) {
                if (map[r][c] == map[r][c - 1]) scr++;
                else {
                    maxScore = Math.max(maxScore, scr);
                    scr = 1;
                }
            }
            maxScore = Math.max(maxScore, scr);
        }
        // 가장 긴 연속 row 길이 찾기
        for (int c = 0; c < N; c++) {
            int scr = 1;
            for (int r = 1; r < N; r++) {
                if (map[r][c] == map[r - 1][c]) scr++;
                else {
                    maxScore = Math.max(maxScore, scr);
                    scr = 1;
                }
            }
            maxScore = Math.max(maxScore, scr);
        }
        return maxScore;
    }

    public static void swapCandy(char[][] map, int r1, int c1, int r2, int c2) {
        char tmp = map[r1][c1];
        map[r1][c1] = map[r2][c2];
        map[r2][c2] = tmp;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        char[][] map = new char[N][N];
        for (int i = 0; i < N; i++)
            map[i] = sc.next().toCharArray();

        int ans = 0;
        for (int i = 0; i < N; i++)
            // 가능한 모든 쌍의 사탕으로 서로 교환(4방향 모두면 중복이 발생하기에, 오른쪽/아래쪽만 확인)
            for (int j = 0; j < N; j++){
                // 오른쪽 & 다른 색상일때
                if (j + 1 < N && map[i][j] != map[i][j+1]) {
                    swapCandy(map, i, j, i, j + 1);
                    // 교환한 상태에서 가장 긴 연속 부분 행/열
                    ans = Math.max(ans, calcScore(map));
                    swapCandy(map, i, j, i, j + 1);
                }
                // 오른쪽 & 다른 색상일때
                if (i + 1 < N && map[i][j] != map[i+1][j]) {
                    swapCandy(map, i, j, i + 1, j);
                    // 교환한 상태에서 가장 긴 연속 부분 행/열
                    ans = Math.max(ans, calcScore(map));
                    swapCandy(map, i, j, i + 1, j);
                }
            }
        System.out.println(ans);
    }
} 
```

### 문제 6. 엘베로부터 가까운방 먼저, 같다면 아래층부터 배정할때 N번째 손님에게 배정될 방 번호
- N번째의 호수 
  - H로 나눈 몫(1 ~ H = 1, H+1 ~ 2H = 2)
  - 0 ~ H-1 = 0+1, H ~ 2H-1 = 1+1 
  - =(N-1) / H + 1
- N번째의 층수 
  - H로 나눈 나머지(0~H-1)
  - 0은 안되니까 (1~H)로 취환
  - =(N-1) % H + 1
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int H = sc.nextInt();
            int W = sc.nextInt();
            int N = sc.nextInt();

            // H로 나눈 나머지
            int floor = ((N - 1) % H) + 1;  // [1, H]
            // H로 나눈 몫
            int number = (N - 1) / H + 1; // [1, W]
            System.out.printf("%d%02d\n", floor, number);
        }
    }
} 
```

### 문제 7. 로봇팔의 명령순서가 주어질때, 목판 위에 패인 조각도의 흔적
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        String command = sc.hasNext() ? sc.next() : "";

        boolean[][] passVertical = new boolean[N][N];
        boolean[][] passHorizontal = new boolean[N][N];

        int curR = 0, curC = 0;
        // 1. 팔을 명령대로 움직인다
        for (int i = 0; i < command.length(); i++) {
            char cmd = command.charAt(i);
            if (cmd == 'D') {
                if (curR == N - 1) continue; // 범위 벗어나면 무시
                // 지나간흔적표시
                passVertical[curR][curC] = passVertical[curR + 1][curC] = true; 
                curR++;
            }
            else if (cmd == 'U') {
                if (curR == 0) continue; // 범위 벗어나면 무시
                passVertical[curR][curC] = passVertical[curR - 1][curC] = true;
                curR--;
            }
            else if (cmd == 'L') {
                if (curC == 0) continue; // 범위 벗어나면 무시
                passHorizontal[curR][curC] = passHorizontal[curR][curC - 1] = true;
                curC--;
            }
            else {
                if (curC == N - 1) continue; // 범위 벗어나면 무시
                passHorizontal[curR][curC] = passHorizontal[curR][curC + 1] = true;
                curC++;
            }
        }
        // 2. 누적된 흔적 출력
        for (int i = 0; i < N; i++) {
            String ans = "";
            for (int j = 0; j < N; j++) {
                if (passHorizontal[i][j] && passVertical[i][j]) ans += "+";
                else if (passVertical[i][j]) ans += "|";
                else if (passHorizontal[i][j]) ans += "-";
                else ans += ".";
            }
            System.out.println(ans);
        }
    }
}
```

### 문제8. 바퀴를 시계방향으로 S칸 돌릴때마다 화살표가 가르키는 글자가 있을때 바퀴 칸들의 알파벳을 알아내자
1. 칸수만큼 배열을 만든다
2. 바퀴의 인덱스를 S만큼 움직인다
   - 인덱스가 배열 범위 넘어가면 조정
   - 도착칸이 값이 없는 칸이라면 기록
   - 도착칸의 값이 적힌 값이랑 다르다면 바퀴 이상
   - 도착칸의 값이랑 적힌 값이랑 같다면 패스
3. 바퀴에 중복된 값이 있는지 확인
4. 마지막에 도착한 글자부터 배열의 값을 돌면서 출력
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();
        // 1. 칸수만큼 배열을 만든다
        char[] ans = new char[N];
        Arrays.fill(ans, '?');

        int curIndex = 0;
        while (K-- > 0) {
            int step = sc.nextInt();
            char alphabet = sc.next().charAt(0);
            // 2-1. 인덱스가 배열 범위 넘어가면 조정
            // 음수일때를 가정해 +N %N 해주기
            int nextIndex = ((curIndex - step) % N + N) % N;
            // 2-2.도착칸이 값이 없는 칸이라면 기록
            if (ans[nextIndex] == '?') ans[nextIndex] = alphabet;
            // 2-3. 도착칸의 값이 적힌 값이랑 다르다면 바퀴 이상
            else if (ans[nextIndex] != alphabet) {
                System.out.println("!");
                return ;
            }
            // 2-4. 패스
            curIndex = nextIndex;
        }

        // 3. 바퀴에 중복된 값이 있는지 확인
        boolean[] chk = new boolean[26];
        for (int i = 0; i < N; i++) {
            if (ans[i] == '?') continue;
            if (chk[ans[i] - 'A']) {
                System.out.println("!");
                return ;
            }
            chk[ans[i] - 'A'] = true;
        }

        // 4. 마지막에 도착한 글자부터 배열의 값을 돌면서 출력
        for (int i = 0; i < N; i++)
            System.out.print(ans[(curIndex + i) % N]);
        System.out.println();
    }
} 
```

### 문제9. 득표수가 주어질때 스태프가 받을 칩의 개수 계산
1. 전체 득표수의 5%미만 스태프는 후보에서 제외
2. 남은 스태프 마다 각각 득표수를 1~14로 나눈 점수 계산
3. 전체 점수 집합에서 점수가 큰 순서대로 칩을 1개씩 지급
4. 스태프 이름에 대해 사전순으로 후보 스태프와 받은 칩의 수를 출력
```java
class Main
{
    static class Score{
        Score(int staffIndex, double scr) {
            this.staffIndex = staffIndex;
            this.scr = scr;
        }
        int staffIndex;
        double scr;
    }

    public static void sortScoresDescendingOrder(Score[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i].scr > arr[j].scr) {
                    Score cur = arr[i];
                    for (int k = i; k > j; k--)
                        arr[k] = arr[k - 1];
                    arr[j] = cur;
                }
            }
        }
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        int N = sc.nextInt();

        // 1. 전체 득표수의 5% 미만의 스태프를 후보에서 제외한다.
        double validCut = X * 0.05; // 5%계산
        boolean[] validCandidate = new boolean[26]; // 스태프이름 알파벳순으로 나열하기
        int[] staffVote = new int[26];
        int candidateCount = 0;
        for (int i = 0; i < N; i++) {
            String name = sc.next();
            int vote = sc.nextInt();
            if (vote >= validCut) { // 5%미만 제외
                int index = name.charAt(0) - 'A';
                validCandidate[index] = true; // 스태프 이름 표기
                staffVote[index] = vote;
                candidateCount++;
            }
        }

        // 2. 남은 스태프마다 받은 득표수를 1~14로 나눈 점수 집합을 구한다.
        Score[] scores = new Score[candidateCount * 14];
        int scoreIndex = 0;
        for (int i = 0 ; i < 26; i++) {
            if (validCandidate[i]) {
                for (int j = 1; j <= 14; j++)
                    scores[scoreIndex++] = new Score(i, (double)staffVote[i] / j);
            }
        }

        // 3. 전체 점수 집합 내림차순정렬
        sortScoresDescendingOrder(scores);

        // 3. 총 14번째까지 칩을 지급
        int[] ans = new int[26];
        for (int i = 0; i < 14; i++)
            ans[scores[i].staffIndex]++;

        // 4. 스태프 이름에 대해 사전순으로 후보 스태프와 받은 칩의 수를 출력한다.
        for (int i = 0; i < 26; i++) {
            if (validCandidate[i])
                System.out.println((char)(i + 'A') + " " + ans[i]);
        }

    }
} 
```
