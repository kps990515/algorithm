## Tree
- 계층적인 부모-자식관계 노드 구조
- 루트 : 최상위 노드
- 리프 : 자식이 없는 노드
- 레벨 : 루트를 레밸0으로 했을떄 각 노드의 깊이

### 문제: 루트 없는 트리가 있고 트리의 루트를 1이라고 할때, 각 노드의 부모를 구하는 프로그램 작성
- 1번 노드부터 연결된 노드 탐색해서 부모매핑
- check[]활용해서 재 계산하지 않도록 함
- 6 / 1 2 / 1 3 / 2 4 / 3 5 / 3 6 이라고 할때
- find(1) 호출 -> 2,3은 자식노드가 됨
- find(1) 내부에서 find(2) 호출 -> 4가 자식노드
- find(2) 내부에서 find(4) 호출 -> 자식이 없으므로 리턴
- find(2) 리턴후, find(1)에서 find(3) 호출 -> 5,6이 자식노드
- find(3) 내에서 find(5)호출 -> 리턴
- find(3) 내에서 find(6)호출 -> 리턴
```java
public class Main {
    public static List<Integer>[] tree;  // 각 노드에 연결된 노드들을 리스트로 표현
    public static int[] parents;  // 각 노드의 부모 노드를 저장할 배열
    public static boolean[] check;  // 각 노드의 방문 여부를 체크할 배열

    // 특정 노드에서 시작하여 모든 자식 노드의 부모를 찾는 함수
    public static void find(int node) {
        check[node] = true;  // 현재 노드를 방문 처리
        for (int i = 0; i < tree[node].size(); i++) {  // 현재 노드의 모든 인접 노드를 순회
            int child = tree[node].get(i);  // 인접 노드
            if (!check[child]) {  // 아직 방문하지 않은 노드라면
                parents[child] = node;  // 부모 노드를 현재 노드로 설정
                find(child);  // 재귀적으로 자식 노드 탐색
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 노드의 개수 입력 받기
        tree = new ArrayList[n + 1];  // 노드 개수 +1 크기의 리스트 배열 초기화
        for (int i = 1; i < n + 1; i++) {  // 각 노드에 연결된 노드를 표현할 리스트
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {  // 트리의 간선 정보 입력 받기
            int node1 = sc.nextInt();  // 노드1
            int node2 = sc.nextInt();  // 노드2
            tree[node1].add(node2);  // 노드1의 인접 리스트에 노드2 추가
            tree[node2].add(node1);  // 노드2의 인접 리스트에 노드1 추가
        }

        parents = new int[n + 1];  // 부모 노드 배열 초기화
        check = new boolean[n + 1];  // 방문 체크 배열 초기화

        find(1);  // 1번 노드를 루트로 하여 부모 노드 찾기 시작
        for (int i = 2; i < n + 1; i++) {  // 부모 노드 정보 출력 (1번 노드는 루트이므로 제외)
            System.out.println(parents[i]);
        }

        sc.close();  // 스캐너 리소스를 닫습니다.
    }
}
```

### 문제: 특정 정점 U를 루트로 하는 서브트리에 속한 정점(노드)의 수를 출력
- i번쨰 노드의 자식 수를 구해놓고 저장 -> 필요할떄 재귀가 아닌 저장값 활용 -> sum[]
- 반복 탐색이 되지 않도록 check[]를 통해 방문여부 기록
- i를 루트로 하는 서브트리 노드합은 1로 초기화 하고 탐색 진행
- child를 루트로 하는 노드의 수르르 재귀로 구하고 자신에게 더하기
  - sum[]에 값이 있으면 활용
  - 없으면 재귀로 계산
```arduino
8 1 3  // 노드의 수, 루트 노드, 쿼리의 수
1 2
1 3
2 4
2 5
3 6
6 7
6 8 
```
### 트리의 서브트리 크기 계산 프로그램
#### 트리 구성
각 노드를 연결하여 인접 리스트를 만듭니다. 이를 통해 각 노드의 연결 상태를 쉽게 파악할 수 있습니다.

#### 서브트리 크기 계산
루트 노드 `1`부터 시작하여 DFS(깊이 우선 탐색)를 수행하며 각 노드의 서브트리 크기를 계산합니다.

#### 계산 과정
- `getSum(1)` 호출:
  - `getSum(2)` 호출:
    - `getSum(4)` 호출: 반환 값 `1` (자기 자신만 포함)
    - `getSum(5)` 호출: 반환 값 `1` (자기 자신만 포함)
    - 노드 `2`의 서브트리 크기: `1` (자신) + `1` (노드 4) + `1` (노드 5) = `3`
  - `getSum(3)` 호출:
    - `getSum(6)` 호출:
      - `getSum(7)` 호출: 반환 값 `1` (자기 자신만 포함)
      - `getSum(8)` 호출: 반환 값 `1` (자기 자신만 포함)
      - 노드 `6`의 서브트리 크기: `1` (자신) + `1` (노드 7) + `1` (노드 8) = `3`
    - 노드 `3`의 서브트리 크기: `1` (자신) + `3` (노드 6) = `4`
  - 노드 `1`의 서브트리 크기: `1` (자신) + `3` (노드 2) + `4` (노드 3) = `8`

