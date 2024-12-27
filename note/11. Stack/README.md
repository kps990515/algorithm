## Stack
- 마지막에 넣은 데이터가 먼저 나오는 LIFO
- LinkedList기반의 Stack
  - push : addLast를 사용해 마지막에 원소 추가
  - pop : removeLast를 사용해 마지막 원소 삭제
- Array기반의 Stack
  - push : topIndex를 증가시키고 원소 추가
  - pop : topIndex원소 삭제하고 topIndex 감소

### 문제 : 정수를 저장하는 스택을 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성하시오.
```java
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // 입력 및 출력 버퍼 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 명령의 개수 입력
        int N = Integer.parseInt(br.readLine());

        // 스택 역할을 할 Deque 선언
        Deque<Integer> stack = new ArrayDeque<>();

        // N개의 명령 처리
        while (N-- > 0) {
            // 명령어와 인자를 공백 기준으로 분리
            String[] cmd = br.readLine().split(" ");

            // 명령어 처리
            if (cmd[0].equals("push")) {
                // "push X": 스택의 맨 뒤에 값 추가 (스택의 top 역할)
                stack.offerLast(Integer.parseInt(cmd[1]));
            }
            else if (cmd[0].equals("pop")) {
                // "pop": 스택의 top 값을 제거하고 출력
                // 스택이 비어 있으면 -1 출력
                bw.write(stack.isEmpty() ? "-1\n" : stack.pollLast() + "\n");
            }
            else if (cmd[0].equals("size")) {
                // "size": 스택에 들어있는 요소의 개수 출력
                bw.write(stack.size() + "\n");
            }
            else if (cmd[0].equals("empty")) {
                // "empty": 스택이 비어 있으면 1, 아니면 0 출력
                bw.write(stack.isEmpty() ? "1\n" : "0\n");
            }
            else if (cmd[0].equals("top")) {
                // "top": 스택의 top 값을 출력
                // 스택이 비어 있으면 -1 출력
                bw.write(stack.isEmpty() ? "-1\n" : stack.peekLast() + "\n");
            }
        }
        // 출력 버퍼 비우기
        bw.flush();
    }
}
```

