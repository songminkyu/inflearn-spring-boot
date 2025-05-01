package membership_management.core.singleton;

public class SingletonService {

    //1. static 영역에 객체를 딱 1개만 생성한다.
    private static final SingletonService instance = new SingletonService();

    //2. 생성자를 private으로 선언 해서 외부에 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService(){
    }

    //3. public으로 열어서 객체 인스터스가 필요하면 해당 static 메서드를 통해서 조회하도록 허용한다.
    public static SingletonService getInstance(){
        return instance;
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
