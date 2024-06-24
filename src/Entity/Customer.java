package Entity;

public class Customer {
    private String id;
    private String cus_name;
    private String cus_phone;

    public  Customer(){;}
    public Customer(String id, String cus_name, String cus_phone) {
        this.id = id;
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_phone() {
        return cus_phone;
    }

    public void setCus_phone(String cus_phone) {
        this.cus_phone = cus_phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", cus_name='" + cus_name + '\'' +
                ", cus_phone='" + cus_phone + '\'' +
                '}';
    }
}
