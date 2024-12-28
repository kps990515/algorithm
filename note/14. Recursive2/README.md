## 재귀함수2
- 부분문자열 : 연속성을 가짐
- 부분수열 : 연속성을 가지지 않아도됨(2의n승)

### [부분수열의 합](https://www.acmicpc.net/problem/1182)
- N개의 정수로 이루어진 수열에서 부분 수열의 합이 S가 되는 경우의 수를 구하는 프로그램
- 부분 수열의 모든 경우의 수 : i번째 수를 고르는 경우 / i번째 수를 고르지 않는 경우
- Recursive Case : index번째까지의 결정에 대한 합을 알고 있을때
  - index+1을 포함하는 경우
  - index+1을 포함하지 않는 경우
```java
import java.util.Scanner;
public class Main {

    public static int[] numbers;  // 숫자들을 저장할 배열
    public static int s;          // 부분 집합의 합과 비교할 목표값
    public static int answer = 0; // 조건을 만족하는 부분 집합의 수를 저장할 변수

    // index 위치의 수를 포함할지 말지 결정하며 부분 집합의 합을 계산하는 재귀 함수
    public static void solve(int index, int sum) {
        // BaseCase
        if (index == numbers.length) return;
        
        // 현재 요소를 포함한 합이 s와 같다면 answer 증가
        if (sum + numbers[index] == s) answer++;

        // RecursiveCase: 현재 요소를 포함하지 않고 다음 요소를 검토
        solve(index + 1, sum);
        // RecursiveCase: 현재 요소를 포함하고 다음 요소를 검토
        solve(index + 1, sum + numbers[index]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 사용자로부터 숫자 배열의 크기 n을 입력받음
        s = sc.nextInt();      // 부분 집합의 목표 합 s를 입력받음
        numbers = new int[n];  // 입력받은 크기 n에 맞게 배열 초기화

        // 사용자로부터 n개의 숫자를 입력받아 배열에 저장
        for (int i = 0; i < n; i++) {
            numbers[i] = sc.nextInt();
        }

        // solve 함수를 첫 번째 요소와 0합으로 시작하여 호출
        solve(0, 0);

        // 조건을 만족하는 부분 집합의 수를 출력
        System.out.println(answer);
    }
}
```

### [로또](https://www.acmicpc.net/problem/2758)
- [1,m] 범위의 정수에서 n개를 뽑은 순열
- 단 i번째 수는 i-1번째보다 두배이상 커야한다
- 풀이법
  - 현재 i번째 수에 대해서, 마지막 값이 last인 경우
    - i가 last이고 i-1이 last/2인 경우
    - last-1까지의 숫자로 i 개의 숫자를 선택하는 경우
  - 한번 구한 결과는 저장해두고 반복 계산하지 않도록 하게

```java
import java.util.Scanner;

public class Main {

    // mem 배열은 메모이제이션을 위해 사용됩니다. 각 (i, last) 쌍의 결과를 저장하여 중복 계산을 방지합니다.
    public static long[][] mem;

    // solve 함수는 주어진 i와 last 값을 사용하여 조합의 수를 계산합니다.
    // i: 선택해야할 숫자의 수, last 선택한 숫자들 중 가장 큰 값
    public static long solve(int i, int last) {
        // BaseCase
        // last가 0 이하이면 더 이상의 유효한 조합을 생성할 수 없으므로 0을 반환합니다.
        if (last <= 0) return 0;
        // 선택해야 할 숫자가 하나만 남았을 때의 처리
        // (예: last = 5이면, 가능한 숫자는 1, 2, 3, 4, 5 이므로 5개)
        if (i == 1) return last;

        // 메모이제이션: 이미 계산된 값이 있다면 그 값을 바로 반환합니다.
        if (mem[i][last] == -1) {
            // 현재 숫자를 포함하지 않는 경우의 수와 현재 숫자를 포함하는 경우의 수를 재귀적으로 계산합니다.
            // solve(i - 1, last / 2): last / 2 범위 내에서 i-1개의 숫자를 선택하는 경우의 수
            // solve(i, last - 1): 현재 last 값을 선택하지 않고, last-1까지의 범위에서 i개의 숫자를 선택하는 경우의 수
            mem[i][last] = solve(i - 1, last / 2) + solve(i, last - 1);
        }
        return mem[i][last];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // 테스트 케이스의 수를 입력받습니다.

        for (int tc = 0; tc < t; tc++) {
            int n = sc.nextInt();  // 선택해야 하는 숫자의 개수
            int m = sc.nextInt();  // 가능한 최대 숫자
            mem = new long[n + 1][m + 1];  // 메모이제이션 배열을 초기화합니다.

            // 메모이제이션 배열을 -1로 초기화하여, 아직 계산되지 않았음을 표시합니다.
            for (int i = 0; i < n + 1; i++) {
                for (int j = 0; j < m + 1; j++) {
                    mem[i][j] = -1;
                }
            }

            // solve 함수를 호출하여 n개의 숫자를 m까지의 범위에서 선택할 수 있는 조합의 수를 계산합니다.
            System.out.println(solve(n, m));
        }
    }
}
```

