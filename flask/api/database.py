from pymongo import MongoClient

# in this file, the database configuration will be done

# this is a temporar configuration and will change over time
client = MongoClient("mongodb://localhost:27017/")
db = client['SmartFridge']
users = db["Users"]
food = db["Food"]
new_user = db["NewUser"]
raspberry = db["raspberry"]


flask_jwt_auth = client['flask_jwt_auth']
flask_jwt_auth_coll = flask_jwt_auth["flask_jwt_auth"]