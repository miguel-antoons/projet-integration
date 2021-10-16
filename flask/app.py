from flask import Flask, request
from pymongo import MongoClient
from bson import json_util
import json
app=Flask(__name__)


#Connection with local DataBase
client = MongoClient("mongodb://localhost:27017")
#Name of my database
db = client.get_database('aliments')


#First API TEST
@app.route('/')
def hello_world():
    return 'Hello world!'

@app.route('/aliments', methods=['GET', 'POST', 'PUT', 'DELETE'])
def aliments():

    #Collection of my database
    records = db.noms

    #Method request
    if request.method == 'GET':

        result = list(records.find())

        for client in result:
            client.pop('_id')
        
        return json.dumps(result)

     
    elif request.method == 'POST':

        parameters = request.json
        print(parameters)
        new_aliment = {

        'noms': "Pomme", 
        'quantitéé': "30",
        'date de peeremtion': "07/12/2022",
        'categorie': "Fruits et légumes",
        'store': "Frigo",

    }
        db.insert_one(parameters)
        return "client ajouté"


        


if __name__ == '__main__':
    app.run(debug=True)





"""
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
"""
