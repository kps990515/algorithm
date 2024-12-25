## 이분탐색((O(log n)))
- 주로 "정렬된" 데이터에서 "한 개"의 특정 값을 빠르게 찾는 데 사용
- 함수
  - set.contains() -> O(1)
  - set.floor(x) -> set값 중 x이하이면서 가장 가까운 값
  - set.ceiling(x) -> set값 중 x이상이면서 가장 가까운 값
  - Arrays.binarySearch(arr, x) -> O(logN)
  - Math.sqrt(number) -> 제곱근 구하기

### 문제1. N개의 정수가 있을때 X라는 정수가 있는지 확인
- set사용
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < N; i++)
            set.add(sc.nextInt());
        int M = sc.nextInt();
        while (M-- > 0) {
            int x = sc.nextInt();
            System.out.println(set.contains(x) ? 1 : 0);
        }
    }
}
```
- 이분탐색 사용
```java
class Main
{
    static boolean isExist(int[] arr, int x) {
        int start = 0, end = arr.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (arr[middle] < x) start = middle + 1; // x가 중간값보다 큰 경우
            else if (arr[middle] > x) end = middle - 1; // x가 중간값보다 작은 경우
            else return true; // x가 중간값과 같은 경우
        }
        return false; // 찾지 못한 경우
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++)
            arr[i] = sc.nextInt();

        Arrays.sort(arr);

        int M = sc.nextInt();
        while (M-- > 0) {
            int x = sc.nextInt();
            boolean ans = isExist(arr, x);
            System.out.println(ans ? 1 : 0);
            //int idx = Arrays.binarySearch(arr, x);
            // System.out.println(idx >= 0 ? 1 : 0);
        }
    }
} 
```

### 문제2. N개의 문자열로 이루어진 집합 S, M개의 문자열 중 집합 S에 포함된 문자열 개수
- Set으로 풀기
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < N; i++)
            set.add(sc.next());

        int count = 0;
        while (M-- > 0) {
            String x = sc.next();
            if (set.contains(x))
                count++;
        }
        System.out.println(count);
    }
}
```
- 이분탐색으로 풀기
```java
class Main
{
  static boolean isExist(String[] arr, String x) {
    int l = 0, r = arr.length - 1;
    while (l <= r) {
      int m = (l + r) / 2;
      int compareResult = arr[m].compareTo(x);
      if (compareResult < 0) l = m + 1;
      else if (compareResult > 0) r = m - 1;
      else return true;
    }
    return false;
  }

  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int M = sc.nextInt();
    String[] arr = new String[N];
    for (int i = 0; i < N; i++)
      arr[i] = sc.next();

    Arrays.sort(arr);

    int count = 0;
    while (M-- > 0) {
      String x = sc.next();
      if (isExist(arr, x))
        count++;
//    if (Arrays.binarySearch(arr, x) >= 0)
//      count++;
    }
    System.out.println(count);
  }
} 
```

### 문제3. 자연수로 이루어진 집합 U에서 3개를 골랐을때 3개의 합 d도 U안에 포함되어있으면서 가장 큰 d
- A + B + C = X를 A + B = X - C 로 바꾸면 O(N제곱logN)
```java
class Main
{
  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int[] arr = new int[N];
    for (int i = 0; i < N; i++)
      arr[i] = sc.nextInt();

    Set<Integer> sums = new HashSet<>();
    // A+B 구하기
    for (int i = 0; i < N; i++)
      for (int j = i; j < N; j++)
        sums.add(arr[i] + arr[j]);

    int ans = -1;
    // X-C구하기
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++) {
        int target = arr[i] - arr[j];
        if (sums.contains(target))
          ans = Math.max(ans, arr[i]);
      }
    System.out.println(ans);
  }
} 
```

### 문제4. N개의 용액의 특성값이 [-10억, 10억]범위로 주어질때 서로 다른 용액을 더했을때 0에 가장 가까운 값
1. 숫자크기대로 정렬
2. 첫번째 용액부터 이분탐색을 통해 다른 용액을 더해 0이랑 가까운 값을 구하기
    - 음수면 오른쪽으로 이분탐색
    - 양수면 왼쪽으로 이분탐색
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        int ansAbs = 2000000001;
        int ans1 = 0;
        int ans2 = 0;
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            // 용액 값 x
            int x = sc.nextInt();
            // -x이하 값, -x이상 값중 가장 가까운 값들
            Integer[] pairValues = {set.floor(-x), set.ceiling(-x)};
            for (Integer pairValue : pairValues) {
                if (pairValue == null) continue;
                int sumAbs = Math.abs(x + pairValue);
                if (ansAbs > sumAbs) {
                    ansAbs = sumAbs;
                    ans1 = x;
                    ans2 = pairValue;
                }
            }
            // 어차피 -x의 값을 구하는거기 때문에 해당 용액의 왼쪽에 있는값들만 봄
            // set.floor, set.ceiling에 x가 나오지 않게 하기 위해 마지막에 add
            set.add(x);
        }
        System.out.println(Math.min(ans1, ans2) + " " + Math.max(ans1, ans2));
    }
}
```

## 문제5. N개의 숫자카드에 M값의 숫자카드가 몇개씩 있는가
```java
class Main
{
    public static void main (String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            // 숫자카드의 숫자마다 1을 더해줌
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int M = sc.nextInt();
        while (M-- > 0) {
            int x = sc.nextInt();
            // M의값이 있으면 map의 value반환, 없으면 0
            bw.write(map.getOrDefault(x, 0) + " ");
        }
        bw.write("\n");
        bw.flush();
    }
}
```

### 문제6. 제곱근 구하기
- Math.sqrt 사용
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("정수를 입력하세요: ");
        int number = scanner.nextInt();

        // 제곱근을 계산하고, 결과를 정수로 변환
        int sqrtResult = (int) Math.sqrt(number);

        System.out.println("입력한 정수의 제곱근(정수 부분): " + sqrtResult);
        
        scanner.close();
    }
}
```
- 이분탐색 사용
```java
class Main
{
  static long calcSqrtInteger(long x) {
    if (x == 0) return 0;
    // 1L << 32 : 32비트이동 -> 2의 32승
    long start = 1, last = 1L << 32, sqrt = -1;
    while(l <= r) {
      // m: 이분탐색의 중앙값  
      long m = (start + last) / 2;
      if (m*m >= x) {
        last = m - 1;
        sqrt = m;
      }
      else last = m + 1;
    }
    return sqrt;
  }

  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);

    long N = sc.nextLong();
    System.out.println(calcSqrtInteger(N));
  }
} 
```

