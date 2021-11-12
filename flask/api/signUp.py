from flask import Blueprint, request
from .database import new_user

addUser = Blueprint('addUser', __name__)


@addUser.route('/api/addUser', methods=['POST'])
def add_food():
    if request.method == 'POST':
        # TODO : tests
        req = request.get_json(force=True)
        new_user.insert_one(req)
        return "user added"