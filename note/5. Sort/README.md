## Sort
- primitive형 Arrays.sort : Dual-Pivot Quick Sort -> O(N제곱)
- Object형 Arrays.sort : Tim sort -> O(Nlogn)

## 문제1. 수 정렬
```java
class Main
{
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Integer[] arr = new Integer[N]; //Reference타입 선언
        for (int i = 0; i < N; i++)
            arr[i] = Integer.parseInt(br.readLine());
        // Tim Sort
        Arrays.sort(arr);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < N; i++)
            bw.write(arr[i] + "\n");
        bw.flush();
    }
}
```

## 문제2. N개의 단어 중복제거, 길이가 짧은것, 같으면 사전순으로 정렬
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        String[] words = new String[N];
        for (int i = 0; i < N; i++)
            words[i] = sc.next();

        /* 람다 안쓸때
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() == o2.length())
                    return o1.compareTo(o2);
                return o1.length() - o2.length();
            }
        });
         */
        
        Arrays.sort(words, (o1, o2) -> {
            if (o1.length() == o2.length())
                return o1.compareTo(o2); // 길이가 같으면 사전순
            return o1.length() - o2.length(); // 길이가 짧은것 부터
        });

        System.out.println(words[0]);
        for (int i = 1; i < N; i++)
            if (!words[i].equals(words[i - 1]))
                System.out.println(words[i]);
    }
}
```

### 문제3. N명의 회원 중 나이가 적은순, 나이가 같으면 먼저 가입한 순서
- 나이만 비교할때
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        String[][] members = new String[N][2];
        for (int i = 0; i < N; i++) {
            members[i][0] = sc.next();
            members[i][1] = sc.next();
        }

        // Arrays.sort(members, (o1, o2) -> Integer.parseInt(o1[0]) - Integer.parseInt(o2[0]));
        Arrays.sort(members, Comparator.comparingInt(o -> Integer.parseInt(o[0])));

        for (int i = 0; i < N; i++)
            System.out.println(members[i][0] + " " + members[i][1]);
    }
}
```
- CompareTo 구현
```java
class Member {
    int age;
    String name;
    int idx;
}

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Member[] members = new Member[N];
        for (int i = 0; i < N; i++) {
            members[i] = new Member();
            members[i].age = sc.nextInt();
            members[i].name = sc.next();
            members[i].idx = i;
        }

        Arrays.sort(members, (o1, o2) -> {
            if (o1.age != o2.age)
                return o1.age - o2.age;
            return o1.idx - o2.idx;
        });

        for (Member member : members)
            System.out.println(member.age + " " + member.name);
    }
} 
```
- Comparable 구현
```java
class Member implements Comparable<Member> {
    int age;
    String name;
    int idx;

    public Member(int age, String name, int idx) {
        this.age = age;
        this.name = name;
        this.idx = idx;
    }

    @Override
    public int compareTo(Member o) {
        if (this.age != o.age)
            return this.age - o.age;
        return this.idx - o.idx;
    }
}

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Member[] members = new Member[N];
        for (int i = 0; i < N; i++)
            members[i] = new Member(sc.nextInt(), sc.next(), i);

        Arrays.sort(members);

        for (Member member : members)
            System.out.println(member.age + " " + member.name);
    }
} 
```

