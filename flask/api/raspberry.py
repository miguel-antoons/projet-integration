from flask import Blueprint, request, json
from .database import users, raspberry
from bson import ObjectId
from flask_jwt_extended import jwt_required, get_jwt_identity
import random, string, secrets

app_raspberry = Blueprint('raspberry', __name__)



def api_raspberry():
    if request.method == 'POST':
        return link_raspberry(request.json)
    elif request.method == 'PUT':
        return validate_raspberry(request.json)

@app_raspberry.route('/api/raspberry', methods=['POST'])
def link_raspberry():
    payload = request.json
    secret_key = res = ''.join(
        secrets.choice(
            string.ascii_letters + string.digits + string.punctuation
        ) for x in range(150)
    )

    result = list(
        users.find(
            {"Username": payload['user']}
        )
    )

    if len(result):
        _id = raspberry.insert_one({
            "user": payload['user'],
            "location": "",
            "status": "waiting",
            "secret": secret_key
        })

        return json.dumps({
            "status": 1,
            "raspberry_id": str(_id.inserted_id),
            "secret": secret_key
        })

    return json.dumps({
        "status": 0
    })

@app_raspberry.route('/api/raspberry', methods=['PUT'])
@jwt_required()
def validate_raspberry():
    jwt_data = get_jwt_identity()
    payload = request.json
    raspberry.update_one(
        {
            "_id": ObjectId(payload['_id']),
            "user": jwt_data["Username"]
        },
        {"$set": {
            "location": payload['location'],
            "status": payload['status']
        }}
    )

    return json.dumps({'Response': "Raspberry was updated"})


@app_raspberry.route('/api/raspberry/<raspberry_id>', methods=['DELETE'])
def delete_raspberry(raspberry_id):
    raspberry.delete_one(
        {"_id": ObjectId(raspberry_id)}
    )
    return json.dumps({'Response': "Raspberry has been removed"})


@app_raspberry.route('/api/raspberry', methods=['GET'])
@jwt_required()
def get_temporar_raspberry():
    data = get_jwt_identity()
    temporar_raspberries = list(raspberry.find(
        {
            "user": data["Username"],
            "status": "waiting"
        }
    ))

    for item in temporar_raspberries:
        item["_id"] = str(item.get('_id'))

    return json.dumps(temporar_raspberries)
