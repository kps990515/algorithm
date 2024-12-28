## 재귀함수
- 자기 자신을 호출하는 함수
- 하나의 커다란 문제를 작은 문제로 나누어 해결하기 위해
- 문제를 귀납적으로 풀기 위해서 : i번째 답을 구하기 위해 i-1, i-2의 결과를 활용
- 백트래킹 : 다른 독립적 경우의 수 진행을 위해 원 상태로 복구하는 것

## 잘 설계하는 법
- BaseCase : 재귀호출을 멈추고 함수가 종료되는 조건(무조건 하나 존재 필요)
- RecursiveCase : 재귀호출로 문제를 작은 부분으로 쪼개야함(제일 작은부분은 BaseCase로 수렴해야함)
- 귀납적 사고 : 큰 문제를 작은 부분문제로 분할
- 중복계산 방지

## 순열 : 집합안에서 가능한 모든 조합을 나열하는 것
- {1,2,3} : {1,2,3},{1,3,2},{2,1,3}...
- 특징
  - 중복이 없는 원소 집합: n!개의 순열 생성 가능
  - 중복이 있는 경우라면: n! / ((n1중복횟수)! * (n2중복횟수)! * (n3중복횟수)!)

### 숫자출력
- 오름차순
```java
class Main{
    public void asc(int n){
        if(n==0) return;
        
        asc(n-1);
        System.out.printf("%d", n);
    }
}
```
- 내림차순
```java
class Main{
    public void asc(int n){
        if(n==0) return;
        
        System.out.printf("%d", n);
        desc(n-1);
    }
}
```

### 피보나치
- 시간 초과 케이스 : 이미 계산된 결과값이 있음에도 활용하지않고 중복 계산
```java
class Main{
    public int fibo(int n){
        if(n==1 || n==2) return 1; //baseCase
        return fibo(n-1)+fibo(n-2); // RecursiveCase
    }
}
```
- 결과값 재활용
```java
class Main{
    static int[] cache = new int[50];
    
    public int fibo(int n){
        //baseCase
        if(n==1 || n==2) return 1; 
        if(cache[n] != 0) return cache[n]; // 데이터 재활용(중복계산방지)

        // RecursiveCase
        cache[n] = fibo(n-1)+fibo(n-2);
        return cache[n];
    }
}
```

### 문제 : N개의 자연수 집합에서 M개를 고른 수열, 수열은 사전순, 중복되는 수열은 한번반 출력
- N개의 자연수를 사전순으로 정렬
- depth가 m에 도달할때 까지 재귀로 순열 생성
  - 가능한 숫자 선택 : 숫자가 순열에 사용되면 true로 세팅
  - 백트래킹
    - i 추가한 뒤 순열 길이가 되면 i false로 세팅
    - i++로 넘어감 
```java
class Main {
    // 전역 변수 선언
    public static int[] numbers; // 입력받은 숫자를 저장하는 배열
    public static int[] output;  // 순열을 저장하는 배열
    public static boolean[] check; // 숫자를 사용했는지 여부를 기록하는 배열

    public static int n; // 입력된 숫자의 개수
    public static int m; // 생성할 순열의 길이

    // 순열 생성 메서드 (백트래킹)
    public static void perm(int depth) {
        // BaseCase: 순열의 길이가 m이면 출력
        if (depth == m) {
            for (int i = 0; i < m; i++) {
                System.out.print(output[i] + " "); // 순열 출력
            }
            System.out.println();
            return;
        }

        // 모든 수를 순회하며 순열 생성
        for (int i = 0; i < n; i++) {
            if (!check[i]) { // 아직 사용되지 않은 숫자인 경우
                check[i] = true; // 해당 숫자를 사용 처리
                output[depth] = numbers[i]; // 순열의 현재 위치에 숫자를 배치
                perm(depth + 1); // 다음 위치로 재귀 호출
                check[i] = false; // 백트래킹을 위해 사용 처리 해제
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); // 입력된 숫자의 개수
        m = sc.nextInt(); // 생성할 순열의 길이

        numbers = new int[n]; // 입력받은 숫자를 저장할 배열
        output = new int[m];  // 순열을 저장할 배열
        check = new boolean[n]; // 숫자의 사용 여부를 기록할 배열

        for (int i = 0; i < n; i++) {
            numbers[i] = sc.nextInt(); // 숫자 입력받기
        }
        Arrays.sort(numbers); // 숫자를 오름차순으로 정렬
        perm(0); // 순열 생성 시작
    }
}
```

