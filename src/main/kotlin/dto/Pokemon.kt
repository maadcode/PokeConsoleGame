package dto

import enums.TipoPokemon

class Pokemon (val id: Int, val nombre: String, var vida: Int, var mana: Int, val ataqueBase: Int, val tipo: TipoPokemon, val poderes:ArrayList<Poder>)