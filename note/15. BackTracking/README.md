## BackTracking(퇴각검색)
- 재귀를 이용해 모든 가능한 경우를 탐색하는 알고리즘
- 재귀랑 다른점
  - 답을 찾는 도중 최적해의 가능성이 없어지면 탐색 중단

### [알파벳](https://www.acmicpc.net/problem/1987)
- 문제 : 세로R칸, 가로 C칸의 보드에 대문자 알파벳 입력, (1,1)부터 탐색 시작, 같은 알파벳은 지나갈수없음, 최대로 지나갈수있는 칸 수
- 아이디어 : visited를 사용함
  - {r,c}좌표까지 동일한 알파벳 집합을 사용해서 도착했다면, 다음번에 {r,c}도착할떄 같은 알파벳 집합 사용시, 계산안해도됨
  - 지나간 알파벳 집합 표기는 비트마스크로 표현(2진수)
    - 0000_0000_0000_0000_0000_0000_0000_0111 == 7(A,B,C)
    - 0000_0000_0000_0000_0000_0000_0000_1101 == 13(A,C,D)
    - 조회 : 조회 원하는 비트에 -> & (1 << n) 하면 n번째 비트가 1이면 1리턴 0이면 0리턴
    - true대입 : |=(1<<n) 하면 n번째 비트가 어떤값이든 1로 리턴
    - false대입 : &=~(1<<n) 하면 n번째 비트가 어떤값이든 0으로 리턴
```java
class Main {
    public static int[][] board;  // 문자에서 파생된 정수로 보드를 저장
    public static boolean[] check = new boolean[26];  // 알파벳 사용 여부를 추적
    public static int r, c;  // 보드의 크기(행과 열)

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        r = sc.nextInt();  // 사용자로부터 행의 크기를 입력받음
        c = sc.nextInt();  // 사용자로부터 열의 크기를 입력받음
        board = new int[r][c];
        visited = new int[r][c];  // {r,c}도달하기 위해 사용한 알파벳(비트마스크로 표현)
        for (int i = 0; i < r; i++) {
            String line = sc.next();  // 한 줄을 읽음
            for (int j = 0; j < c; j++) {
                board[i][j] = line.charAt(j) - 'A';  // 각 문자를 'A'로부터의 차이값으로 정수로 변환
            }
        }
        visited[0][0] = 1 << (board[0][0]);  // 시작 지점 방문 처리
        check[board[0][0]] = true;  // 시작 지점의 알파벳 사용 처리
        System.out.println(solve(0, 0));  // 문제를 해결하고 결과 출력
    }

    // dr[0] dc[0] : 상
    // dr[1] dc[1] : 우
    // dr[2] dc[2] : 하
    // dr[3] dc[3] : 좌
    static int[] dr = {-1, 0, 1, 0};  // 행 이동을 위한 배열
    static int[] dc = {0, 1, 0, -1};  // 열 이동을 위한 배열

    // visited[row][col] -> (row, col) 도달했을때 사용한 알파벳
    public static int[][] visited;  // 각 위치에 도달했을 때 사용된 알파벳을 비트마스킹으로 저장

    public static int solve(int row, int col) {
        int result = 0;  // 현재 경로의 길이를 저장
        for (int i = 0; i < 4; i++) {  // 네 방향을 탐색
            int nr = row + dr[i], nc = col + dc[i];  // 새 위치 계산
            if (isOutOfBound(nr, nc, r, c)) continue;  // BaseCase : 범위를 벗어나면 건너뜀
            int next = board[nr][nc];  // 다음 위치의 알파벳
            if (check[next]) continue;  // BaseCase: 이미 사용된 알파벳이면 건너뜀
            int route = 1 << next;  // 다음 위치의 알파벳을 비트로 표현
            // BaseCase: 이미 같은 경로로 방문한 적 있으면 건너뜀
            if (visited[nr][nc] == (visited[row][col] | route)) continue;  

            visited[nr][nc] = visited[row][col] | route;  // 방문 처리 업데이트
            check[next] = true;  // 알파벳 사용 처리
            int nextResult = solve(nr, nc);  // 재귀 호출을 통한 다음 위치 탐색
            result = Math.max(result, nextResult);  // 최대 길이 갱신
            check[next] = false;  // 알파벳 사용 해제
        }
        return result + 1;  // 현재 위치 포함하여 +1 반환
    }

    static boolean isOutOfBound(int row, int col, int boundR, int boundC) {
        return row < 0 || col < 0 || row >= boundR || col >= boundC;  // 범위 체크 함수
    }
}
```