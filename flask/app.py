from flask import Flask, request
from pymongo import MongoClient
from api import blueprints, database
from flask_jwt_extended import JWTManager
import os
import base64

# app need to be outside the function for testing
app = Flask(__name__)

# token manager
jwt_manager = JWTManager(app)
# JWT Config
app.config["JWT_SECRET_KEY"] = base64.urlsafe_b64decode(os.environ['JWT_SECRET_KEY'])




for blueprint in blueprints:
    app.register_blueprint(blueprint)



# Run debug mode
if __name__ == '__main__':
    app.run(debug=True)