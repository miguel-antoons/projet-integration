import unittest.mock
from unittest import TestCase, main as unittest_main

from flask_jwt_extended import create_access_token

from app import app
import json

sample_user = {
    "_id": "619e8f45ee462d6d876bbdbc",
    "Username": "LeTest",
    "Email": "sendme@gmail.com",
    "Password": "$argon2i$v=19$m=65536,t=5,p=2$hAXIxb7pps7WuZfWUdoKJJKPIoM$HNzycd5XhfhwFngbcYRfsnsULoveAcqWhqrexIS+Ef8",
    "Qrcode": "TODO",
    "Code": "628476"
}

sample_user_sign_up = {
    "Username": "LeTest",
    "Password": "Test1234",
    "Email": "sendme@gmail.com"
}

sample_raspberry = {
    "_id": "619e8f45ee462d6d876bbdbc",
    "id_raspberry": "619e8f45ee462d6d876bbdbc",
    "user": "LeTest",
    "location": "Frigo",
    "status": "waiting",
    "secret": "jeSuisUnToken666",
    "Light": 12.5,
    "Temperature": 20.4,
    "Humidity": 44
}

sample_capteur = {
    "ID_raspberry": "619e8f45ee462d6d876bbdbc",
    "Light": 12.0,
    "Humidity": 44,
    "Temperature": 20.5,
    "Date": "02/02.2022"
}

sample_food = {
    "_id": "619e8f45ee462d6d876bbdbc",
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
    'Categorie': "Produit laitiers"

}


