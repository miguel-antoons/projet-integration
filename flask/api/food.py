from bson import json_util, ObjectId
from flask import Blueprint, request, jsonify
from flask_jwt_extended import jwt_required, get_jwt_identity

from .database import food, raspberry

app_food = Blueprint('food', __name__)


@app_food.route('/api/food', methods=['GET'])
@jwt_required()
def get_food():
    data = get_jwt_identity()
    result = list(
        food.find(
            {"Utilisateur": data["Username"]}
        )
    )

    for item in result:
        item["_id"] = str(item.get('_id'))
    
    # TODO : if id == current user return his food list
    return json_util.dumps(result)


@app_food.route('/api/food', methods=['POST'])
@jwt_required()
def add_food():
    data = get_jwt_identity()
    req = request.get_json(force=True)
    req["Utilisateur"] = data["Username"]
    food.insert_one(req)
    return jsonify({'Response': "Food was added"})


@app_food.route('/api/food/<product_id>', methods=['PUT', 'DELETE'])
@jwt_required()
def food_with_id(product_id):
    data = get_jwt_identity()
    if request.method == 'PUT':
        return modify_food(product_id, request.get_json(force=True), data["Username"])

    elif request.method == 'DELETE':
        return remove_food(product_id, data["Username"])


@app_food.route('/api/food/<raspberry_id>', methods=['POST'])
def raspberry_post(raspberry_id):
    new_food = request.json

    raspberry_data = list(raspberry.find(
        {"_id": ObjectId(raspberry_id)}
    ))

    if new_food['secret'] == raspberry_data[0]['secret']:
        try:
            if raspberry_data[0]['status'] == "ready":
                new_food["Utilisateur"] = raspberry_data[0]['user']
                new_food["Lieu"] = raspberry_data[0]['location']

                food.insert_one(new_food)
                
                return {"response": "food has been inserted"}

            else:
                return {"response": "raspberry not ready"}

        except IndexError:
            return {"response": "raspberry does not exist"}
    
    else:
        return {"response": "raspberry does not exist"}


def remove_food(product_id, username):
    food.delete_one({"_id": ObjectId(product_id), "Utilisateur" : username})
    return jsonify({'Response': "Food was removed"})


def modify_food(product_id, req, username):
    food.update_one(
        {
            "_id": ObjectId(product_id),
            "Utilisateur": username
        },
        {"$set": {
            "Nom": req['Nom'],
            "Quantite": req['Quantite'],
            "Marque": req['Marque'],
            "Date": req['Date'],
            "Lieu": req['Lieu'],
            "Categorie": req['Categorie']
        }}
    )

    return jsonify({'Response': "Food was updated"})
