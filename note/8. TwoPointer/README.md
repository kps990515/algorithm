## Two Pointer
- (주로) 선형 데이터구조에서 두개의 인덱스를 관리하여 
- 특정 조건을 만족하는 부분집합이나 특정값을 찾는 알고리즘
- BinarySearch는 정렬이 필요하지만 Two Pointer는 필요없음

### [수들의 합 2](https://www.acmicpc.net/problem/2003)
- 구간합이 M보다 크거나 같을때 까지 j 한칸씩 이동하면서 더해줌
- 합이 M이면 답을 증가
- 구간합을 재활용하기 위해 맨 처음값(i)만 구간합에서 제거
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++)
            arr[i] = sc.nextInt();

        int ans = 0;
        int nextIndex = 0;
        int currentSum = 0;
        for (int i = 0; i < N; i++) {
            while (currentSum < M && nextIndex < N) {
                // 1. 구간합이 M보다 크거나 같을때 까지 j 한칸씩 이동
                currentSum += arr[nextIndex++];
            }
            // 2. 합이 M이면 답을 증가
            if (currentSum == M) ans++;
            // 3. 구간합을 재활용하기 위해 맨 처음값만 구간합에서 제거
            currentSum -= arr[i];
        }

        System.out.println(ans);
    }
}
```

### [두 용액](https://www.acmicpc.net/problem/2470)
- 정렬
- 맨 처음값과 맨 뒤에 값을 더해주기
- 배열을 순회하면서 가장 0에 가까운 두 요소의 합 찾기
- 현재의 절대값이 이전 최소값보다 작으면 업데이트
- 현재 합이 0보다 크면 오른쪽 포인터 왼쪽으로 이동 / 0보다 작으면 왼쪽포인터 오른쪽으로 이동
```java
public class Main {
    static FastReader scan = new FastReader();
    static StringBuilder sb = new StringBuilder();

    static int N;
    static int[] A;

    static void input() {
        N = scan.nextInt();
        A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = scan.nextInt();
        }
    }

    static void pro() {
        // 최소, 최대 원소를 빠르게 찾기 위해서 정렬을 미리 해주자.
        Arrays.sort(A, 1, N + 1);

        int best_sum = Integer.MAX_VALUE;
        // L은 제일 작은 원소, R은 제일 큰 원소
        int v1 = 0, v2 = 0, L = 1, R = N;

        while (L < R) {  // L == R 인 상황이면 용액이 한 개 뿐인 것이므로, L < R 일 때까지만 반복한다.
            if (best_sum > Math.abs(A[L] + A[R])) {
                best_sum = Math.abs(A[L] + A[R]);
                v1 = A[L];
                v2 = A[R];
            }
            // L+R > 0 이므로 최대 입장에서 L의 최선을 만난 상태 -> 이제 고려안함
            if (A[L] + A[R] > 0) R--;
            else L++;
        }
        sb.append(v1).append(' ').append(v2);
        System.out.println(sb);
    }

    public static void main(String[] args) {
        input();
        pro();
    }
}
```

### [부분합](https://www.acmicpc.net/problem/1806)
- 구간합이 M보다 크거나 같을때 까지 j 한칸씩 이동
- 구간합이 M보다 크거나 같을때 기존 길이와 지금 i~j의 길이중 짧은걸 업데이트
- 시작 구간합 삭제
- S이상이 되는 부분합이 없다면 답이 없으니까 0으로 출력
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++)
            arr[i] = sc.nextInt();

        int ansLength = N + 1;
        int nextIndex = 0;
        int currentSum = 0;
        for (int i = 0; i < N; i++) {
            while (currentSum < M && nextIndex < N)
                // 1. 구간합이 M보다 크거나 같을때 까지 j 한칸씩 이동
                currentSum += arr[nextIndex++];
                // 2. 구간합이 M보다 크거나 같을때 기존 길이와 지금 i~j의 길이중 짧은걸 업데이트
                if (currentSum >= M) ansLength = Math.min(ansLength, nextIndex - i);
                // 3. 시작 구간합 삭제
                currentSum -= arr[i];
        }
        // 4. S이상이 되는 부분합이 없다면 답이 없으니까 0으로 출력
        if (ansLength > N) ansLength = 0;
        System.out.println(ansLength);
    }
}
```

### [수 고르기](https://www.acmicpc.net/problem/2230)
- 두수의 차이가 M미만이고, rightIndex가 끝에 도달하지 않을때까지 rightIndex증가
- 두 수의 차이 구하기
- 기존값과 비교해서 작은걸로 없데이트
```java
  public static void main(String[] args) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] numbers = new int[N];
      for(int i=0; i<N; i++){
          numbers[i] = sc.nextInt();
      }

      Arrays.sort(numbers);

      int R = 0, answer = Integer.MAX_VALUE;
      for(int i=0; i<N; i++){
          while(R < N){
              if(numbers[R] - numbers[i] >=M){
                  answer = Math.min(answer, numbers[R] - numbers[i]);
                  break;
              }
              R++;
          }
      }
      System.out.println(answer);
  }
```