### 문제7. M미터 이상의 나무를 가져갈 수 있는 절단기 높이의 최대값
```java
class Main
{
    static boolean isPossible(int[] heights, int cutHeight, int thresholdHeight) {
        long sum = 0;
        for (int height : heights)
            if (height > cutHeight) sum += height - cutHeight;
        return sum >= thresholdHeight;
    }

    public static void main (String[] args) {
      Scanner sc = new Scanner(System.in);
  
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] h = new int[N];
      for (int i = 0; i < N; i++)
        h[i] = sc.nextInt();
  
      int start = 0, last = 1000000000, ans = -1;
      while (start <= last) {
        int m = (start + last) / 2;
        if (isPossible(h, m, M)) {
          ans = m;
          start = m + 1;
        }
        else last = m - 1;
      }
      System.out.println(ans);
    }
  }
```

### 문제8. 길이가 다른 K개 랜선으로 N개의 같은 길이의 랜선으로 만들때 만들 수 있는 최대 랜선 길이
```java
class Main
{
    static int calculateCount(int[] lengths, long cutLength) {
        int cnt = 0;
        for (int length : lengths)
            cnt += length / cutLength;
        return cnt;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int K = sc.nextInt();
        int N = sc.nextInt();
        int[] len = new int[K];
        for (int i = 0; i < K; i++)
            len[i] = sc.nextInt();

        long start = 1, last = (1L << 31) - 1, ans = -1;
        while (start <= last) {
            long m = start + (last - start) / 2;
            if (calculateCount(len, m) >= N) {
                ans = m;
              start = m + 1;
            }
            else last = m - 1;
        }
        System.out.println(ans);
    }
}
```

### 문제9. N일동안 사용할 금액이 있을때, M번 K원 인출할때 K의 최소값
```java
class Main
{
    static boolean isPossible(int[] useAmounts, int drawAmount, int maxDrawCount) {
        int drawCount = 1;
        int currentAmount = drawAmount;
        for (int useAmount : useAmounts) {
            // 한번 사용금액이 인출금액보다 크면 종료
            if (useAmount > drawAmount) return false;
            if (currentAmount < useAmount) {
                // 인출횟수가 다 되면 종료
                if (drawCount == maxDrawCount) return false;
                // 남은금액이 사용할금액보다 적으면 인출한번 더
                drawCount++;
                // 현재금액 = 인출금액
                currentAmount = drawAmount;
            }
            // 현재금액 = 보유금액 - 사용금액
            currentAmount -= useAmount;
        }
        return true;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] useAmounts = new int[N];
        for (int i = 0; i < N; i++)
            useAmounts[i] = sc.nextInt();

        int start = 1, last = N * 10000, ans = -1;
        while (start <= last) {
            int m = (start + last) / 2;
            if (isPossible(useAmounts, m, M)) {
                ans = m;
                last = m - 1;
            }
            else start = m + 1;
        }
        System.out.println(ans);
    }
}
```

### 문제10. N개의 집 중 C개의 집을 골라 공유기 설치할때 가장 인접한 공유기 사이의 최대거리
```java
class Main
{
    static int calculateCount(int[] xs, int distance) {
        int pastX = xs[0];
        int cnt = 1;
        for (int i = 1; i < xs.length; i++)
            if (xs[i] - pastX >= distance) {
                pastX = xs[i];
                cnt++;
            }
        return cnt;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int C = sc.nextInt();
        int[] xs = new int[N];
        for (int i = 0; i < N; i++)
            xs[i] = sc.nextInt(); // 집의 x좌표들

        Arrays.sort(xs);

        int start = 1, last = xs[N - 1] - xs[0], ans = -1;
        while (start <= last) {
            int m = (start + last) / 2;
            if (calculateCount(xs, m) >= C) {
                ans = m;
              start = m + 1;
            }
            else last = m - 1;
        }
        System.out.println(ans);
    }
}
```