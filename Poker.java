import java.util.ArrayList;

public final class Poker { // naipes, código, métodos de una mano jugada
	private final int numNaipes = 5; // naipes de una mano
	private String naipes; // dq4r4p1tcr"; // string de 10 caracteres; contiene la descripción de cada naipe en 2 caracteres: valor y palo
	// ejemplo: "dq4r4p1tdr" significa: 'K' de corazones, '4' de diamantes (rombos), '4' de picas, 'AS' de tréboles, 'K' de diamantes (rombos)
	// su código final de valor será: "3dd44e" significando: doble pareja de 'K' y '4' con 'AS'
	private String codigo; // código alfanumérico de 6 caracteres, contiene el valor de la jugada
	private String codigoA; // registro de código cuando as vale máximo
	private String tipoJugada="1"; // contiene el tipo de jugada (carta alta, pareja, doble pareja, trío...)
	private ArrayList<String> naipesPalos = new ArrayList<String>();
	private ArrayList<String> naipesValores = new ArrayList<String>();
	private final String palosPosibles = "qrtp";
	private final String valoresPosibles = "123456789abcde";
	private final String[] palosNombres = {"corazones", "diamantes", "tréboles", "picas"};
	private final String[] naipesNombres = {"as", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "sota", "dama", "rey", "as"};
	private final String[] jugadasNombres = {"escalera real", "carta alta", "pareja", "doble pareja", "trío", "escalera", "color", "full", "póker", "escalera de color"};
	private int indiceNombreJugada; // índice en los nombres de jugadas
	private int indiceNombreJugadaA; // registro de índice cuando as vale máximo

	public Poker() {
		return;
	}

	private boolean validar() {
		if (naipes.length() != 2 * numNaipes)
			return false;
		for (int n = 0; n < numNaipes; n++)
			if (!palosPosibles.contains("" + naipes.charAt(2 * n + 1)) || !valoresPosibles.contains("" + naipes.charAt(2 * n)))
				return false;
		return true;
	}

	private void asValorMax() {
		naipes=naipes.replace("1", "e");
		return;
	}

	private void asValorMin() {
		naipes=naipes.replace("e", "1");
		return;
	}

	private void organizar() {
		vectorizarNaipes();
		ordenarVectores();
		generarNaipes();
		generarCodigo();
		return;
	}

	private void vectorizarNaipes() {
		naipesValores.clear();
		naipesPalos.clear();
		for (int n = 0; n < numNaipes; n++) {
			naipesValores.add(""+naipes.charAt(2 * n));
			naipesPalos.add(""+naipes.charAt(2 * n + 1));
		}
		return;
	}

	private void ordenarVectores() {
		for (int i = 0; i < numNaipes - 1; i++)
			for (int j = i + 1; j < numNaipes; j++)
				if (naipesValores.get(i).charAt(0)<naipesValores.get(j).charAt(0))
					intercambiarNaipes(i, j);
		return;
	}

	private void intercambiarNaipes(int i, int j) {
		String auxValor =naipesValores.get(i);
		String auxPalo =naipesPalos.get(i);
		naipesValores.set(i, naipesValores.get(j));
		naipesPalos.set(i, naipesPalos.get(j));
		naipesValores.set(j, auxValor);
		naipesPalos.set(j, auxPalo);
		return;
	}

	private void generarNaipes() {
		naipes = "";
		for (int n = 0; n < numNaipes; n++) {
			naipes +=naipesValores.get(n) +naipesPalos.get(n);
		}
		return;
	}

	private void generarCodigo() {
		codigo =tipoJugada;
		for (int n=0; n<numNaipes; n++) {
			codigo +=naipesValores.get(n);
		}
		return;
	}

	private void pedirNaipes() { // pide los datos por consola de los naipes de la jugada
		naipes="";
		char respuesta;
		String palo;
		String valor;
		K.escribir("-----------------------------------------------------\n");
		K.escribir("Voy a pedirle los cinco naipes de una mano de póker para codificar esa jugada.\n");
		for (int n = 1; n <= numNaipes; n++) {
			K.escribir("Naipe " + n + ":\n");
			respuesta = K.preguntarChar("¿Cuál es el valor del naipe " + n + "? Indique número o primera letra: As, 2, 3, 4, 5, 6, 7, 8, 9, Diez, J, Q, K", "a23456789djqk");
			switch (respuesta) {
				case 'a':
					valor = "1";
					break;
				case 'd':
					valor = "a";
					break;
				case 'j':
					valor = "b";
					break;
				case 'q':
					valor = "c";
					break;
				case 'k':
					valor = "d";
					break;
				default:
					valor = ""+respuesta;
					break;
			}
			respuesta = K.preguntarChar("¿Cuál es el palo del naipe " + n + "? Indique primera letra: Corazones, Diamantes, Tréboles, Picas", "cdtp");
			switch (respuesta) {
				case 'c':
					palo = "q";
					break;
				case 'd':
					palo = "r";
					break;
				default:
					palo = ""+respuesta;
					break;
			}
			naipes +=valor +palo;
		}
		K.escribir("------------------------------------------\n");
		K.escribir("Los naipes introducidos son:\n");
		for (int n = 0; n < numNaipes; n++)
			K.escribir(nombrarNaipe(naipes.substring(2 * n, 2 * n + 2)) + "\n");
		return;
	}
	private void informarResultado() { // informa el resultado del análisis por consola
		long puntos=puntuarCodigo();
		K.escribir("------------------------------------------\n");
		K.escribir("Jugada: " +jugadasNombres[indiceNombreJugada]+"\n");
		K.escribir("Código asignado: \"" + codigo + "\"\n");
		K.escribir("Valoración: "+puntos+(puntos==1 ? " punto.\n" : " puntos.\n"));
		if (puntos==1)
			K.escribir("Esa es la jugada más pobre que existe en el póker.\n");
		K.escribir("------------------------------------------\n");
		return;
	}
	private String nombrarNaipe(String naipe) {
		return nombrarNaipeValor(naipe) + " de " + nombrarNaipePalo(naipe);
	}

