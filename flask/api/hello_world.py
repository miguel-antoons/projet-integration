from flask import Blueprint

app_hw = Blueprint('app_hw', __name__)


@app_hw.route('/')
def hello_world():
    return 'Hello SmartFridge!'