#### 쿼리 처리
각 쿼리에 대한 노드의 서브트리 크기를 출력합니다.
- 노드 `1`의 서브트리 크기: `8`
- 노드 `6`의 서브트리 크기: `3`
- 노드 `2`의 서브트리 크기: `3`

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
  static List<Integer>[] tree;  // 트리를 표현할 인접 리스트
  static boolean[] check;       // 노드 방문 여부 체크 배열
  static int[] sum;             // 각 노드의 서브트리 크기를 저장할 배열

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt(); // 노드의 개수
    int r = sc.nextInt(); // 루트 노드
    int q = sc.nextInt(); // 쿼리의 개수
    check = new boolean[n + 1];
    sum = new int[n + 1];
    tree = new ArrayList[n + 1];

    // 인접 리스트 초기화
    for (int i = 1; i <= n; i++) {
      tree[i] = new ArrayList<>();
    }

    // 트리 구성 정보 입력 받기
    for(int i = 0; i < n - 1; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      tree[u].add(v);
      tree[v].add(u);
    }

    // 루트 노드에서 서브트리 크기 계산 시작
    sum[r] = getSum(r);

    // 쿼리 처리
    for (int i = 0; i < q; i++) {
      int u = sc.nextInt();
      System.out.println(sum[u]);
    }

    sc.close();  // 스캐너 리소스 닫기
  }

  // 서브트리 크기 계산을 위한 재귀 함수
  public static int getSum(int node) {
    if (sum[node] != 0) return sum[node];  // 이미 계산된 서브트리 크기 반환
    check[node] = true;  // 노드 방문 표시
    int result = 1; // 자기 자신을 포함하여 초기화
    for (int child : tree[node]) {
      if (!check[child]) {  // 방문하지 않은 자식 노드만 처리
        result += getSum(child);  // 자식 노드의 서브트리 크기를 결과에 더함
      }
    }
    return sum[node] = result;  // 계산된 서브트리 크기를 저장하고 반환
  }
} 
```

### [회사문화1](https://www.acmicpc.net/problem/14267)
- 상사의 부하직원들을 트리에서 조회하고 누적된 칭찬수치를 부하직원에게 더해준다
- 탐색대상을 부하직원으로 변경하여 재귀 수행
```arduino
5 3 // 회사 직원수, 최초 칭찬 횟수
-1 1 2 3 4 // 직속 상사 번호(상사일수록 번호가 작음)
2 2 // 직속상사부터 칭찬을 받은 직원번호, 칭찬 수치
3 4
5 6
```

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int[] parent;  // 각 직원의 상사를 나타내는 배열
    static int[] like;    // 각 직원의 점수를 저장하는 배열
    static List<Integer>[] tree;  // 트리를 표현할 인접 리스트

    // 현재 노드의 점수를 자식 노드로 전파하는 함수
    public static void next(int node) {
        for (int i = 0; i < tree[node].size(); i++) {  // 현재 노드의 모든 자식 노드를 순회
            int child = tree[node].get(i);
            like[child] += like[node];  // 부모 노드의 점수를 자식 노드에 누적
            next(child);  // 재귀 호출로 자식 노드의 점수를 다시 전파
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();  // 직원 수
        int m = sc.nextInt();  // 점수 할당 횟수

        parent = new int[n + 1];  // 상사 정보를 저장할 배열
        like = new int[n + 1];    // 점수를 저장할 배열
        tree = new ArrayList[n + 1];  // 트리를 표현할 리스트 초기화

        // 트리 구성: 상사-부하 관계를 정의
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
            parent[i] = sc.nextInt();  // i번 직원의 상사 정보 입력

            if (parent[i] != -1) {  // 상사가 존재하면
                tree[parent[i]].add(i);  // 상사-부하 관계를 트리에 추가
            }
        }

        // 점수 할당: 특정 직원에게 점수를 추가
        for (int i = 0; i < m; i++) {
            int employee = sc.nextInt();  // 점수를 받을 직원 번호
            int point = sc.nextInt();     // 점수 값
            like[employee] += point;      // 해당 직원의 점수 증가
        }

        // 점수를 부하 직원들에게 전파
        next(1);  // 루트 노드(1번 직원)부터 시작

        // 결과 출력: 각 직원의 최종 점수
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(like[i]).append(" ");
        }
        System.out.println(sb);  // 모든 직원의 점수를 공백으로 구분해 출력
    }
}

```