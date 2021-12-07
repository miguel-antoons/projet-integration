from flask import Blueprint, request, json
from .database import users

app_location = Blueprint('location', __name__)


@app_location.route('/api/locations/<user>', methods=['GET', 'PUT'])
def locations(user):
    if request.method == 'GET':
        return get_locations(user)
    elif request.method == 'PUT':
        return modify_locations(user, request.json)


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
