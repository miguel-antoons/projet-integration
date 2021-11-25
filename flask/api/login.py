from flask import Blueprint, request, json
from .database import db

login = Blueprint('login', __name__)

@login.route('/api/login/<email>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def client_login(email):
    records = db.Users

    if request.method == 'GET':
        result = list(records.find({"Email" : email}))

        for client in result:
            client.pop('_id')
        
        print(json.dumps(result))
        return json.dumps(result)
