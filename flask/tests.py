# Tests File

import unittest
from flask import Flask
from pymongo import MongoClient

app = Flask(__name__)

# Connection to a new mongo DB only to test
client = MongoClient("mongodb://localhost:27017/")
# Create this db to correctly link
db = client['SmartFridgeTests']
# Create this collection to correctly link
collection = db["Utilisateurs"]

# Test Class
class IntegrationTests(unittest.TestCase):
    # Initialisation of the db
    def init(self):
        with app.app_context():
            db.drop_all()
            db.create_all()

    # Test to get all users in the db (name, firstname and mail)
    def test_get_users(self):
        with app.app_context():
            pass

