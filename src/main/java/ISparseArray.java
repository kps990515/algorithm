// SparseArray 인터페이스 (제네릭 버전)
interface ISparseArray<T> {
    void init(T[] arr, int size);  // 원본 배열 초기화
    void set(int i, T val);        // 특정 인덱스 값을 설정
    T get(int i);                  // 특정 인덱스 값을 반환
}

