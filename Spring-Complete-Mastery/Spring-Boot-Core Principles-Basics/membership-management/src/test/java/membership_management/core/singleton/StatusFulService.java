package membership_management.core.singleton;

public class StatusFulService {

    private int price; // 상태를 유지하는 공유 필드

    public void order(String name, int price){
        System.out.println("name= " + name + ", price= " + price);
        this.price = price; //여기서 문제
    }
    
    //위에는 필드가 공유되고 유지되는 문제로 아래처럼 무상태(stateless)로 설계 하는 방식을 지향 해야한다
    public int renew_order(String name, int price){
        System.out.println("name= " + name + ", price= " + price);
        return price;// 마지막의 최종 값으로 변경 되는것을 방지할수 있음
    }
    public int getPrice(){
        return price;
    }
}
