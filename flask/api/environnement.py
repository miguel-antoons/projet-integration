from flask import Blueprint, request
from flask_jwt_extended import jwt_required, get_jwt_identity
import json

from .database import raspberry

envi = Blueprint('environnement', __name__)

@envi.route('/api/environnement', methods=['GET', 'POST', 'PUT', 'DELETE'])
@jwt_required()
def environnement():
    if request.method == 'GET':
        data = get_jwt_identity()

        # data client
        result = list(raspberry.find({"user" : data["Username"]}))
        for envi in result:
            envi.pop('_id')
            envi.pop('secret')
            envi.pop('user')
            envi.pop('status')

        return json.dumps(result)