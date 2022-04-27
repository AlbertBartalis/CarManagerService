package domain;


import ExceptieProprie.ExceptiiMasina;
import ExceptieProprie.ExceptiiTranzactie;
import repository.IRepository;

public class TranzactieValidator {
    public void validareTranzactie(Tranzactie tranzactie, IRepository<Tranzactie> tranzactieRepository, IRepository<Masina> masinaRepository, IRepository<Client> clientRepository, String ceFace) {
        String errors = "";
        if (tranzactie.getSumaManopera() < 0) {
            errors += "Manopera nu poate fi suma negativa!\n";
        }
        if (tranzactie.getSumaPiese() < 0) {
            errors += "Piesele nu pot avea suma negativa!\n";
        }
        /*
        if (!masinaRepository.read().contains(tranzactie.getId_masina())) {
            errors += "Id-ul masinii de pe tranzactie +" + tranzactie.getId_masina() + " nu se afla in masina repository\n";
        }
        if (!clientRepository.read().contains(tranzactie.getIdCardClient())) {
            errors += "Id-ul clientului de pe tranzactie +" + tranzactie.getIdCardClient() + " nu se afla in client repository\n";
        }
        */

        if (!errors.isEmpty()) {
            throw new ExceptiiTranzactie(errors);
        }
    }
}