### 문제4. 회사 출입기록이 있을때, 퇴근하지 못한 사람 목록(사전 역순)
```java
import java.io.*;
import java.util.*;

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        String[][] records = new String[N][2];
        for (int i = 0; i < N; i++) {
            records[i][0] = sc.next();
            records[i][1] = sc.next();
        }

        // 사람이름순으로 정렬
        Arrays.sort(records, (o1, o2) -> o2[0].compareTo(o1[0]));

        // 각 사원마다 마지막 기록이 enter인지 확인
        // enter인지 && enter다음 다른 사람이 오는지
        for (int i = 0; i < N - 1; i++) {
            if (records[i][1].equals("enter") && !records[i][0].equals(records[i + 1][0]))
                System.out.println(records[i][0]);
        }
        // N-1번째에 대한 처리
        if (records[N - 1][1].equals("enter"))
            System.out.println(records[N - 1][0]);
    }
}
```
- set활용
  - TreeSet(Ordered) -> O(logN)
  - HashSet(Unordered) -> O(1)
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        // 이름 정렬을 위해 TreeSet사용
        Set<String> entered = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            String name = sc.next();
            String status = sc.next();
            if (status.equals("enter"))
                entered.add(name);
            else entered.remove(name);
        }

        String[] orderedAnswer = entered.toArray(new String[entered.size()]);
        for (int i = orderedAnswer.length - 1; i >= 0; i--)
            System.out.println(orderedAnswer[i]);
    }
} 
```

### 문제5. 판매된 책의 제목들이 있을때, 가장 많이 팔린 책
- Map
  - HashMap : Unordered, O(1)
  - TreeMap : Ordered, O(longN)
```java
import java.util.*;

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Map<String, Integer> titles = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String title = sc.next();
            // 이미 값이 있으면 가져오고 아니면 0
            // 0이면 +1
            titles.put(title, titles.getOrDefault(title, 0) + 1);
        }

        String maxTitle = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> title : titles.entrySet()) {
            if (title.getValue() > maxCount ||
                    // 사전순으로 정렬
                    (title.getValue() == maxCount && title.getKey().compareTo(maxTitle) < 0)) {
                maxTitle = title.getKey();
                maxCount = title.getValue();
            }
        }
        System.out.println(maxTitle);
    }
} 
```

### 문제6. N개의 좌표가 있을 때, 각 좌표가 입력된 좌표 중 몇번째인가(중복 제거 필요)
```java
class Main
{
  public static void main (String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int N = Integer.parseInt(br.readLine());
    String[] inputs = br.readLine().split(" ");
    int[] xs = new int[N];
    // 1. 입력된 좌표를 작은순으로 정렬
    Set<Integer> setX = new TreeSet<>();
    for (int i = 0; i < N; i++) {
      xs[i] = Integer.parseInt(inputs[i]);
      setX.add(xs[i]); // 2. 중복제거
    }

    Map<Integer, Integer> sortedIndex = new HashMap<>();
    int idx = 0;
    for (int x : setX)
      sortedIndex.put(x, idx++); // 3. 입력된 좌표에 대한 순서를 기록

    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    for (int i = 0; i < N; i++)
      bw.write(sortedIndex.get(xs[i]) + " ");
    bw.flush();
  }
}
```

## 문제 7. N개의 숫자가 있을때 더 많이 등장한 순서, 등장횟수가 같다면 먼저 입력된 것
```java
import java.util.*;

class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int C = sc.nextInt();
        // 1. 입력 순서대로 Map 세팅
        Map<Integer, Integer> messages = new LinkedHashMap<>();
        for (int i = 0; i < N; i++) {
            int message = sc.nextInt();
            messages.put(message, messages.getOrDefault(message, 0) + 1);
        }

        Integer[] frequencies = messages.keySet().toArray(new Integer[messages.size()]);
        // 2. 빈도순으로 정렬
        Arrays.sort(frequencies, (o1, o2) -> messages.get(o2) - messages.get(o1));

        for (int frequency : frequencies) {
            int count = messages.get(frequency);
            while (count-- > 0)
                System.out.print(frequency + " ");
        }
        System.out.println();
    }
}
```

## 문제 8. N개의 회의시간이 있을때 한 회의실에서 진행될 수 있는 최대 회의 개수
- 최적의 답 
  1. 회의 종료시간이 빠른 순으로 정렬
  2. 종료시간이 같다면 시작 시간이 빠른 순으로 정렬
  3. 가능한 회의부터 차례로 진행
```java
import java.util.*;

class Meeting {
  public Meeting(int start, int end) {
    this.start = start;
    this.end = end;
  }
  int start;
  int end;
}

class Main
{
  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    Meeting[] meetings = new Meeting[N];
    for (int i = 0; i < N; i++)
      meetings[i] = new Meeting(sc.nextInt(), sc.nextInt());
    
    Arrays.sort(meetings, (o1, o2) -> {
      if (o1.end == o2.end)
        return o1.start - o2.start; // 2. 종료시간이 같다면 시작 시간이 빠른 순으로 정렬
      return o1.end - o2.end; // 1. 회의 종료시간이 빠른 순으로 정렬
    });

    int cnt = 0;
    int ended = 0;
    // 3. 가능한 회의부터 차례로 진행
    for (int i = 0; i < N; i++)
      if (ended <= meetings[i].start) {
        cnt++;
        ended = meetings[i].end;
      }
    System.out.println(cnt);
  }
} 
```
