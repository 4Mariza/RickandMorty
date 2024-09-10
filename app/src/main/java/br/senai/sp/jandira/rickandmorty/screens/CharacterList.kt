package br.senai.sp.jandira.rickandmorty.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.Result
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterList(controleDeNavegacao: NavHostController){



    var characterList by remember {
        mutableStateOf(listOf<Character>())
    }

    val callCharacter = RetrofitFactory().getCharacterService().getAllCharacters()

    callCharacter.enqueue(object : Callback<Result> {
        override fun onResponse(p0: Call<Result>, p1: Response<Result>) {
            characterList = p1.body()!!.results
        }

        override fun onFailure(p0: Call<Result>, p1: Throwable) {
        }
    })

    Surface(modifier = Modifier.fillMaxSize()){
        Column {
            Text(text = "Rick and Morty")
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn {
                items(characterList){ char ->
                    CharacterCard(character = char, navController = controleDeNavegacao)
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character?, navController: NavHostController) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp)
        .height(100.dp)
        .clickable {
                navController.navigate("CharacterDetails/${character?.id}")
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ){
            Card(modifier = Modifier.size(100.dp))
            {
                AsyncImage(
                    model = character?.image,
                    contentDescription = "Imagem do(a) ${character?.name}"
                )
            }
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = character?.name!!,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = character.species,
                    color = Color.White
                )
            }
        }

    }
}

