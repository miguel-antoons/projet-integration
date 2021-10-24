from flask import Flask, request
from pymongo import MongoClient
from api import blueprints, database


def create_app():
    app = Flask(__name__)

    for blueprint in blueprints:
        app.register_blueprint(blueprint)

    return app

app = create_app()
