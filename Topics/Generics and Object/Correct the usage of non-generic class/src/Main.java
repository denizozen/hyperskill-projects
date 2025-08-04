class Holder {
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object v) {
        this.value = v;
    }
}

class Main {
    public static void main(String... args) {
        Holder holder = new Holder();
        holder.setValue(256);

        // correct the line to make the code compile
        Integer value = holder.getValue();

        // do not change
        System.out.println(value);
    }
}