class PlaylistsTests(TestCase):
    """Flask tests.
    https://makeschool.org/mediabook/oa/tutorials/playlistr-video-playlists-with-flask-and-mongodb-1c/adding-tests/
    """

    def setUp(self):
        """Stuff to do before every test."""

        # Get the Flask test client
        self.client = app.test_client()

        with app.app_context():
            self.headers = {
                'Authorization': 'Bearer {}'.format(create_access_token(sample_user))
            }

        # Show Flask errors that happen during tests
        app.config['TESTING'] = True

    def test_idex(self):
        result = self.client.get('/')
        self.assertEqual(result.status, '200 OK')
        page_content = result.get_data(as_text=True)
        self.assertIn('Hello smartfridge!', page_content)

    # ---------------------------------------API USER ---------------------------------------------------

    # Testing of Mock on the POST SignUp User
    def test_post_user(self):
        # Mock the user values in ./api.users.py
        with unittest.mock.patch('api.signUp.users') as MockUser:
            # Force the return value of users.insert_one(req) to sample_user
            MockUser.insert_one.return_value = sample_user_sign_up
            with self.client.post("/api/addUser", json=sample_user_sign_up, headers=self.headers) as res:
                # Check if users.insert_one(json) was called
                MockUser.insert_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{state : 200}')

    def test_login(self):
        sample_user['_id'] = "619e8f45ee462d6d876bbdbc"
        # Mock the food value in ./api.users.py
        with unittest.mock.patch('api.login.users') as MockUser:
            # Force the return value of users.find(req) to sample_user
            MockUser.find.return_value = [sample_user]
            user_password = {"Email": "sendme@gmail.com", "Password": "Test12345"}
            with self.client.post("/api/login", json=user_password, headers=self.headers) as res:
                self.assertEqual(res.status_code, 200)
                # DATA
                res = json.loads(res.data.decode("utf-8"))
                self.assertEqual(res["Username"], sample_user["Username"])
                self.assertEqual(res["Email"], sample_user["Email"])

    def test_get_email(self):
        # Mock the user value in ./api.users.py
        # Mock the food value in ./api.users.py
        with unittest.mock.patch('api.users.users') as MockUsers, unittest.mock.patch('api.users.mail') as MockSend:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = [sample_user]
            MockSend.send.return_value = None
            # Sample user

            # TEST API USER /reset-password/checkemail/<email>'
            # Check Email
            with self.client.put(
                    f'/api/users/reset-password/checkemail', json={"Email": sample_user["Email"]}) as result:
                # STATUS
                self.assertEqual(result.status, '200 OK')
                # Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                # DATA
                self.assertEqual(result.data, b'{"message": "message: this email exist"}')

    def test_get_email_Notexist(self):
        # Mock the user value in ./api.users.py
        # Mock the food value in ./api.users.py
        with unittest.mock.patch('api.users.users') as MockUsers, unittest.mock.patch('api.users.mail') as MockSend:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = []
            MockSend.send.return_value = None
            # Sample user

            # TEST API USER /reset-password/checkemail/<email>'
            # Check Email
            with self.client.put(
                    f'/api/users/reset-password/checkemail', json={"Email": "Franceaufrancais@zemour.fr"}) as result:
                # STATUS
                self.assertEqual(result.status, '200 OK')
                # Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                # DATA
                self.assertEqual(result.data, b'{"message": "this email does not exist"}')

    def test_checkcode_true(self):
        # Mock the user value in ./api.users.py
        with unittest.mock.patch('api.users.users') as MockUsers, unittest.mock.patch('api.users') as MockSend:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = [sample_user]
            # Sample user

            # TEST API USER /api/users/reset-password/checkcode'
            # Check Email
            with self.client.put(
                    f'/api/users/reset-password/checkcode',
                    json={"Email": "sendme@gmail.com", "Code": "628476"}) as result:
                # STATUS
                self.assertEqual(result.status, '200 OK')
                # Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                # DATA
                self.assertEqual(result.data, b'{"message": "The code is good"}')

    def test_checkcode_false(self):
        # Mock the user value in ./api.users.py
        with unittest.mock.patch('api.users.users') as MockUsers, unittest.mock.patch('api.users') as MockSend:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = [sample_user]
            # Sample user

            # TEST API USER /api/users/reset-password/checkcode'
            # Check Email
            with self.client.put(
                    f'/api/users/reset-password/checkcode',
                    json={"Email": "sendme@gmail.com", "Code": "111111"}) as result:
                # STATUS
                self.assertEqual(result.status, '200 OK')
                # Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                # DATA
                self.assertEqual(result.data, b'{"message": "code is false"}')

    def test_reset_password_ok(self):
        # Mock the user value in ./api.users.py
        with unittest.mock.patch('api.users.users') as MockUsers, unittest.mock.patch('api.users') as MockSend:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = [sample_user]
            # Sample user

            # TEST API USER /api/users/update-password
            # Check Email
            with self.client.put(
                    f'/api/users/update-password',
                    json={"Email": "sendme@gmail.com", "Code": "111111", "Password": "Test1234"}) as result:
                # STATUS
                self.assertEqual(result.status, '200 OK')
                # Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                # DATA
                self.assertEqual(result.data, b'["Password Update is ok"]')

    def test_reset_password_NotOk(self):
        # Mock the user value in ./api.users.py
        with unittest.mock.patch('api.users.users') as MockUsers, unittest.mock.patch('api.users') as MockSend:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = [sample_user]
            # Sample user

            # TEST API USER /api/users/update-password
            # Check Email
            with self.client.put(
                    f'/api/users/update-password',
                    json={"Email": "sendme@gmail.com", "Code": "111111", "Password": "Test1234"}) as result:
                # STATUS
                self.assertEqual(result.status, '200 OK')
                # Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")
                # DATA
                self.assertEqual(result.data, b'["Password Update is ok"]')

    def test_update_password_put(self):
        # Mock the food value in ./api.users.py
        with unittest.mock.patch('api.users') as MockUsers:
            # Force the return value of food.find() to sample_food
            MockUsers.find.return_value = sample_user
            # PUT methode
            # Sample user
            # /users/reset-password/checkcode/

            with self.client.get(
                    f'/users/reset-password/checkcode/{sample_user["Email"]}/TEST1234') as result:
                # STATUS
                self.assertEqual(result.status, '404 NOT FOUND')
                # Content Type
                self.assertEqual(result.content_type, "text/html; charset=utf-8")

    # ----------------------------------- API FOOD---------------------------------------

    def test_get_food(self):
        self.maxDiff = None
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.food.food') as MockFood:
            # Force the return value of food.find() to sample_food
            MockFood.find.return_value = [sample_food]
            with self.client.get("/api/food", headers=self.headers) as res:
                resultat = res.data
                final = resultat.decode('unicode-escape')
                self.assertEqual(res.status_code, 200)
                self.assertEqual(final,
                                 '[{"_id": "619e8f45ee462d6d876bbdbc", "Utilisateur": "999", "Nom": '
                                 '"Danette Vanille", "Marque": "Danone", "Quantite": 4, "ingredients": ["lait '
                                 'entier", "lait écrémé reconstitué à base de lait en poudre", "sucre", "crème", '
                                 '"lait écrémé concentré ou en poudre", "épaississants (amidon modifié, '
                                 'carraghénanes)", "perméat de petit lait (lactosérum) en poudre", "amidon", '
                                 '"arôme (lait)", "colorant (bêta-carotène)"], "Date": "20/12/2021", "Valeurs": {'
                                 '"Energie": "107 kcal", "Matières grasses": "3,0g", "Glucides": "17,1g", '
                                 '"Proteines": "3g", "Sel": "0,14g"}, "Poids": "125g", "Lieu": "Frigo", "Categorie": '
                                 '"Produit laitiers"}]')
                # Check if food.find() was called
                MockFood.find.assert_called()

    def test_post_food(self):
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.food.food') as MockFood:
            # Force the return value of food.insert_one(json) to sample_food
            MockFood.insert_one.return_value = sample_food
            with self.client.post("/api/food", json=sample_food, headers=self.headers) as res:
                # Check if food.insert_one(json) was called
                MockFood.insert_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{"Response":"Food was added"}\n')

    def test_remove_food(self):
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.food.food') as MockFood:
            MockFood.delete_one.return_value = sample_food
            with self.client.delete('/api/food/619e8f45ee462d6d876bbdbc', headers=self.headers) as res:
                MockFood.delete_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{"Response":"Food was removed"}\n')

    def test_modify_food(self):
        with unittest.mock.patch('api.food.food') as MockFood:
            MockFood.update_one.return_value = [sample_food]
            with self.client.put('/api/food/619e8f45ee462d6d876bbdbc', json=sample_food, headers=self.headers) as res:
                MockFood.update_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{"Response":"Food was updated"}\n')

    # ---------------------------------------API ACCOUNT ---------------------------------------------------

    def test_delete_account(self):
        with unittest.mock.patch('api.delete_account.food') as MockFood:
            MockFood.remove.return_value = sample_food
            with unittest.mock.patch('api.delete_account.users') as MockUsers:
                with self.client.delete('/api/account', headers=self.headers) as res:
                    MockFood.remove.assert_called()
                    MockUsers.remove.assert_called()
                    self.assertEqual(res.status_code, 200)
                    self.assertEqual(res.data, b'{"Response":"All Food of the user and the user account were '
                                               b'removed"}\n')

    # ---------------------------------------API ENVIRONNEMENT ---------------------------------------------------

    def test_get_environement(self):
        with unittest.mock.patch('api.environnement.raspberry') as MockRaspberry:
            MockRaspberry.find.return_value = [sample_raspberry]
            with self.client.get('/api/environnement', headers=self.headers) as res:
                MockRaspberry.find.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'[{"id_raspberry": "619e8f45ee462d6d876bbdbc", "location": "Frigo", '
                                           b'"Light": 12.5, "Temperature": 20.4, "Humidity": '
                                           b'44}]')

    def test_temphumlight(self):
        with unittest.mock.patch('api.environnement.raspberry') as MockRaspberry:
            MockRaspberry.find.return_value = [sample_raspberry]
            MockRaspberry.update_one.return_value = [sample_raspberry]
            with self.client.put('/api/environnement/temhumlight', json=sample_capteur) as res:
                MockRaspberry.update_one.assert_called()
                self.assertEqual(res.status_code, 200)
                self.assertEqual(res.data, b'{"Response": "Raspberry exist and Temprature,Humidity,Light is update"}')



if __name__ == '__main__':
    unittest_main()
