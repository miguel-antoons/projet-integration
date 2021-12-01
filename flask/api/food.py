import json
import bson
from bson import json_util, ObjectId
from flask import Blueprint, request, jsonify
from .database import food

app_food = Blueprint('food', __name__)


@app_food.route('/api/food', methods=['POST', 'GET'])
def food_api():
    if request.method == 'GET':
        return get_food()

    elif request.method == 'POST':
        return add_food(request.get_json(force=True))


@app_food.route('/api/food/<product_id>', methods=['PUT', 'DELETE'])
def food_with_id(product_id):
    if request.method == 'PUT':
        return modify_food(product_id, request.get_json(force=True))

    elif request.method == 'DELETE':
        return remove_food(product_id)


def add_food(req):
    food.insert_one(req)
    return jsonify({'Response': "Food was added"})


@getFood.route('/api/getFood/<user>', methods=['GET'])
def get_food():
    result = list(food.find())

    for item in result:
        item["_id"] = str(item.get('_id'))
    
    # TODO : if id == current user return his food list
    return json_util.dumps(result)


def remove_food(product_id):
    food.delete_one({"_id": ObjectId(product_id)})
    return jsonify({'Response': "Food was removed"})


def modify_food(product_id, req):
    food.update_one(
        {
            "_id": ObjectId(product_id),
            "Utilisateur": req['Utilisateur']
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