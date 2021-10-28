from flask import Blueprint, json
from .database import db, users


getUsers = Blueprint('getUsers', __name__)


@getUsers.route('/api/users', methods=['GET'])
def get_all_users():

    result = list(users.find())
    print(result)

    for user in result:
        user.pop('_id')

    return json.dumps(result)