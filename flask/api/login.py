from flask import Blueprint, request, json
from .database import db, collection

login = Blueprint('login', __name__)

@login.route('/api/login/<email>/<password>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def client_login(email, password):
    records = db.Client

    if request.method == 'GET':
        result = list(records.find({"Email" : email, "Password" : password}))

        for client in result:
            client.pop('_id')
        
        return json.dumps(result)
