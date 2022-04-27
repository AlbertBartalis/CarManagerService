package com.ubb.postuniv;

import domain.*;


import repository.IRepository;
import repository.InMemoryRepository;

import service.ClientService;
import service.MasinaService;
import service.TranzactieService;
import userInterface.console;

import java.io.Console;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Main {

    public static void main(String[] args) {
        // formatari pentru hardcodare + injecatare in consola
        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

            IRepository<Tranzactie> tranzactieRepository=new InMemoryRepository<Tranzactie>();
        IRepository<Masina> masinaRepository=new InMemoryRepository<Masina>();
        IRepository<Client> clientRepository=new InMemoryRepository<Client>();
        MasinaValidator masinaValidator = new MasinaValidator();
        ClientValidator clientValidator =new ClientValidator();
        TranzactieValidator tranzactieValidator=new TranzactieValidator();
        masinaRepository.create(new Masina("1","BMW",2021,2000,true));
        masinaRepository.create(new Masina("2","VW",2010,480000,false));
        masinaRepository.create(new Masina("3","Bmw",2017,190121,false));
        clientRepository.create(new Client("1","Maria","Maldarasanu","6021009147536",LocalDate.parse("19.12.2002",dateFormatter),LocalDate.parse("01.03.2020",dateFormatter)));
        clientRepository.create(new Client("2","Raul","Balace","1921129342870",LocalDate.parse("29.11.1992",dateFormatter), LocalDate.parse("02.03.2021",dateFormatter)));
        clientRepository.create(new Client("3","Marius","Istrate","6030625175656",LocalDate.parse("25.06.2003",dateFormatter),LocalDate.parse("03.03.2022",dateFormatter)));
        tranzactieRepository.create(new Tranzactie("1","1","1",223,100,LocalDateTime.parse("01.03.2022 12:30",dateTimeFormatter)));
        tranzactieRepository.create(new Tranzactie("2","2","2",4531,2000,LocalDateTime.parse("01.03.2022 10:22",dateTimeFormatter)));
        tranzactieRepository.create(new Tranzactie("3","3","3",531,200,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter)));
        TranzactieService tranzactieService = new TranzactieService(masinaRepository,clientRepository,tranzactieRepository,tranzactieValidator);
        MasinaService masinaService = new MasinaService(masinaRepository,tranzactieRepository,masinaValidator);
        ClientService clientService = new ClientService(clientRepository,tranzactieRepository,masinaRepository,clientValidator);
        console console=new console(tranzactieService,masinaService,clientService,dateFormatter,dateTimeFormatter);
        console.runUI();
    }
}
