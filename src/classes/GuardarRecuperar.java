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
            while (hiHaTelefons) {
                dadesTelefon = linia.split(SEPARADOR_ETIQUETA_TELEFON);
                int numeroTelefon = Integer.parseInt(dadesTelefon[0]);
                String etiqueta = dadesTelefon[1];
                llistaTelefons.add(new Telefon(numeroTelefon, etiqueta));
                linia = bur.readLine();
                hiHaTelefons = (linia != null && !linia.equals(MARCA_FINAL_TELEFONS));
            }
            if (linia.equals(MARCA_FINAL_TELEFONS)) {
                linia = bur.readLine();
            }
            hiHaAdreces = (linia != null && !linia.equals(MARCA_FINAL_ADRECES));
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

    public static boolean guardarAgenda(String nomArxiu) throws IOException {
        boolean guardatCorrecte = false;
        if (getLlistaContactes().isEmpty()) {
            System.out.println("Agenda buida, no hi ha res a guardar!");
        } else {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomArxiu))) {
                for (Contacte contacte : getLlistaContactes()) {
                    // Escriure el nom i el cognom del contacte
                    bw.write(contacte.getNom());
                    bw.newLine();
                    bw.write(contacte.getCognom());
                    bw.newLine();

                    // Escriure els telèfons del contacte
                    for (Telefon telefon : contacte.getLlistaTelefons()) {
                        bw.write(telefon.getNumero() + SEPARADOR_ETIQUETA_TELEFON + telefon.getEtiqueta());
                        bw.newLine();
                    }
                    // Marca final de telèfons
                    bw.write(MARCA_FINAL_TELEFONS);
                    bw.newLine();

                    // Escriure les adreces del contacte
                    for (Adressa adressa : contacte.getLlistaAdreces()) {
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
                    // Marca final d'adreces
                    bw.write(MARCA_FINAL_ADRECES);
                    bw.newLine();
                }
                guardatCorrecte = true;
                System.out.println("Agenda guardada al fitxer " + nomArxiu + "!");
            } catch (IOException e) {
                System.out.println("Problemes a l'hora de guardar el fitxer de contactes!");
                e.printStackTrace();
            }
        }
        return guardatCorrecte;
    }
}
