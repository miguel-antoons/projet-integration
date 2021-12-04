from flask import Blueprint, request, json
from .database import users
from argon2 import PasswordHasher

getUser = Blueprint('login', __name__)
ph = PasswordHasher()

@getUser.route('/api/login', methods=['GET', 'POST', 'PUT', 'DELETE'])
def client_login():
    if request.method == 'POST':
        req = request.get_json(force=True)

        # data client
        verify_password = list(users.find({"Email" : req["Email"]}))
        for client in verify_password:
         client.pop('_id')

        # verification mots de passe
        if(ph.verify(verify_password[0]["Password"], req["Password"])):
            return verify_password[0]
