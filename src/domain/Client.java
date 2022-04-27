package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Client extends Entity {

    private String nume;
    private String prenume;
    private String cnp;
    private LocalDate dataNasterii;
    private LocalDate dataInregistrarii;
   /*
    private String dataNasterii; // voi folosi SimpleDateFormat
    private String dataInregistrarii; // voi folosi SimpleDateFormat
*/
    public Client(String id, String nume, String prenume, String cnp, LocalDate dataNasterii, LocalDate dataInregistrarii) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.dataNasterii = dataNasterii;
        this.dataInregistrarii = dataInregistrarii;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public LocalDate getDataNasterii() {
        return dataNasterii;
    }

    public LocalDate getDataInregistrarii() {
        return dataInregistrarii;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", cnp='" + cnp + '\'' +
                ", dataNasterii='" + dataNasterii + '\'' +
                ", dataInregistrarii='" + dataInregistrarii + '\'' +
                '}';
    }
}