	private String nombrarNaipeValor(String naipe) {
		return naipesNombres[valoresPosibles.indexOf(naipe.charAt(0))];
	}

	private String nombrarNaipePalo(String naipe) {

		return palosNombres[palosPosibles.indexOf(naipe.charAt(1))];
	}
	private long puntuarCodigo() {
		return Long.parseLong(codigo, 16)-1528881L;
	}
	public static void main(String[] args) {
		try {
			Poker manoPoker = new Poker();
			manoPoker.ejecutarPrograma();
		} catch (Exception exc) {
			K.escribir("- Errores en ejecución. Abortando programa.\n");
			K.escribir(exc.getMessage());
			System.exit(1);
		}
		K.escribir("- Programa terminado normalmente.\n");
		return;
	}
	public void ejecutarPrograma () {
		pedirNaipes();
		if (!validar())
			System.exit(100);
		asValorMax();
		organizar();
		analizarJugada();
		codigoA=codigo;
		indiceNombreJugadaA=indiceNombreJugada;
		asValorMin();
		organizar();
		analizarJugada();
		if (codigo.compareTo(codigoA)<0) {
			codigo = codigoA;
			indiceNombreJugada = indiceNombreJugadaA;
		}
		informarResultado();
		K.preguntar("\nPulsar INTRO para salir");
		return;
	}

	private void analizarJugada() {
		boolean res;
		res=detectarEscaleraReal() || detectarEscaleraColor() || detectarPoker() || detectarFull() || detectarColor()
			|| detectarEscalera() || detectarTrio() || detectarDoblePareja() || detectarPareja() || detectarCartaAlta();
		return;
	}

	private boolean mismoPalo() {
		for (String car : naipesPalos)
			if (!car.equals(naipesPalos.getFirst()))
				return false;
		return true;
	}

	private boolean enSecuencia() {
		for (int n = 0; n < numNaipes-1; n++)
			if (Integer.parseInt(naipesValores.get(n), 16)!= Integer.parseInt(naipesValores.get(n+1), 16)+1)
				return false;
		return true;
	}

