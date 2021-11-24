from flask import Blueprint, request
from .database import users

addUser = Blueprint('addUser', __name__)


@addUser.route('/api/addUser', methods=['POST'])
def add_user():
    if request.method == 'POST':
        
        req = request.get_json(force=True)
        users.insert_one(req)
        return "{state : 200}"