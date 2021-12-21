from flask import Blueprint, jsonify
from flask_jwt_extended import jwt_required, get_jwt_identity
from .database import food, users

app_account = Blueprint('account', __name__)


@app_account.route('/api/account', methods=['DELETE'])
@jwt_required()
def delete_account():
    data = get_jwt_identity()
    food.remove({"Utilisateur": data["Username"]})
    users.remove({"Username": data["Username"]})

    return jsonify({'Response': "All Food of the user and the user account were removed"})
