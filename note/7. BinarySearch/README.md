## 이분탐색((O(log n)))
- 주로 "정렬된" 데이터에서 "한 개"의 특정 값을 빠르게 찾는 데 사용
- 함수
  - set.contains() -> O(1)
  - set.floor(x) -> set값 중 x이하이면서 가장 가까운 값(없으면 NPE)
  - set.ceiling(x) -> set값 중 x이상이면서 가장 가까운 값(없으면 NPE)
  - Arrays.binarySearch(arr, x) -> O(logN)
  - Math.sqrt(number) -> 제곱근 구하기
  - Math.pow(2,3) -> 거듭제곱 구하기

### [수 찾기](https://www.acmicpc.net/problem/1920)
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

### [문자열 집합](https://www.acmicpc.net/problem/14425)
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

### [세 수의 합](https://www.acmicpc.net/problem/2295)
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

### [두 용액](https://www.acmicpc.net/problem/2470)
1. 숫자크기대로 정렬
2. 첫번째 용액부터 이분탐색을 통해 다른 용액을 더해 0이랑 가까운 값을 구하기
    - 음수면 오른쪽으로 이분탐색
    - 양수면 왼쪽으로 이분탐색
```java
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());
        int[] solutions = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            solutions[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(solutions);

        int left = 0, right = N - 1;
        int minSum = Integer.MAX_VALUE, result1 = 0, result2 = 0;

        while (left < right) {
            int sum = solutions[left] + solutions[right];
            if (Math.abs(sum) < minSum) {
                minSum = Math.abs(sum);
                result1 = solutions[left];
                result2 = solutions[right];
            }

            if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println(result1 + " " + result2);
    }
}

```

## [숫자 카드 2](https://www.acmicpc.net/problem/10816)
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

### [정수 제곱근](https://www.acmicpc.net/problem/2417)
- Math.sqrt는 double을 리턴하기 때문에 long으로 변환 시 정밀도로 인해 오답이 발생
- 이분탐색 사용
```java
public static void main(String[] args) {
    long N = sc.nextLong();
    long start = 0;
    long end = N;
    long answer = 0;
    
    while(start<=end){
        long mid = (start+end) / 2;
        if(Math.pow(mid,2)>=N){
            answer = mid;
            end = mid-1;
        }else{
            start = mid+1;
        }
    }
    System.out.println(answer);
}
```

### [나무 자르기](https://www.acmicpc.net/problem/2805)
- 나무길이의 합이 int범위를 넘어서기 때문에 sum의 값은 long으로 해야함
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

### [랜선 자르기](https://www.acmicpc.net/problem/1654)
- 2의 31승-1이 int의 최대값이지만 L이 1이라 더하면 int범위 벗어남
- long으로 해야함
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

### [용돈 관리](https://www.acmicpc.net/problem/6236)
```java
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        int[] uses = new int[N];
        int maxUse = 0; // 가장 큰 사용 금액을 찾기 위한 변수

        for (int i = 0; i < N; i++) {
            uses[i] = Integer.parseInt(br.readLine());
            maxUse = Math.max(maxUse, uses[i]); // 최대 사용 금액 업데이트
        }

        int L = maxUse, R = 10000 * N, answer = 0;
        while (L <= R) {
            int mid = (L + R) / 2;
            int count = 1; // M번 인출해야 하므로 최소 한 번은 인출함
            int currentAmount = mid; 

            for (int i = 0; i < N; i++) {
                if (currentAmount < uses[i]) { // 현재 금액으로 부족할 경우
                    count++; // 인출 횟수 증가
                    if (count > M) break; // M번 초과하면 반복 중단
                    currentAmount = mid; // 다시 인출
                }
                // currentAmount는 maxUse보다 무조건 크기때문에 음수가 나올일 없음
                currentAmount -= uses[i]; // 남은 금액 계산
            }

            if (count > M) {
                L = mid + 1; // 인출 횟수가 너무 많으면 금액을 증가
            } else {
                R = mid - 1;
                answer = mid; // 가능한 K 중 최소값 갱신
            }
        }

        System.out.println(answer);
    }
}
```

### [공유기 설치](https://www.acmicpc.net/problem/2110)
- 시작부터 설치하면서 하기 때문에 cnt = 1로 시작
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

