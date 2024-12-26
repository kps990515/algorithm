## Queue
- 먼저 들어간 데이터가 먼저 나오는 FIFO구조
- 데이터가 들어가면 맨 뒤로 들어감, 가장 앞의 원소부터 처리됨
- 삽입 
  - offer() : 큐에 마지막에 삽입, 큐가 꽉차면 false 반환
  - add() : 큐에 마지막에 삽입, 큐가 꽉차면 Exception 발생
- 제거 후 반환 
  - remove() : 큐의 head 원소제거하고 반환, 큐가 비었으면 Exception 발생
  - poll() : 큐의 head 원소제거하고 반환, 큐가 비었으면 null리턴
- 원소반환 
  - element() : 큐의 head 반환, 큐가 비었으면 Exception 발생
  - peek() : 큐의 head 반환, 큐가 비었으면 null 리턴
- Linked List기반 큐
  - enqueue : addLast를 사용해 마지막 원소 추가
  - dequeue : removeFirst를 사용해 맨 앞 원소 삭제
- Array 기반 큐
  - enqueue : rearIndex에 원소를 추가하고, rearIndex를 이동
  - dequeue : firstIndex를 삭제하고, firstIndex를 이동
- Deque : 양쪽 끝에 원소를 삽입/삭제할수있는 양방향 Queue
  - prev/next를 모두 관리하는 양방향 List가 필요
  - addFirst()/addLast(), offerFirst()/offerLast()
  - removeFirst()/removeLast(), pollFirst()/pollLast()
  - getFirst()/getLast(), peekFirst()/peekLast()
- StringTokenizer : 문자열을 지정한 **구분자(delimiter)**에 따라 **분리된 토큰(token)**으로 쪼개는 데 사용
  - new StringTokenizer(sc.next(), "[,]") : [ ] , 을 제거하고 숫자만 추출
  - st.nextToken()으로 토큰 가져옴

### 문제 : 정수를 저장하는 큐를 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성
- 명령
  - push X: 정수 X를 큐에 넣는 연산이다.
  - pop: 큐에서 가장 앞에 있는 정수를 빼고, 그 수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
  - size: 큐에 들어있는 정수의 개수를 출력한다.
  - empty: 큐가 비어있으면 1, 아니면 0을 출력한다.
  - front: 큐의 가장 앞에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
  - back: 큐의 가장 뒤에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
```java
import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException {
    // BufferedReader와 BufferedWriter를 사용하여 입력과 출력을 처리
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 첫 번째 줄 입력: 명령의 개수 N
    int N = Integer.parseInt(br.readLine());

    // Queue를 LinkedList로 구현
    Queue<Integer> q = new LinkedList<>();

    // Queue의 마지막으로 들어간 값을 저장하기 위한 변수
    int lastValue = -1;

    // N개의 명령을 처리
    while (N-- > 0) {
      // 명령어를 읽고 공백으로 분리
      String[] cmd = br.readLine().split(" ");

      // 명령어가 "push"인 경우
      if (cmd[0].equals("push")) {
        // 입력받은 값을 정수로 변환 후 Queue에 추가
        lastValue = Integer.parseInt(cmd[1]);
        q.offer(lastValue); // Queue의 끝에 값을 추가
      }
      // 명령어가 "pop"인 경우
      else if (cmd[0].equals("pop")) {
        // Queue가 비어 있으면 -1 출력, 아니면 앞의 값을 제거하고 출력
        bw.write(q.isEmpty() ? "-1\n" : q.poll() + "\n");
      }
      // 명령어가 "size"인 경우
      else if (cmd[0].equals("size")) {
        // Queue의 크기를 출력
        bw.write(q.size() + "\n");
      }
      // 명령어가 "empty"인 경우
      else if (cmd[0].equals("empty")) {
        // Queue가 비어 있으면 1 출력, 아니면 0 출력
        bw.write(q.isEmpty() ? "1\n" : "0\n");
      }
      // 명령어가 "front"인 경우
      else if (cmd[0].equals("front")) {
        // Queue가 비어 있으면 -1 출력, 아니면 앞의 값을 출력
        bw.write(q.isEmpty() ? "-1\n" : q.peek() + "\n");
      }
      // 명령어가 "back"인 경우
      else if (cmd[0].equals("back")) {
        // Queue가 비어 있으면 -1 출력, 아니면 마지막으로 추가된 값을 출력
        bw.write(q.isEmpty() ? "-1\n" : lastValue + "\n");
      }
    }

    // BufferedWriter를 사용하여 출력 버퍼 비우기
    bw.flush();
  }
}
```

