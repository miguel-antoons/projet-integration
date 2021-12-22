from pymongo import MongoClient
import os

# in this file, the database configuration will be done

# this is a temporar configuration and will change over time
# client = MongoClient("mongodb://localhost:27017/")
MONGO_DB_PASSWORD = os.environ['MONGO_DB_PASSWORD']

if MONGO_DB_PASSWORD:
    client = MongoClient(
        host=os.environ['MONGO_DB_HOST'],
        port=int(os.environ['MONGO_DB_PORT']), 
        username=os.environ['MONGO_DB_USER'],
        password=os.environ['MONGO_DB_PASSWORD'],
        authSource=os.environ['MONGO_DB_AUTH']
    )

else:
    client = MongoClient(os.environ['MONGO_DB_HOST'])

db = client['SmartFridge']
users = db["Users"]
food = db["Food"]
raspberry = db["raspberry"]
