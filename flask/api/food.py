import json

import bson
from bson import json_util, ObjectId
from flask import Blueprint, request, jsonify
from .database import food

addFood = Blueprint('addFood', __name__)
getFood = Blueprint('getFood', __name__)
removeFood = Blueprint('removeFood', __name__)
modifyFood = Blueprint('modifyFood', __name__)


@addFood.route('/api/addFood', methods=['POST'])
def add_food():
    req = request.get_json(force=True)
    food.insert_one(req)
    return jsonify({'Response': "Food was added"})


@getFood.route('/api/getFood/<user>', methods=['GET'])
def get_food(user):
    result = list(food.find({"Utilisateur": user}))
    # TODO : if id == current user return his food list
    return json_util.dumps(result)


@removeFood.route('/api/removeFood', methods=['POST'])
def remove_food():
    req = request.get_json(force=True)
    food.delete_one(req)
    return jsonify({'Response': "Food was removed"})


@modifyFood.route('/api/modifyFood/<product_id>', methods=['POST'])
def modify_food(product_id):
    req = request.get_json(force=True)
    slice_id = product_id[9:(len(product_id))-2]
    food.delete_one({"_id": ObjectId(slice_id)})
    food.insert_one(req)

    return jsonify({'Response': "Food was updated"})