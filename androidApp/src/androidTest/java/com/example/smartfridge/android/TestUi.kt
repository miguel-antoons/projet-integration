package com.example.smartfridge.android


import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestUi {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreen::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.CAMERA"
        )

    @Test
    fun splashScreenTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.buttonRegister), withText("Inscription"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.username),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.username),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Test"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.password),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("Test1234"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.confirm_password),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("Test1234"), closeSoftKeyboard())

        val appCompatCheckBox = onView(
            allOf(
                withId(R.id.checkBox2),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatCheckBox.perform(click())

        val appCompatCheckBox2 = onView(
            allOf(
                withId(R.id.checkBox1),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatCheckBox2.perform(click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.email),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.button), withText("Inscription"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    13
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.email), withText("test"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(click())

        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.email), withText("test"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText7.perform(replaceText("test@gmail.test"))

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.email), withText("test@gmail.test"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText8.perform(closeSoftKeyboard())

        val appCompatCheckBox3 = onView(
            allOf(
                withId(R.id.Terms_checkbox), withText("J'accepte avoir lu et accepter les"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        appCompatCheckBox3.perform(click())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.button), withText("Inscription"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    13
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.editTextUser),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(replaceText("test@gmail.test"), closeSoftKeyboard())

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.editTextPassword),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText10.perform(replaceText("Test1234"), closeSoftKeyboard())

        val appCompatCheckBox4 = onView(
            allOf(
                withId(R.id.showPassword),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatCheckBox4.perform(click())

        val appCompatCheckBox5 = onView(
            allOf(
                withId(R.id.rememberMe), withText("Se souvenir de moi"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatCheckBox5.perform(click())

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.buttonLogin), withText("Connexion"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton4.perform(click())

        val appCompatButton5 = onView(
            allOf(
                withId(R.id.fragment_button), withText("Connecter un SmartFridge"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton5.perform(click())

        val appCompatButton6 = onView(
            allOf(
                withId(R.id.button_return_list_product),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatButton6.perform(click())

        val appCompatButton7 = onView(
            allOf(
                withId(R.id.scan_button), withText("Scanner un code bar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton7.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.button_return_scan),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.home_page), withContentDescription("Accueil"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavBar),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(longClick())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.product_page), withContentDescription("Liste produits"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavBar),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.addingBtn), withContentDescription("Add new product"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText11 = onView(
            allOf(
                withId(R.id.AlimentName),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        )
        appCompatEditText11.perform(scrollTo(), replaceText("Banane"), closeSoftKeyboard())

        val appCompatEditText12 = onView(
            allOf(
                withId(R.id.AlimentQuantite),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    9
                )
            )
        )
        appCompatEditText12.perform(scrollTo(), replaceText("5"), closeSoftKeyboard())

        val appCompatButton8 = onView(
            allOf(
                withId(R.id.button_date_select), withText("Choisir Date"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    13
                )
            )
        )
        appCompatButton8.perform(scrollTo(), click())

        val appCompatButton9 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton9.perform(scrollTo(), click())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.categorie_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    17
                )
            )
        )
        appCompatSpinner.perform(scrollTo(), click())

        val appCompatCheckedTextView = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(1)
        appCompatCheckedTextView.perform(click())

        val appCompatButton10 = onView(
            allOf(
                withId(R.id.button_add_aliment), withText("Ajouter"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    21
                )
            )
        )
        appCompatButton10.perform(scrollTo(), click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.settings_page), withContentDescription("Paramètres"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavBar),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())

        val appCompatButton11 = onView(
            allOf(
                withId(R.id.new_location), withText("Modifier Emplacements"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton11.perform(click())

        val appCompatEditText13 = onView(
            allOf(
                withId(R.id.location_popup_new_location_edit),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText13.perform(replaceText("Frigo"), closeSoftKeyboard())

        val appCompatButton12 = onView(
            allOf(
                withId(R.id.location_popup_save), withText("Enregistrer"),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatButton12.perform(click())

        val appCompatButton13 = onView(
            allOf(
                withId(R.id.new_location), withText("Modifier Emplacements"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton13.perform(click())

        val appCompatEditText14 = onView(
            allOf(
                withId(R.id.location_popup_new_location_edit),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText14.perform(replaceText("Panier"), closeSoftKeyboard())

        val appCompatButton14 = onView(
            allOf(
                withId(R.id.location_popup_save), withText("Enregistrer"),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatButton14.perform(click())

        val appCompatButton15 = onView(
            allOf(
                withId(R.id.new_location), withText("Modifier Emplacements"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton15.perform(click())

        val appCompatEditText15 = onView(
            allOf(
                withId(R.id.location_popup_new_location_edit),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText15.perform(replaceText("Armoire"), closeSoftKeyboard())

        val appCompatButton16 = onView(
            allOf(
                withId(R.id.location_popup_save), withText("Enregistrer"),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatButton16.perform(click())

        val appCompatButton17 = onView(
            allOf(
                withId(R.id.new_location), withText("Modifier Emplacements"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton17.perform(click())

        val appCompatSpinner2 = onView(
            allOf(
                withId(R.id.location_popup_spinner),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatSpinner2.perform(click())

        val appCompatCheckedTextView2 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(4)
        appCompatCheckedTextView2.perform(click())

        val appCompatButton18 = onView(
            allOf(
                withId(R.id.location_popup_discard), withText("Supprimmer"),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatButton18.perform(click())

        val bottomNavigationItemView4 = onView(
            allOf(
                withId(R.id.product_page), withContentDescription("Liste produits"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavBar),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView4.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.addingBtn), withContentDescription("Add new product"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val appCompatEditText16 = onView(
            allOf(
                withId(R.id.AlimentName),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        )
        appCompatEditText16.perform(scrollTo(), replaceText("Viande"), closeSoftKeyboard())

        val appCompatEditText17 = onView(
            allOf(
                withId(R.id.AlimentQuantite),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    9
                )
            )
        )
        appCompatEditText17.perform(scrollTo(), replaceText("5500"), closeSoftKeyboard())

        val appCompatButton19 = onView(
            allOf(
                withId(R.id.button_date_select), withText("Choisir Date"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    13
                )
            )
        )
        appCompatButton19.perform(scrollTo(), click())

        val appCompatButton20 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton20.perform(scrollTo(), click())

        val appCompatSpinner3 = onView(
            allOf(
                withId(R.id.categorie_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    17
                )
            )
        )
        appCompatSpinner3.perform(scrollTo(), click())

        val appCompatCheckedTextView3 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(3)
        appCompatCheckedTextView3.perform(click())

        val appCompatSpinner4 = onView(
            allOf(
                withId(R.id.place_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    20
                )
            )
        )
        appCompatSpinner4.perform(scrollTo(), click())

        val appCompatCheckedTextView4 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(1)
        appCompatCheckedTextView4.perform(click())

        val appCompatButton21 = onView(
            allOf(
                withId(R.id.button_add_aliment), withText("Ajouter"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    21
                )
            )
        )
        appCompatButton21.perform(scrollTo(), click())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.addingBtn), withContentDescription("Add new product"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val appCompatEditText18 = onView(
            allOf(
                withId(R.id.AlimentName),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        )
        appCompatEditText18.perform(scrollTo(), replaceText("Tomates"), closeSoftKeyboard())

        val appCompatEditText19 = onView(
            allOf(
                withId(R.id.AlimentQuantite),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    9
                )
            )
        )
        appCompatEditText19.perform(scrollTo(), replaceText("300"), closeSoftKeyboard())

        val appCompatButton22 = onView(
            allOf(
                withId(R.id.button_date_select), withText("Choisir Date"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    13
                )
            )
        )
        appCompatButton22.perform(scrollTo(), click())

        val appCompatButton23 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton23.perform(scrollTo(), click())

        val appCompatSpinner5 = onView(
            allOf(
                withId(R.id.categorie_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    17
                )
            )
        )
        appCompatSpinner5.perform(scrollTo(), click())

        val appCompatSpinner6 = onView(
            allOf(
                withId(R.id.categorie_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    17
                )
            )
        )
        appCompatSpinner6.perform(scrollTo(), click())

        val appCompatCheckedTextView5 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(1)
        appCompatCheckedTextView5.perform(click())

        val appCompatSpinner7 = onView(
            allOf(
                withId(R.id.place_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    20
                )
            )
        )
        appCompatSpinner7.perform(scrollTo(), click())

        val appCompatSpinner8 = onView(
            allOf(
                withId(R.id.place_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    20
                )
            )
        )
        appCompatSpinner8.perform(scrollTo(), click())

        val appCompatCheckedTextView6 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(2)
        appCompatCheckedTextView6.perform(click())

        val appCompatButton24 = onView(
            allOf(
                withId(R.id.button_add_aliment), withText("Ajouter"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    21
                )
            )
        )
        appCompatButton24.perform(scrollTo(), click())


        val appCompatButton25 = onView(
            allOf(
                withId(R.id.modify_button), withText("Modifier"),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    23
                ),
                isDisplayed()
            )
        )
        appCompatButton25.perform(click())

        val appCompatSpinner9 = onView(
            allOf(
                withId(R.id.place_spinner),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    20
                )
            )
        )
        appCompatSpinner9.perform(scrollTo(), click())

        val appCompatCheckedTextView7 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(2)
        appCompatCheckedTextView7.perform(click())

        val appCompatButton26 = onView(
            allOf(
                withId(R.id.button_add_aliment), withText("Modifier"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    21
                )
            )
        )
        appCompatButton26.perform(scrollTo(), click())

        val appCompatButton27 = onView(
            allOf(
                withId(R.id.delete_button), withText("Supprimer"),
                childAtPosition(
                    allOf(
                        withId(R.id.product_popup),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    22
                ),
                isDisplayed()
            )
        )
        appCompatButton27.perform(click())

        val view = onView(
            allOf(
                withId(R.id.sort_by_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        view.perform(click())

        val view2 = onView(
            allOf(
                withId(R.id.sort_by_location),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        view2.perform(click())

        val view3 = onView(
            allOf(
                withId(R.id.sort_by_expiration_date),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        view3.perform(click())

        val appCompatEditText20 = onView(
            allOf(
                withId(R.id.search_products),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText20.perform(replaceText("Viande"), closeSoftKeyboard())

        val bottomNavigationItemView5 = onView(
            allOf(
                withId(R.id.settings_page), withContentDescription("Paramètres"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavBar),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView5.perform(click())

        val appCompatButton28 = onView(
            allOf(
                withId(R.id.disconnect), withText("Se déconnecter"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatButton28.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.forgotPassword), withText("Mot de passe oublié ?"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        pressBack()

        val appCompatButton29 = onView(
            allOf(
                withId(R.id.buttonRegister), withText("Inscription"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatButton29.perform(click())

        val appCompatTextView2 = onView(
            allOf(
                withId(R.id.terms_link), withText("termes d'utilisation"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    12
                ),
                isDisplayed()
            )
        )
        appCompatTextView2.perform(click())

        val appCompatEditText21 = onView(
            allOf(
                withId(R.id.confirm_password),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatEditText21.perform(longClick())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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
