## List

### ArrayList
- 원소가 추가될 떄 배열의 남는공간이 없다면 크기를 일정배수로 늘린 뒤, 재배치
- 조회: 원소가 배열에 저장되어있어 원소 접근이 빠름(O(1))
- 삽입/제거: 기존 배열과 같이 재배치가 필요함으로 느림(O(N))

### LinkedList
- 차례로 Node를 사용한 구현제
- 조회 : 순차탐색으로 느림(O(N))
- 삽입/제거 : 빠름(O(1))
- ListIterator : **ListIterator**는 양방향 순회 가능한 반복자(iterator)
  - previous() : 왼쪽
  - next() : 오른쪽
  - 현재 위치에 add(),remove(),set()(변경) 가능
- list.listIterator(int index) : 해당 index에 ListIterator 생성

### 문제. 1~N번까지 N명의 사람이 원으로 이루어져있을때 원형을 따라 매번 K번째 사람을 제거할때 제거되는 순서
- targetIdx = (pastIdx + K - 1) % list.size()로 K번째 사람 제거
- targetIdx값을 pastIdx에 넣어주기
```java
import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // N: 총 사람의 수, K: 제거할 순서를 나타내는 간격
        int N = sc.nextInt();
        int K = sc.nextInt();

        // N명의 사람을 저장하는 리스트 생성 (1부터 N까지)
        List<Integer> list = new ArrayList<>();
        // List<Integer> list = new LinkedList<>();  // LinkedList로도 사용 가능
        for (int i = 1; i <= N; i++)
            list.add(i);  // 사람 번호를 리스트에 추가

        // 결과를 저장할 배열
        int[] ans = new int[N];

        // 이전에 제거된 인덱스를 저장 (초기값은 0)
        int pastIdx = 0;

        // N번 반복하여 모든 사람을 제거
        for (int i = 0; i < N; i++) {
            // 현재 리스트 크기를 기준으로 제거할 대상의 인덱스 계산
            int targetIdx = (pastIdx + K - 1) % list.size();
            // 계산된 인덱스의 사람을 제거하고 결과 배열에 저장
            ans[i] = list.remove(targetIdx);
            // 다음 제거를 위해 현재 인덱스를 업데이트
            pastIdx = targetIdx;
        }

        // 결과를 요세푸스 순열 형식으로 출력
        System.out.println("<" + Arrays.stream(ans)
                .mapToObj(String::valueOf)  // 배열 요소를 문자열로 변환
                .collect(Collectors.joining(", "))  // 문자열을 ", "로 연결
                + ">");
    }
}
```

### 문제. 명령어에 따라 커서를 이동한 편집기 결과

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 원본 문자열을 입력받음
        String origin = sc.next();
        // LinkedList를 사용하여 각 문자를 저장
        List<Character> list = new LinkedList<>();
        for (char alp : origin.toCharArray()) {
            list.add(alp);
        }

        // 명령어의 개수 입력받음
        int M = sc.nextInt();
        // ListIterator를 사용하여 목록을 탐색 및 수정
        // 원본 문자열의 끝에 iterator를 위치시킴
        ListIterator<Character> it = list.listIterator(origin.length());
        
        // M개의 명령을 처리
        while (M-- > 0) {
            char cmd = sc.next().charAt(0); // 명령을 읽음

            if (cmd == 'L') {
                // 커서를 왼쪽으로 이동
                if (it.hasPrevious()) // 이전 요소가 있는지 확인
                    it.previous();    // 있으면 커서를 왼쪽으로 이동
            }
            else if (cmd == 'D') {
                // 커서를 오른쪽으로 이동
                if (it.hasNext())    // 다음 요소가 있는지 확인
                    it.next();       // 있으면 커서를 오른쪽으로 이동
            }
            else if (cmd == 'B') {
                // 커서 왼쪽의 문자를 삭제
                if (it.hasPrevious()) { // 삭제할 문자가 있으면
                    it.previous();    // 커서를 왼쪽으로 옮긴 후
                    it.remove();      // 그 위치의 문자를 삭제
                }
            }
            else if (cmd == 'P') {
                // 커서 왼쪽에 새 문자를 삽입
                char charToAdd = sc.next().charAt(0); // 삽입할 문자를 읽음
                it.add(charToAdd); // 커서 위치의 왼쪽에 문자를 삽입
            }
        }

        // 최종 문자열을 출력하기 위해 StringBuilder를 사용
        StringBuilder sb = new StringBuilder();
        for (char alp : list) {
            sb.append(alp); // 모든 문자를 StringBuilder에 추가
        }
        System.out.println(sb.toString()); // 완성된 문자열을 출력
    }
}

```