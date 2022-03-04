package untangletestlib;

public class AddressPrinter {

    private final Address address;

    public AddressPrinter(Address address) {
        this.address = address;
    }

    public String print() {
        return this.address.getAddress() + " " + this.address.getCity();
    }
}
