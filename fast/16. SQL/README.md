## SQL

### 순서
1. FROM 절
2. WHERE 절
3. GROUP BY 절
    - SELECT 문에는 그룹화된 열 또는 집계 함수(예: SUM(), AVG(), COUNT() 등)를 사용한 열만 포함
4. HAVING 절
    - group by로 묶인 컬럼에만 사용 가능
5. SELECT 절
6. ORDER BY 절

### Union VS Union All
- union : 중복제거
- union all : 중복포함

### RANK, Dense Rank, Row Number
```roomsql
SELECT name,
       subject,
       score,
       ROW_NUMBER() OVER (PARTITION BY subject ORDER BY score DESC) AS RowNumber,
       RANK() OVER (PARTITION BY subject ORDER BY score DESC) AS Rank,
       DENSE_RANK() OVER (PARTITION BY subject ORDER BY score DESC) AS DenseRank
FROM Scores;
```
1. RANK() : 동일한 값에 대해 동일한 순위를 부여하고, 다음 순위는 동일한 값의 개수만큼 건너뜁니다.(1,1,3)
2. DENSE_RANK() : 함수도 동일한 값에 대해 동일한 순위를 부여하지만, 다음 순위는 건너뛰지 않고 연속적으로 할당됩니다.(1,1,2)
3. ROW_NUMBER() : 각 행에 대해 고유한 연속 숫자를 할당(1,2,3)

### 집계함수
- MAX()
- MIN()
- COUNT()
- SUM()
- AVG()

### 문자열 함수
1. SUBSTRING() : 문자열의 특정 부분을 추출
   - SUBSTRING(string, int, int)
   ```roomsql
   SELECT SUBSTRING('Hello World' FROM 1 FOR 4) AS ExtractedString;
   -- HELL 
   ```
2. TRIM() : 함수는 문자열 앞뒤의 특정 문자들을 제거(공백제거)
    ```roomsql
    SELECT TRIM('    Hello World    ') AS TrimmedString;
    ```
   - LTRIM() : 문자열의 시작 부분에서 원치 않는 문자(기본적으로 공백)를 제거
   ```roomsql
   SELECT LTRIM('    Hello World') AS TrimmedLeft;
   ```
   - RTRIM() : 문자열의 끝 부분에서 원치 않는 문자(기본적으로 공백)를 제거
   ```roomsql
   SELECT RTRIM('Hello World    ') AS TrimmedRight;
   ```
   - TRIM(string, string)
   ```roomsql
   SELECT LTRIM('***Hello World***', '*') AS TrimmedStarsLeft, -- Hello World*** 
          RTRIM('***Hello World***', '*') AS TrimmedStarsRight -- ***Hello World
     FROM dual;
   ```
   
3. PAD() : 문자열의 길이를 특정 길이로 만들기 위해 문자를 추가
    - LPAD(string, length, pad_string)
   ```roomsql
   SELECT LPAD('Hello', 10, '*') AS PaddedString; -- *****Hello
   ```
    - RPAD(string, length, pad_string)
   ```roomsql
   SELECT LPAD('Hello', 10, '*') AS PaddedString; -- Hello*****
   ```   
   
4. LENGTH() : 문자열의 길이를 반환
5. REPLACE(string, substring_to_replace, replacement_string)
    - 첫 번째 인자로 주어진 문자열에서, 두 번째 인자로 지정된 문자열(패턴)을 찾아, 세 번째 인자로 주어진 문자열로 모두 교체
   ```roomsql
   SELECT REPLACE('Hello World', 'World', 'There') AS ReplacedString; 
   -- Hello There
   ```
   
### 날짜함수(ORACLE)
1. SYSDATE : 현재 날짜와 시간을 반환합니다.
```roomsql
SELECT SYSDATE FROM dual;
-- 결과: 2025-01-12 12:45:01 (예시, 실행 시점에 따라 다름)
```
2. CURRENT_DATE
```roomsql
SELECT CURRENT_DATE FROM dual;
-- 결과: 2025-01-12 (예시, 사용자 세션의 타임존 기준)
```
3. EXTRACT
```roomsql
SELECT EXTRACT(YEAR FROM SYSDATE) AS year FROM dual;
-- 결과: 2025
SELECT EXTRACT(MONTH FROM SYSDATE) AS month FROM dual;
-- 결과: 1
```
4. TO_DATE
```roomsql
SELECT TO_DATE('2021-01-01', 'YYYY-MM-DD') FROM dual;
-- 결과: 2021-01-01
```
5. ADD_MONTHS
    - 일자 : SYSDATE +10, -5를 통해 조절 가능
    - 년도 : ADD_MONTHS(SYSDATE, 12*2) : 2년후
```roomsql
SELECT ADD_MONTHS(SYSDATE, 1) FROM dual;
-- 결과: 2025-02-12 (예시, 실행 시점에 따라 다름)
SELECT SYSDATE, SYSDATE - 5 AS "5 Days Ago"
SELECT SYSDATE, ADD_MONTHS(SYSDATE, 12 * 2) AS "Two Years Later"
```
6. TRUNC
```roomsql
SELECT TRUNC(SYSDATE, 'YEAR') FROM dual;
-- 결과: 2025-01-01 (예시, 실행 시점에 따라 다름)
```

### 그 외
1. TO_CHAR : 문자열로변환
```roomsql
SELECT TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') AS formatted_date FROM dual;
```
2. COALESCE : 인자 목록에서 첫 번째로 NULL이 아닌 값을 반환
```roomsql
SELECT COALESCE(phone_number, email) AS contact_info FROM employees;
```
3. NVL : 특정 표현식이 NULL일 경우 대체 값을 제공하는 역할
```roomsql
SELECT NVL(phone_number, 'No Phone') AS Phone
FROM employees;
```
4. CAST : 데이터 유형을 한 형식에서 다른 형식으로 변환
```roomsql
SELECT CAST('123' AS INT) AS NumericValue FROM dual;
SELECT CAST('2021-01-01' AS DATE) AS NewDate FROM dual;
```
5. ROUND : SQL에서 숫자를 특정 소수점 자리에서 반올림하는 데 사용
```roomsql
SELECT ROUND(123.456, 1) AS RoundedValue; --123.5
SELECT ROUND(123.456, -2) AS RoundedValue; -- 100
```
