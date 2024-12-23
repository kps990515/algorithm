## String

1. Immutable object
 - String은 한번 생성되면 수정 불가
 - 값의 변경 불가 -> new String으로 생성 필요

2. 선언의 두가지 방법
 - String str = "test" : Java Heap -> constant String Pool 저장
   - 값이 같으면 새로 저장하지 않고 기존 주소의 값을 재활용
   - ==(주소값 비교) 으로 비교했을때 일치
 - String str = new String("test") : Java Heap
   - 항상 새로운 주소에 할당
   - == 비교시 다른 값

3. 함수
- Scanner : import java.util.Scanner; / Scanner sc = new Scanner(System.in);
- charAt : str.chartAt(i);
- StringBuilder : sb = new StringBuilder(); / sb.append()
- 배열 : int[] countA = new int[26];
- 대문자 / 소문자
 - 확인 : Character.isUpperCase() / isLowerCase()
 - 변환 : Character.toUpperCase() / toLowerCase()
- doc.indexOf(word, startIndex) 
  - doc의 startIndex부터 처음으로 등장하는 word문자열 찾기, 찾으면 단어 시작인덱스 반환 아니면 -1
- 문자열 자르기
  - String[] current = sc.next().split(":");
- 문자열 포맷
  - String.format("%02d:%02d:%02d", needHour, needMinute, needSecond)

### 문제1 : 대소문자 바꾸기
 - 영어 소문자는 대문자로, 대문자는 소문자로 바꾸기
```java
public class ToggleCase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine(); // next()가 아닌 nextLine()으로 받으면 공백 포함 가능
        sc.close();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                // 대문자 -> 소문자
                sb.append(Character.toLowerCase(ch));
            } else if (Character.isLowerCase(ch)) {
                // 소문자 -> 대문자
                sb.append(Character.toUpperCase(ch));
            } else {
                // 알파벳이 아닌 문자는 그대로 유지
                sb.append(ch);
            }
        }

        System.out.println(sb.toString());
    }
}
```

### 문제2 : 애너그램 만들기
- 두개의 영단어가 있을때 서로 애너그램이 될수있도록 제거해야하는 최소 문자수 구하기
- 풀이방법 : 두개의 영단어에서 각 알파벳 개수 다른수만큼 출력
  1. 문자 수 세기 : 두개의 영단어의 a~z까지 빈도수 세서 저장
  2. 각 알파벳 개수 비교 : 각 알파벳에 대해 |countA[i] - countB[i]| 더하기
  3. 결과 : 각 더한값 결과값으로 출력
```java
public static int[] getAlphabetCountArray(String str) {
    int[] count = new int[26];
    for (int i = 0; i < str.length(); i++)
        count[str.charAt(i) - 'a']++;
    return count;
}

public static void main (String[] args)
{
    Scanner sc = new Scanner(System.in);
    String a = sc.nextLine();
    String b = sc.nextLine();
    sc.close();

    int[] countA = getAlphabetCountArray(a);
    int[] countB = getAlphabetCountArray(b);

    int ans = 0;
    for (int i = 0; i < 26; i++)
        ans += Math.abs(countA[i] - countB[i]);
    System.out.println(ans);
}
```

### 문제3 : 단어에서 가장 많이 사용된 알파벳이 무엇인지 알아내는 프로그램을 작성하시오.
```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine().toUpperCase();
        sc.close();

        int[] count = new int[26];
        for (int i = 0; i < str.length(); i++)
            count[str.charAt(i) - 'A']++;

        int maxCount = 0;
        char maxAlphabet = '?';
        for (int i = 0; i < 26; i++) {
            if (count[i] > maxCount) {
                maxCount = count[i];
                maxAlphabet = (char)('A' + i);
            } else if (count[i] == maxCount) {
                maxAlphabet = '?';
            }
        }
        System.out.println(maxAlphabet);
    }
}
```

### 문제4 : 주어진 단어가 문서에 등장하는 횟수
```java
class Main
{
    public static void main (String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String doc = sc.nextLine();
        String word = sc.nextLine();
        int startIndex = 0;
        int count = 0;
        while (true) {
            int findIndex = doc.indexOf(word, startIndex);
            if (findIndex < 0) // 찾으면 계속 찾기, 못찾으면 끝내기
                break;
            // 찾으면 해당 단어의 첫번째 인덱스 + 단어의길이 -> 해당 단어 다음부터 검색
            startIndex = findIndex + word.length();
            count++;
        }
        System.out.println(count);
    }
}
```

### 문제5 : HH:MM:SS 포맷의 두 시간 차이를 HH:MM:SS 포맷으로 출력해주기
- : 문자 자르기
  1. int hour = (time.charAt(0) - '0') * 10 + time.charAt(1) - '0';
  2. int hour = Integer.parseInt(time.subString(0,2));
  3. String[] time = "09:10:11".split(":") -> int hour = Integer.parseInt(time[0])
```java
class Main
{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] current = sc.next().split(":");
        String[] target = sc.next().split(":");

        int currentHour = Integer.parseInt(current[0]);
        int currentMinute = Integer.parseInt(current[1]);
        int currentSecond = Integer.parseInt(current[2]);

        int targetHour = Integer.parseInt(target[0]);
        int targetMinute = Integer.parseInt(target[1]);
        int targetSecond = Integer.parseInt(target[2]);

        // 모든걸 초 단위로 바꿔주기
        int currentSecondAmount = currentHour * 3600 + currentMinute * 60 + currentSecond;
        int targetSecondAmount = targetHour * 3600 + targetMinute * 60 + targetSecond;
        
        int NeedSecondAmount = targetSecondAmount - currentSecondAmount;
        // 음수면 날이 넘어간 거니까 하루를 더해주기
        if (NeedSecondAmount <= 0) NeedSecondAmount += 24 * 3600;

        int needHour = NeedSecondAmount / 3600;
        int needMinute = (NeedSecondAmount % 3600) / 60;
        int needSecond = NeedSecondAmount % 60;
        // %02d : 2자리수 정수인데 십자리수가 비어있으면 0으로 표기
        System.out.println(String.format("%02d:%02d:%02d", needHour, needMinute, needSecond));
    }
} 
```