### 문제: 1~N번까지 사람이 원을 그리고 있을때, 원형을 따라 매번 K번째 사람을 제거할때 제거되는 순서
- K-1번째 사람까지 큐의 맨 뒤로 이동
- K번째에는 큐에서 제거
```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력: N과 K를 입력받음
        int N = sc.nextInt(); // 사람 수 (1부터 N까지)
        int K = sc.nextInt(); // 제거할 간격 (K번째 사람 제거)

        // 1부터 N까지의 숫자를 큐에 추가
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            q.add(i); // 큐에 사람 번호 추가
        }

        // 결과를 저장할 배열 (요세푸스 순열을 저장)
        int[] ans = new int[N];

        // 요세푸스 순열을 계산
        for (int i = 0; i < N; i++) {
            // K-1번째 사람까지 큐의 맨 뒤로 이동
            for (int j = 1; j < K; j++) {
                q.add(q.poll()); // 큐의 맨 앞의 사람을 꺼내서 뒤로 보냄
            }
            // K번째 사람을 제거하고 결과 배열에 저장
            ans[i] = q.poll(); // 큐에서 제거된 사람을 순열에 추가
        }

        // 결과 출력 (요세푸스 순열)
        // 배열을 "<a, b, c, ...>" 형태로 출력
        System.out.print("<" + Arrays.stream(ans)
                .mapToObj(String::valueOf) // 각 숫자를 문자열로 변환
                .collect(Collectors.joining(", ")) // ", "로 구분하여 문자열 합치기
                + ">");
    }
}
```

### 문제 : 버퍼 크기가 N인 라우터 입력/처리 명령어를 차례로 처리 후 남아있는 패킷
- 추가 조건 : 버퍼 빈 공간 없으면 패킷은 버려짐, 0은 라우터가 패킷하나 처리함을 의미

```java
import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 라우터가 처리할 수 있는 최대 패킷 수 입력
        int N = sc.nextInt();

        // 라우터를 표현하는 큐 (FIFO)
        Queue<Integer> router = new LinkedList<>();

        // 명령 처리 루프
        while (true) {
            int cmd = sc.nextInt(); // 명령 입력
            
            // 양수 명령: 패킷 추가
            if (cmd > 0) {
                // 라우터가 가득 차지 않았으면 패킷 추가
                if (router.size() < N) {
                    router.offer(cmd); // 패킷 번호를 큐에 추가
                }
            }
            // 명령 0: 가장 앞의 패킷 제거
            else if (cmd == 0) {
                router.poll(); // 큐의 가장 앞에 있는 패킷을 제거
            }
            // 음수 명령: 입력 종료
            else break; // 반복문 종료
        }

        // 결과 출력
        if (router.isEmpty()) {
            // 큐가 비어 있으면 "empty" 출력
            System.out.println("empty");
        } else {
            // 큐에 남아 있는 패킷들을 공백으로 구분하여 출력
            System.out.println(router.stream()
                .map(Object::toString) // 각 패킷을 문자열로 변환
                .collect(Collectors.joining(" "))); // 공백으로 구분하여 연결
        }
    }
}
```

### 문제: N개의 문서가 규칙에 따라 인쇄될때 M번쨰로 입력된 문서의 출력 순서
- 추가조건 : 큐 가장 앞 문서의 중요도가 남은 문서 중 가장 높다면 출력, 중요도가 더 높은 문서가있다면 맨뒤로 보내기

```java
import java.util.*;

// Job 클래스 정의: 각 작업의 인덱스와 우선순위를 저장
class Job {
    int index;       // 작업의 원래 인덱스
    int priority;    // 작업의 우선순위
    Job (int index, int priority) {
        this.index = index;
        this.priority = priority;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 테스트 케이스 개수 입력
        int T = sc.nextInt();

        // 각 테스트 케이스 처리
        while (T-- > 0) {
            // 문서의 개수(N)와 목표 문서의 인덱스(M) 입력
            int N = sc.nextInt();
            int M = sc.nextInt();

            // 큐와 우선순위 배열 초기화
            Queue<Job> q = new LinkedList<>();
            Integer[] prioritySequence = new Integer[N];

            // 문서의 우선순위와 인덱스를 큐와 배열에 저장
            for (int i = 0; i < N; i++) {
                prioritySequence[i] = sc.nextInt(); // 우선순위 저장
                q.offer(new Job(i, prioritySequence[i])); // 큐에 Job 객체 저장
            }

            // 우선순위 배열을 내림차순 정렬
            Arrays.sort(prioritySequence, Collections.reverseOrder());

            // 문서를 출력한 순서를 계산
            for (int i = 0; i < N; i++) {
                // 현재 우선순위와 큐의 앞에 있는 작업의 우선순위를 비교
                while (q.peek().priority != prioritySequence[i]) {
                    // 우선순위가 일치하지 않으면 큐의 앞의 작업을 뒤로 보냄
                    q.offer(q.poll());
                }

                // 목표 문서가 현재 출력되는지 확인
                if (q.peek().index == M) {
                    // 목표 문서가 출력되는 순서를 출력하고 종료
                    System.out.println(i + 1);
                    break;
                }

                // 현재 문서를 출력(큐에서 제거)
                q.poll();
            }
        }
    }
}
```

