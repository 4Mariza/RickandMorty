package br.senai.sp.jandira.rickandmorty.service

import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.Episode
import br.senai.sp.jandira.rickandmorty.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

  //https://rickandmortyapi.com/api/character
    @GET("character")
    fun getAllCharacters(): Call<Result>

    @GET("character/{id}")
    fun getCharacterById(@Path("id") id:Int): Call<Character>

    @GET("episode/{id}")
    fun getEpisodeById(@Path("id") id:Int): Call<Episode>

}