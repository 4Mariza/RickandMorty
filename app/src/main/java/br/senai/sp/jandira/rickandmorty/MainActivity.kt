package br.senai.sp.jandira.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.senai.sp.jandira.rickandmorty.screens.CharacterDetail
import br.senai.sp.jandira.rickandmorty.screens.CharacterList
import br.senai.sp.jandira.rickandmorty.ui.theme.RickAndMortyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                val controleDeNavegacao = rememberNavController()
                NavHost(
                    navController = controleDeNavegacao,
                    startDestination = "CharacterList"
                ){
                    composable(route = "CharacterList"){ CharacterList(controleDeNavegacao) }
                    composable(route = "CharacterDetails/{characterId}",
                    arguments = listOf(navArgument("characterId") {type = NavType.IntType})
                    ) { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                    CharacterDetail(characterId)
                }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}