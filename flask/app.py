from flask import Flask, request
from pymongo import MongoClient
from api import blueprints, database


# app need to be outside the function for testing
app = Flask(__name__)




for blueprint in blueprints:
    app.register_blueprint(blueprint)



# Run debug mode
if __name__ == '__main__':
    app.run(debug=True)