## Sort
- TreeSet(Ordered) -> O(logN)
- HashSet(Unordered) -> O(1)
- TreeMap : Ordered, O(longN)
- HashMap : Unordered, O(1)
- LinkedHashMap : 입력한 순서대로 정렬이 필요할때, O(1)
- Arrays.sort
  - primitive형 Arrays.sort : Dual-Pivot Quick Sort -> O(N제곱)
  - Object형 Arrays.sort : Tim sort -> O(Nlogn)
- getOrDefault(set, 0) : Set에 값을 넣을때 값이 없는 경우 default값 세팅
```java
titles.put(title, titles.getOrDefault(title, 0) + 1);
```

```java
Arrays.sort(arr, (a, b) -> {return b.scr-a.acr} // 내림차순
Arrays.sort(arr, (a, b) -> Integer.compare(b.scr, a.scr));
```
```java
Arrays.sort(words, (o1, o2) -> {
    if (o1.length() == o2.length())
      return o1.compareTo(o2); // 길이가 같으면 사전순
      return o1.length() - o2.length(); // 길이가 짧은것 부터
    });
```

## [수 정렬하기 2](https://www.acmicpc.net/problem/2751)
```java
public static void main(String[] args) {
    int N = sc.nextInt();
    Integer[] numbers = new Integer[N];
    
    for(int i=0; i<N;i++){
        numbers[i] = sc.nextInt();
    }
    
    Arrays.sort(numbers);
    
    for(int temp : numbers){
        sb.append(temp).append("\n");
    }
    
    System.out.println(sb);
}
```

## [단어 정렬 ](https://www.acmicpc.net/problem/1181)
- TreeSet으로는 구현이 어려움
- Set자체로는 정렬이 안됨
```java
    public static void main(String[] args) {
        int N = sc.nextInt();
        HashSet<String> S = new HashSet<>();
        for(int i=0; i<N; i++){
            S.add(sc.nextLine());
        }
        // HashSet -> List 변환해야지 sort가능
        List<String> list = new ArrayList<>(S);

        Collections.sort(list, (s1, s2) ->{
            if(s1.length() == s2.length()){
                return s1.compareTo(s2);
            }else{
                return s1.length() - s2.length();
            }
        });

        for(String s : list){
            sb.append(s).append("\n");
        }
        System.out.println(sb);
    }
```

### [나이순 정렬](https://www.acmicpc.net/problem/10814)
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

### [회사에 있는 사람](https://www.acmicpc.net/problem/7785)
```java
public static void main(String[] args) {
    int N = sc.nextInt();
    TreeMap<String, String> map = new TreeMap<>();
    
    for(int i=0; i<N; i++){
        String name = sc.next();
        String enter = sc.next();
        
        if(enter.equals("enter")){
            map.put(name, "enter");
        }else{
            // 없는값 remove해도 괜찮음
            map.remove(name);
        }
    }

    ArrayList<String> list = new ArrayList<>(map.keySet());
    list.sort(Comparator.reverseOrder());
    
    for(String s : list){
        sb.append(s).append("\n");
    }
    System.out.println(sb);
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

### [베스트셀러](https://www.acmicpc.net/problem/1302)
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
                    // compareTo : 객체 자신이 매개변수 객체보다 작으면 음수, 같으면 0, 크면 양수를 반환
                    (title.getValue() == maxCount && title.getKey().compareTo(maxTitle) < 0)) {
                maxTitle = title.getKey();
                maxCount = title.getValue();
            }
        }
        System.out.println(maxTitle);
    }
} 
```

### [좌표 압축](https://www.acmicpc.net/problem/18870)
```java
  public static void main(String[] args) {
      int N = sc.nextInt();
      TreeSet<Integer> treeSet = new TreeSet<>();
      int[] numbers = new int[N];
      
      for(int i=0; i<N; i++){
          int number = sc.nextInt();
          treeSet.add(number);
          numbers[i] = number;
      }

      HashMap<Integer, Integer> hashMap = new HashMap<>();
      int i = 0;
      for(int x : treeSet){
          hashMap.put(x, i);
          i++;
      }

      for(int j=0; j<N; j++){
          sb.append(hashMap.get(numbers[j])).append(" ");
      }
      System.out.println(sb);
  }
```

## [빈도 정렬](https://www.acmicpc.net/problem/2910)
```java
  public static void main(String[] args) {
      int N = sc.nextInt();
      int C = sc.nextInt();
      LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();

      for(int i=0; i<N; i++){
          int number = sc.nextInt();
          linkedHashMap.put(number, linkedHashMap.getOrDefault(number, 0) +1);
      }
      
      // list에 숫자 담기
      ArrayList<Integer> list = new ArrayList<>(linkedHashMap.keySet());
      
      // list에 담긴 숫자를 linkedHashMap의 빈도수로 sort해주기
      list.sort(((o1, o2) -> linkedHashMap.get(o2) - linkedHashMap.get(o1)));

      // list에 담긴 숫자를 linkedHashMap의 빈도수만큼 출력하기
      for(int num : list){
          int count = linkedHashMap.get(num);
          while(count-->0){
              sb.append(num).append(" ");
          }
      }
      System.out.println(sb);
  }
```

## [회의실 배정](https://www.acmicpc.net/problem/1931)
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

## [시리얼 번호](https://www.acmicpc.net/problem/1431)
```java
public class Main {
    static StringBuilder sb = new StringBuilder();
    static FastReader sc = new FastReader();

    static class guitar implements Comparable<guitar> {
        int sum;
        String name;
        int length;


        @Override
        public int compareTo(guitar other) {
            if (length != other.length) {
                return length - other.length;
            } else if (sum != other.sum) {
                return sum - other.sum;
            } else {
                return name.compareTo(other.name);
            }
        }
    }

    public static void main(String[] args) {
        int N = sc.nextInt();
        guitar[] guitars = new guitar[N];
        for (int i = 0; i < N; i++) {
            guitars[i] = new guitar();
            String name = sc.nextLine();
            guitars[i].name = name;
            guitars[i].sum = calLength(guitars[i].name);
            guitars[i].length = name.length();
        }

        Arrays.sort(guitars);

        for (guitar g : guitars) {
            sb.append(g.name).append("\n");
        }
        System.out.println(sb);
    }

    static int calLength(String name) {
        int sum = 0;
        for (int i = 0; i < name.length(); i++) {
            if ('0' <= name.charAt(i) && name.charAt(i) <= '9') {
                sum += name.charAt(i) - '0';
            }
        }
        return sum;
    }
}
```

## [안테나](https://www.acmicpc.net/problem/18310)
- N일 홀수 일 경우 (N-1)/2이 중앙 값
- N이 짝수 일 경우 이론적으로 (N-1)/2과 N/2는 결과값이 같음
```java
public static void main(String[] args) {
    int N = sc.nextInt();
    int[] houses = new int[N];

    for(int i=0; i<N; i++){
        houses[i] = sc.nextInt();
    }
    Arrays.sort(houses);

    // N일 홀수 일 경우 (N-1)/2이 중앙 값
    // N이 짝수 일 경우 이론적으로 (N-1)/2과 N/2는 결과값이 같음
    // 문제에서는 작은 걸 뽑으라고 했기 때문에 (N-1)/2이 정답
    System.out.println(houses[(N-1)/2]);
}
```

