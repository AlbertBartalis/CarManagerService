package domain;


import ExceptieProprie.ExceptiiClient;
import ExceptieProprie.ExceptiiMasina;
import repository.IRepository;

public class ClientValidator {
    public void validareClient(Client client, IRepository<Client> clientRepository, String ceFace){
        String errors="";
       Client clientVerificat=clientRepository.read(client.getId());

        if(clientRepository.read(client.getId())==null && ceFace=="update"){ // verific, daca la update client id-ul exista in repository
            errors+="Nu exista clientul cu id-ul"+client.getId()+"\n";
        }
        if(client.getCnp().matches("[a-zA-Z]+")==true) {
            errors+="CNP-ul contine si alte caractere in afara de cifre \n";
        }
        if(client.getNume().matches("[a-zA-Z]+")==false) {
            errors+="Numele contine cifre sau alte caractere\n";
        }
        if(client.getPrenume().matches("[a-zA-Z]+")==false) {
            errors += "Prenumele contine cifre sau alte caractere\n";
        }
        if (!errors.isEmpty()) {
            throw new ExceptiiClient(errors);
        }
    }
}