### [DNA 비밀번호](https://www.acmicpc.net/problem/12891)
- 최초 부분 시퀀스에 대해 A,C,G,T개수 계산하고 조건을 만족하는지 확인
- 슬라이딩 윈도우를 통해 나머지 부분 검사
  - 다음 문자 추가하고 A,C,G,T개수 계산 
  - 시퀀스 첫부분 제거하고 A,C,G,T개수 계산
  - 조건 만족했는지 확인
```java
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
```

### 문제6. 원형으로 연결된 N개의 지점 중 두곳에 탑을 세울 때 가능한 거리의 최댓값
- 환형구조를 나타낼때 선형자료구조(배열)를 2배로 늘리면 인덱스 관리가 편함
- 시계방향 거리가 반시계방향거리보다 작을때까지 반복
  - 시계방향에 다음지점 거리 더하기
  - 반시계방향에 다음 지점 거리 빼기
  - 다음 지점 구하기
- 다음 지점으로 윈도우 이동
  - 시계방향거리에서 전 지점 거리 빼기
  - 반시계방향거리에서 전 지점 거리 더하기
```java
import java.util.Scanner;

class Main {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        // 사용자로부터 원형 배열의 지점 수 N을 입력받음
        int N = sc.nextInt();
        int[] distance = new int[N];
        int distanceSum = 0; // 모든 지점 간의 거리 합을 저장할 변수

        // 지점 간의 거리를 입력받아 총합 계산
        for (int i = 0; i < N; i++) {
            distance[i] = sc.nextInt(); // 각 지점간 거리 입력
            distanceSum += distance[i]; // 전체 거리 합산
        }

        // 초기 분할을 설정하여 첫 지점을 기준으로 두 그룹으로 나눔
        int pairIndex = 1; // // 첫 시작점은 0번, 분할 지점은 1번부터 시작
        int leftDistance = distance[0]; // 시계방향, 시작점에서 1번까지의 거리
        int rightDistance = distanceSum - distance[0]; // 반시계방향 거리
        int maxDistance = Math.min(leftDistance, rightDistance); // 두 그룹 간의 거리의 최소값을 최대 거리로 초기 설정

        // 각 지점을 기준으로 순환하며 최적의 분할점을 찾음
        for (int i = 0; i < N; i++) {
            // 1.시계방향 거리가 반시계방향거리보다 작을때까지 반복
            while (leftDistance < rightDistance) {
                leftDistance += distance[pairIndex]; // 1-1.시계방향에 다음 지점 거리 더하기
                rightDistance -= distance[pairIndex]; // 1-2.반시계방향에 다음 지점 거리 빼기
                pairIndex = (pairIndex + 1) % N; // 1-3.원형 배열을 고려하여 인덱스 순환
            }
            // 최대 거리 업데이트
            maxDistance = Math.max(maxDistance, Math.min(leftDistance, rightDistance));

            // 2. 다음 지점으로 윈도우를 이동
            leftDistance -= distance[i]; // 2-1.시계방향거리에서 전 지점 거리 빼기
            rightDistance += distance[i]; // 2-2.반시계방향거리에서 전 지점 거리 더하기
        }
        // 계산된 최대 균등 거리를 출력
        System.out.println(maxDistance);
    }
}

```

