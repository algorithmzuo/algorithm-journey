class Address implements Cloneable {
    String city;
    String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}

class Person implements Cloneable {
    String name;
    int age;
    Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return name + " (" + age + ") - " + address;
    }
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        int i = Integer.MAX_VALUE;
        Integer j = Integer.MAX_VALUE;
        System.out.println(i==j);

    }
}