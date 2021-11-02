from flask import Blueprint, request
from .database import food

addFood = Blueprint('addFood', __name__)


@addFood.route('/api/addFood', methods=['POST'])
def add_food():
    if request.method == 'POST':
        # TODO : tests
        req = request.get_json(force=True)
        food.insert_one(req)
        return "food added"
