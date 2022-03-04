package test;

public class PersonPrinter {

    private final Person person;

    public PersonPrinter(Person person) {
        this.person = person;
    }

    public String print() {
        return person.getName() + " " + person.getSurname();
    }
}
