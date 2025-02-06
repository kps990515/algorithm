# codingTest

스냅챗에서 출제된 문제입니다.

중첩될 수 있는 인터벌들을 갖는 리스트가 있습니다. 중첩되는 인터벌들을 하나로 합친 새로운 리스트를 반환하세요.

입력 리스트는 정렬되어 있지 않습니다.

예를 들어, [(1, 3), (5, 8), (4, 10), (20, 25)] 가 주어지면, [(1, 3), (4, 10), (20, 25)] 를 반환해야 합니다.

```java
public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();

    public static void main(String[] args) {
        runTests(); // 테스트 실행
    }

    public static List<int[]> mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new ArrayList<>();
        }

        // 1. 시작값 기준으로 정렬
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        int[] current = intervals[0]; // 첫 번째 구간을 초기값으로 설정
        merged.add(current);

        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            // 2. 현재 구간이 이전 구간과 겹치는 경우 병합
            if (start <= current[1]) {
                current[1] = Math.max(current[1], end); // 끝값 업데이트
            } else {
                // 겹치지 않으면 새 구간 추가
                current = new int[]{start, end};
                merged.add(current);
            }
        }

        return merged;
    }

    // 테스트 메서드
    public static void runTests() {
        testMergeIntervals(new int[][]{{1, 3}, {5, 8}, {4, 10}, {20, 25}},
                new int[][]{{1, 3}, {4, 10}, {20, 25}}, "Test 1: Basic Case");

        testMergeIntervals(new int[][]{{1, 5}, {6, 10}, {11, 15}},
                new int[][]{{1, 5}, {6, 10}, {11, 15}}, "Test 2: Already Merged");

        testMergeIntervals(new int[][]{{1, 10}, {2, 6}, {3, 5}, {7, 9}},
                new int[][]{{1, 10}}, "Test 3: Fully Overlapping");

        testMergeIntervals(new int[][]{},
                new int[][]{}, "Test 4: Empty Input");

        testMergeIntervals(new int[][]{{2, 5}},
                new int[][]{{2, 5}}, "Test 5: Single Interval");
    }

    // 개별 테스트 수행 및 결과 확인
    public static void testMergeIntervals(int[][] input, int[][] expected, String testName) {
        List<int[]> result = mergeIntervals(input);
        boolean isPassed = compareResults(result, expected);

        System.out.println(testName + ": " + (isPassed ? "Passed" : "Failed"));
        if (!isPassed) {
            System.out.println("  Expected: " + Arrays.deepToString(expected));
            System.out.println("  Got     : " + Arrays.deepToString(result.toArray()));
        }
    }

    // 결과 비교 메서드
    private static boolean compareResults(List<int[]> result, int[][] expected) {
        if (result.size() != expected.length) {
            return false;
        }

        for (int i = 0; i < expected.length; i++) {
            if (!Arrays.equals(result.get(i), expected[i])) {
                return false;
            }
        }

        return true;
    }
}
```

페이스북에서 제출된 문제입니다.

0이 대다수를 차지하는 큰 배열이 있습니다.

더 공간 효율적인 자료구조인 SparseArray를 다음과 같은 인터페이스로 구현하세요.

init(arr, size): 큰 원본 배열과 사이즈를 인자로 받아 초기화 합니다.
set(i, val): 인덱스 i 를 val 값으로 업데이트 합니다.
get(i): 인덱스 i 번째 값을 반환합니다.