### 문제7. 정렬된 두 배열 A,B를 합쳐서 정렬하기
- A,B 0번째 인덱스 확인해서 작은거를 먼저 넣어주기
- 작은 배열의 인덱스 +1
- 다시 A,B 비교해주기
- 다하고나서 남은 A나 B의 데이터 넣어주기
```java
import java.io.*;
import java.util.Scanner;

class Main {
    public static void main (String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // 배열 A의 크기 N과 배열 B의 크기 M을 입력 받음
        int N = sc.nextInt();
        int M = sc.nextInt();

        // 배열 A와 B를 선언하고 입력 값을 받음
        int[] a = new int[N];
        int[] b = new int[M];
        for (int i = 0; i < N; i++)
            a[i] = sc.nextInt(); // 배열 A의 모든 요소를 입력 받음
        for (int i = 0; i < M; i++)
            b[i] = sc.nextInt(); // 배열 B의 모든 요소를 입력 받음

        // 결과 배열 ans를 선언 (크기는 배열 A와 B의 크기의 합)
        int[] ans = new int[N + M];
        // 각 배열에 대한 인덱스 포인터
        int indexA = 0; // 배열 A의 인덱스
        int indexB = 0; // 배열 B의 인덱스
        int indexAnswer = 0; // 결과 배열의 인덱스

        // 두 배열의 요소를 비교하며 결과 배열을 채움
        while (indexA < N && indexB < M) {
            // 배열 A의 요소가 배열 B의 요소보다 작거나 같으면 배열 A의 요소를 결과 배열에 추가
            if (a[indexA] < b[indexB])
                ans[indexAnswer++] = a[indexA++];
            else // 배열 B의 요소가 배열 A의 요소보다 작으면 배열 B의 요소를 결과 배열에 추가
                ans[indexAnswer++] = b[indexB++];
        }

        // 배열 A에 남은 요소가 있으면 결과 배열에 추가
        while (indexA < N) {
            ans[indexAnswer++] = a[indexA++];
        }

        // 배열 B에 남은 요소가 있으면 결과 배열에 추가
        while (indexB < M) {
            ans[indexAnswer++] = b[indexB++];
        }

        // BufferedWriter를 사용하여 결과 배열의 모든 요소를 출력
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int x : ans)
            bw.write(x + " "); // 결과 배열의 요소를 공백으로 구분하여 출력
        bw.write("\n"); // 줄바꿈 문자 추가
        bw.flush(); // 스트림을 비움
    }
}
```
- Stream 사용법
```java
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();


    public static void main(String[] args) {
        // 배열 A의 크기 N과 배열 B의 크기 M을 입력 받음
        int N = sc.nextInt();
        int M = sc.nextInt();

        // 배열 A와 B를 선언하고 입력 값을 받음
        int[] a = new int[N];
        int[] b = new int[M];
        for (int i = 0; i < N; i++)
            a[i] = sc.nextInt(); // 배열 A의 모든 요소를 입력 받음
        for (int i = 0; i < M; i++)
            b[i] = sc.nextInt(); // 배열 B의 모든 요소를 입력 받음

        int[] merged = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).sorted().toArray();

        for (int temp : merged) {
            sb.append(temp).append(" ");
        }
        System.out.println(sb);
    }
}
```

### 문제 : 주어진 문자열이 회문인지, 유사회문인지, 그 외인지 확인하는 문제
- 첫글자, 끝글자가 같으면 회문
- 다를시 첫글자 +1 이 회문인지, 끝글자 -1이 유사회문인지 확인
- 둘다 아니면 회문이 아닌것으로 리턴
```java
import java.util.Arrays;
import java.util.Scanner;

class Main {
    // 주어진 부분 문자열이 회문인지 확인하는 함수
    static boolean isPalindrome(char[] str, int first, int last) {
        while (first <= last) {
            if (str[first] != str[last]) return false;  // 문자가 서로 다르면 회문이 아님
            first++;
            last--;
        }
        return true;  // 모든 문자가 일치하면 회문임
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();  // 테스트 케이스의 수 입력
        while (T-- > 0) {  // 각 테스트 케이스에 대해 실행
            char[] str = sc.next().toCharArray();  // 문자열을 char 배열로 변환
            int ans = 0;  // 회문 상태를 나타내는 변수, 0: 완전한 회문, 1: 한 글자 제거로 회문 가능, 2: 회문 불가능
            int first = 0, last = str.length - 1;  // 양 끝에서 시작하는 포인터
            while (first <= last) {
                if (str[first] != str[last]) {  // 양 끝의 문자가 다를 때
                    // 왼쪽 문자 또는 오른쪽 문자를 제거하여 회문이 되는지 검사
                    if (isPalindrome(str, first + 1, last) || isPalindrome(str, first, last - 1)){
                        ans = 1;
                    }
                    else ans = 2;  // 두 경우 모두 회문이 되지 않으면 회문을 만들 수 없음
                    break;
                }
                first++;  // 문자가 같으면 양 끝 포인터를 안쪽으로 이동
                last--;
            }
            System.out.println(ans);  // 결과 출력
        }
    }
}
```

### 문제. N개의 조약돌 중 구간의 까만색 조약돌이 B이하, 하얀색 조약돌이 W이상인 가장 긴 연속구간길이
- 다음 인덱스가 N보다 작고, 현재 'B'의 개수가 B와 같은데 다음 문자도 'B'라면 중단
- W이면 흰색 개수 증가
- B이면 검은색 개수 증가
- 현재 부분 문자열에서 'W'의 개수가 W 이상이면 최대 길이 갱신
- 슬라이딩 윈도우를 오른쪽으로 이동시키기 전에 현재 시작 문자를 제거
  - 문자가 W이면 흰색 개수 감소
  - 문자가 B이면 검은색 개수 감소
