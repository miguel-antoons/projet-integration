from flask import Blueprint, request, json
from .database import users, raspberry
from bson import ObjectId

app_raspberry = Blueprint('raspberry', __name__)


@app_raspberry.route('/api/raspberry', methods=['POST', 'PUT'])
def api_raspberry():
    if request.method == 'POST':
        return link_raspberry(request.json)
    elif request.method == 'PUT':
        return validate_raspberry(request.json)



def link_raspberry(payload):
    result = list(
        users.find(
            {"Username": payload['user']}
        )
    )

    if len(result):
        _id = raspberry.insert_one({
            "user": payload['user'],
            "location": "",
            "status": "waiting"
        })

        return json.dumps({
            "status": 1,
            "raspberry_id": str(_id.inserted_id)
        })

    return json.dumps({
        "status": 0
    })


def validate_raspberry(payload):
    raspberry.update_one(
        {
            "_id": ObjectId(payload['_id']),
            "user": payload['user']
        },
        {"$set": {
            "location": payload['location'],
            "status": payload['status']
        }}
    )

    return json.dumps({'Response': "Raspberry was updated"})


@app_raspberry.route('/api/raspberry/<user>', methods=['GET'])
def get_temporar_raspberry(user):
    temporar_raspberries = list(raspberry.find(
        {
            "user": user,
            "status": "waiting"
        }
    ))

    for item in temporar_raspberries:
        item["_id"] = str(item.get('_id'))

    return json.dumps(temporar_raspberries)
