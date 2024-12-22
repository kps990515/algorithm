## 시간복잡도

### 문제 1. (W,H)공간에서 대각선으로 이동하는 개미의 T시간후 위치
- 경계면에 부딪힐 때마다 이동 x,y를 움직이는 코드로 짜면 O(T)가 걸림 -> 시간초과
- 규칙성 필요 : 격자안에서 다시 처음의 규칙이 적용하는 위치로 돌아올때까지 얼마걸리는 구해야함
- 개미의 움직임의 주기를 구해주기 -> O(W*H) -> 메모리 초과
- 풀이법 : X,Y의 패턴 따로 파악
  - X의 왕복 주기는 W*2 -> X(T) = X(T % 2W)
  - Y의 왕복 주기는 H*2 -> Y(T) = Y(T % 2H)
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int W = sc.nextInt();
        int H = sc.nextInt();
        int P = sc.nextInt();
        int Q = sc.nextInt();
        int T = sc.nextInt();

        int p = (P + T) % (2 * W);
        int q = (Q + T) % (2 * H);
        // W 이후 방향 전환, 차이만큼 W로부터 멀어짐
        // W - (p - W)
        if (p > W) p = 2 * W - p;
        if (q > H) q = 2 * H - q;
        System.out.println(p + " " + q);
    }
} 
```