### [예산](https://www.acmicpc.net/problem/2512)
```java
import java.util.Scanner;

class Main
{
    static int calcTotalBudget(int[] budgets, int budgetLimit) {
        int sum = 0;
        for (int budget : budgets)
            sum += Math.min(budget, budgetLimit);
        return sum;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] budgets = new int[N];
        int maxBudget = 0;
        for (int i = 0; i < N; i++) {
            budgets[i] = sc.nextInt();
            maxBudget = Math.max(maxBudget, budgets[i]);
        }
        int M = sc.nextInt();

        int l = 1, r = maxBudget, ans = -1;
        while (l <= r) {
            int m = (l + r) / 2;
            int totalBudget = calcTotalBudget(budgets, m);
            if (totalBudget <= M) {
                ans = m;
                l = m + 1;
            }
            else r = m - 1;
        }
        System.out.println(ans);
    }
}
```

### [기타 레슨](https://www.acmicpc.net/problem/2343)
- 처음부터 count=1로 시작
```java
import java.util.Scanner;

class Main
{
    public static boolean isPossible(int[] lengths, int videoLength, int videoCount) {
        int currentLength = 0;
        int currentCount = 1;
        for (int len : lengths) {
            if (len > videoLength) return false;
            if (currentLength + len > videoLength) {
                if (++currentCount > videoCount) return false;
                currentLength = 0;
            }
            currentLength += len;
        }
        return true;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] lengths = new int[N];
        for (int i = 0; i < N; i++)
            lengths[i] = sc.nextInt();

        int l = 1, r = N * 10000, ans = -1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (isPossible(lengths, m, M)) {
                ans = m;
                r = m - 1;
            }
            else l = m + 1;
        }
        System.out.println(ans);
    }
}
```

### [보석 상자](https://www.acmicpc.net/problem/2792)
```java
public static void main(String[] args) {
    int N = sc.nextInt();
    int M = sc.nextInt();
    HashMap<Integer, Integer> hashMap = new HashMap<>();
    int sum = 0;
    int max = 0;
    for(int i=0 ;i<M; i++){
        int x = sc.nextInt();
        hashMap.put(i, x);
        sum+=x;
        max = Math.max(x, max);
    }

    int L=1, R=max, answer = -1;
    while(L<=R){
        int mid = (L+R) / 2;
        // 이 조건이 중요 반대로 하면 계속 숫자가 커짐
        if(determine(hashMap, mid) <= N){ 
            answer = mid;
            R = mid -1;
        }else{
            L = mid +1;
        }
    }
    System.out.println(answer);
}

static int determine(HashMap<Integer, Integer> hashMap, int mid){
    int count = 0;
    for(int n : hashMap.values()){
        count += n / mid;
        if(n%mid !=0){
            count++;
        }
    }
    return count;
}
```

### [두 배열의 합](https://www.acmicpc.net/problem/2143)
- T - A부분합 = B부분합 으로 구하면 더 빠름
- A부분합 계산
- B부분합 계산, sort
- B부분합 배열에 중복값이 있을수 있으므로, B부분합이 나오는 첫 index ~ B부분합 초과 index 구해주기
- 결과값 : B부분합 초과 index - B부분합 첫 index
```java
import java.util.Arrays;
import java.util.Scanner;

class Main
{
    static int findLowerBoundIndex(int[] arr, int x) {
        // x 이상의 값이 처음으로 나타나는 위치
        int lowerBoundIndex = arr.length;
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (arr[m] < x) l = m + 1;
            else {
                r = m - 1;
                lowerBoundIndex = m;
            }
        }
        return lowerBoundIndex;
    }

    static int findUpperBoundIndex(int[] arr, int x) {
        // x 초과의 값이 처음으로 나타나는 위치
        int upperBoundIndex = arr.length;
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (arr[m] <= x) l = m + 1;
            else {
                r = m - 1;
                upperBoundIndex = m;
            }
        }
        return upperBoundIndex;
    }

    static int[] getAllPartSum(int[] arr) {
        int N = arr.length;
        int[] partSum = new int[N * (N + 1) / 2];
        int partSumIndex = 0;
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = i; j < N; j++) {
                sum += arr[j];
                partSum[partSumIndex++] = sum;
            }
        }
        return partSum;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        int N = sc.nextInt();
        int[] arr1 = new int[N];
        for (int i = 0; i < N; i++)
            arr1[i] = sc.nextInt();

        int M = sc.nextInt();
        int[] arr2 = new int[M];
        for (int i = 0; i < M; i++)
            arr2[i] = sc.nextInt();

        int[] partSum1 = getAllPartSum(arr1);
        int[] partSum2 = getAllPartSum(arr2);

        Arrays.sort(partSum2);

        long ans = 0;
        for (int sum1 : partSum1) {
            int pairSum = T - sum1;
            int lowerBoundIndex = findLowerBoundIndex(partSum2, pairSum);
            int upperBoundIndex = findUpperBoundIndex(partSum2, pairSum);
            ans += upperBoundIndex - lowerBoundIndex;
        }
        System.out.println(ans);
    }
}
```