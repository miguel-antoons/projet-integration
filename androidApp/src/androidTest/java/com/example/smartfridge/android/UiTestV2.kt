package com.example.smartfridge.android


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UiTestV2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreen::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA")

    @Test
    fun uiTestV2() {
        val appCompatButton = onView(
allOf(withId(R.id.buttonRegister), withText("Inscription"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
8),
isDisplayed()))
        appCompatButton.perform(click())
        
        val appCompatEditText = onView(
allOf(withId(R.id.username),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
1)))
        appCompatEditText.perform(scrollTo(), replaceText("Text"), closeSoftKeyboard())
        
        val appCompatEditText2 = onView(
allOf(withId(R.id.password),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4)))
        appCompatEditText2.perform(scrollTo(), replaceText("tesxt"), closeSoftKeyboard())
        
        val appCompatEditText3 = onView(
allOf(withId(R.id.confirm_password),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
9)))
        appCompatEditText3.perform(scrollTo(), replaceText("text"), closeSoftKeyboard())
        
        val appCompatCheckBox = onView(
allOf(withId(R.id.checkBox1),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
6)))
        appCompatCheckBox.perform(scrollTo(), click())
        
        val appCompatCheckBox2 = onView(
allOf(withId(R.id.checkBox2),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
7)))
        appCompatCheckBox2.perform(scrollTo(), click())
        
        val appCompatEditText4 = onView(
allOf(withId(R.id.password), withText("tesxt"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4)))
        appCompatEditText4.perform(scrollTo(), replaceText("text"))
        
        val appCompatEditText5 = onView(
allOf(withId(R.id.password), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4),
isDisplayed()))
        appCompatEditText5.perform(closeSoftKeyboard())
        
        val appCompatEditText6 = onView(
allOf(withId(R.id.email),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText6.perform(scrollTo(), replaceText("text"), closeSoftKeyboard())
        
        val appCompatButton2 = onView(
allOf(withId(R.id.button), withText("Inscription"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
13)))
        appCompatButton2.perform(scrollTo(), click())
        
        val appCompatEditText7 = onView(
allOf(withId(R.id.password), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4)))
        appCompatEditText7.perform(scrollTo(), click())
        
        val appCompatEditText8 = onView(
allOf(withId(R.id.password), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4)))
        appCompatEditText8.perform(scrollTo(), replaceText("Text"))
        
        val appCompatEditText9 = onView(
allOf(withId(R.id.password), withText("Text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4),
isDisplayed()))
        appCompatEditText9.perform(closeSoftKeyboard())
        
        val appCompatButton3 = onView(
allOf(withId(R.id.button), withText("Inscription"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
13)))
        appCompatButton3.perform(scrollTo(), click())
        
        val appCompatEditText10 = onView(
allOf(withId(R.id.password), withText("Text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4)))
        appCompatEditText10.perform(scrollTo(), click())
        
        val appCompatEditText11 = onView(
allOf(withId(R.id.password), withText("Text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4)))
        appCompatEditText11.perform(scrollTo(), replaceText("Text1234"))
        
        val appCompatEditText12 = onView(
allOf(withId(R.id.password), withText("Text1234"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
4),
isDisplayed()))
        appCompatEditText12.perform(closeSoftKeyboard())
        
        val appCompatButton4 = onView(
allOf(withId(R.id.button), withText("Inscription"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
13)))
        appCompatButton4.perform(scrollTo(), click())
        
        val appCompatEditText13 = onView(
allOf(withId(R.id.confirm_password), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
9)))
        appCompatEditText13.perform(scrollTo(), click())
        
        val appCompatEditText14 = onView(
allOf(withId(R.id.confirm_password), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
9)))
        appCompatEditText14.perform(scrollTo(), replaceText("Text1234"))
        
        val appCompatEditText15 = onView(
allOf(withId(R.id.confirm_password), withText("Text1234"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
9),
isDisplayed()))
        appCompatEditText15.perform(closeSoftKeyboard())
        
        val appCompatButton5 = onView(
allOf(withId(R.id.button), withText("Inscription"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
13)))
        appCompatButton5.perform(scrollTo(), click())
        
        val appCompatEditText16 = onView(
allOf(withId(R.id.email), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText16.perform(scrollTo(), replaceText("text@gmail.test"))
        
        val appCompatEditText17 = onView(
allOf(withId(R.id.email), withText("text@gmail.test"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5),
isDisplayed()))
        appCompatEditText17.perform(closeSoftKeyboard())
        
        val appCompatTextView = onView(
allOf(withId(R.id.terms_link), withText("termes d'utilisation"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
12)))
        appCompatTextView.perform(scrollTo(), click())

        pressBack()

        val appCompatButton6 = onView(
allOf(withId(R.id.button), withText("Inscription"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
13)))
        appCompatButton6.perform(scrollTo(), click())
        
        val appCompatCheckBox3 = onView(
allOf(withId(R.id.Terms_checkbox), withText(startsWith("J'accepte avoir lu et accepter les")),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
11)))
        appCompatCheckBox3.perform(scrollTo(), click())
        
        val appCompatButton7 = onView(
allOf(withId(R.id.button), withText("Inscription"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
13)))
        appCompatButton7.perform(scrollTo(), click())
        
        val appCompatEditText18 = onView(
        allOf(withId(R.id.editTextUser),
        childAtPosition(
        childAtPosition(
        withClassName(`is`("android.widget.LinearLayout")),
        0),
        2),
        isDisplayed()))
        appCompatEditText18.perform(replaceText("Text"), closeSoftKeyboard())
        
        val appCompatEditText19 = onView(
allOf(withId(R.id.editTextPassword),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText19.perform(replaceText("text"), closeSoftKeyboard())
        
        val appCompatCheckBox4 = onView(
allOf(withId(R.id.showPassword),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatCheckBox4.perform(click())
        
        val appCompatButton8 = onView(
allOf(withId(R.id.buttonLogin), withText("Connexion"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
6),
isDisplayed()))
        appCompatButton8.perform(click())
        
        val appCompatEditText20 = onView(
allOf(withId(R.id.editTextPassword), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText20.perform(click())
        
        val appCompatEditText21 = onView(
allOf(withId(R.id.editTextPassword), withText("text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText21.perform(replaceText("Test1234"))
        
        val appCompatEditText22 = onView(
allOf(withId(R.id.editTextPassword), withText("Test1234"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText22.perform(closeSoftKeyboard())
        
        val appCompatCheckBox5 = onView(
allOf(withId(R.id.rememberMe), withText("Se souvenir de moi"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
5),
isDisplayed()))
        appCompatCheckBox5.perform(click())
        
        val appCompatButton9 = onView(
allOf(withId(R.id.buttonLogin), withText("Connexion"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
6),
isDisplayed()))
        appCompatButton9.perform(click())
        
        val appCompatEditText23 = onView(
allOf(withId(R.id.editTextPassword), withText("Test1234"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText23.perform(click())
        
        val appCompatEditText24 = onView(
allOf(withId(R.id.editTextPassword), withText("Test1234"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText24.perform(replaceText("Text1234"))
        
        val appCompatEditText25 = onView(
allOf(withId(R.id.editTextPassword), withText("Text1234"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText25.perform(closeSoftKeyboard())
        
        val appCompatButton10 = onView(
allOf(withId(R.id.buttonLogin), withText("Connexion"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
6),
isDisplayed()))
        appCompatButton10.perform(click())
        
        val appCompatEditText26 = onView(
allOf(withId(R.id.editTextPassword), withText("Text1234"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
0),
isDisplayed()))
        appCompatEditText26.perform(click())
        
        val appCompatEditText27 = onView(
allOf(withId(R.id.editTextUser), withText("Text"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
2),
isDisplayed()))
        appCompatEditText27.perform(replaceText("text@gmail.test"))
        
        val appCompatEditText28 = onView(
allOf(withId(R.id.editTextUser), withText("text@gmail.test"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
2),
isDisplayed()))
        appCompatEditText28.perform(closeSoftKeyboard())
        
        val appCompatButton11 = onView(
allOf(withId(R.id.buttonLogin), withText("Connexion"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
6),
isDisplayed()))
        appCompatButton11.perform(click())
        
        val appCompatButton12 = onView(
allOf(withId(R.id.fragment_button), withText("Connecter un SmartFridge"),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
0),
isDisplayed()))
        appCompatButton12.perform(click())
        
        val appCompatImageButton = onView(
allOf(withId(R.id.return_icon), withContentDescription("Fermer la pop-up"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
8)))
        appCompatImageButton.perform(scrollTo(), click())
        
        val appCompatButton13 = onView(
allOf(withId(R.id.scan_button), withText("Scanner un code barre"),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
1),
isDisplayed()))
        appCompatButton13.perform(click())
        
        val appCompatImageButton2 = onView(
allOf(withId(R.id.button_return_scan), withContentDescription("Fermer la pop-up"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
0)))
        appCompatImageButton2.perform(scrollTo(), click())
        
        val bottomNavigationItemView = onView(
allOf(withId(R.id.product_page), withContentDescription("Produits"),
childAtPosition(
childAtPosition(
withId(R.id.bottomNavBar),
0),
1),
isDisplayed()))
        bottomNavigationItemView.perform(click())
        
        val appCompatImageButton3 = onView(
allOf(withId(R.id.info_legend),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
2),
isDisplayed()))
        appCompatImageButton3.perform(click())
        
        val appCompatImageView = onView(
allOf(withId(R.id.leg_close_popup), withContentDescription("Fermer la pop-up"),
childAtPosition(
allOf(withId(R.id.legend_popup),
childAtPosition(
withId(android.R.id.content),
0)),
0),
isDisplayed()))
        appCompatImageView.perform(click())
        
        val floatingActionButton = onView(
allOf(withId(R.id.addingBtn), withContentDescription("Add new product"),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
11),
isDisplayed()))
        floatingActionButton.perform(click())
        
        val appCompatEditText29 = onView(
allOf(withId(R.id.AlimentName),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText29.perform(scrollTo(), replaceText("Banane"), closeSoftKeyboard())
        
        val appCompatEditText30 = onView(
allOf(withId(R.id.AlimentQuantite),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
8)))
        appCompatEditText30.perform(scrollTo(), replaceText("5"), closeSoftKeyboard())
        
        val appCompatButton14 = onView(
allOf(withId(R.id.button_date_select), withText("Choisir Date"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
11)))
        appCompatButton14.perform(scrollTo(), click())
        
        val appCompatButton15 = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton15.perform(scrollTo(), click())
        
        val appCompatSpinner = onView(
allOf(withId(R.id.categorie_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
15)))
        appCompatSpinner.perform(scrollTo(), click())
        
        val appCompatCheckedTextView = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(1)
        appCompatCheckedTextView.perform(click())
        
        val appCompatButton16 = onView(
allOf(withId(R.id.button_add_aliment), withText("Ajouter"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
19)))
        appCompatButton16.perform(scrollTo(), click())
        
        val bottomNavigationItemView2 = onView(
allOf(withId(R.id.settings_page), withContentDescription("Paramètres"),
childAtPosition(
childAtPosition(
withId(R.id.bottomNavBar),
0),
2),
isDisplayed()))
        bottomNavigationItemView2.perform(click())
        
        val appCompatButton17 = onView(
allOf(withId(R.id.new_location), withText("Modifier Emplacements"),
childAtPosition(
childAtPosition(
withId(R.id.fragment_container),
0),
2),
isDisplayed()))
        appCompatButton17.perform(click())
        
        val appCompatEditText31 = onView(
allOf(withId(R.id.location_popup_new_location_edit),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
6),
isDisplayed()))
        appCompatEditText31.perform(replaceText("Frigo"), closeSoftKeyboard())
        
        val appCompatButton18 = onView(
allOf(withId(R.id.location_popup_save), withText("Enregistrer"),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
7),
isDisplayed()))
        appCompatButton18.perform(click())
        
        val appCompatButton19 = onView(
allOf(withId(R.id.new_location), withText("Modifier Emplacements"),
childAtPosition(
childAtPosition(
withId(R.id.fragment_container),
0),
2),
isDisplayed()))
        appCompatButton19.perform(click())
        
        val appCompatEditText32 = onView(
allOf(withId(R.id.location_popup_new_location_edit),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
6),
isDisplayed()))
        appCompatEditText32.perform(replaceText("Panier"), closeSoftKeyboard())
        
        val appCompatButton20 = onView(
allOf(withId(R.id.location_popup_save), withText("Enregistrer"),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
7),
isDisplayed()))
        appCompatButton20.perform(click())
        
        val appCompatButton21 = onView(
allOf(withId(R.id.new_location), withText("Modifier Emplacements"),
childAtPosition(
childAtPosition(
withId(R.id.fragment_container),
0),
2),
isDisplayed()))
        appCompatButton21.perform(click())
        
        val appCompatEditText33 = onView(
allOf(withId(R.id.location_popup_new_location_edit),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
6),
isDisplayed()))
        appCompatEditText33.perform(replaceText("Armoire"), closeSoftKeyboard())
        
        val appCompatButton22 = onView(
allOf(withId(R.id.location_popup_save), withText("Enregistrer"),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
7),
isDisplayed()))
        appCompatButton22.perform(click())
        
        val appCompatButton23 = onView(
allOf(withId(R.id.new_location), withText("Modifier Emplacements"),
childAtPosition(
childAtPosition(
withId(R.id.fragment_container),
0),
2),
isDisplayed()))
        appCompatButton23.perform(click())
        
        val appCompatSpinner2 = onView(
allOf(withId(R.id.location_popup_spinner),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
4),
isDisplayed()))
        appCompatSpinner2.perform(click())
        
        val appCompatCheckedTextView2 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(4)
        appCompatCheckedTextView2.perform(click())
        
        val appCompatButton24 = onView(
allOf(withId(R.id.location_popup_discard), withText("Supprimer"),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
8),
isDisplayed()))
        appCompatButton24.perform(click())
        
        val bottomNavigationItemView3 = onView(
allOf(withId(R.id.product_page), withContentDescription("Produits"),
childAtPosition(
childAtPosition(
withId(R.id.bottomNavBar),
0),
1),
isDisplayed()))
        bottomNavigationItemView3.perform(click())
        
        val recyclerView = onView(
allOf(withId(R.id.product_page_list),
childAtPosition(
withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
10)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        
        val appCompatButton25 = onView(
allOf(withId(R.id.modify_button), withText("Modifier"),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
24),
isDisplayed()))
        appCompatButton25.perform(click())
        
        val appCompatSpinner3 = onView(
allOf(withId(R.id.place_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
18)))
        appCompatSpinner3.perform(scrollTo(), click())
        
        val appCompatCheckedTextView3 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(2)
        appCompatCheckedTextView3.perform(click())
        
        val appCompatButton26 = onView(
allOf(withId(R.id.button_add_aliment), withText("Modifier"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
19)))
        appCompatButton26.perform(scrollTo(), click())
        
        val floatingActionButton2 = onView(
allOf(withId(R.id.addingBtn), withContentDescription("Add new product"),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
11),
isDisplayed()))
        floatingActionButton2.perform(click())
        
        val appCompatEditText34 = onView(
allOf(withId(R.id.AlimentName),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText34.perform(scrollTo(), replaceText("Viande haché"), closeSoftKeyboard())
        
        val appCompatEditText35 = onView(
allOf(withId(R.id.AlimentQuantite),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
8)))
        appCompatEditText35.perform(scrollTo(), replaceText("550"), closeSoftKeyboard())
        
        val appCompatButton27 = onView(
allOf(withId(R.id.button_date_select), withText("Choisir Date"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
11)))
        appCompatButton27.perform(scrollTo(), click())
        
        val appCompatButton28 = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton28.perform(scrollTo(), click())
        
        val appCompatSpinner4 = onView(
allOf(withId(R.id.categorie_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
15)))
        appCompatSpinner4.perform(scrollTo(), click())
        
        val appCompatCheckedTextView4 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(3)
        appCompatCheckedTextView4.perform(click())
        
        val appCompatSpinner5 = onView(
allOf(withId(R.id.place_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
18)))
        appCompatSpinner5.perform(scrollTo(), click())
        
        val appCompatCheckedTextView5 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(1)
        appCompatCheckedTextView5.perform(click())
        
        val appCompatButton29 = onView(
allOf(withId(R.id.button_add_aliment), withText("Ajouter"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
19)))
        appCompatButton29.perform(scrollTo(), click())
        
        val floatingActionButton3 = onView(
allOf(withId(R.id.addingBtn), withContentDescription("Add new product"),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
11),
isDisplayed()))
        floatingActionButton3.perform(click())
        
        val appCompatEditText36 = onView(
allOf(withId(R.id.AlimentName),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText36.perform(scrollTo(), replaceText("Jus de pommes"), closeSoftKeyboard())
        
        val appCompatEditText37 = onView(
allOf(withId(R.id.AlimentQuantite),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
8)))
        appCompatEditText37.perform(scrollTo(), replaceText("1000"), closeSoftKeyboard())
        
        val appCompatButton30 = onView(
allOf(withId(R.id.button_date_select), withText("Choisir Date"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
11)))
        appCompatButton30.perform(scrollTo(), click())
        
        val appCompatButton31 = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton31.perform(scrollTo(), click())
        
        val appCompatSpinner6 = onView(
allOf(withId(R.id.categorie_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
15)))
        appCompatSpinner6.perform(scrollTo(), click())
        
        val appCompatCheckedTextView6 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(6)
        appCompatCheckedTextView6.perform(click())
        
        val appCompatSpinner7 = onView(
allOf(withId(R.id.place_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
18)))
        appCompatSpinner7.perform(scrollTo(), click())
        
        val appCompatCheckedTextView7 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(1)
        appCompatCheckedTextView7.perform(click())
        
        val appCompatButton32 = onView(
allOf(withId(R.id.button_add_aliment), withText("Ajouter"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
19)))
        appCompatButton32.perform(scrollTo(), click())
        
        val floatingActionButton4 = onView(
allOf(withId(R.id.addingBtn), withContentDescription("Add new product"),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
11),
isDisplayed()))
        floatingActionButton4.perform(click())
        
        val appCompatEditText38 = onView(
allOf(withId(R.id.AlimentName),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText38.perform(scrollTo(), replaceText("Emmental"), closeSoftKeyboard())
        
        val appCompatEditText39 = onView(
allOf(withId(R.id.AlimentQuantite),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
8)))
        appCompatEditText39.perform(scrollTo(), replaceText("200"), closeSoftKeyboard())
        
        val appCompatButton33 = onView(
allOf(withId(R.id.button_date_select), withText("Choisir Date"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
11)))
        appCompatButton33.perform(scrollTo(), click())
        
        val appCompatButton34 = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton34.perform(scrollTo(), click())
        
        val appCompatSpinner8 = onView(
allOf(withId(R.id.categorie_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
15)))
        appCompatSpinner8.perform(scrollTo(), click())
        
        val appCompatCheckedTextView8 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(2)
        appCompatCheckedTextView8.perform(click())
        
        val appCompatSpinner9 = onView(
allOf(withId(R.id.place_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
18)))
        appCompatSpinner9.perform(scrollTo(), click())
        
        val appCompatCheckedTextView9 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(1)
        appCompatCheckedTextView9.perform(click())
        
        val appCompatButton35 = onView(
allOf(withId(R.id.button_add_aliment), withText("Ajouter"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
19)))
        appCompatButton35.perform(scrollTo(), click())
        
        val floatingActionButton5 = onView(
allOf(withId(R.id.addingBtn), withContentDescription("Add new product"),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
11),
isDisplayed()))
        floatingActionButton5.perform(click())
        
        val appCompatEditText40 = onView(
allOf(withId(R.id.AlimentName),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText40.perform(scrollTo(), replaceText("Raisin en sucre"), closeSoftKeyboard())
        
        val appCompatEditText41 = onView(
allOf(withId(R.id.AlimentQuantite),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
8)))
        appCompatEditText41.perform(scrollTo(), replaceText("5"), closeSoftKeyboard())
        
        val appCompatButton36 = onView(
allOf(withId(R.id.button_date_select), withText("Choisir Date"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
11)))
        appCompatButton36.perform(scrollTo(), click())
        
        val appCompatImageButton4 = onView(
allOf(withClassName(`is`("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Next month"),
childAtPosition(
allOf(withClassName(`is`("android.widget.DayPickerView")),
childAtPosition(
withClassName(`is`("com.android.internal.widget.DialogViewAnimator")),
0)),
2)))
        appCompatImageButton4.perform(scrollTo(), click())
        
        val appCompatImageButton5 = onView(
allOf(withClassName(`is`("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Next month"),
childAtPosition(
allOf(withClassName(`is`("android.widget.DayPickerView")),
childAtPosition(
withClassName(`is`("com.android.internal.widget.DialogViewAnimator")),
0)),
2)))
        appCompatImageButton5.perform(scrollTo(), click())
        
        val appCompatButton37 = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton37.perform(scrollTo(), click())
        
        val appCompatSpinner10 = onView(
allOf(withId(R.id.categorie_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
15)))
        appCompatSpinner10.perform(scrollTo(), click())
        
        val appCompatCheckedTextView10 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(5)
        appCompatCheckedTextView10.perform(click())
        
        val appCompatSpinner11 = onView(
allOf(withId(R.id.place_spinner),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
18)))
        appCompatSpinner11.perform(scrollTo(), click())
        
        val appCompatCheckedTextView11 = onData(anything())
.inAdapterView(allOf(withId(R.id.select_dialog_listview),
childAtPosition(
withId(R.id.contentPanel),
0)))
.atPosition(2)
        appCompatCheckedTextView11.perform(click())
        
        val appCompatButton38 = onView(
allOf(withId(R.id.button_add_aliment), withText("Ajouter"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
19)))
        appCompatButton38.perform(scrollTo(), click())
        
        val view = onView(
allOf(withId(R.id.sort_by_location),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
6),
isDisplayed()))
        view.perform(click())
        
        val view2 = onView(
allOf(withId(R.id.sort_by_name),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
4),
isDisplayed()))
        view2.perform(click())
        
        val view3 = onView(
allOf(withId(R.id.sort_by_expiration_date),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
8),
isDisplayed()))
        view3.perform(click())
        
        val recyclerView2 = onView(
allOf(withId(R.id.product_page_list),
childAtPosition(
withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
10)))
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(1, click()))
        
        val appCompatButton39 = onView(
allOf(withId(R.id.modify_button), withText("Modifier"),
childAtPosition(
allOf(withId(R.id.product_popup),
childAtPosition(
withId(android.R.id.content),
0)),
24),
isDisplayed()))
        appCompatButton39.perform(click())
        
        val appCompatEditText42 = onView(
allOf(withId(R.id.AlimentName), withText("Banane"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5)))
        appCompatEditText42.perform(scrollTo(), replaceText("Banane périmée"))
        
        val appCompatEditText43 = onView(
allOf(withId(R.id.AlimentName), withText("Banane périmée"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
5),
isDisplayed()))
        appCompatEditText43.perform(closeSoftKeyboard())
        
        val appCompatButton40 = onView(
allOf(withId(R.id.button_date_select), withText("Choisir Date"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
11)))
        appCompatButton40.perform(scrollTo(), click())
        
        val appCompatButton41 = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton41.perform(scrollTo(), click())
        
        val appCompatButton42 = onView(
allOf(withId(R.id.button_add_aliment), withText("Modifier"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
19)))
        appCompatButton42.perform(scrollTo(), click())
        
        val view4 = onView(
allOf(withId(R.id.sort_by_expiration_date),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
8),
isDisplayed()))
        view4.perform(click())
        
        val appCompatEditText44 = onView(
allOf(withId(R.id.search_products),
childAtPosition(
childAtPosition(
withId(R.id.swiperefresh),
0),
1),
isDisplayed()))
        appCompatEditText44.perform(replaceText("Pommes"), closeSoftKeyboard())
        
        val bottomNavigationItemView4 = onView(
allOf(withId(R.id.settings_page), withContentDescription("Paramètres"),
childAtPosition(
childAtPosition(
withId(R.id.bottomNavBar),
0),
2),
isDisplayed()))
        bottomNavigationItemView4.perform(click())
        
        val appCompatButton43 = onView(
allOf(withId(R.id.delete_account), withText("supprimer son compte"),
childAtPosition(
childAtPosition(
withId(R.id.fragment_container),
0),
4),
isDisplayed()))
        appCompatButton43.perform(click())
        
        val appCompatButton44 = onView(
allOf(withId(R.id.delte_account_popup_discard), withText("Supprimer"),
childAtPosition(
allOf(withId(R.id.delete_account_popup),
childAtPosition(
withId(android.R.id.content),
0)),
3),
isDisplayed()))
        appCompatButton44.perform(click())
        }
    
    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    }
