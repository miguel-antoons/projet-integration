from flask import Blueprint, request, jsonify, json
from database import db


app_hw = Blueprint('app_hw', __name__)


@app_hw.route('/')
def hello_world():
    return 'Hello world!'