### [부분수열의합2](https://www.acmicpc.net/problem/1208)
- 문제 : N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.
- 수열이 너무 커져서 수열의범위를 두개로 나눠서 풀어야함
- 가장 좋은 나누기 범위는 반반
- 왼쪽 수열 : S-{sum} / 오른쪽 수열 {sum}
- 풀이법
  1. 왼쪽 구간에서 만들어지는 수열의 합을 Map에 저장해둔다
  2. 오른쪽 구간에서 만들어지는 수열의 합 sum을 구한다
  3. 1번 Map을 통해 S-{sum}이 나오는 경우의 수를 더한다
  4. 2,3번을 재귀를 통해 반복한다(오른쪽 구간 모든 수열 만큼)
```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  static int s;  // 목표 합 S
  static int[] numbers;  // 입력 받은 수열
  static long answer = 0;  // 정답 카운트
  public static int status = -1;  // 현재 처리 중인 구간 (왼쪽 또는 오른쪽)
  static final int LEFT = 0;  // 왼쪽 구간을 나타내는 상수
  static final int RIGHT = 1;  // 오른쪽 구간을 나타내는 상수

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();  // 수열의 크기 N
    s = sc.nextInt();  // 목표 합 S

    numbers = new int[n];
    for (int i = 0; i < n; i++) {
      numbers[i] = sc.nextInt();  // 수열의 원소를 입력 받음
    }

    // 왼쪽 구간의 처리를 시작
    status = LEFT;
    solve(0, n/2, 0);

    // 오른쪽 구간의 처리를 시작
    status = RIGHT;
    solve(n/2, n, 0);

    // S가 0인 경우, 공집합의 합도 0이므로 이를 제외
    if (s == 0) answer--;
    System.out.println(answer);
  }

  static Map<Integer, Integer> cnt = new HashMap<>();  // 각 수열 합의 발생 횟수를 저장할 맵

  public static void solve(int index, int end, int sum) {
    // Base case: 구간의 끝에 도달했을 때
    if (index == end) {
      if (status == LEFT) {
        // 왼쪽 구간에서는 sum 값을 카운트하여 Map에 저장
        int prev = cnt.getOrDefault(sum, 0);
        cnt.put(sum, prev + 1);
      } else if (status == RIGHT) {
        // 오른쪽 구간에서는 Map을 참조하여 S-sum이 되는 경우의 수를 answer에 더함
        answer += cnt.getOrDefault(s - sum, 0);
      }
      return;
    }

    // Recursive calls
    solve(index + 1, end, sum);  // 현재 수를 포함하지 않는 경우
    solve(index + 1, end, sum + numbers[index]);  // 현재 수를 포함하는 경우
  }
} 
```

### [암호 만들기](https://www.acmicpc.net/problem/1759)
- 문제 : C개의 문자를 이용해 L 길이의 암호 만들기, 최소 1개의 모음 2개의 자음, 암호는 알파벳 오름차순으로 배열
```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
  static int l, c; // l: 암호의 길이, c: 사용할 수 있는 문자의 수
  static char[] input; // 입력받은 문자들
  static char[] password; // 생성될 암호를 저장할 배열

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    l = sc.nextInt(); // 암호의 길이 입력 받기
    c = sc.nextInt(); // 사용할 수 있는 문자의 수 입력 받기

    input = new char[c]; // 입력 문자 저장 배열 초기화
    password = new char[l]; // 암호 저장 배열 초기화
    for (int i = 0; i < c; i++) {
      input[i] = sc.next().charAt(0); // 문자 입력 받기
    }
    Arrays.sort(input); // 입력받은 문자를 사전순으로 정렬

    generate(0, 0, 0); // 재귀적으로 암호 생성 시작
  }

  // 암호를 생성하는 재귀 함수
  static void generate(int length, int index, int vowelCnt) {
    // base case: 암호의 길이가 목표 길이에 도달했을 때
    if (length == l) {
      if (vowelCnt >= 1 && l - vowelCnt >= 2) { // 최소 1개의 모음과 2개의 자음이 포함되어야 함
        System.out.println(password); // 조건을 만족하는 암호 출력
      }
      return;
    }

    // recursive case: 아직 암호가 완성되지 않았을 때
    if (index < c) {
      // password에 input[index]를 포함하는 경우
      password[length] = input[index]; // 현재 인덱스의 문자를 암호에 추가
      int v = isVowel(input[index]) ? 1 : 0; // 모음인 경우, 모음 카운트 증가
      generate(length + 1, index + 1, vowelCnt + v); // 다음 문자와 함께 다음 단계 재귀 호출

      // password에 input[index]를 포함하지 않는 경우
      // password[length] = 0; // 이 줄은 필요 없으므로 주석 처리
      generate(length, index + 1, vowelCnt); // 현재 문자를 포함하지 않고 다음 문자로 넘어가는 재귀 호출
    }
  }

  // 주어진 문자가 모음인지 판별하는 함수
  static boolean isVowel(char c) {
    return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
  }
} 
```

