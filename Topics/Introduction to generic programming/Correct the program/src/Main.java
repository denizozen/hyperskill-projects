class Main {
    public static void main(String... args) {
        // correct the next line
        Printer<Integer> printer = new Printer();

        // do not change the code below
        printer.set("value");
        printer.print();
    }
}

class Printer<T> {
    private T value;

    void set(T value) {
        this.value = value;
    }

    void print() {
        System.out.println("Passed value: " + value);
    }
}