	private boolean detectarEscaleraReal() {
		if (mismoPalo() && enSecuencia() && naipesValores.getFirst().equals("e")) {
			indiceNombreJugada = 0;
			tipoJugada = "9";
			generarNaipes();
			generarCodigo();
			return true;
		} else
			return false;
	}
	private boolean detectarEscaleraColor() {
		if (mismoPalo() && enSecuencia()) {
			indiceNombreJugada = 9;
			tipoJugada = "9";
			generarNaipes();
			generarCodigo();
			return true;
		} else
			return false;
	}
	private boolean detectarPoker() {
		boolean esPoker;
		if (
			naipesValores.getFirst().charAt(0) == naipesValores.get(1).charAt(0)
			&& naipesValores.getFirst().charAt(0) == naipesValores.get(2).charAt(0)
			&& naipesValores.getFirst().charAt(0) == naipesValores.get(3).charAt(0)
		)
			esPoker = true;
		else if (
			naipesValores.get(1).charAt(0) == naipesValores.get(2).charAt(0)
			&& naipesValores.get(1).charAt(0) == naipesValores.get(3).charAt(0)
			&& naipesValores.get(1).charAt(0) == naipesValores.get(4).charAt(0)
		) {
			esPoker = true;
			intercambiarNaipes(0, 4);
		} else
			esPoker = false;
		if (esPoker) {
			indiceNombreJugada = 8;
			tipoJugada = "8";
			generarNaipes();
			generarCodigo();
		}
		return esPoker;
	}
	private boolean detectarFull() {
		boolean esFull;
		if (
			naipesValores.getFirst().charAt(0)==naipesValores.get(1).charAt(0)
			&& naipesValores.getFirst().charAt(0)==naipesValores.get(2).charAt(0)
			&& naipesValores.get(3).charAt(0)==naipesValores.get(4).charAt(0)
		)
			esFull=true;
		else if (
			naipesValores.getFirst().charAt(0)==naipesValores.get(1).charAt(0)
			&& naipesValores.get(2).charAt(0)==naipesValores.get(3).charAt(0)
			&& naipesValores.get(2).charAt(0)==naipesValores.get(4).charAt(0)
		) {
			esFull=true;
			intercambiarNaipes(0, 4);
			intercambiarNaipes(1, 3);
		} else
			esFull=false;
		if (esFull) {
			indiceNombreJugada = 7;
			tipoJugada = "7";
			generarNaipes();
			generarCodigo();
		}
		return esFull;
	}
	private boolean detectarColor() {
		if (mismoPalo()) {
			indiceNombreJugada = 6;
			tipoJugada = "6";
			generarNaipes();
			generarCodigo();
			return true;
		} else
			return false;
	}
	private boolean detectarEscalera() {
		if (enSecuencia()) {
			indiceNombreJugada = 5;
			tipoJugada = "5";
			generarNaipes();
			generarCodigo();
			return true;
		} else
			return false;
	}
	private boolean detectarTrio() {
		boolean esTrio;
		if (
			naipesValores.getFirst().charAt(0)==naipesValores.get(1).charAt(0)
			&& naipesValores.getFirst().charAt(0)==naipesValores.get(2).charAt(0)
		) {
			esTrio=true;
			if (naipesValores.get(3).charAt(0)<naipesValores.get(4).charAt(0))
				intercambiarNaipes(3, 4);
		} else if (
			naipesValores.get(1).charAt(0)==naipesValores.get(2).charAt(0)
			&& naipesValores.get(1).charAt(0)==naipesValores.get(3).charAt(0)
		) {
			esTrio = true;
			if (naipesValores.get(0).charAt(0) < naipesValores.get(4).charAt(0))
				intercambiarNaipes(0, 4);
		} else if (
			naipesValores.get(2).charAt(0)==naipesValores.get(3).charAt(0)
			&& naipesValores.get(2).charAt(0)==naipesValores.get(4).charAt(0)
		) {
			esTrio = true;
			if (naipesValores.get(0).charAt(0) < naipesValores.get(1).charAt(0))
				intercambiarNaipes(0, 1);
		} else
			esTrio=false;
		if (esTrio) {
			indiceNombreJugada = 4;
			tipoJugada = "4";
			generarNaipes();
			generarCodigo();
		}
		return esTrio;
	}
	private boolean detectarDoblePareja() {
		boolean esDoblePareja;
		if (
			naipesValores.get(0).charAt(0)==naipesValores.get(1).charAt(0)
			&& naipesValores.get(2).charAt(0)==naipesValores.get(3).charAt(0)
		) {
			esDoblePareja = true;
		} else if (
			naipesValores.get(0).charAt(0)==naipesValores.get(1).charAt(0)
			&& naipesValores.get(3).charAt(0)==naipesValores.get(4).charAt(0)
		) {
			esDoblePareja = true;
			intercambiarNaipes(2, 4);
		} else if (
			naipesValores.get(1).charAt(0)==naipesValores.get(2).charAt(0)
			&& naipesValores.get(3).charAt(0)==naipesValores.get(4).charAt(0)
		) {
			esDoblePareja = true;
			intercambiarNaipes(0, 2);
			intercambiarNaipes(2, 4);
		} else
			esDoblePareja=false;
		if (esDoblePareja) {
			indiceNombreJugada = 3;
			tipoJugada = "3";
			generarNaipes();
			generarCodigo();
		}
		return esDoblePareja;
	}
	private boolean detectarPareja() {
		boolean esPareja;
		if (naipesValores.get(0).charAt(0)==naipesValores.get(1).charAt(0)) {
			esPareja = true;
		} else if (naipesValores.get(1).charAt(0)==naipesValores.get(2).charAt(0)) {
			esPareja = true;
			intercambiarNaipes(0, 2);
		} else if (naipesValores.get(2).charAt(0)==naipesValores.get(3).charAt(0)) {
			esPareja = true;
			intercambiarNaipes(0, 2);
			intercambiarNaipes(1, 3);
		} else if (naipesValores.get(3).charAt(0)==naipesValores.get(4).charAt(0)) {
			esPareja = true;
			intercambiarNaipes(0, 3);
			intercambiarNaipes(1, 4);
		} else
			esPareja=false;
		if (esPareja) {
			indiceNombreJugada = 2;
			tipoJugada = "2";
			generarNaipes();
			generarCodigo();
		}
		return esPareja;
	}
	private boolean detectarCartaAlta() {
		indiceNombreJugada = 1;
		tipoJugada = "1";
		generarNaipes();
		generarCodigo();
		return true;
	}
}
