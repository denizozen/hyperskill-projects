class TestDrive {
    public static void main(String[] args) {
        Breakfast bagel = /* write your code here */;
        bagel = new Butter(bagel);
        bagel = new Cheese(bagel);
        System.out.println(bagel.getSummary());

        Breakfast bun = /* write your code here */;
        bun = new Butter(bun);
        bun = new Jam(bun);
        System.out.println(bun.getSummary());
    }
}

interface Breakfast {
    String getDescription();

    int getKcal();

    default String getSummary() {
        return getDescription() + ". kCal: " + getKcal();
    }
}

class Bread implements Breakfast {
    String description = "Bread";
    int kCal = 200;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getKcal() {
        return kCal;
    }
}

class Butter implements Breakfast {
    private final Breakfast breakfast;

    Butter(Breakfast breakfast) {
        this.breakfast = breakfast;
    }

    @Override
    public String getDescription() {
        return "Butter on top of " + /* write your code here */;
    }

    @Override
    public int getKcal() {
        return /* write your code here */ + 50;
    }
}

class Jam implements Breakfast {
    private final Breakfast breakfast;

    Jam(Breakfast breakfast) {
        this.breakfast = breakfast;
    }

    @Override
    public String getDescription() {
        return "Jam on top of " + /* write your code here */;
    }

    @Override
    public int getKcal() {
        return /* write your code here */ + 120;
    }
}

class Cheese implements Breakfast {
    private final Breakfast breakfast;

    Cheese(Breakfast breakfast) {
        this.breakfast = breakfast;
    }

    @Override
    public String getDescription() {
        return "Cheese on top of " + /* write your code here */;
    }

    @Override
    public int getKcal() {
        return /* write your code here */ + 40;
    }
}