### 문제: 2종류의 괄호가 섞인 문자열이 들어올때, 문자열 안의 괄호가 올바른지 판단
- 올바른 괄호 : ()
- 기본 풀이
```java
class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성
    int T = sc.nextInt(); // 테스트 케이스의 수를 입력받음

    // 테스트 케이스 수만큼 반복
    while (T-- > 0) {
      char[] input = sc.next().toCharArray(); // 문자열 입력을 받아 문자 배열로 변환
      int openCount = 0; // '('의 개수를 세기 위한 변수 초기화

      // 입력된 문자열의 각 문자를 순회
      for (char ch : input) {
        if (ch == '(') {
          openCount++; // 열린 괄호 '('가 나타나면 카운트 증가
        } else if (ch == ')') {
          openCount--; // 닫힌 괄호 ')'가 나타나면 카운트 감소
          if (openCount < 0) break;
          // 열린 괄호 개수보다 닫힌 괄호가 많아지는 경우 반복 종료
        }
      }

      // openCount가 0이면 YES 출력 (올바른 괄호), 아니면 NO 출력
      System.out.println(openCount == 0 ? "YES" : "NO");
    }
  }
} 
```
- Stack 풀이
  - (가 들어오면 스택에 추가
  - )가 들어오면 스택에 들어있던 ( 하나 제거
```java
class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성
    int T = sc.nextInt(); // 테스트 케이스의 수를 입력받음

    // 테스트 케이스 수만큼 반복
    while (T-- > 0) {
      char[] input = sc.next().toCharArray(); // 문자열 입력을 받아 문자 배열로 변환
      Deque<Character> st = new ArrayDeque<>(); // 스택 역할을 할 Deque 객체 생성
      boolean isValid = true; // 문자열의 유효성을 나타낼 변수 초기화

      // 입력된 문자열의 각 문자를 순회
      for (char ch : input) {
        if (ch == '(') {
          st.offerLast('('); // 열린 괄호 '('를 스택에 추가
        } else if (ch == ')') {
          if (st.isEmpty()) {
            // 닫힌 괄호 ')'를 처리하려는데 스택이 비어 있으면 유효하지 않음
            isValid = false;
            break; // 반복 종료
          }
          st.pollLast(); // 스택에서 열린 괄호 '('를 제거
        }
      }

      // 모든 문자를 처리한 후 스택이 비어 있지 않으면 유효하지 않음
      if (!st.isEmpty()) isValid = false;

      // 유효한 괄호 문자열이면 "YES", 아니면 "NO" 출력
      System.out.println(isValid ? "YES" : "NO");
    }
  }
}
```

### 문제: 인접한 괄호는 레이저, 인접하지 않은 괄호는 쇠막대기의 시작과 끝일때 주어진 괄호에 대해 잘려진 막대기 조각의 개수
- 힌트 : 막대기는 구간 안에 포함된 레이저 개수 +1 개
- '('로 시작하면 막대기 추가
- '()'이면 레이저로 막대기 개수만큼 답에 더해줌
- '))'이면 막대기 끝이므로 답에 1더해줌

- 기본풀이
```java
class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성

    char[] input = sc.next().toCharArray(); // 입력된 문자열을 문자 배열로 변환
    int openCount = 0; // 열린 괄호 '('의 개수를 추적하기 위한 변수
    int ans = 0; // 최종 결과값을 저장할 변수

    // 입력된 문자열의 각 문자를 순회
    for (int i = 0; i < input.length; i++) {
      if (input[i] == '(') {
        openCount++; // 막대기 추가
      } else { // 닫힌 괄호 ')'인 경우
        openCount--; // 막대기 감소
        if (input[i - 1] == '(') {
          // ()인 경우 레이저로 간주
          ans += openCount; // 막대기의 개수만큼 답 더해주기
        } else { 
          // '))' 인 경우 : 막대의 끝인 경우
          ans++; // 막대 하나의 끝으로 간주하고 1 추가
        }
      }
    }

    // 최종 결과 출력
    System.out.println(ans);
  }
} 
```

### 문제. 두 종류의 괄호로 이루어진 괄호문자열을 아래 규칙에 따라 계산한 값
- 조건1 : '()'는 2, '[]'은 3의 값을 가짐
- 조건2 : '(X)'는 2X, '[X]'는 3X, 'XY'는 X+Y
1. 열린괄호 '(', '['인 경우 스택에 넣고 각 괄호에 대한 값을 곱해줌
2. 닫힌괄호의 경우
   - 스택이 비어있거나 스택 상단의 값이 닫힌괄호의 값과 다른 경우 잘못된 괄호로 간주, 종료
   - 이전 문자가 열린 괄호인 경우 곱한 값을 답에 더해줌
   - 곱한 값에서 닫힌 괄호의 값 나눠주기
```java
class Main {

    // 각 괄호에 대한 값을 반환하는 헬퍼 메서드
    static int delimiterToValue(char delimiter) {
        if (delimiter == '(' || delimiter == ')') return 2; // 소괄호는 2
        else if (delimiter == '[' || delimiter == ']') return 3; // 대괄호는 3
        return -1; // 유효하지 않은 입력에 대한 기본값
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성
        char[] input = sc.next().toCharArray(); // 입력된 문자열을 문자 배열로 변환

        int[] stack = new int[input.length]; // 괄호 값을 저장할 스택 역할 배열
        int topIndex = -1; // 스택의 현재 최상위 인덱스
        int currentMultiple = 1; // 괄호의 중첩 값(곱셈) 추적
        int ans = 0; // 최종 결과값

        // 입력된 문자열의 각 문자를 순회
        for (int i = 0; i < input.length; i++) {
            int delimiterValue = delimiterToValue(input[i]); // 현재 문자의 괄호 값을 얻음

            // 열린 괄호 처리
            if (input[i] == '(' || input[i] == '[') {
                stack[++topIndex] = delimiterValue; // 스택에 괄호 값을 push
                currentMultiple *= delimiterValue; // 중첩 값 갱신
            } 
            // 닫힌 괄호 처리
            else {
                // 스택이 비어 있거나 스택의 상단 괄호 값이 현재 닫힌 괄호의 값과 다를 경우
                if (topIndex < 0 || stack[topIndex--] != delimiterValue) {
                    ans = 0; // 잘못된 괄호 구조로 간주
                    break; // 반복 종료
                }
                // 이전 문자가 열린 괄호인 경우(레이어를 닫을 때 값 계산)
                if (input[i - 1] == '(' || input[i - 1] == '[')
                    ans += currentMultiple; // 현재 중첩 값 추가
                currentMultiple /= delimiterValue; // 중첩 값 감소
            }
        }

        // 스택이 비어 있어야 유효한 구조로 판단 (topIndex < 0)
        System.out.println(topIndex < 0 ? ans : 0);
    }
}
```

### 문제: 입력 글자와 백스페이스, 커서의 움직임 명령어를 처리했을 때 기록되는 문자열
- 쉬운방법
```java
class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int T = sc.nextInt();
    while (T-- > 0) {
      char[] input = sc.next().toCharArray();
      StringBuilder text = new StringBuilder();
      int cursor = 0; // 커서의 위치를 추적

      for (char cmd : input) {
        if (cmd == '-') {
          if (cursor > 0) {
            text.deleteCharAt(--cursor); // 커서 앞의 문자를 삭제
          }
        } else if (cmd == '<') {
          if (cursor > 0) cursor--; // 커서를 왼쪽으로 이동
        } else if (cmd == '>') {
          if (cursor < text.length()) cursor++; // 커서를 오른쪽으로 이동
        } else {
          text.insert(cursor++, cmd); // 커서 위치에 문자를 삽입하고 커서 이동
        }
      }
      System.out.println(text);
    }
  }
}
```

- deque이용
  - beforCursor : 커서 앞쪽 문자 저장할 스택
  - afterCursor : 커서 뒤쪽 문자 저장할 스택
```java
class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성
    int T = sc.nextInt(); // 테스트 케이스의 수를 입력받음

    // 테스트 케이스마다 실행
    while (T-- > 0) {
      char[] input = sc.next().toCharArray(); // 입력된 문자열을 문자 배열로 변환
      Deque<Character> beforCursor = new ArrayDeque<>(); // 커서 앞쪽의 문자들을 저장할 스택(덱)
      Deque<Character> afterCursor = new ArrayDeque<>(); // 커서 뒤쪽의 문자들을 저장할 스택(덱)

      // 입력 명령어 처리
      for (char cmd : input) {
        if (cmd == '-') {
          // 백스페이스: 커서 앞쪽에서 가장 마지막 문자를 제거
          beforCursor.pollLast();
        } else if (cmd == '<') {
          // 커서를 왼쪽으로 이동: 커서 앞쪽에서 문자를 빼서 커서 뒤쪽에 추가
          if (!beforCursor.isEmpty())
            afterCursor.offerLast(beforCursor.pollLast());
        } else if (cmd == '>') {
          // 커서를 오른쪽으로 이동: 커서 뒤쪽에서 문자를 빼서 커서 앞쪽에 추가
          if (!afterCursor.isEmpty())
            beforCursor.offerLast(afterCursor.pollLast());
        } else {
          // 일반 문자 입력: 커서 앞쪽에 문자를 추가
          beforCursor.offerLast(cmd);
        }
      }

      // 최종 문자열 구성
      StringBuilder sb = new StringBuilder();
      while (!beforCursor.isEmpty()) sb.append(beforCursor.pollFirst()); // 커서 앞쪽 문자들 추가
      while (!afterCursor.isEmpty()) sb.append(afterCursor.pollLast()); // 커서 뒤쪽 문자들 추가
      System.out.println(sb); // 결과 출력
    }
  }
}
```

### 문제: 주어진 문자열은 "P"에서 시작해 문자열 내의 "P"를 "PPAP"로 바꾸어 만들 수 있는 문자열인가 
- 오히려 반대로 PPAP를 P로 바꿨을떄 결국 PPAP가 나오는지 확인하는게 빠름
- 배열의 마지막 4글자가 PPAP인지 확인 -> P로 치환
- 남은결과가 P만 있는 경우 맞는 문자열
```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성

        char[] input = sc.next().toCharArray(); // 입력된 문자열을 문자 배열로 변환
        char[] rewind = new char[input.length]; // PPAP 문자열을 처리하기 위한 배열
        int len = 0; // 현재 `rewind` 배열의 유효 길이 (현재 처리된 문자열의 길이)

        // 입력된 문자를 순회하며 "PPAP" 문자열 판단
        for (char ch : input) {
            rewind[len++] = ch; // 현재 문자를 `rewind` 배열에 추가

            // `rewind` 배열의 마지막 4글자가 "PPAP"인지 확인
            if (len >= 4 && rewind[len - 1] == 'P' 
                    && rewind[len - 2] == 'A' 
                    && rewind[len - 3] == 'P' 
                    && rewind[len - 4] == 'P') {
                len -= 3; // "PPAP"를 "P"로 축약 (마지막 3글자 제거)
            }
        }

        // 남은 결과가 "P"만 있는 경우 "PPAP", 그렇지 않으면 "NP" 출력
        System.out.println(len == 1 && rewind[0] == 'P' ? "PPAP" : "NP");
    }
}
```

### 문제: 1~N까지 수를 오름차순으로 스택에 push하면서 원할때 pop할수있을때, pop한 결과가 주어진 수열대로 나오게하는 push/pop순서를 찾아라
- x값이 들어갈때까지 1~X까지 push
- pop을 하려는 숫자가 스택의 최상위가 아니면 NO반환

```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성

        int N = sc.nextInt(); // 입력되는 수열의 길이
        int[] arr = new int[N]; // 수열을 저장할 배열
        for (int i = 0; i < N; i++)
            arr[i] = sc.nextInt(); // 수열 입력받기

        int[] stack = new int[N]; // 스택 역할을 할 배열
        int topIndex = -1; // 스택의 최상위 인덱스
        int nextNumber = 1; // 다음에 스택에 넣을 숫자
        StringBuilder sb = new StringBuilder(); // 결과 문자열을 저장할 StringBuilder

        // 수열의 각 숫자에 대해 처리
        for (int x : arr) {
            // 현재 숫자 x가 스택에 없으면 스택에 추가 (+)
            while (nextNumber <= x) {
                sb.append("+\n"); // 스택에 추가하는 동작 기록
                stack[++topIndex] = nextNumber++; // 스택에 숫자를 넣고 증가
            }

            // 스택의 최상위 숫자가 x와 다르면 수열 생성 불가능
            if (topIndex < 0 || stack[topIndex] != x) {
                sb.setLength(0); // 기존 동작 기록 삭제
                sb.append("NO\n"); // "NO"를 결과로 저장
                break; // 더 이상 처리하지 않고 종료
            }

            // 스택의 최상위 숫자가 x와 같으면 스택에서 제거 (-)
            sb.append("-\n"); // 스택에서 제거하는 동작 기록
            topIndex--; // 스택 최상위 인덱스 감소
        }

        System.out.println(sb); // 결과 출력
    }
}
```

### 문제: 6개의 줄을 가진 기타를 연주할때 각 줄은 눌러진 프렛 중 가장 높은 음을 낸다.
### 손가락으로 프렛을 한번 누르거나 떼는 것을 한번 움직였다고 할때 
### 주어진 멜로디를 연주하기 위해 손가락을 움직여야하는 최소 횟수를 구하라

- 줄에 누른 플랫이 없다면 스택에 추가, 손가락 움직임 ++
  - 다음 누른 플랫이 이전 보다 크다면 스택에 추가, 손가락 움직임 ++
  - 다음 누른 플랫이 이전 보다 작다면 스택에서 제거하고 손가락 움직임 ++
  - 다음 누른 플랫이 이전 플랫과 같다면 넘어감
- 플랫이 비었다면 종료
```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체 생성

        int N = sc.nextInt(); // 음의 개수
        int P = sc.nextInt(); // 프렛의 최대 번호

        // 각 줄의 프렛 상태를 저장할 덱 배열 (6줄, 1-based index 사용)
        Deque<Integer>[] pushed = new ArrayDeque[7];
        for (int i = 1; i <= 6; i++) 
            pushed[i] = new ArrayDeque<>(); // 각 줄에 대해 플랫 Deque생성

        int moveCount = 0; // 손가락 움직임의 횟수를 저장할 변수

        // N개의 음을 처리
        for (int i = 0; i < N; i++) {
            int num = sc.nextInt(); // 줄 번호
            int fret = sc.nextInt(); // 눌러야 할 프렛 번호

            // 현재 줄의 프렛 상태를 조정
            while (!pushed[num].isEmpty()) { 
                if (pushed[num].peekLast() > fret) { 
                    // 현재 프렛보다 높은 프렛을 누르고 있으면 손가락을 떼야 함
                    pushed[num].pollLast(); // 스택에서 제거
                    moveCount++; // 손가락 움직임 증가
                } else break; // 더 이상 제거할 필요가 없으면 종료
            }

            // 이미 해당 프렛이 눌려져 있으면 다음으로 진행
            if (!pushed[num].isEmpty() && pushed[num].peekLast() == fret) 
                continue;

            // 현재 프렛을 눌러야 하는 경우
            pushed[num].offerLast(fret); // 스택에 추가
            moveCount++; // 손가락 움직임 증가
        }

        System.out.println(moveCount); // 최종 손가락 움직임 횟수 출력
    }
}
```

### 문제: 크기가 N인 수열 A의 각 원소에 대해 오큰수 NEG(i)를 구하라
- 오큰수 : 특정 A의 오른쪽에 있고, A보다 큰것중 가장 왼쪽에 있는것

- 오른쪽부터 왼쪽으로 탐색
- 현재 숫자보다 작은값은 오큰수가 될 수없으므로 스택에서 제거
- 스택의 최상위 값이 현재 숫자의 오큰수
- 스택에 추가
```java
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 효율적인 입력 처리를 위한 BufferedReader 생성

        int N = Integer.parseInt(br.readLine()); // 수열의 길이 입력
        int[] arr = Arrays.stream(br.readLine().split(" ")) // 공백으로 구분된 수열 입력
                .mapToInt(Integer::parseInt) // 각 숫자를 정수로 변환
                .toArray(); // 배열로 변환

        int[] ans = new int[N]; // 각 숫자에 대한 오큰수를 저장할 배열
        int[] descendingSequence = new int[N]; // 스택 역할을 할 배열
        int sequenceIndex = -1; // 스택의 현재 최상위 인덱스

        // 뒤에서부터 각 숫자에 대해 처리
        for (int i = N - 1; i >= 0; i--) {
            // 현재 숫자보다 작은 값은 오큰수가 될 수 없기에 제거
            while (sequenceIndex >= 0 && descendingSequence[sequenceIndex] <= arr[i])
                sequenceIndex--; // 스택 최상위 인덱스를 감소시켜 제거

            // 스택의 최상위 값이 오큰수, 스택이 비어 있으면 -1
            ans[i] = (sequenceIndex < 0 ? -1 : descendingSequence[sequenceIndex]);

            // 현재 숫자를 스택에 추가
            descendingSequence[++sequenceIndex] = arr[i];
        }

        // 결과 출력: 오큰수 배열을 공백으로 구분된 문자열로 출력
        System.out.println(Arrays.stream(ans)
                .mapToObj(String::valueOf) // 정수를 문자열로 변환
                .collect(Collectors.joining(" "))); // 공백으로 구분하여 출력
    }
}
```