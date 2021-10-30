from flask import Blueprint, request
from .database import food

addFood = Blueprint('addFood', __name__)

@addFood.route('/api/addFood', methods=['POST'])
def add_food():
    if request.method == 'POST':
        # TODO : add a food in db + tests
        req = request.get_json(force=True)
        print(req)
        return "food added"
1

