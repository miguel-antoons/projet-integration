import json

from flask import Blueprint, request, jsonify
from .database import food

addFood = Blueprint('addFood', __name__)
getFood = Blueprint('getFood', __name__)


@addFood.route('/api/addFood', methods=['POST'])
def add_food():
    # TODO : tests
    req = request.get_json(force=True)
    food.insert_one(req)
    return jsonify({'Response': "Food was added"})


@getFood.route('/api/getFood', methods=['GET'])
def get_food():
    result = list(food.find())
    for product in result:
        product.pop('_id')
    # TODO : if id == current user return his food list + tests
    return jsonify(result)
