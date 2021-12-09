from flask import Blueprint, request, json
from .database import users
from flask_jwt_extended import jwt_required, get_jwt_identity

app_location = Blueprint('location', __name__)


@app_location.route('/api/locations', methods=['GET', 'PUT'])
@jwt_required()
def locations():
    data = get_jwt_identity()

    if request.method == 'GET':
        return get_locations(data["Username"])
    elif request.method == 'PUT':
        return modify_locations(data["Username"], request.json)


def get_locations(user):
    response = json.dumps([{
        "Locations": list(
                users.find(
                    {"Username": user}
                )
            )[0]['Locations']
    }])

    print(response)
    return response


def modify_locations(user, json_data):
    users.update_one(
        {
            "Username": user
        },
        {"$set": {
            "Locations": json_data['Locations']
        }}
    )

    return json.dumps({"Response": "Locations has been saved"})
