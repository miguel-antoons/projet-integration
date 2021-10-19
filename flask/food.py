from flask import Blueprint, request

addFood = Blueprint('addFood', __name__)


@addFood.route('', methods=['POST'])
def add_food():
    if request.method == 'POST':
        # TODO : add a food in db + tests
        pass


