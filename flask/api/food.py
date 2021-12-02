import json
import bson
from bson import json_util, ObjectId
from flask import Blueprint, request, jsonify
from .database import food

app_food = Blueprint('food', __name__)


@app_food.route('/api/food/<user>', methods=['GET'])
def get_food(user):
    result = list(
        food.find(
            {"Utilisateur": user}
        )
    )

    for item in result:
        item["_id"] = str(item.get('_id'))
    
    # TODO : if id == current user return his food list
    return json_util.dumps(result)


@app_food.route('/api/food', methods=['POST'])
def add_food():
    req = request.get_json(force=True)
    food.insert_one(req)
    return jsonify({'Response': "Food was added"})


@app_food.route('/api/food/<product_id>', methods=['PUT', 'DELETE'])
def food_with_id(product_id):
    if request.method == 'PUT':
        return modify_food(product_id, request.get_json(force=True))

    elif request.method == 'DELETE':
        return remove_food(product_id)


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