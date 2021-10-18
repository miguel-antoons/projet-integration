from flask import Blueprint, request, jsonify, json
from database import db


app_client = Blueprint('app_client', __name__)


@app_client.route('/client', methods=['GET', 'POST', 'PUT', 'DELETE'])
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
        "name" : "test",
        "firstName" : "test",
        "email" : "test@gmail.com",
        "pseudo" : "Miki",
        "password" : "Miki1234",
        "furniture" : {
                        "Frigo" : {
                            "temperature" : "4.3",
                            "Hygrometry" : "68",
                            "Products" : {
                                    "Cordon bleu" : {
                                        "ingredients" : ["jambon","fromage","chapelure"],
                                        "expiration_date" : "20/12/21",
                                        "weigth" : "300", 
                                        "energy" : "250",
                                        "fat" : "35",
                                        "carbohydrates" : "10",
                                        "proteins" : "29",
                                        "minerals" : "2"                                         
                                    },
                                    "Pomme" : {
                                        "ingredients" : [],
                                        "expiration_date" : "25/12/21",
                                        "weigth" : "300", 
                                        "energy" : "250",
                                        "fat" : "35",
                                        "carbohydrates" : "10",
                                        "proteins" : "29",
                                        "minerals" : "2"                                         
                                    }
                            }
                        }, 

                        "Cabinet" : {
                            "temperature" : "20",
                            "Hygrometry" : "68",
                            "Products" : {
                                    "Cordon bleu" : {
                                        "ingredients" : ["jambon","fromage","chapelure"],
                                        "expiration_date" : "20/12/21",
                                        "weigth" : "300", 
                                        "energy" : "250",
                                        "fat" : "35",
                                        "carbohydrates" : "10",
                                        "proteins" : "29",
                                        "minerals" : "2"                                         
                                    },
                                    "Pomme" : {
                                        "ingredients" : [],
                                        "expiration_date" : "25/12/21",
                                        "weigth" : "300", 
                                        "energy" : "250",
                                        "fat" : "35",
                                        "carbohydrates" : "10",
                                        "proteins" : "29",
                                        "minerals" : "2"                                         
            }}}}}
            
        records.insert_one(new_client)
        return "client ajouté"
