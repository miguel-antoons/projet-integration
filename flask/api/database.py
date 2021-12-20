from pymongo import MongoClient

# in this file, the database configuration will be done

# this is a temporar configuration and will change over time
client = MongoClient("mongodb://localhost:27017/")
db = client['SmartFridge']
users = db["Users"]
food = db["Food"]
raspberry = db["raspberry"]



