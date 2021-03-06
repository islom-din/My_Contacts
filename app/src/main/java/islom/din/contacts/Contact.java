package islom.din.contacts;

public class Contact {
    private int id;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String img;

    public Contact(int id, String name, String lastName, String phone, String email, String img) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.img = img;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
