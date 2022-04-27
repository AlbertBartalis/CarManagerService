package domain;


import ExceptieProprie.ExceptiiMasina;
import repository.IRepository;

public class MasinaValidator {
    public void validate(Masina masina, IRepository<Masina> masinaRepository, String ceFace){
        String errors="";
        Masina masinaVerificata=masinaRepository.read(masina.getId());

        if(masinaRepository.read(masina.getId())==null && ceFace=="update") {
            errors+="Nu exista o masina cu id-ul"+masina.getId()+"\n";
        } else {
            if(masina.getNrKm()<0){
                errors+="Kilometrajul masinii este negativ \n";
            }
            if(masina.getAnAchizitie()<0){
                errors+="Anul achizitiei este negativ \n";
            }
        }
        if (!errors.isEmpty()) {
            throw new ExceptiiMasina(errors);
        }
    }
}
