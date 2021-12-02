from flask import Blueprint, request, json
from .database import users

getUser = Blueprint('login', __name__)

@getUser.route('/api/login/<email>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def client_login(email):
    if request.method == 'GET':
        result = list(users.find({"Email" : email}))

        for client in result:
            client.pop('_id')
        
        return json.dumps(result)
