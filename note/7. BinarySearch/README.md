## 이분탐색
- 정렬되어있는 집합에서 원하는 값을 찾는 효율적인 탐색 방법
- 함수
  - set.contains() -> O(1)
  - Arrays.binarySearch(arr, x) -> O(logN)

### 문제1. N개의 정수가 있을때 X라는 정수가 있는지 확인
- set사용
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < N; i++)
            set.add(sc.nextInt());
        int M = sc.nextInt();
        while (M-- > 0) {
            int x = sc.nextInt();
            System.out.println(set.contains(x) ? 1 : 0);
        }
    }
}
```
- 이분탐색 사용
```java
class Main
{
    static boolean isExist(int[] arr, int x) {
        int start = 0, end = arr.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (arr[middle] < x) end = middle + 1;
            else if (arr[middle] > x) start = middle - 1;
            else return true;
        }
        return false;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++)
            arr[i] = sc.nextInt();

        Arrays.sort(arr);

        int M = sc.nextInt();
        while (M-- > 0) {
            int x = sc.nextInt();
            boolean ans = isExist(arr, x);
            System.out.println(ans ? 1 : 0);
            //int idx = Arrays.binarySearch(arr, x);
            // System.out.println(idx >= 0 ? 1 : 0);
        }
    }
} 
```