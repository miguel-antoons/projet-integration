from flask import Blueprint, request
from flask_jwt_extended import jwt_required, get_jwt_identity
import json
from bson import ObjectId
from .database import raspberry

envi = Blueprint('environnement', __name__)


@envi.route('/api/environnement', methods=['GET'])
@jwt_required()
def environnement():
    data = get_jwt_identity()
    # data client

    result = list(raspberry.find({"user": data["Username"]}))

    for envi in result:
        envi.pop('_id')
        envi.pop('secret')
        envi.pop('user')
        envi.pop('status')

    return json.dumps(result)


"""
*** API ROUTE FOR Temprature,Humidity,Light change  ***

 --- This route is the API to change Temprature,Humidity,Light  --

"""


@envi.route('/api/environnement/temhumlight', methods=['PUT'])
def temphumlight():

    requete = request.json
    id_raspberry = requete["ID_raspberry"]
    humidity = requete["Humidity"]
    light = requete["Light"]
    temperature = requete["Temperature"]
    date = requete["Date"]
    # Checks in the database if the id rasberry exists
    rasberryexist = list(raspberry.find({"_id": ObjectId(id_raspberry)}))


    if rasberryexist:

        # Update the Temprature,Humidity,Light via raspberry ID
        raspberry.update_one(
            {"_id": ObjectId(id_raspberry)},
            {"$set":
                {
                    "Temperature": temperature,
                    "Humidity": humidity,
                    "Light": light,
                    "Date": date

                }
            }

        )
        return json.dumps({'Response': "Raspberry exist and Temprature,Humidity,Light is update"})
    else:
        return json.dumps({'Response': "Raspberry dont exist"})
