import java.util.List;

public class Student {

    private int id;
    private String name;
    private List<Phone> ph;
    private Address add;

    public class Phone{
        private String mob;
    }

    public class Address {
        private String city;
        private String state;
        private String country;
        private String zipcode;
    }
}
