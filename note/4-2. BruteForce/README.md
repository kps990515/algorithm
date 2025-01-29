## Brute Force2

### [진법 변환](https://www.acmicpc.net/problem/2745)
- 제곱 구할때 매 숫자마다 다른 제곱이 아닌 for문을 통해서 계속 곱해나가면 됨
```java
import java.util.Scanner;

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String N = sc.next();
        int B = sc.nextInt();
        int numberInDecimal = 0;
        
        for (int i = 0; i < N.length(); i++) {
            char digit = N.charAt(i);
            // 매숫자마다 제곱할필요 없이 for문을 통해서 곱해짐
            numberInDecimal *= base; 
            if ('0' <= digit && digit <= '9') numberInDecimal += digit - '0';
            else numberInDecimal += 10 + digit - 'A';
        }
        System.out.println(numberInDecimal);
    }
}
```

### [주사위](https://www.acmicpc.net/problem/1233)
- 그냥 다 더해주기
```java
import java.util.Scanner;

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int S1 = sc.nextInt();
        int S2 = sc.nextInt();
        int S3 = sc.nextInt();

        int maxNumber = S1 + S2 + S3;
        int[] count = new int[maxNumber + 1];
        for (int i = 1; i <= S1; i++)
            for (int j = 1; j <= S2; j++)
                for (int k = 1; k <= S3; k++)
                    count[i + j + k]++;

        int ans = 1;
        int ansCount = count[1];
        for (int i = 2; i <= maxNumber; i++)
            if (count[i] > ansCount) {
                ans = i;
                ansCount = count[i];
            }

        System.out.println(ans);
    }
}
```

### [분해합](https://www.acmicpc.net/problem/2231)
- 그냥 1부터 다 구해보기
```java
import java.util.Scanner;

class Main
{
    public static int generate(int seed) {
        int generatedNumber = seed;
        while (seed > 0) {
            generatedNumber += seed % 10;
            seed /= 10;
        }
        return generatedNumber;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int ans = 0;
        for (int i = 1; i <= N; i++)
            if (generate(i) == N) {
                ans = i;
                break;
            }

        System.out.println(ans);
    }
}
```

### [더하기 사이클](https://www.acmicpc.net/problem/1110)
```java
import java.util.Scanner;

class Main
{
    public static int getNextNumber(int x) {
        int leftDigit = x % 10;
        int rightDigit = (x / 10 + leftDigit) % 10;
        return leftDigit * 10 + rightDigit;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int cur = N;
        int len = 0;
        do {
            cur = getNextNumber(cur);
            len++;
        } while(cur != N);
        System.out.println(len);
    }
}
```

### [셀프 넘버](https://www.acmicpc.net/problem/4673)
1. 가능한 모든 쌍의 사탕으로 서로 교환(4방향 모두면 중복이 발생하기에, 오른쪽/아래쪽만 확인)
2. 교환한 상태에서 가장 긴 연속 부분 행/열을 찾는다
3. 교환한 사탕을 원복
```java
import java.util.Arrays;

class Main
{
    final static int MAX_NUMBER = 10000;

    public static int generate(int seed) {
        int generatedNumber = seed;
        while (seed > 0) {
            generatedNumber += seed % 10;
            seed /= 10;
        }
        return generatedNumber;
    }

    public static void main (String[] args) {
        boolean[] isSelfNumber = new boolean[MAX_NUMBER + 1];
        Arrays.fill(isSelfNumber, true);

        for (int i = 1; i <= MAX_NUMBER; i++) {
            int seed = i;
            
            if(isSelfNumber[i]) continue;
            
            while (true) {
                int generatedNumber = generate(seed);
                if (generatedNumber > MAX_NUMBER)
                    break;
                isSelfNumber[generatedNumber] = false;
                seed = generatedNumber;
            }
        }

        for (int i = 1; i <= MAX_NUMBER; i++)
            if (isSelfNumber[i])
                System.out.println(i);
    }
}
```

### [문자열](https://www.acmicpc.net/problem/1120)
- A문자열을 B문자열 안에서 이동시키면서
- A문자열, B문자열 차이 중 가장 작은것 뽑아내기
```java
import java.util.Scanner;

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        String A = sc.next();
        String B = sc.next();
        int ans = A.length();
        for (int i = 0; i <= B.length() - A.length(); i++) {
            int cnt = 0;
            for (int j = 0; j < A.length(); j++)
                if (A.charAt(j) != B.charAt(i + j))
                    cnt++;
            ans = Math.min(ans, cnt);
        }
        System.out.println(ans);
    }
}
```

### [체스판 다시 칠하기](https://www.acmicpc.net/problem/1018)
- 가능한 체스판의 경우의 수는 "WB" 패턴이 반복되거나 "BW" 패턴이 반복되는 딱 두가지
1. 전체 칸 중 8*8 체스칸 만들수있을 만큼 순회
2. BW, WB 조합중 작은 것 구하기
3. 8*8 체스판에서 패턴 구하기
   - 짝수칸이면 BW중에 W, WB중에 B 아닌 것
   - 홀수칸이면 BW중에 B, WB중에 W 아닌 것
```java
import java.util.Scanner;

class Main
{
    private static int countDifferent(char[][] map, int starRow, int startColumn, String pattern) {
        int cnt = 0;
        // 8*8 체스판에서 패턴 구하기
        for (int i = starRow; i < starRow + 8; i++)
            for (int j = startColumn; j < startColumn + 8; j++)
                // 짝수칸이면 BW중에 W, WB중에 B 아닌 것
                // 홀수칸이면 BW중에 B, WB중에 W 아닌 것
                if (map[i][j] != pattern.charAt((i + j) % 2))
                    cnt++;
        return cnt;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        char[][] map = new char[N][M];
        for(int i = 0; i < N; i++) {
            String line = sc.next();
            map[i] = line.toCharArray();
        }

        int ans = N * M;
        // 8*8 체스칸 만들수있을 만큼 순회
        for (int i = 0; i <= N - 8; i++)
            for (int j = 0; j <= M - 8; j++) {
                // BW, WB 조합중 작은 것 구하기
                int countBW = countDifferent(map, i, j, "BW");
                int countWB = countDifferent(map, i, j,"WB");
                ans = Math.min(ans, Math.min(countBW, countWB));
            }
        System.out.println(ans);
    }
}
```