### 문제 : N개의 자연수 집합에서 M개를 고른 수열, 수열은 사전순, 중복되는 수열은 한번반 출력
### + 추가조건 : 수열은 오름차순이어야 한다.
- i번째 재귀에서 배열의 i번째부터 사용했다고 하면, i+1번째 재귀에서는 i+1부터 사용하면 된다
- start+1을 추가
```java
import java.util.*;

public class Main {

    public static int[] numbers;    // 입력 받은 숫자들을 저장할 배열
    public static int[] output;     // 현재 생성 중인 조합을 저장할 배열
    public static boolean[] check;  // 각 숫자의 사용 여부를 체크하는 배열

    // 출력 함수: 현재 조합을 출력합니다.
    public static void print(int[] arr, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(arr[i]).append(" "); // 조합의 각 원소를 공백과 함께 추가
        }
        System.out.println(sb); // 완성된 문자열 출력
    }

    // 조합을 생성하는 재귀 함수
    public static void perm(int depth, int start) {
        if (depth == m) { // BaseCase : 조합이 목표 길이에 도달했을 경우
            print(output, m); // 조합 출력
            return;
        }
        // RecursiveCase
        for (int i = start; i < n; i++) { // 시작 인덱스부터 숫자를 하나씩 고려
            if (!check[i]) { // 현재 숫자가 사용되지 않았다면
                check[i] = true; // 사용 표시
                output[depth] = numbers[i]; // 현재 깊이에 숫자 저장
                perm(depth + 1, start + 1); // 다음 깊이를 위해 재귀 호출
                check[i] = false; // 사용 표시 해제 (백트래킹)
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 숫자 개수 입력 받음
        int m = sc.nextInt(); // 조합의 길이 입력 받음

        numbers = new int[n]; // 입력 숫자 저장 배열 초기화
        output = new int[m];  // 조합 저장 배열 초기화
        check = new boolean[n]; // 숫자 사용 체크 배열 초기화

        for (int i = 0; i < n; i++) {
            numbers[i] = sc.nextInt(); // 숫자 입력 받음
        }
        Arrays.sort(numbers); // 조합을 사전 순으로 출력하기 위해 입력 받은 숫자 정렬

        perm(0,0); // 조합 생성 시작
    }
}
```

### 문제 : N개의 자연수 집합에서 M개를 고른 수열, 수열은 사전순, 중복되는 수열은 한번반 출력
### + 추가조건 : 같은 수를 여러번 골라도된다
- check 배열이 필요 없음
- StringBuilder 사용

```java
class Main {
    // 숫자 배열, 결과 배열, 방문 여부 배열을 선언합니다.
    public static int[] numbers;
    public static int[] output;
    public static int n,m;
    public static StringBuilder sb = new StringBuilder();  // 결과를 저장할 StringBuilder

    // 순열 함수: 순열을 생성합니다.
    public static void perm(int depth) {
        // BaseCase : 깊이(depth)가 m에 도달하면 출력 함수를 호출합니다.
        if (depth == m) {
            for(int i = 0; i < m; i++){
                sb.append(output[i] + " ");
            }
            sb.append("\n");
            return;
        }

        // RecursiveCase
        for (int i = 0; i < n; i++) {
            output[depth] = numbers[i];
            perm(depth + 1, n, m);  // 다음 깊이로 재귀 호출
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 숫자 배열의 길이
        int m = sc.nextInt();  // 생성할 순열의 길이

        // 숫자 배열 초기화
        numbers = new int[n];
        output = new int[m];
        for (int i = 0; i < n; i++) {
            numbers[i] = sc.nextInt();  // 숫자 입력
        }
        Arrays.sort(numbers);  // 입력받은 숫자를 정렬

        perm(0);  // 순열 생성 함수 호출
        System.out.println(sb);  // 결과 출력
    }
}
```

### 문제 : N개의 자연수 집합에서 M개를 고른 수열, 수열은 사전순, 중복되는 수열은 한번반 출력
### + 추가조건 : 같은 수를 여러번 골라도된다, 고른 수열은 비내림차순
- 비내림차순 : 오름차순과 동일하지만 같은 중복된 수가 나와도 됨
- i번째 재귀에서 배열의 i번째부터 사용했다고 하면, i+1번째 재귀에서는 i+1부터 사용하면 된다

```java
public class Main {
  public static StringBuilder sb = new StringBuilder();  // 결과를 저장할 StringBuilder 객체
  public static int[] numbers;  // 입력할 숫자들을 저장할 배열
  public static int[] output;   // 순열 결과를 저장할 배열
  public static int n, m;       // n: 숫자 배열의 크기, m: 생성할 순열의 길이

  // 순열 함수: 선택된 요소를 제외하고 순열을 생성합니다.
  public static void perm(int depth, int start) {
    // Base case: 깊이(depth)가 m에 도달하면 출력 함수를 호출합니다.
    if (depth == m) {
      for(int i = 0; i < m; i++){
        sb.append(output[i] + " ");
      }
      sb.append("\n");
      return;
    }

    // Recursive case: start 인덱스부터 n까지 순회하면서 순열을 생성
    for (int i = start; i < n; i++) {
      output[depth] = numbers[i];
      perm(depth + 1, i);  // start를 i로 설정하여 중복 허용 조합을 생성
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt();  // 숫자 배열의 크기 입력 받기
    m = sc.nextInt();  // 생성할 순열의 길이 입력 받기

    // 숫자 배열 초기화
    numbers = new int[n];
    output = new int[m];

    for (int i = 0; i < n; i++) {
      numbers[i] = sc.nextInt();  // 숫자 입력 받기
    }
    Arrays.sort(numbers);  // 입력 받은 숫자를 정렬

    perm(0, 0);  // 순열 생성 함수 호출
    System.out.println(sb);  // 생성된 모든 순열 출력
    sc.close();  // 스캐너 리소스를 닫습니다.
  }
}
```