### 문제: 정수를 저장하는 Deque를 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성
- 추가조건 : Deque에 들어있는 값이 없는 경우 -1 출력
```java
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // BufferedReader와 BufferedWriter를 사용하여 빠르게 입력과 출력을 처리
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 명령의 개수 N 입력
        int N = Integer.parseInt(br.readLine());

        // Deque를 사용하여 양쪽에서 삽입/삭제를 처리
        Deque<Integer> q = new LinkedList<>();

        // N개의 명령 처리
        while (N-- > 0) {
            // 명령 입력 및 공백 기준으로 분리
            String[] cmd = br.readLine().split(" ");

            // "push_front X": 앞쪽에 값을 추가
            if (cmd[0].equals("push_front")) {
                q.offerFirst(Integer.parseInt(cmd[1]));
            }
            // "push_back X": 뒤쪽에 값을 추가
            else if (cmd[0].equals("push_back")) {
                q.offerLast(Integer.parseInt(cmd[1]));
            }
            // "pop_front": 앞쪽에서 값을 제거하고 출력
            else if (cmd[0].equals("pop_front")) {
                bw.write(q.isEmpty() ? "-1\n" : q.pollFirst() + "\n");
            }
            // "pop_back": 뒤쪽에서 값을 제거하고 출력
            else if (cmd[0].equals("pop_back")) {
                bw.write(q.isEmpty() ? "-1\n" : q.pollLast() + "\n");
            }
            // "size": Deque의 크기를 출력
            else if (cmd[0].equals("size")) {
                bw.write(q.size() + "\n");
            }
            // "empty": Deque가 비어 있는지 확인 (비었으면 1, 아니면 0 출력)
            else if (cmd[0].equals("empty")) {
                bw.write(q.isEmpty() ? "1\n" : "0\n");
            }
            // "front": Deque의 앞쪽 값을 출력
            else if (cmd[0].equals("front")) {
                bw.write(q.isEmpty() ? "-1\n" : q.peekFirst() + "\n");
            }
            // "back": Deque의 뒤쪽 값을 출력
            else if (cmd[0].equals("back")) {
                bw.write(q.isEmpty() ? "-1\n" : q.peekLast() + "\n");
            }
        }

        // BufferedWriter를 사용해 출력 버퍼를 비움
        bw.flush();
    }
}
```

### 문제: 길이 N 정수배열에 R(뒤집기), D(버리기) 함수P개를 적용한 결과

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 테스트 케이스 개수 입력
        int T = sc.nextInt();

        // 각 테스트 케이스 처리
        while (T-- > 0) {
            // 명령어 (R과 D로 이루어진 문자열)
            char[] cmds = sc.next().toCharArray();
            
            // 배열의 크기
            int N = sc.nextInt();
            
            // 배열 입력 (문자열 형태로 주어짐, "[...]" 포함)
            StringTokenizer st = new StringTokenizer(sc.next(), "[,]");

            // 배열을 저장할 Deque (양방향 큐)
            Deque<String> dq = new LinkedList<>();
            
            // 배열 요소를 Deque에 저장
            for (int i = 0; i < N; i++) {
                dq.offerLast(st.nextToken());
            }

            // 현재 배열 상태를 나타내는 플래그
            boolean isFlipped = false; // 뒤집힌 상태 여부
            boolean isValid = true; // 명령어 실행 성공 여부

            // 명령어 처리
            for (char cmd : cmds) {
                if (cmd == 'D') { // D 명령어: 첫/마지막 요소 삭제
                    if (dq.isEmpty()) { // Deque가 비어 있으면 에러
                        isValid = false;
                        break;
                    }
                    if (isFlipped) dq.pollLast(); // 뒤집힌 상태이면 마지막 요소 제거
                    else dq.pollFirst(); // 기본 상태이면 첫 번째 요소 제거
                } else if (cmd == 'R') {
                    isFlipped = !isFlipped; // 뒤집힌 상태를 반전
                }
            }

            // 결과 출력
            if (isValid) {
                // 남은 Deque 요소를 순서대로 출력
                StringBuilder sb = new StringBuilder();
                while (!dq.isEmpty()) {
                    // 뒤집힌 상태에 따라 앞/뒤에서 요소를 가져옴
                    sb.append(isFlipped ? dq.pollLast() : dq.pollFirst());
                    if (!dq.isEmpty()) sb.append(","); // 요소가 남아 있으면 ',' 추가
                }
                System.out.println("[" + sb + "]"); // 최종 결과 출력
            } else {
                // 에러 발생 시 "error" 출력
                System.out.println("error");
            }
        }
    }
}

```