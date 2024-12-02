package com.example.gitdev_assign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.gitdev_assign.Navigation.NavGraph
import com.example.gitdev_assign.ViewModel.GitSearchViewModel
import com.example.gitdev_assign.ui.theme.GitDev_AssignTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var gitSearchViewModel: GitSearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitDev_AssignTheme{

                val gitSearchViewModel: GitSearchViewModel by viewModels() // This will be injected by Hilt


                // ViewModel initialization with Hilt injection
                val navController = rememberNavController()

                // Pass the injected ViewModel to the NavGraph
                NavGraph(navController = navController, viewModel = gitSearchViewModel)

            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

}*/



