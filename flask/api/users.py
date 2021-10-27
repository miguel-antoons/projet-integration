from flask import Blueprint,request, jsonify, json

from flask import Flask
from flask_bcrypt import Bcrypt
from flask_jwt_extended import JWTManager
from flask_mail import Mail

from .database import db,users





getUsers = Blueprint('getUsers', __name__)


@getUsers.route('/api/users', methods=['GET'])


def get_all_users():

    result = list(users.find())
    print(result)

    for user in result:
        user.pop('_id')
    
    return json.dumps(result)


@getUsers.route('/api/users/reset-password/<email>', methods=['GET','PUT'])

def get_user_email(email):

    if request.method == 'GET':

        result = list(users.find({"email" : email}))
        print(result)


        """

        import uuid
        stringId  = uuid.uuid4()
        print("Secure unique string id", stringId)
        
        result.insert_one(
            {
            "code" : stringId
            }
        )
        """

        for user in result:
            user.pop('_id')
        
        return json.dumps(result)

    elif request.method == 'PUT':

        def update_user_password(email):
            result = list(users.find({"email" : email}))
            print(result)



            

            import uuid
            stringId  = uuid.uuid4()
            print("Secure unique string id", stringId)
            
            result.update(
                {
                "code" : stringId
                }
            )
            
        

            for user in result:
                user.pop('_id')
            
            return json.dumps(result)

