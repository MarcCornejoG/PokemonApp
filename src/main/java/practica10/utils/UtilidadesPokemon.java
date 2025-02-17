package practica10.utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UtilidadesPokemon {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static Scanner scanner = new Scanner(System.in);

    public LocalDate convertirStringALocalDate(String fecha) {
        LocalDate date = LocalDate.parse(fecha, formatter);
        return date;
    }

    public String convertLocalDateString(LocalDate fecha) {
        String fechaStr = fecha.format(formatter);
        return fechaStr;
    }

    public boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public boolean validarFecha(String fecha) {
        try {
            LocalDate.parse(fecha);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean validarEsLegendario(String esLegendario) {
        return esLegendario.equals("true") || esLegendario.equals("false");
    }

    public boolean validarGeneracion(String generacion) {
        try {
            int generacionValida = Integer.parseInt(generacion);
            return generacionValida >= 1 && generacionValida <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validarPeso(BigDecimal peso) {
        return peso != null && peso.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean validarAltura(BigDecimal altura) {
        return altura != null && altura.compareTo(BigDecimal.ZERO) > 0;
    }

    public int obtenerNumeroValido(Scanner scanner) {
        int numero = -1;
        while (numero == -1) {
            try {
                String numeroIntroducido = scanner.nextLine();
                numero = Integer.parseInt(numeroIntroducido);

            } catch (NumberFormatException e) {
                System.out.println("Debe introducir un número válido. Introduzca un numero valido");
            }

        }
        return numero;
    }

    public BigDecimal leerBigDecimal(String mensaje) {
        BigDecimal valor = null;
        DecimalFormat formato = new DecimalFormat();
        formato.setParseBigDecimal(true);

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        formato.setDecimalFormatSymbols(simbolos);

        while (valor == null) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Error. El valor no puede estar vacío.");
                continue;
            }

            if (entrada.contains(".")) {
                System.out.println("Error. No se permite el punto. Utiliza coma (,) como separador decimal.");
                continue;
            }

            try {
                valor = new BigDecimal(entrada.replace(",", "."));

                if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Error. El valor debe ser mayor que cero.");
                    valor = null;
                }

            } catch (NumberFormatException e) {
                System.out.println("Error. Formato inválido. Introduce un número válido.");
            }
        }
        return valor;
    }
}