```java
import java.util.Scanner;

class Main {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        // 사용자로부터 전체 문자열의 길이 N, 허용되는 최대 B개의 'B', 최소 W개의 'W'를 입력받음
        int N = sc.nextInt();
        int B = sc.nextInt();
        int W = sc.nextInt();
        char[] color = sc.next().toCharArray();  // 문자열을 char 배열로 변환

        int currentWhiteCount = 0;  // 현재 부분 문자열 내 'W'의 개수
        int currentBlackCount = 0;  // 현재 부분 문자열 내 'B'의 개수
        int ansLength = 0;  // 조건을 만족하는 최대 부분 문자열의 길이
        int nextIndex = 0;  // 다음 문자를 가리키는 인덱스

        for (int i = 0; i < N; i++) {
            // 다음 인덱스가 N보다 작고, 현재 'B'의 개수가 B와 같은데 다음 문자도 'B'라면 중단
            while (nextIndex < N) {
                if (currentBlackCount == B && color[nextIndex] == 'B') break;
                if (color[nextIndex++] == 'W') currentWhiteCount++;  // 'W'이면 흰색 개수 증가
                else currentBlackCount++;  // 'B'이면 검은색 개수 증가
            }

            // 현재 부분 문자열에서 'W'의 개수가 W 이상이면 최대 길이 갱신
            if (currentWhiteCount >= W)
                ansLength = Math.max(ansLength, nextIndex - i);

            // 슬라이딩 윈도우를 오른쪽으로 이동시키기 전에 현재 시작 문자를 제거
            if (color[i] == 'W') currentWhiteCount--;  // 시작 문자가 'W'이면 흰색 개수 감소
            else currentBlackCount--;  // 시작 문자가 'B'이면 검은색 개수 감소
        }

        // 계산된 최대 길이 출력
        System.out.println(ansLength);
    }
}
```

### 문제. 소문자로 이루어진 문자열에서 N개 이하의 알파벳 종류만 사용되는 가장 긴 부분문자열의 길이
- nextIndex를 증가시키며 increaseFrequency
- 알파벳 종류 초과하면 중단
  - 마지막 알파벳 decreaseFrequency
- 기존길이와 현재길이 비교
- 현재 첫 문자 decreaseFrequency
```java
import java.util.Scanner;

class Main {
    // 26개의 알파벳 빈도를 저장하는 배열
    static int[] currentAlphabetFrequency = new int[26];
    // 현재 고유 알파벳의 개수를 저장하는 변수
    static int currentUniqueAlphabetCount = 0;

    // 특정 알파벳의 빈도를 증가시키는 메서드
    static void increaseFrequency(char alphabet) {
        // 알파벳의 빈도가 0에서 1로 바뀌면 고유 알파벳 개수 증가
        if (currentAlphabetFrequency[alphabet - 'a']++ == 0)
            currentUniqueAlphabetCount++;
    }

    // 특정 알파벳의 빈도를 감소시키는 메서드
    static void decreaseFrequency(char alphabet) {
        // 알파벳의 빈도가 1에서 0으로 바뀌면 고유 알파벳 개수 감소
        if (--currentAlphabetFrequency[alphabet - 'a'] == 0)
            currentUniqueAlphabetCount--;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 고유한 알파벳의 최대 개수 N 입력받기
        int N = sc.nextInt();
        // 문자열을 입력받아 char 배열로 변환
        char[] nyang = sc.next().toCharArray();
        int nextIndex = 0;  // 슬라이딩 윈도우의 끝점을 나타내는 변수
        int maxLength = 0; // 조건을 만족하는 최대 길이를 저장하는 변수

        // 슬라이딩 윈도우의 시작점을 `i`로 설정하고 순회
        for (int i = 0; i < nyang.length; i++) {
            // `nextIndex`를 증가시키며 윈도우 확장
            while (nextIndex < nyang.length) {
                // 현재 문자의 빈도를 증가
                increaseFrequency(nyang[nextIndex++]);
                // 고유 알파벳의 개수가 N을 초과하면 윈도우 확장 중단
                if (currentUniqueAlphabetCount > N) {
                    // 확장했던 마지막 문자를 다시 제거
                    decreaseFrequency(nyang[--nextIndex]);
                    break;
                }
            }
            // 현재 윈도우 길이를 계산하고 최대 길이를 갱신
            maxLength = Math.max(maxLength, nextIndex - i);
            // 슬라이딩 윈도우의 시작점을 한 칸 오른쪽으로 이동하기 위해 현재 문자를 제거
            decreaseFrequency(nyang[i]);
        }
        // 최대 길이를 출력
        System.out.println(maxLength);
    }
}
```