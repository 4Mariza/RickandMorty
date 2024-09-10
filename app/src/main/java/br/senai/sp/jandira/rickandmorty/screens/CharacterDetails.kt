package br.senai.sp.jandira.rickandmorty.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.Episode
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun getEpisodeIds(character: Character): List<Int> {
    val episodeIds = mutableListOf<Int>()
    character.episode?.forEach { episodeUrl->
        val episodeId = episodeUrl.substringAfterLast("/").toIntOrNull()
        if (episodeId != null) {
            episodeIds.add(episodeId)
        }
    }
    return episodeIds
}

@Composable
fun CharacterDetail(characterId: Int) {


    val character = remember {
        mutableStateOf(Character())
    }

    Column {
            val callCharacterById = RetrofitFactory()
                .getCharacterService()
                .getCharacterById(characterId)

            callCharacterById.enqueue(object : Callback<Character> {
                override fun onResponse(p0: Call<Character>, p1: Response<Character>) {
                    character.value = p1.body()!!
                    Log.i(
                        "RICK_MORTY",
                        "${character.value.name} - ${character.value.origin!!.name} - ${character.value.image}"
                    )
                }

                override fun onFailure(p0: Call<Character>, p1: Throwable) {
                    TODO("Not yet implemented")
                }

            })

            var episodes = getEpisodeIds(character.value)

        Column (modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AsyncImage(
                modifier = Modifier.background(color = Color.Transparent, shape = RoundedCornerShape(100.dp)),
                model = "${character.value.image}",
                contentDescription = "Imagem do(a) ${character.value.name}"
            )
            Text(
                text = character.value.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = character.value.origin?.name ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = character.value.species,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = character.value.gender,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = character.value.status,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = character.value.type,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "EPISODES")


            var episode = remember {
                mutableStateOf(Episode())
            }

            LazyColumn (modifier = Modifier.padding(horizontal = 8.dp)){
                Log.i("EPISODES", episodes.toString())
                items(episodes){ id ->
                    Log.i("episode it", episodes[id].toString())
                    val callEpisodeById = RetrofitFactory()
                        .getCharacterService()
                        .getEpisodeById(episodes[id])

                    callEpisodeById.enqueue(object : Callback<Episode> {
                        override fun onResponse(p0: Call<Episode>, p1: Response<Episode>) {
                            Log.i("ENQUEUE", episode.value.toString())
                             episode.value = p1.body()!!
                        }

                        override fun onFailure(p0: Call<Episode>, p1: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = CardDefaults.cardColors(Color.LightGray)
                    ){
                        Row (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                                .background(color = Color.Magenta)
                        ){
                            Column (
                                modifier = Modifier
                                    .background(color = Color.Blue)
                                    .width(150.dp)
                                    .fillMaxHeight()
                            ){
                                Log.i("nome", episode.value.name)
                                Text(text = episode.value.name)
                                Text(text = episode.value.episode)
                            }
                            Column {
                                Text(text = episode.value.air_date)
                        }
                    }
                }
            }
            }

        }

    }

}


