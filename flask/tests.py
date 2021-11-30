import unittest.mock
from unittest import TestCase, main as unittest_main

from bson import ObjectId

from app import app
import json
from unittest.mock import patch

sample_user = {
    "Name": "Test",
    "FirstName": "Test",
    "Username": "LeTest",
    "Email": "sendme@gmail.com",
    "Password": "Test1234",
    "Qrcode": "TODO",
    "Code" : "628476"
}

sample_user_sign_up = {
    "Username": "LeTest",
    "Password": "Test1234",
    "Email": "sendme@gmail.com"
}

sample_food = [{
    "_id": {
        "$oid": "619e8f45ee462d6d876bbdbc"
    },
    'Utilisateur': "999",
    'Nom': 'Danette Vanille',
    'Marque': 'Danone',
    'Quantite': 4,
    'ingredients': [
        'lait entier',
        'lait écrémé reconstitué à base de lait en poudre',
        'sucre',
        'crème',
        'lait écrémé concentré ou en poudre',
        'épaississants (amidon modifié, carraghénanes)',
        'perméat de petit lait (lactosérum) en poudre',
        'amidon',
        'arôme (lait)',
        'colorant (bêta-carotène)'
    ],
    'Date': '20/12/2021',
    'Valeurs': {
        'Energie': '107 kcal',
        'Matières grasses': '3,0g',
        'Glucides': '17,1g',
        'Proteines': '3g',
        'Sel': '0,14g'
    },
    'Poids': '125g',
    'Lieu': 'Frigo',
    'Category': "Produit laitiers"

}]


class PlaylistsTests(TestCase):
    """Flask tests.
    https://makeschool.org/mediabook/oa/tutorials/playlistr-video-playlists-with-flask-and-mongodb-1c/adding-tests/
    """

    def setUp(self):
        """Stuff to do before every test."""

        # Get the Flask test client
        self.client = app.test_client()

        # Show Flask errors that happen during tests
        app.config['TESTING'] = True

    def test_idex(self):
        result = self.client.get('/')
        self.assertEqual(result.status, '200 OK')
        page_content = result.get_data(as_text=True)
        self.assertIn('Hello world!', page_content)

    def test_login(self):
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.login.getUser') as MockUser:
            # Force the return value of login.insert_one(json) to sample_user
            MockUser.insert_one.return_value = sample_user
            with self.client.get(f'/api/login/{sample_user["Email"]}') as result:
                self.assertEqual(result.status, '200 OK')
                page_content = result.get_data(as_text=True)
                page_content = json.loads(page_content)[0]
                self.assertIn(sample_user['Username'], page_content['Username'])
                self.assertIn(sample_user['Password'], page_content['Password'])

    #---------------------------------------API USER ---------------------------------------------------

    def test_get_email(self):
        # Mock the food value in ./api.users.py
        with unittest.mock.patch('api.users') as MockUsers:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = sample_user
            #Sample user

            # TEST API USER /reset-password/checkemail/<email>'
            #Check Email 
            with self.client.get(f'/api/users/reset-password/checkemail/{sample_user["Email"]}') as result:
                #STATUS
                self.assertEqual(result.status, '200 OK')
                #Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                #DATA
                self.assertEqual(result.data, b'["message: this email exist"]')
                self.assertTrue(result.data, b'["message: this email exist"]')

    def test_get_email_Notexist(self):
        # Mock the food value in ./api.users.py
        with unittest.mock.patch('api.users') as MockUsers:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = sample_user
            #Sample user

            # TEST API USER /reset-password/checkemail/<email>'
            #Check Email 
            with self.client.get(f'/api/users/reset-password/checkemail/FranceaufranÃ§ais@zemour.fr') as result:
                #STATUS
                self.assertEqual(result.status, '200 OK')
                #Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                #DATA
                self.assertEqual(result.data, b'["message: this email does not exist"]')
                self.assertTrue(result.data, b'["message: this email does not exist"]')


    

              
    def test_update_password_put(self):
        # Mock the food value in ./api.users.py
        with unittest.mock.patch('api.users') as MockUsers:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = sample_user
            #PUT methode  
            #Sample user
            #/users/reset-password/checkcode/

            with self.client.get(f'/users/reset-password/checkcode/{sample_user["Email"]}/TEST1234') as result:
                #STATUS
                self.assertEqual(result.status, '404 NOT FOUND')
                #Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")

   

    def test_get_food(self):
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.food.food') as MockFood:
            # Force the return value of food.find() to sample_food
            MockFood.find.return_value = sample_food
            with self.client.get("/api/getFood/999") as res:
                resultat = res.data
                final = resultat.decode('unicode-escape')
                self.assertEqual(res.status_code, 200)
                self.assertEqual(final,
                                 '[{"_id": {"$oid": "619e8f45ee462d6d876bbdbc"}, "Utilisateur": "999", "Nom": '
                                 '"Danette Vanille", "Marque": "Danone", "Quantite": 4, "ingredients": ["lait '
                                 'entier", "lait écrémé reconstitué à base de lait en poudre", "sucre", "crème", '
                                 '"lait écrémé concentré ou en poudre", "épaississants (amidon modifié, '
                                 'carraghénanes)", "perméat de petit lait (lactosérum) en poudre", "amidon", '
                                 '"arôme (lait)", "colorant (bêta-carotène)"], "Date": "20/12/2021", "Valeurs": {'
                                 '"Energie": "107 kcal", "Matières grasses": "3,0g", "Glucides": "17,1g", '
                                 '"Proteines": "3g", "Sel": "0,14g"}, "Poids": "125g", "Lieu": "Frigo", "Category": '
                                 '"Produit laitiers"}]')
                # Check if food.find() was called
                MockFood.find.assert_called()

    def test_post_food(self):
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.food.food') as MockFood:
            # Force the return value of food.insert_one(json) to sample_food
            MockFood.insert_one.return_value = sample_food
            with self.client.post("/api/addFood", json=sample_food[0]) as res:
                # Check if food.insert_one(json) was called
                MockFood.insert_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{"Response":"Food was added"}\n')

    # Testing of Mock on the POST SignUp User
    def test_post_user(self):
        # Mock the user values in ./api.users.py
        with unittest.mock.patch('api.signUp.users') as MockUser:
            # Force the return value of users.insert_one(req) to sample_user
            MockUser.insert_one.return_value = sample_user_sign_up
            with self.client.post("/api/addUser", json=sample_user_sign_up) as res:
                # Check if users.insert_one(json) was called
                MockUser.insert_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{state : 200}')



    def test_remove_food(self):
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.food.food') as MockFood:
            MockFood.delete_one.return_value = sample_food
            with self.client.post('/api/removeFood', json=sample_food) as res:
                MockFood.delete_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{"Response":"Food was removed"}\n')


    def test_modify_food(self):
        with unittest.mock.patch('api.food.food') as MockFood:
            MockFood.delete_one.return_value = sample_food
            with self.client.post('/api/modifyFood/"{$oid": 619e8f45ee462d6d876bbdbc"}', json=sample_food) as res:
                MockFood.delete_one.assert_called()
                MockFood.insert_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{"Response":"Food was updated"}\n')


if __name__ == '__main__':
    unittest_main()
