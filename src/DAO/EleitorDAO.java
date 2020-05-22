package DAO;

import Classes.Eleitor;
import EstruturasDeDados.Eleitor.ABBEleitor;

import java.io.*;

public class EleitorDAO {

    private Eleitor[] eleitor;
    private String filename;
    public EleitorDAO(String filename) {
        this.filename = filename;
    }

    public ABBEleitor getAll() {
        ABBEleitor arvore = readFromFile();
        return arvore;
    }

    private ABBEleitor readFromFile() {
        ABBEleitor aux = new ABBEleitor();
        try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(filename))) {
            String idSTR;
            int numeroDeLinhas = countLinesNew(filename);
            int i = 0;
            while ((idSTR = buffer_entrada.readLine()) != null) {
                try {
                    String[] arrayEntrada = idSTR.split(";");
                    if (arrayEntrada.length > 5 || arrayEntrada.length < 5){
                        throw new Exception("Erro De leitura! Dados obrigatórios não foram preenchidos");
                    }
                   this.eleitor = new Eleitor[numeroDeLinhas + 1];

                    this.eleitor[i] = new Eleitor();
                    this.eleitor[i].setNome(arrayEntrada[0]);
                    this.eleitor[i].setTituloEleitoral(Double.parseDouble(arrayEntrada[1]));
                    this.eleitor[i].setMunicipioEleitoral(arrayEntrada[2]);
                    this.eleitor[i].setZonaEleitoral(arrayEntrada[3]);
                    this.eleitor[i].setSecaoEleitoral(arrayEntrada[4]);

                    aux.inserir(eleitor[i]);
                } catch (Exception e) {
                    System.out.println(e);
                }
                i++;
            }
            return aux;
        } catch (Exception e) {
            System.out.println("Erro na abertura do Arquivo! \n Verifique o nome do arquivo e tente novamente.");
        }
        return null;
    }

    public static int countLinesNew(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i=0; i<1024;) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                System.out.println(readChars);
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        } finally {
            is.close();
        }
    }
}