### [외판원 순회2](https://www.acmicpc.net/problem/10971)
- 문제 : N개의 도시, 모든 도시를 방문하고 출발지로 복귀, 간선마다 cost존재, 순회 경로 중 최소 cost경로구하기
- 인접횡렬 활용 : 배열 [n][n]  
```java
import java.util.Scanner;

public class Main {
  static int n; // 도시의 수
  static int[][] w; // 도시 간 비용을 저장하는 배열
  static boolean[] visited; // 방문 여부를 추적하는 배열
  static int answer = Integer.MAX_VALUE; // 최소 비용을 저장할 변수, 초기값은 최대 정수

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt(); // 도시의 수 입력 받기
    w = new int[n][n]; // 도시 간 비용을 저장할 2차원 배열 초기화
    visited = new boolean[n]; // 방문 배열 초기화

    // 비용 입력 받기
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        w[i][j] = sc.nextInt();
      }
    }

    // 여행 시작
    travel(0, 0, 0, 0);
    System.out.println(answer); // 계산된 최소 비용 출력
  }

  // 여행을 수행하는 재귀 함수
  static void travel(int start, int node, int sum, int cnt) {
    // base case: 모든 도시를 방문하고 시작 도시로 돌아온 경우
    if (cnt == n && start == node) {
      answer = Math.min(sum, answer); // 현재 경로의 비용과 이전에 계산된 최소 비용 비교
      return;
    }

    // recursive case: 다음 도시로 이동
    for (int i = 0; i < n; i++) {
      if (!visited[i] && w[node][i] != 0) { // 방문하지 않았고, 현재 도시에서 다음 도시로 가는 비용이 0이 아닐 때
        visited[i] = true; // 도시 방문 처리
        travel(start, i, sum + w[node][i], cnt + 1); // 다음 도시로 이동
        visited[i] = false; // 도시 방문 처리 취소 (백트래킹)
      }
    }
  }
} 
```

### [연산자 끼워넣기](https://www.acmicpc.net/problem/14888)
- 문제 : 수열사이에 연산자 넣기 가능, 연산자 우선순위 무시, 나눗셈은 몫만 취함, 만들어지는 결과 최대/최소 구하기
```java
import java.util.Scanner;

class Main {
  public static final int PLUS = 0;  // 더하기 연산자 상수
  public static final int MINUS = 1; // 빼기 연산자 상수
  public static final int MUL = 2;   // 곱하기 연산자 상수
  public static final int DIV = 3;   // 나누기 연산자 상수

  public static int n;              // 수열의 길이
  public static int[] numbers;      // 수열의 원소를 저장할 배열
  public static int[] operators = new int[4];  // 각 사칙연산의 사용 가능 횟수를 저장할 배열

  public static int max = Integer.MIN_VALUE;  // 가능한 결과값 중 최댓값
  public static int min = Integer.MAX_VALUE;  // 가능한 결과값 중 최솟값

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt();  // 사용자로부터 수열의 길이를 입력 받음
    numbers = new int[n];
    for (int i = 0; i < n; i++) {
      numbers[i] = sc.nextInt();  // 수열의 각 원소를 입력 받음
    }
    for (int i = 0; i < 4; i++) {
      operators[i] = sc.nextInt();  // 각 사칙연산의 사용 가능 횟수를 입력 받음
    }
    solve(1, numbers[0]);  // 재귀적으로 문제를 해결하기 위해 solve 함수 호출
    System.out.println(max);  // 계산된 최댓값 출력
    System.out.println(min);  // 계산된 최솟값 출력
  }

  static void solve(int index, int sum) {
    // base case: 모든 수열의 원소를 처리했을 때
    if (index == n) {
      max = Math.max(sum, max);  // 최댓값 갱신
      min = Math.min(sum, min);  // 최솟값 갱신
      return;
    }
    // recursive case: 각 연산자에 대해 재귀 호출
    for (int i = 0; i < 4; i++) {
      if (operators[i] > 0) {  // 해당 연산자를 사용할 수 있는 경우
        operators[i]--;  // 연산자 사용 횟수 감소
        switch (i) {
          case PLUS -> solve(index + 1, sum + numbers[index]);  // 더하기 연산
          case MINUS -> solve(index + 1, sum - numbers[index]); // 빼기 연산
          case MUL -> solve(index + 1, sum * numbers[index]);   // 곱하기 연산
          case DIV -> solve(index + 1, sum / numbers[index]);   // 나누기 연산
        }
        operators[i]++;  // 재귀 호출이 끝난 후 연산자 사용 횟수 복원 (백트래킹)
      }
    }
  }
} 
```

