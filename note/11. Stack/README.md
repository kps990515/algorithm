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