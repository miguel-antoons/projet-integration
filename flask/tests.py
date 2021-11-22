import unittest.mock
from unittest import TestCase, main as unittest_main
from app import app
import json
from unittest.mock import patch

sample_user = {
    "Name":"Test",
    "FirstName":"Test",
    "Username":"LeTest",
    "Email":"sendme@gmail.com",
    "Password":"test123",
    "Qrcode":"TODO"
}



sample_food = [{
    '_id' : "Don't matter",
    'Utilisateur' : "999",
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
        result = self.client.get(f'/api/login/{sample_user["Email"]}/{sample_user["Password"]}')
        self.assertEqual(result.status, '200 OK')
        page_content = result.get_data(as_text=True)
        page_content = json.loads(page_content)[0]
        self.assertIn(sample_user['Username'], page_content['Username'])
        self.assertIn(sample_user['Password'], page_content['Password'])

    #TEST API USER /reset-password/checkemail/<email>'

    #check if Response is 200
    def test_check_email_status(self):

        result = self.client.get('/api/users/reset-password/checkemail/Test@gmail.com' )
        status_code = result.status_code
        self.assertEqual(status_code,200)

    #check if content return is text
    def test_check_email_content(self):
        result = self.client.get('/api/users/reset-password/checkemail/Test@gmail.com' )
        self.assertEqual(result.content_type,"text/html; charset=utf-8")

    #check for data returned
    def test_check_email_data(self):

        #Test with email in database
        result = self.client.get('/api/users/reset-password/checkemail/Michael@Scofield.be' )
        self.assertEqual( result.data,  b'["message: this email exist"]')
        self.assertTrue( result.data,  b'["message: this email exist"]')

        # Email Not exist
        #Test with email is not in  database
        false_result = self.client.get('/api/users/reset-password/checkemail/Lafranceaufrancais@Scofield.be' )
        self.assertEqual( false_result.data,b'["message: this email does not exist"]')
        self.assertTrue( false_result.data,  b'["message: this email exist"]')



    #TEST API USER /api/users/reset-password/checkcode/<email>/<code>
    #A False USER in Database with false data

    #check if Response is 200
    def test_check_code_codestatus(self):

        result = self.client.get('/api/users/reset-password/checkcode/test@api.be/628476' )
        status_code = result.status_code
        self.assertEqual(status_code,200)

    #check if content return is text
    def test_check_code_content(self):
        result = self.client.get('/api/users/reset-password/checkcode/test@api.be/628476' )
        self.assertEqual(result.content_type,"text/html; charset=utf-8")

    #check for data returned
    def test_check_code_data(self):

        #Test with email and true code in database
        #Good code
        result = self.client.get('/api/users/reset-password/checkcode/test@api.be/897456' )
        self.assertEqual(result.data, b'["The code is good"]')
        self.assertTrue(result.data, b'["The code is good"]')

        # Email exist but fasle code
        #False Code
        #Test with email and false code  database
        false_result = self.client.get('/api/users/reset-password/checkcode/test@api.be/11111' )
        self.assertEqual( false_result.data,b'["code is false"]')
        self.assertTrue( false_result.data,b'["code is false"]')

    def test_get_food(self):
        # Mock the food value in ./api.food.py
        with unittest.mock.patch('api.food.food') as MockFood:
            # Force the return value of food.find() to sample_food
            MockFood.find.return_value = sample_food
            with self.client.get("/api/getFood") as res:
                resultat = res.data
                final = resultat.decode('unicode-escape')
                self.assertEqual(res.status_code, 200)
                self.assertEqual(final, '[{"Category":"Produit laitiers","Date":"20/12/2021","Lieu":"Frigo","Marque":"Danone","Nom":"Danette Vanille","Poids":"125g","Quantite":4,"Utilisateur":"999","Valeurs":{"Energie":"107 kcal","Glucides":"17,1g","Matières grasses":"3,0g","Proteines":"3g","Sel":"0,14g"},"ingredients":["lait entier","lait écrémé reconstitué à base de lait en poudre","sucre","crème","lait écrémé concentré ou en poudre","épaississants (amidon modifié, carraghénanes)","perméat de petit lait (lactosérum) en poudre","amidon","arôme (lait)","colorant (bêta-carotène)"]}]\n')
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


    #TEST API USER /api/users/reset-password/checkcode/<email>/<code>
   

    #PUT METHODE

    def test_update_password_put(self):
        result = self.client.put('/users/reset-password/checkcode/Test@api.be/JesuisBanane12234TEST' )
        status_code = result.status_code
        self.assertEqual(status_code,404)

    #The email not existing

    #GET METHODE

    def test_email_not_exist(self):
        result = self.client.get('/users/reset-password/checkcode/Banane@api.be/JesuisBanane12234TEST' )
        status_code = result.status_code
        self.assertEqual(status_code,404)

  
        result = self.client.get('/users/reset-password/checkcode/Michael@Scofield.be/JesuisBanane12234TEST' )
        status_code = result.status_code
        self.assertEqual(status_code,404)


        result = self.client.get('/users/reset-password/checkcode//Test@gmail.com/jnjkfnkd21232' )
        status_code = result.status_code
        self.assertEqual(status_code,404)

    #PUT METHODE

    def test_update_password_put_not_email(self):
        result = self.client.put('/users/reset-password/checkcode/BlaBlaest@api.be/JesuisBanane12234TEST' )
        status_code = result.status_code
        self.assertEqual(status_code,404)



















if __name__ == '__main__':
    unittest_main()