```java
interface ISparseArray<T> {
    void init(T[] arr, int size);  // 원본 배열에서 0이 아닌 값만 해시맵에 저장하여 공간을 절약합니다.
    void set(int i, T val);        // val이 0이 아닌 경우 → 해시맵에 저장, val이 0이면 → 기존에 값이 있으면 삭제하여 공간 절약
    T get(int i);                  // 인덱스 i가 해시맵에 존재하면 값을 반환, 존재하지 않으면 기본값 0 반환
}
```
```java
class SparseArray<T> implements ISparseArray<T> {
    private Map<Integer, T> sparseMap;
    private int size;
    private T defaultValue; // 기본값 (null이 아닌 값 가능)

    // 생성자: 기본값을 설정할 수 있도록 함
    public SparseArray(T defaultValue) {
        sparseMap = new HashMap<>();
        this.defaultValue = defaultValue; // null이 아닐 경우, 기본값 사용 가능
    }

    // 초기화 메서드: 0이 아닌 값만 저장
    @Override
    public void init(T[] arr, int size) {
        this.size = size;
        sparseMap.clear(); // 기존 데이터 초기화

        for (int i = 0; i < size; i++) {
            if (!arr[i].equals(defaultValue)) { // 기본값이 아닌 경우 저장
                sparseMap.put(i, arr[i]);
            }
        }
    }

    // 값 설정 메서드
    @Override
    public void set(int i, T val) {
        if (!val.equals(defaultValue)) {
            sparseMap.put(i, val);
        } else {
            sparseMap.remove(i); // 기본값이면 삭제
        }
    }

    // 값 가져오기 메서드
    @Override
    public T get(int i) {
        return sparseMap.getOrDefault(i, defaultValue); // 존재하지 않으면 기본값 반환
    }

    // 내부 저장된 데이터 보기 (디버깅용)
    public void printSparseArray() {
        System.out.println("SparseArray Data: " + sparseMap);
    }

    // 테스트 코드
    public static void main(String[] args) {
        System.out.println("==== Integer SparseArray ====");
        SparseArray<Integer> intSparseArray = new SparseArray<>(0);
        Integer[] intArr = {0, 0, 0, 5, 0, 0, 10, 0, 0, 3, 0};
        intSparseArray.init(intArr, intArr.length);
        System.out.println("get(3) → " + intSparseArray.get(3)); // 5
        System.out.println("get(6) → " + intSparseArray.get(6)); // 10
        System.out.println("get(9) → " + intSparseArray.get(9)); // 3
        System.out.println("get(1) → " + intSparseArray.get(1)); // 0
        intSparseArray.set(3, 7);
        intSparseArray.set(6, 0); // 0이면 제거됨
        intSparseArray.printSparseArray();

        System.out.println("\n==== String SparseArray ====");
        SparseArray<String> stringSparseArray = new SparseArray<>("");
        String[] strArr = {"", "", "", "hello", "", "", "world", "", "", "test", ""};
        stringSparseArray.init(strArr, strArr.length);
        System.out.println("get(3) → " + stringSparseArray.get(3)); // "hello"
        System.out.println("get(6) → " + stringSparseArray.get(6)); // "world"
        System.out.println("get(9) → " + stringSparseArray.get(9)); // "test"
        System.out.println("get(1) → " + stringSparseArray.get(1)); // ""
        stringSparseArray.set(3, "changed");
        stringSparseArray.set(6, ""); // 빈 문자열이면 제거됨
        stringSparseArray.printSparseArray();
    }
}
```
애플에서 출제된 문제입니다.

주어진 이진 트리에서, 루트 (최상위 노드) 에서 리프 (자식이 없는 최하위 노드) 까지의 경로를 모두 더하였을 떄 가장 작은 값을 갖는 경로를 찾고, 그 합을 반환하세요.

예를 들어, 이 트리에서 최소 값을 갖는 경로는 [10, 5, 1, -1] 이며, 그 합인 15를 반환해야 합니다.

 10
/  \
5    5
\     \
2    1
/
-1

```java
class TreeNode {
    int val;
    TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class MinPathSum {
    // 최소 합을 저장할 변수
    private static int minSum = Integer.MAX_VALUE;

    public static int minPathSum(TreeNode root) {
        if (root == null) return 0;
        minSum = Integer.MAX_VALUE; // 최소값 초기화
        dfs(root, 0); // DFS 시작
        return minSum;
    }

    private static void dfs(TreeNode node, int sum) {
        if (node == null) return;

        sum += node.val; // 현재 노드 값을 더함

        // 리프 노드라면 최소값 갱신
        if (node.left == null && node.right == null) {
            minSum = Math.min(minSum, sum);
            return;
        }

        // 왼쪽과 오른쪽 자식 노드로 재귀 호출
        dfs(node.left, sum);
        dfs(node.right, sum);
    }

    // 테스트 코드
    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(5);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(1);
        root.right.right.left = new TreeNode(-1);

        System.out.println("최소 경로 합: " + minPathSum(root)); // 15 출력
    }
}

```

 
