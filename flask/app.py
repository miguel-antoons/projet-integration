from flask import Flask, request
from pymongo import MongoClient
from signIn import signIn
from food import addFood
from bson import json_util
import json

app = Flask(__name__)
# Connection to a new mongo DB only on localhost
client = MongoClient("mongodb://localhost:27017/")
# Create this db to correctly link
db = client['SmartFridge']
# Create this collection to correctly link
collection = db["Utilisateurs"]

# Shortcut for blueprint and route
app.register_blueprint(signIn, url_prefix='/api/signIn')
app.register_blueprint(addFood, url_prefix='/api/addFood')
=======
from api import blueprints, database

def create_app():
    app = Flask(__name__)

    for blueprint in blueprints:
        app.register_blueprint(blueprint)

# TODO : Need refractor and move to users file
@app.route('/client', methods=['GET', 'POST', 'PUT', 'DELETE'])
def client():
    records = db.Client

    if request.method == 'GET':
        result = list(records.find())

        for client in result:
            client.pop('_id')

        return json.dumps(result)

    elif request.method == 'POST':

        parameters = request.json
        print(parameters)

        new_client = {
            "name": "test",
            "firstName": "test",
            "email": "test@gmail.com",
            "pseudo": "Miki",
            "password": "Miki1234",
            "furniture": {
                "Frigo": {
                    "temperature": "4.3",
                    "Hygrometry": "68",
                    "Products": {
                        "Cordon bleu": {
                            "ingredients": ["jambon", "fromage", "chapelure"],
                            "expiration_date": "20/12/21",
                            "weigth": "300",
                            "energy": "250",
                            "fat": "35",
                            "carbohydrates": "10",
                            "proteins": "29",
                            "minerals": "2"
                        },
                        "Pomme": {
                            "ingredients": [],
                            "expiration_date": "25/12/21",
                            "weigth": "300",
                            "energy": "250",
                            "fat": "35",
                            "carbohydrates": "10",
                            "proteins": "29",
                            "minerals": "2"
                        }
                    }
                },

                "Cabinet": {
                    "temperature": "20",
                    "Hygrometry": "68",
                    "Products": {
                        "Cordon bleu": {
                            "ingredients": ["jambon", "fromage", "chapelure"],
                            "expiration_date": "20/12/21",
                            "weigth": "300",
                            "energy": "250",
                            "fat": "35",
                            "carbohydrates": "10",
                            "proteins": "29",
                            "minerals": "2"
                        },
                        "Pomme": {
                            "ingredients": [],
                            "expiration_date": "25/12/21",
                            "weigth": "300",
                            "energy": "250",
                            "fat": "35",
                            "carbohydrates": "10",
                            "proteins": "29",
                            "minerals": "2"
                        }}}}}

        collection.insert_one(new_client)
        return "client ajouté"

# Run debug mode
if __name__ == '__main__':
    app.run(debug=True)
=======
    return app

app = create_app()