### [계란으로 계란치기](https://www.acmicpc.net/problem/16987)
- 문제
  - 모든 계란은 무게와 내구도 상태가 있다
  - 계란 A,B가 부딪히면 A의 내구도는 B의 무게만큼 차감, B의 내구도도 A의 무게만큼 차감
  - 내구도가 0이하로 낮아지면 계란은 깨짐
  - 손에 들 계란은 왼쪽에서 오른쪽 순으로 선택하고 남아있는 임의의 계란과 충돌 시도
  - 가장 최근의 잡은 계란을 내려놓고, 한칸 오른쪽 계란을 들어 위의 과정을 반복
  - 깨진 최대 계란의 수 계산
```java
import java.util.Scanner;

public class Main {

  public static int n; // 계란의 수
  public static Egg[] eggs; // 계란 배열
  public static int answer = 0; // 깨진 계란의 최대 수

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt(); // 사용자로부터 계란의 수를 입력받음
    eggs = new Egg[n]; // 계란 배열 초기화
    for(int i = 0; i < n; i++) {
      eggs[i] = new Egg(sc.nextInt(), sc.nextInt()); // 각 계란의 내구도와 무게를 입력받아 초기화
    }
    solve(0); // 계란 부딪히기 시작
    System.out.println(answer); // 최대로 깨진 계란의 수 출력
  }

  public static void solve(int pick) {
    if(pick == n) { // 모든 계란을 검토했을 때
      int count = 0; // 깨진 계란의 수를 세기
      for(int i = 0; i < n; i++) {
        if(eggs[i].durability <= 0) count++; // 내구도가 0 이하면 깨진 것으로 카운트
      }
      answer = Math.max(answer, count); // 깨진 계란의 최대 수를 갱신
      return;
    }

    // 현재 선택한 계란이 아직 깨지지 않았다면
    if(eggs[pick].durability > 0) {
      boolean targetExists = false; // 깰 수 있는 대상 계란이 있는지 확인
      for(int target = 0; target < n; target++) {
        if(target == pick || eggs[target].durability <= 0) continue; // 자기 자신이거나 이미 깨진 계란은 넘어감
        targetExists = true; // 깰 대상이 존재
        eggs[pick].fight(eggs[target]); // 계란을 서로 부딪힘
        solve(pick + 1); // 다음 계란으로 넘어감
        // 계속 부딪히는 과정이 끝나고 다른 독립적 시나리오를 진행하기 위해 복구(백트래킹)
        eggs[pick].restore(eggs[target]); 
      }
      if(!targetExists) solve(pick + 1); // 깰 대상이 없으면 다음 계란으로 넘어감
    } else { // 현재 계란이 이미 깨졌다면 다음 계란으로 넘어감
      solve(pick + 1);
    }
  }
}

class Egg {
  int durability; // 계란의 내구도
  int weight; // 계란의 무게

  public Egg(int durability, int weight) {
    this.durability = durability;
    this.weight = weight;
  }

  public void fight(Egg other) { // 다른 계란과 부딪힐 때 내구도 감소
    this.durability -= other.weight;
    other.durability -= this.weight;
  }

  public void restore(Egg other) { // 부딪힌 후 내구도 복원
    this.durability += other.weight;
    other.durability += this.weight;
  }
}
 
```