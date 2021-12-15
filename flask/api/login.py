from argon2 import PasswordHasher
from argon2.low_level import verify_secret
from flask import Blueprint, request
from flask_jwt_extended import create_access_token

from .database import users

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
            additional_claims = {"Email": req["Email"], "Username": verify_password[0]["Username"]}
            access_token = create_access_token(identity=additional_claims)
            verify_password[0]["access_token"] = access_token

            # delete password
            verify_password[0].pop('Password')
            

            return verify_password[0]
