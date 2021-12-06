from flask import Blueprint, request, json
from .database import users

addUser = Blueprint('addUser', __name__)


@addUser.route('/api/addUser', methods=['POST'])
def add_user():
    if request.method == 'POST':
        
        req = request.get_json(force=True)
        users.insert_one(req)
        return "{state : 200}"


@addUser.route('/api/email_verfication/<email>', methods=['GET'])
def email_verification(email):
    if request.method == 'GET':
        result = list(users.find({"Email" : email}))
        for client in result:
         client.pop('_id')

        if len(result) > 0:
            return {'state' : 'true'}

        return {'state' : 'false'}


@addUser.route('/api/username_verfication/<username>', methods=['GET'])
def username_verification(username):
    if request.method == 'GET':
        result = list(users.find({"Username" : username}))
        for client in result:
         client.pop('_id')

        if len(result) > 0:
            return {'state' : 'true'}

        return {'state' : 'false'}