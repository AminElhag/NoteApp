package com.example.noteapp.presenter.note.list.components

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.core.TestTags
import com.example.noteapp.di.note.AppModule
import com.example.noteapp.presenter.MainActivity
import com.example.noteapp.presenter.note.Screens
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            val navController = rememberNavController()
            NoteAppTheme {
                NavHost(navController = navController, startDestination = Screens.NoteList.route) {
                    composable(Screens.NoteList.route) {
                        NoteListScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeTestRule.onNodeWithTag(TestTags.TOGGLE_ORDER_SECTION).assertDoesNotExist()
    }
}