import dto.Poder
import dto.Pokemon
import enums.TipoPokemon

fun main(args: Array<String>) {
    var poderes = arrayListOf<Poder>(
        Poder(0, "Bomba de agua", 1000, 500, 100),
        Poder(1, "Impactrueno", 900, 500, 90),
        Poder(2, "Lanza llamas", 1000, 500, 100),
        Poder(3, "Terremoto", 1500, 250, 120),
        Poder(4, "Embestida", 500, 100, 20),
        Poder(5, "Hojas afiladas", 1000, 500, 100),
        Poder(6, "Huracán", 1000, 1000, 100),
    )

    var pokemones = arrayListOf<Pokemon>(
        Pokemon(0,"Pikachu", 9000, 2000, 550, TipoPokemon.TRUENO, arrayListOf<Poder>(poderes[1])),
        Pokemon(1,"Bulbasaur", 8000, 2500, 450, TipoPokemon.HIERBA, arrayListOf<Poder>(poderes[5])),
        Pokemon(2,"Charmander", 8000, 2500, 450, TipoPokemon.FUEGO, arrayListOf<Poder>(poderes[2])),
        Pokemon(3,"Squirtle", 8000, 2500, 450, TipoPokemon.AGUA, arrayListOf<Poder>(poderes[0])),
        Pokemon(4,"Pidgey", 5000, 1000, 600, TipoPokemon.AIRE, arrayListOf<Poder>(poderes[6])),
        Pokemon(5,"Sandshrew", 12000, 500, 300, TipoPokemon.TIERRA, arrayListOf<Poder>(poderes[3])),
        Pokemon(6,"Clefairy", 10000, 500, 300, TipoPokemon.NORMAL, arrayListOf<Poder>(poderes[4])),
    )
    var pokemonSelected: Pokemon?
    do {
        mostrarPokemones(pokemones)
        var option =  readLine()
        pokemonSelected = pokemones.find { pokemon: Pokemon ->  pokemon.id == option?.toInt() }
        if(pokemonSelected == null) println("Esa opción no está disponible") else println("Has seleccionado a ${pokemonSelected?.nombre}")
    } while(pokemonSelected == null)

    var pokemonOponente: Pokemon? = obtenerPokemonOponente(pokemones)
    if(pokemonOponente != null) println("Te enfrentarás a ${pokemonOponente.nombre}") else println("No se ha encontrado contricante")
    if(pokemonSelected != null && pokemonOponente != null) {
        iniciarGame(pokemonSelected, pokemonOponente)
    } else {
        println("No se pudo iniciar el juego. Inténtalo más tarde.")
    }
}

fun iniciarGame(usuario: Pokemon, oponente: Pokemon) {
    var turno = true
    while(usuario.vida > 0 && oponente.vida > 0) {
        if(turno) {
            oponente.vida = oponente.vida - seleccionarAtaque(usuario)
            println("Al oponente le quedan ${oponente.vida} puntos de vida")
        } else {
            usuario.vida = usuario.vida - obtenerAtaqueOponente(oponente)
            println("Te queda ${usuario.vida} puntos de vida")
        }
        turno = !turno
    }
    if(usuario.vida <= 0) println("${oponente.nombre} te ha derrotado! Perdiste el juego.") else println("Ganaste el juego. Felicitaciones!!!")
}

fun obtenerAtaqueOponente(pokemon: Pokemon): Int {
    var ataque = 0
    var option = Math.random()
    if(option > 0.5) {
        println("El oponente ${pokemon.nombre} te ataca con ${pokemon.poderes.get(0).nombre}")
        ataque = pokemon.poderes.get(0).dano
    } else {
        println("El oponente ${pokemon.nombre} te ataca con ataque básico")
        ataque = pokemon.ataqueBase
    }
    return ataque
}

fun seleccionarAtaque(pokemon: Pokemon): Int {
    var ataque = 0
    var option: String? = "0"
    do {
        println("Con cual deseas atacar?")
        println("0: Ataque Base")
        println("1: ${pokemon.poderes.get(0).nombre}")
        option =  readLine()
        if(option != "0" && option != "1") {
            println("Esa opción no es un ataque válido, selecciona otro.")
        }
    } while (option != "0" && option != "1")

    while (ataque == 0) {
        when(option) {
            "0" -> {
                println("Tu ${pokemon.nombre} ataca con ataque básico")
                ataque = pokemon.ataqueBase
            }
            "1" -> {
                println("Tu ${pokemon.nombre} ataca con ${pokemon.poderes.get(0).nombre}")
                ataque = pokemon.poderes.get(0).dano
            }
        }
    }
    return ataque
}


fun obtenerPokemonOponente(pokemones: ArrayList<Pokemon>): Pokemon? {
    var random = Math.random() * pokemones.size
    var pokemonSelected: Pokemon? = pokemones.find { pokemon: Pokemon ->  pokemon.id == random.toInt() }
    return pokemonSelected
}

fun mostrarPokemones(pokemones: ArrayList<Pokemon>) {
    println("**************************Bienvenido al PokeConsoleGame!!**************************")
    println("Selecciona el número de tu pokemón para la batalla")
    for (pokemon in pokemones)
        println("${pokemon.id}: ${pokemon.nombre}")
}