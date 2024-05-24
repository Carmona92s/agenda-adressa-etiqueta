package classes;

import java.io.*;
import java.util.LinkedList;

// Importem la llista de contactes de l'agenda actual
import static classes.Agenda.getLlistaContactes;

public class GuardarRecuperar {
    private static final String MARCA_FINAL_TELEFONS = "M_F_T";
    private static final String MARCA_FINAL_ADRECES = "M_F_A";
    private static final String SEPARADOR_ETIQUETA_TELEFON = "#";

    public static LinkedList<Contacte> recuperarAgenda(String nomArxiu) throws IOException {
        BufferedReader bur;
        boolean hiHaTelefons = false;
        boolean hiHaAdreces = false;
        bur = new BufferedReader(new FileReader(nomArxiu));
        LinkedList<Contacte> llistaContactes = new LinkedList<>();

        String linia;
        String[] dadesTelefon;

        linia = bur.readLine();
        while (linia != null) {
            // Inicialitzem les llistes
            LinkedList<Telefon> llistaTelefons = new LinkedList<>();
            LinkedList<Adressa> llistaAdreces = new LinkedList<>();
            // Llegir nom i cognom del contacte
            String nom = linia;
            String cognom = bur.readLine();
            // llistaTelefons per afegir TOTS els telèfons llegits del contacte
            // Llegir l'etiqueta del telèfon del contacte
            linia = bur.readLine();
            hiHaTelefons = (linia != null && !linia.equals(MARCA_FINAL_TELEFONS));
//            hiHaContactes = !llistaContactes.isEmpty();
            while (hiHaTelefons) {
                dadesTelefon = null;
                // Hi ha telefons amb el format int telefon#String etiqueta
                dadesTelefon = linia.split(SEPARADOR_ETIQUETA_TELEFON);
                int numeroTelefon = Integer.parseInt(dadesTelefon[0]);
                String etiqueta = dadesTelefon[1];
                llistaTelefons.add(new Telefon(numeroTelefon, etiqueta));
                linia = bur.readLine();
                hiHaTelefons = (linia != null && !linia.equals(MARCA_FINAL_TELEFONS));
            }
            if(linia.equals(MARCA_FINAL_TELEFONS)){
                linia = bur.readLine();
            }
            hiHaAdreces = (linia != null && !linia.equals(MARCA_FINAL_ADRECES));
            // Pot ser MARCA_FINAL_ADRESSES, o etiqueta 1a Adreça
            while (hiHaAdreces) {
                String etiqueta = linia;
                String carrer = bur.readLine();
                int numeroCarrer = Integer.parseInt(bur.readLine());
                String codiPostal = bur.readLine();
                String ciutat = bur.readLine();
                String pais = bur.readLine();
                llistaAdreces.add(new Adressa(carrer, numeroCarrer, codiPostal, ciutat, pais, etiqueta));
                linia = bur.readLine();
                hiHaAdreces = (linia != null && !linia.equals(MARCA_FINAL_ADRECES));
            }
            Contacte contacte = new Contacte(nom, cognom, llistaTelefons, llistaAdreces);
            llistaContactes.add(contacte);
            linia = bur.readLine();
        }
        bur.close();
        return llistaContactes;
    }
    public static boolean guardarAgenda(String nomArxiu) 
            throws IOException {
        // desa el contingut de la col·lecció donada en el primer paràmetre
        // a l'arxiu especificat pel segon paràmetre.

        boolean guardatCorrecte = false;
        if (getLlistaContactes().isEmpty()) {
            System.out.println("Agenda buida, no hi ha res a guardar!");
        } else {
            // Tipus de fitxer
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomArxiu))){ 
                // Línia a esborrar afegida perquè no aparegui cap error
                int prova = Character.getNumericValue(nomArxiu.charAt(0));
                // Tipus de canal
                for (Contacte contacte : getLlistaContactes()) {
                    // Escriure el nom del contacte ...
                   
                    // Escriure el cognom del contacte ...

                    // Obtenir TOTS els telèfons del contacte per poder
                    // iterar sobre TOTS els telèfons del contacte.
                     bw.write(contacte.getNom());
                    bw.newLine();
                    bw.write(contacte.getCognom());
                    bw.newLine();

                    for (Telefon telefon : contacte.getLlistaTelefons()) {
                        // Escriure el número del telèfon del contacte

                        // seguit del separador de l'etiqueta del telèfon i el número de teléfon.

                        // més escriure l'etiqueta del telèfon del contacte més el salt de línia
                        bw.write(telefon.getNumero() + SEPARADOR_ETIQUETA_TELEFON + telefon.getEtiqueta());
                        bw.newLine();

                    }

                    // desar marca de MARCA_FINAL_TELEFONS per indicar
                    // que ja no hi ha més telèfons del contacte
                     bw.write(MARCA_FINAL_TELEFONS);
                    bw.newLine();

                    if (contacte.getLlistaAdreces() == null) {
                        System.out.printf("No hi ha adreces per guardar!");
                    } else {
                        // Obtenir TOTES les adreces del contacte per poder
                        for (Adressa adressa : contacte.getLlistaAdreces()) {

                            // iterar sobre TOTES les adreces del contacte.

                            // Escriure el nom del carrer de l'adreça del contacte més el salt de línia

                            // Escriure el número del carrer de l'adreça del contacte més el salt de línia

                            // Escriure el codi postal del carrer de l'adreça del contacte més el salt de línia

                            // Escriure el nom de la ciutat de l'adreça del contacte més el salt de línia

                            // Escriure el nom del país de l'adreça del contacte més el salt de línia
                            bw.write(adressa.getEtiqueta());
                        bw.newLine();
                        bw.write(adressa.getCarrer());
                        bw.newLine();
                        bw.write(String.valueOf(adressa.getNumero()));
                        bw.newLine();
                        bw.write(adressa.getCodiPostal());
                        bw.newLine();
                        bw.write(adressa.getCiutat());
                        bw.newLine();
                        bw.write(adressa.getPais());
                        bw.newLine();
                        }

                        // desar marca de MARCA_FINAL_ADRESSES de la seqüència de telèfons  més el salt de línia
                         bw.write(MARCA_FINAL_ADRECES);
                    bw.newLine();
                    }
                }
                System.out.println("Agenda guardada al fitxer " +
                        nomArxiu + "!");
                guardatCorrecte = true;
                // Tancat canal
            } catch (Exception e) {
                System.out.println("Problemes a l'hora de llegir el fitxer de contactes!");
                // O millor, mostra un missatge d'error a l'usuari
                e.printStackTrace();            }
        }
        return guardatCorrecte;
    }
}
