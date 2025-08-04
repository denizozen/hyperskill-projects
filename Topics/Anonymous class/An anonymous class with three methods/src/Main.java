public class Main {

    public static void main(String[] args) {

        ThreeMethodsInterface instance = /* create an instance of an anonymous class here, 
                                            do not forget ; in the end */

        instance.do1();
        instance.do2();
        instance.do3();
    }
}

interface ThreeMethodsInterface {

    void do1();

    void do2();

    void do3();
}